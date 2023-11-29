package com.papa.portal.design;

public enum OrderStatus {
    PENDING_PAYMENT(0, "待付款"),
    PENDING_SHIPMENT(1, "待发货"),
    SHIPPED(2, "已发货"),
    COMPLETED(3, "已完成"),
    CLOSED(4, "已关闭"),
    INVALID(5, "无效订单");

    OrderStatus(Integer code,String message){
        this.value = code;
        this.message = message;
    }

    private Integer value;
    private String message;

    public String getMessage() {
        return message;
    }

    public Integer getValue() {
        return value;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
