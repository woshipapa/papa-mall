package com.papa.portal.service.impl;

import com.papa.mbg.mapper.UmsIntegrationConsumeSettingMapper;
import com.papa.mbg.model.UmsIntegrationConsumeSetting;
import com.papa.portal.service.UmsIntegrationConsumeSettingService;

import javax.annotation.Resource;

public class UmsIntegrationConsumeSettingServiceImpl implements UmsIntegrationConsumeSettingService {

    @Resource
    private UmsIntegrationConsumeSettingMapper consumeSettingMapper;

    @Override
    public UmsIntegrationConsumeSetting getConsumeSetting() {
        return consumeSettingMapper.selectByPrimaryKey(1L);
    }
}
