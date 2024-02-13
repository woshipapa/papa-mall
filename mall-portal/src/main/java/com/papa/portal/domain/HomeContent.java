package com.papa.portal.domain;

import lombok.Getter;

@Getter
public enum HomeContent {
    ALL(0, "全部"),
    BRAND(1, "品牌"),
    NEW(2, "新品推荐"),
    HOT(3, "人气推荐"),
    AD(4, "轮播广告");

    private final int value;
    private final String description;

    HomeContent(int value, String description) {
        this.value = value;
        this.description = description;
    }
}
