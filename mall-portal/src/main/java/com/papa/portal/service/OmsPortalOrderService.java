package com.papa.portal.service;

import com.papa.common.api.CommonPage;
import com.papa.common.api.CommonResult;
import com.papa.portal.domain.ConfirmOrderResult;
import com.papa.portal.domain.OmsOrderDetail;
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

    public CommonPage<OmsOrderDetail> list(Integer status, Integer pageNum, Integer pageSize);

    public OmsOrderDetail detail(Long orderId);
    public void deleteOrder(Long orderId);

    public void confirmReceiveOrder(Long orderId);
}
