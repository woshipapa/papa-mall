package com.papa.portal.service;

import com.papa.portal.domain.CartPromotionItem;
import com.papa.portal.domain.SmsCouponHistoryDetail;

import java.util.List;

public interface UmsMemberCouponService {


    public List<SmsCouponHistoryDetail> listCart(Long memberId,List<CartPromotionItem> cartPromotionItemList,Integer type);
}
