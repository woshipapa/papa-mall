package com.papa.portal.domain;
//优惠券使用平台
public enum CouponPlatform {
    ALL(0, "全部"),
    MOBILE(1, "移动"),
    PC(2, "PC");

    private final int value;
    private final String description;

    CouponPlatform(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }
}
