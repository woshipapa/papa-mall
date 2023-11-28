package com.papa.portal.service;

import com.papa.portal.domain.ConfirmOrderResult;

import java.util.List;

public interface OmsPortalOrderService {

    public ConfirmOrderResult generateConfirm(List<Long> ids);
}
