package com.papa.portal.service;

import com.papa.mbg.model.UmsMemberReceiveAddress;

import java.util.List;

public interface UmsMemberReceiveAddressService {

    public List<UmsMemberReceiveAddress> getReceiveAddressList(Long memberId);

}
