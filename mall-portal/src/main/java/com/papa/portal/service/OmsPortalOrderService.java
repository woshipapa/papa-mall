package com.papa.portal.service;

import com.papa.portal.domain.ConfirmOrderResult;
import com.papa.portal.domain.OrderParam;

import java.util.List;
import java.util.Map;

public interface OmsPortalOrderService {

    public ConfirmOrderResult generateConfirm(List<Long> ids);

    public void cancelOrder(Long orderId);

    public Map<String,Object> generateOrder(OrderParam param);

    public void cancelTimeOutOrder();

    public void pay(Long orderId);

    public void delivery(Long orderId);
}
