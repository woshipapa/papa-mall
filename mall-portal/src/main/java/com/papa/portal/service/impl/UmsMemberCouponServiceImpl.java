package com.papa.portal.service.impl;

import com.papa.mbg.model.SmsCoupon;
import com.papa.portal.dao.SmsHistoryDAO;
import com.papa.portal.domain.CartPromotionItem;
import com.papa.portal.domain.SmsCouponHistoryDetail;
import com.papa.portal.domain.SmsCouponStrategy;
import com.papa.portal.service.UmsMemberCouponService;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UmsMemberCouponServiceImpl implements UmsMemberCouponService {


    @Resource
    private SmsHistoryDAO smsHistoryDAO;
    @Override
    public List<SmsCouponHistoryDetail> listCart(Long memberId, List<CartPromotionItem> cartPromotionItemList, Integer type) {
        //获取当前用户所有可用的优惠券
        List<SmsCouponHistoryDetail> allCoupon = smsHistoryDAO.getDetailList(memberId);
        List<SmsCouponHistoryDetail> enableList = new ArrayList<>();
        List<SmsCouponHistoryDetail> disableList = new ArrayList<>();
        Date now = new Date();
        for(SmsCouponHistoryDetail s : allCoupon){
            SmsCoupon coupon = s.getCoupon();
            Integer useType = coupon.getUseType();
            SmsCouponStrategy strategy = SmsCouponStrategy.valueOfStrategy(useType);
            //判断这个优惠券是否适用于选中的购物车商品
            if(strategy.isCoupon(s,cartPromotionItemList,now)){
                enableList.add(s);
            }else{
                disableList.add(s);
            }
        }
        if(type.equals(1)) return enableList;
        return disableList;


    }
}
