package com.papa.portal.service.impl;

import com.papa.portal.design.OmsPortalOrderFacade;
import com.papa.portal.domain.ConfirmOrderResult;
import com.papa.portal.domain.OrderParam;
import com.papa.portal.service.OmsPortalOrderService;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class OmsPortalOrderServiceImpl implements OmsPortalOrderService {
    @Resource
    private OmsPortalOrderFacade orderFacade;

    @Override
    public ConfirmOrderResult generateConfirm(List<Long> ids) {
        return orderFacade.generateConfirmOrder(ids);
    }

    @Override
    public void cancelOrder(Long orderId) {

    }


    public Map<String, Object> generateOrder(OrderParam orderParam){
        return orderFacade.generateOrder(orderParam);
    }
}
