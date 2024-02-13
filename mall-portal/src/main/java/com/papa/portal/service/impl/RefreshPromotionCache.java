package com.papa.portal.service.impl;

import com.github.benmanes.caffeine.cache.Cache;
import com.papa.portal.config.PromotionRedisKey;
import com.papa.portal.domain.FlashPromotionProduct;
import com.papa.portal.domain.HomeContentResult;
import com.papa.portal.service.HomeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

//异步定时任务刷新本地缓存
@Service
@Slf4j
public class RefreshPromotionCache {

    @Autowired
    private HomeService homeService;

    @Resource
    @Qualifier("promotion")
    private Cache<String, HomeContentResult> promotionCache;

    @Resource
    @Qualifier("promotionBak")
    private Cache<String, HomeContentResult> promotionCacheBak;

    @Resource
    @Qualifier("secKill")
    private Cache<String, List<FlashPromotionProduct>> secKillCache;

    @Resource
    @Qualifier("secKillBak")
    private Cache<String,List<FlashPromotionProduct>> secKillCacheBak;

    @Autowired
    private PromotionRedisKey promotionRedisKey;

    @Async
    @Scheduled(initialDelay=5000*60,fixedDelay = 1000*60)
    public void refreshCache(){
        if(promotionRedisKey.isAllowLocalCache()){
            log.info("检查本地缓存[promotionCache] 是否需要刷新...");
            final String brandKey = promotionRedisKey.getBrandKey();
            if(null == promotionCache.getIfPresent(brandKey)||null == promotionCacheBak.getIfPresent(brandKey)){
                log.info("本地缓存[promotionCache] 需要刷新");
                HomeContentResult result = homeService.getFromRemote();
                if(null != result){
                    if(null == promotionCache.getIfPresent(brandKey)) {
                        promotionCache.put(brandKey,result);
                        log.info("刷新本地缓存[promotionCache] 成功");
                    }
                    if(null == promotionCacheBak.getIfPresent(brandKey)) {
                        promotionCacheBak.put(brandKey,result);
                        log.info("刷新本地缓存[promotionCache] 成功");
                    }
                }else{
                    log.warn("从远程获得[promotionCache] 数据失败");
                }
            }
        }
    }

    @Async
    @Scheduled(initialDelay=30,fixedDelay = 30)
    public void refreshSecKillCache(){
        final String secKillKey = promotionRedisKey.getSecKillKey();
        if(null == secKillCache.getIfPresent(secKillKey)||null == secKillCacheBak.getIfPresent(secKillKey)){
            List<FlashPromotionProduct> secKills = homeService.getSecKillFromRemote();
            if(null != secKills){
                if(null == secKillCache.getIfPresent(secKillKey)) {
                    secKillCache.put(secKillKey,secKills);
                }
                if(null == secKillCacheBak.getIfPresent(secKillKey)) {
                    secKillCacheBak.put(secKillKey,secKills);
                }
            }else{
                log.warn("从远程获得[SecKillCache] 数据失败");
            }
        }
    }
}
