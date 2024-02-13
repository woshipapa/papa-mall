package com.papa.portal.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.papa.common.api.CommonResult;
import com.papa.mbg.mapper.SmsHomeAdvertiseMapper;
import com.papa.mbg.mapper.SmsHomeBrandMapper;
import com.papa.mbg.mapper.SmsHomeNewProductMapper;
import com.papa.mbg.mapper.SmsHomeRecommendProductMapper;
import com.papa.mbg.model.*;
import com.papa.portal.config.PromotionRedisKey;
import com.papa.portal.domain.HomeContent;
import com.papa.portal.domain.HomeContentResult;
import com.papa.portal.domain.HomeContentStrategy;
import com.papa.portal.service.HomeRecommendService;
import com.papa.portal.util.RedisOpsExtUtil;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@Slf4j
public class HomeRecommendServiceImpl implements HomeRecommendService {

    @Resource
    private RedisOpsExtUtil redisOpsExtUtil;

    @Resource
    private PromotionRedisKey promotionRedisKey;

    @Resource
    private SmsHomeBrandMapper homeBrandMapper;

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private Config redissionConfig;

    private RedissonClient redissonClient;

    @Resource
    private SmsHomeRecommendProductMapper recommendProductMapper;

    @Resource
    private SmsHomeNewProductMapper newProductMapper;

    @Resource
    private SmsHomeAdvertiseMapper advertiseMapper;

    @PostConstruct
    public void initRedisson(){
         this.redissonClient = Redisson.create(redissionConfig);
    }

    /*推荐内容类型:0->全部；1->品牌；2->新品推荐；3->人气推荐;4->轮播广告*/
    @Override
    public HomeContentResult content(Integer type) {
        HomeContentResult result = new HomeContentResult();
//        if(HomeContent.ALL.getValue() == type || HomeContent.BRAND.getValue() == type){
//            getRecommendBrandList(result);
//        }
//        if(HomeContent.ALL.getValue() == type || HomeContent.NEW.getValue() == type){
//            getRecommendProducts(result);
//        }
//        if(HomeContent.ALL.getValue() == type || HomeContent.HOT.getValue() == type){
//            getHotProducts(result);
//        }
//        if(HomeContent.ALL.getValue() == type || HomeContent.AD.getValue() == type){
//            getHomeAdvertiseList(result);
//        }
        HomeContentStrategy contentStrategy = HomeContentStrategy.values()[type];
        contentStrategy.loadContent(result,this);
        return result;
    }

    public void getRecommendBrandList(HomeContentResult result){
        String brandKey = promotionRedisKey.getBrandKey();
        List<PmsBrand> brandList = redisOpsExtUtil.getListAll(brandKey, PmsBrand.class);
        if(CollUtil.isEmpty(brandList)) {
            RLock lock = redissonClient.getLock("lock:" + brandKey);
            boolean isLocked = false;
            try {
                isLocked = lock.tryLock(10,5, TimeUnit.SECONDS);
                if (isLocked) {
                    //isLocked为true，表明拿到锁了
                    //再次进行检查redis缓存中是否已经有记录了，避免缓存穿透的现象
                    brandList = redisOpsExtUtil.getListAll(brandKey, PmsBrand.class);
                    if (CollUtil.isEmpty(brandList)) {
                        SmsHomeBrandExample homeBrandExample = new SmsHomeBrandExample();
                        homeBrandExample.createCriteria().andRecommendStatusEqualTo(1);
                        List<SmsHomeBrand> smsHomeBrands = homeBrandMapper.selectByExample(homeBrandExample);
                        List<Long> brandIds = smsHomeBrands.stream().map(SmsHomeBrand::getBrandId).collect(Collectors.toList());
                        ResponseEntity<CommonResult<List<PmsBrand>>> responseEntity = restTemplate.exchange(
                                "/brand/list/ids",
                                HttpMethod.POST,
                                new HttpEntity<>(brandIds),
                                new ParameterizedTypeReference<CommonResult<List<PmsBrand>>>() {
                                });
                        CommonResult<List<PmsBrand>> commonResult = responseEntity.getBody();
                        List<PmsBrand> brands = commonResult.getData();
                        redisOpsExtUtil.putListAllRight(brandKey, brands);
                        log.info("品牌推荐信息存入缓存，键{}", brandKey);
                        result.setBrandList(brands);
                    } else {
                        log.info("品牌推荐信息已在缓存，键{}", brandKey);
                        result.setBrandList(brandList);
                    }
                }
            }catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }
            finally {
                if(isLocked&&lock.isHeldByCurrentThread())
                    lock.unlock();
            }
        }
    }



    public void getRecommendProducts(HomeContentResult result) {
        String key = promotionRedisKey.getRecProductKey();
        List<PmsProduct> productList = redisOpsExtUtil.getListAll(key, PmsProduct.class);
        if (CollUtil.isEmpty(productList)) {
            RLock lock = redissonClient.getLock("lock:" + key);
            boolean isLocked = false;
            try {
                isLocked = lock.tryLock(10, 5, TimeUnit.SECONDS);
                if (isLocked) {
                    productList = redisOpsExtUtil.getListAll(key, PmsProduct.class);
                    if (CollUtil.isEmpty(productList)) {
                        // 从数据库加载推荐产品列表
                        SmsHomeRecommendProductExample example = new SmsHomeRecommendProductExample();
                        example.createCriteria().andRecommendStatusEqualTo(1);
                        List<SmsHomeRecommendProduct> smsHomeRecommendProducts = recommendProductMapper.selectByExample(example);
                        List<Long> ids = smsHomeRecommendProducts.stream().map(SmsHomeRecommendProduct::getProductId).collect(Collectors.toList());
                        ResponseEntity<CommonResult<List<PmsProduct>>> response = restTemplate.exchange(
                                "/product/batch",
                                HttpMethod.POST,
                                new HttpEntity<>(ids),
                                new ParameterizedTypeReference<CommonResult<List<PmsProduct>>>() {
                                }
                        );
                        CommonResult<List<PmsProduct>> body = response.getBody();
                        productList = body.getData();
                        // 更新Redis缓存
                        redisOpsExtUtil.putListAllRight(key, productList);
                        log.info("推荐产品信息存入缓存，键{}", key);
                        result.setNewProductList(productList);
                    } else {
                        log.info("推荐产品信息已在缓存，键{}", key);
                        result.setNewProductList(productList);
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                if (isLocked && lock.isHeldByCurrentThread()) {
                    lock.unlock();
                }
            }
        } else {
            log.info("推荐产品信息已在缓存，键{}", key);
            result.setNewProductList(productList);
        }
    }

    public void getHotProducts(HomeContentResult result) {
        String key = promotionRedisKey.getNewProductKey();
        List<PmsProduct> productList = redisOpsExtUtil.getListAll(key, PmsProduct.class);
        if (CollUtil.isEmpty(productList)) {
            RLock lock = redissonClient.getLock("lock:" + key);
            boolean isLocked = false;
            try {
                isLocked = lock.tryLock(10, 5, TimeUnit.SECONDS);
                if (isLocked) {
                    productList = redisOpsExtUtil.getListAll(key, PmsProduct.class);
                    if (CollUtil.isEmpty(productList)) {
                        // 从数据库加载热门产品列表
                        SmsHomeNewProductExample example = new SmsHomeNewProductExample();
                        example.createCriteria().andRecommendStatusEqualTo(1); // 假设1表示热门状态
                        List<SmsHomeNewProduct> smsHomeHotProducts = newProductMapper.selectByExample(example);
                        List<Long> ids = smsHomeHotProducts.stream().map(SmsHomeNewProduct::getProductId).collect(Collectors.toList());
                        ResponseEntity<CommonResult<List<PmsProduct>>> response = restTemplate.exchange(
                                "/product/batch",
                                HttpMethod.POST,
                                new HttpEntity<>(ids),
                                new ParameterizedTypeReference<CommonResult<List<PmsProduct>>>() {
                                }
                        );
                        CommonResult<List<PmsProduct>> body = response.getBody();
                        productList = body.getData();
                        // 更新Redis缓存
                        redisOpsExtUtil.putListAllRight(key, productList);
                        log.info("热门产品信息存入缓存，键{}", key);
                        result.setHotProductList(productList);
                    } else {
                        log.info("热门产品信息已在缓存，键{}", key);
                        result.setHotProductList(productList);
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                if (isLocked && lock.isHeldByCurrentThread()) {
                    lock.unlock();
                }
            }
        } else {
            log.info("热门产品信息已在缓存，键{}", key);
            result.setHotProductList(productList);
        }
    }


    public void getHomeAdvertiseList(HomeContentResult result) {
        String key = promotionRedisKey.getHomeAdvertiseKey();
        List<SmsHomeAdvertise> advertiseList = redisOpsExtUtil.getListAll(key, SmsHomeAdvertise.class);
        if (CollUtil.isEmpty(advertiseList)) {
            RLock lock = redissonClient.getLock("lock:" + key);
            boolean isLocked = false;
            try {
                isLocked = lock.tryLock(10, 5, TimeUnit.SECONDS);
                if (isLocked) {
                    advertiseList = redisOpsExtUtil.getListAll(key, SmsHomeAdvertise.class);
                    if (CollUtil.isEmpty(advertiseList)) {
                        // 从数据库加载首页广告列表
                        SmsHomeAdvertiseExample example = new SmsHomeAdvertiseExample();
                        example.createCriteria().andStatusEqualTo(1); // 假设1表示有效状态
                        List<SmsHomeAdvertise> smsHomeAdvertises = advertiseMapper.selectByExample(example);
                        // 更新Redis缓存
                        redisOpsExtUtil.putListAllRight(key, smsHomeAdvertises);
                        log.info("首页广告信息存入缓存，键{}", key);
                        result.setAdvertiseList(smsHomeAdvertises);
                    } else {
                        log.info("首页广告信息已在缓存，键{}", key);
                        result.setAdvertiseList(advertiseList);
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                if (isLocked && lock.isHeldByCurrentThread()) {
                    lock.unlock();
                }
            }
        } else {
            log.info("首页广告信息已在缓存，键{}", key);
            result.setAdvertiseList(advertiseList);
        }
    }



}
