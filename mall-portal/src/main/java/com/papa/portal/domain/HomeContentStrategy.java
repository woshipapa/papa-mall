package com.papa.portal.domain;

import com.papa.portal.service.impl.HomeRecommendServiceImpl;
//使用函数式接口
public enum HomeContentStrategy {
    ALL(((result, recommendService) -> {
        recommendService.getRecommendBrandList(result);
        recommendService.getRecommendProducts(result);
        recommendService.getHotProducts(result);
        recommendService.getHomeAdvertiseList(result);
    })),
    BRAND((result, service) -> service.getRecommendBrandList(result)),
    NEW((result, service) -> service.getRecommendProducts(result)),
    HOT((result, service) -> service.getHotProducts(result)),
    AD((result, service) -> service.getHomeAdvertiseList(result));


     HomeContentStrategy(ContentLoader loader){
        this.loader = loader;
    }

    public void loadContent(HomeContentResult result, HomeRecommendServiceImpl service) {
        loader.load(result, service);
    }

    private final ContentLoader loader;
}
