package com.papa.portal.domain;

public enum QueueEnum {
    QUEUE_TTL_ORDER_CANCEL("mall.order.direct.ttl","mall.order.cancel.ttl","mall.order.cancel.ttl"),

    QUEUE_ORDER_CANCEL("mall.order.direct","mall.order.cancel","mall.order.cancel");


    private String exchange;

    private String routeKey;

    private String queueName;

    QueueEnum(String exchange, String routeKey, String queueName) {
        this.exchange = exchange;
        this.routeKey = routeKey;
        this.queueName = queueName;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getRouteKey() {
        return routeKey;
    }

    public void setRouteKey(String routeKey) {
        this.routeKey = routeKey;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }
}
