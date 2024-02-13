package com.papa.portal.config;

import org.redisson.api.RLock;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfig {


    @Value("${redis.url}")
    private String url;


    @Bean
    public Config redissonConfig(){
        Config config = new Config();
        config.useSingleServer().setAddress(url);
        return config;
    }

}
