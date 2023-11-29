package com.papa.portal.service.impl;

import com.papa.mbg.mapper.OmsOrderSettingMapper;
import com.papa.mbg.model.OmsOrderSetting;
import com.papa.mbg.model.OmsOrderSettingExample;
import com.papa.portal.component.CancelOrderSender;
import com.papa.portal.service.OmsOrderMessageService;

import javax.annotation.Resource;
import java.util.List;

public class OmsOrderMessageServiceImpl implements OmsOrderMessageService {

    @Resource
    private OmsOrderSettingMapper orderSettingMapper;
    @Resource
    private CancelOrderSender orderSender;
    @Override
    public void sendDelayMessage(Long orderId) {
        OmsOrderSettingExample orderSettingExample = new OmsOrderSettingExample();
        List<OmsOrderSetting> orderSettings = orderSettingMapper.selectByExample(orderSettingExample);
        OmsOrderSetting setting = orderSettings.get(0);
        long delayTime = setting.getNormalOrderOvertime() * 60 * 1000;
        orderSender.sendMessage(orderId,delayTime);

    }
}
