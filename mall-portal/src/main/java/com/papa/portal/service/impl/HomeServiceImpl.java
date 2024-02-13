package com.papa.portal.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.github.benmanes.caffeine.cache.Cache;
import com.papa.mbg.model.PmsBrand;
import com.papa.mbg.model.PmsProduct;
import com.papa.mbg.model.SmsHomeAdvertise;
import com.papa.portal.config.PromotionRedisKey;
import com.papa.portal.domain.FlashPromotionProduct;
import com.papa.portal.domain.HomeContentResult;
import com.papa.portal.service.HomeRecommendService;
import com.papa.portal.service.HomeService;
import com.papa.portal.util.RedisOpsExtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
@Service
@Slf4j
public class HomeServiceImpl implements HomeService {


    @Resource
    private PromotionRedisKey redisKey;

    @Resource
    @Qualifier("promotion")
    private Cache<String,HomeContentResult> promotionCache;


    @Resource
    @Qualifier("promotionBak")
    private Cache<String,HomeContentResult> promotionBakCache;

    @Resource
    private RedisOpsExtUtil redisOpsExtUtil;


    @Resource
    private HomeRecommendService recommendService;

    @Resource
    @Qualifier("secKill")
    private Cache<String,List<FlashPromotionProduct>> secKillCache;

    @Resource
    @Qualifier("secKillBak")
    private Cache<String,List<FlashPromotionProduct>> secKillBakCache;
    @Override
    public HomeContentResult recommendContent() {
        String brandKey = redisKey.getBrandKey();
        boolean allowLocalCache = redisKey.isAllowLocalCache();
        HomeContentResult contentResult = allowLocalCache?promotionCache.getIfPresent(brandKey):null;
        if(contentResult==null){
            //第一级本地缓存中没有，就去第二级本地缓存读取
            contentResult = allowLocalCache?promotionBakCache.getIfPresent(brandKey):null;
        }
        //此时就要从redis中拿取了
        if(contentResult == null){
            log.warn("从本地缓存中获取推荐品牌和商品失败，可能出错或禁用了本地缓存[allowLocalCache = {}]",allowLocalCache);
            contentResult = getFromRemote();
            //因为这里说明本地缓存中没有存储这个键值对，所以这里要设置
            if(contentResult != null){
                promotionCache.put(brandKey,contentResult);
                promotionBakCache.put(brandKey,contentResult);
            }
        }
        String secKey = redisKey.getSecKillKey();
        List<FlashPromotionProduct> secKills = secKillCache.getIfPresent(secKey);
        if(CollUtil.isEmpty(secKills)){
            secKills = secKillBakCache.getIfPresent(secKey);
        }
        if(CollUtil.isEmpty(secKills)){
            /*极小的概率出现本地两个缓存同时失效的问题，
            从远程获取时，只从Redis缓存中获取，不从营销微服务中获取，
            避免秒杀的流量冲垮营销微服务*/
            secKills = getSecKillFromRemote();
            if(!CollUtil.isEmpty(secKills)) {
                secKillCache.put(secKey, secKills);
                secKillBakCache.put(secKey, secKills);
            }else{
                secKills = new ArrayList<FlashPromotionProduct>();
            }
        }else{
            secKillCache.put(secKey,secKills);
        }
        contentResult.setHomeFlashPromotion(secKills);
        //暂时还没有实现
        contentResult.setSubjectList(new ArrayList<>());
        return contentResult;
    }

    @Override
    public void preheatCache() {
        try{
            String secKillKey = redisKey.getSecKillKey();
            List<FlashPromotionProduct> promotionProducts = getSecKillFromRemote();
            secKillCache.put(secKillKey,promotionProducts);
            secKillBakCache.put(secKillKey,promotionProducts);
            log.info("秒杀数据缓存预热成功");
        }catch (Exception e){
            log.error("秒杀数据缓存预热失败：",e);
        }

        try{
            if(redisKey.isAllowLocalCache()){
                String brandKey = redisKey.getBrandKey();
                HomeContentResult result = getFromRemote();
                promotionCache.put(brandKey,result);
                promotionBakCache.put(brandKey,result);
                log.info("首页信息缓存预热成功");
            }
        }catch (Exception e){
            log.error("首页信息缓存预热失败:",e);
        }



    }

    public List<FlashPromotionProduct> getSecKillFromRemote(){
        List<FlashPromotionProduct> result = redisOpsExtUtil.getListAll(redisKey.getSecKillKey(),
                FlashPromotionProduct.class);
        return result;
    }

    public HomeContentResult getFromRemote(){
        List<PmsBrand> recommendBrandList = null;
        List<SmsHomeAdvertise> smsHomeAdvertises = null;
        List<PmsProduct> newProducts  = null;
        List<PmsProduct> recommendProducts  = null;
        HomeContentResult result = null;
        //从redis中获取
        if(redisKey.isAllowRemoteCache()){
            recommendBrandList = redisOpsExtUtil.getListAll(redisKey.getBrandKey(),PmsBrand.class);
            smsHomeAdvertises = redisOpsExtUtil.getListAll(redisKey.getHomeAdvertiseKey(), SmsHomeAdvertise.class);
            newProducts = redisOpsExtUtil.getListAll(redisKey.getNewProductKey(),PmsProduct.class);
            recommendProducts = redisOpsExtUtil.getListAll(redisKey.getRecProductKey(),PmsProduct.class);
        }
        if(CollUtil.isEmpty(recommendBrandList)||
        CollUtil.isEmpty(smsHomeAdvertises)||
        CollUtil.isEmpty(newProducts)||
        CollUtil.isEmpty(recommendProducts)){
            //如果任何一个为空，这里就都要从数据库中查询了
            result = recommendService.content(0);
        }else{
            result = new HomeContentResult();
            result.setAdvertiseList(smsHomeAdvertises);
            result.setBrandList(recommendBrandList);
            result.setNewProductList(newProducts);
            result.setHotProductList(recommendProducts);
        }
        return result;
    }
}
