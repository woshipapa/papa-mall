package com.papa.portal.service.impl;

import com.papa.mbg.mapper.UmsMemberReceiveAddressMapper;
import com.papa.mbg.model.UmsMemberReceiveAddress;
import com.papa.mbg.model.UmsMemberReceiveAddressExample;
import com.papa.portal.service.UmsMemberReceiveAddressService;

import javax.annotation.Resource;
import java.util.List;

public class UmsMemberReceiveAddressServiceImpl implements UmsMemberReceiveAddressService {
    @Resource
    private UmsMemberReceiveAddressMapper memberReceiveAddressMapper;

    @Override
    public List<UmsMemberReceiveAddress> getReceiveAddressList(Long memberId) {
        UmsMemberReceiveAddressExample addressExample = new UmsMemberReceiveAddressExample();
        addressExample.createCriteria().andMemberIdEqualTo(memberId);
        return memberReceiveAddressMapper.selectByExample(addressExample);
    }
}
