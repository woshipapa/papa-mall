package com.papa.portal.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.papa.portal.domain.FlashPromotionProduct;
import com.papa.portal.domain.HomeContentResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@Configuration
public class CaffeineCacheConfig {

    @Value("${caffeine.cache.expireAfterWritePromotion}")
    private long expireAfterWritePromotion;

    @Value("${caffeine.cache.expireAfterAccess}")
    private long expireAfterAccess;


    @Value("caffeine.cache.expireAfterWriteKill")
    private long expireAfterWriteKill;

    @Value("caffeine.cache.expireAfterWriteKillBak")
    private long expireAfterWriteKillBak;
    @Value("${caffeine.cache.initialCapacity}")
    private Integer initialCapacity;

    @Value("${caffeine.cache.maximumSize}")
    private Integer maximumSize;

    @Value("${caffeine.cache.promotionRand}")
    private Integer promotionRand;

    @Value("${caffeine.cache.secKillRand}")
    private Integer secKillRand;
    @Bean(value = "promotion")
    public Cache<String, HomeContentResult> promotionCache(){
        int random = ThreadLocalRandom.current().nextInt(promotionRand);
        return Caffeine.newBuilder()
                .expireAfterWrite(expireAfterWritePromotion +random, TimeUnit.MINUTES)
                .initialCapacity(initialCapacity)
                .maximumSize(maximumSize)
                .build();
    }

    @Bean(value = "promotionBak")
    public Cache<String,HomeContentResult> promotionBakCache(){
        int random = ThreadLocalRandom.current().nextInt(promotionRand);
        return Caffeine.newBuilder()
                .expireAfterAccess(expireAfterAccess+random,TimeUnit.MINUTES)
                .initialCapacity(initialCapacity)
                .maximumSize(maximumSize)
                .build();
    }

    @Bean(value = "secKill")
    public Cache<String, List<FlashPromotionProduct>> secKillCache(){
        int random = ThreadLocalRandom.current().nextInt(secKillRand);
        return Caffeine.newBuilder()
                .expireAfterWrite(expireAfterWriteKill,TimeUnit.MILLISECONDS)
                .initialCapacity(initialCapacity)
                .maximumSize(maximumSize)
                .build();
    }

    @Bean(value = "secKillBak")
    public Cache<String,HomeContentResult> secKillBakCache(){
        int random = ThreadLocalRandom.current().nextInt(secKillRand);
        return Caffeine.newBuilder()
                .expireAfterWrite(expireAfterWriteKillBak+random,TimeUnit.MILLISECONDS)
                .initialCapacity(initialCapacity)
                .maximumSize(maximumSize)
                .build();
    }




}
