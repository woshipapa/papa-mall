package com.papa.portal.domain;

import com.papa.portal.service.impl.HomeRecommendServiceImpl;
import com.papa.portal.service.impl.HomeServiceImpl;

@FunctionalInterface
public interface ContentLoader {

    void load(HomeContentResult result, HomeRecommendServiceImpl recommendService);
}
