package com.papa.portal.service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class PreHeatCache implements CommandLineRunner {


    @Resource
    private HomeService homeService;
    @Override
    public void run(String... args) throws Exception {
        homeService.preheatCache();
    }
}
