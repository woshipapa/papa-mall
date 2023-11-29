package com.papa.portal.design;

public enum CouponStatus {
    UNUSED(0),
    USED(1),
    EXPIRED(2);

    public Integer value;

    CouponStatus(Integer value){
        this.value = value;
    }

    public Integer getValue(){
        return value;
    }

    public static CouponStatus fromValue(Integer value){
        CouponStatus[] statuses = CouponStatus.values();
        for(CouponStatus status : statuses){
            if(value == status.value) return status;
        }
        return null;
    }

}
