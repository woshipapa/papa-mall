package com.papa.portal.domain;

import com.papa.mbg.model.SmsCoupon;

import javax.xml.crypto.Data;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public enum SmsCouponStrategy {
GLOBAL(0){
    @Override
    public boolean isCoupon(SmsCouponHistoryDetail couponHistoryDetail, List<CartPromotionItem> items,Date now) {
        SmsCoupon coupon = couponHistoryDetail.getCoupon();
        Date endTime = coupon.getEndTime();
        BigDecimal minPoint = coupon.getMinPoint();
        BigDecimal totalPrice = calTotalAmount(items);
        if(now.before(endTime)&&totalPrice.subtract(minPoint).intValue()>=0){
            return true;
        }
        return false;
    }
    private BigDecimal calTotalAmount(List<CartPromotionItem> items){
        BigDecimal total = new BigDecimal(0);
        for(CartPromotionItem item : items){
            BigDecimal price = item.getPrice().subtract(item.getReducePrice());
            price = price.multiply(new BigDecimal(item.getQuantity()));
            total = total.add(price);
        }
        return total;
    }
},
PRODUCT_SPECIFIED(2){
    @Override
    public boolean isCoupon(SmsCouponHistoryDetail couponHistoryDetail, List<CartPromotionItem> items, Date now) {
        SmsCoupon coupon = couponHistoryDetail.getCoupon();
        Date endTime = coupon.getEndTime();
        BigDecimal minPoint = coupon.getMinPoint();
        //获取到用户所拥有的优惠券中相关商品的id集合
        List<Long> relationProductIds = couponHistoryDetail.getProductRelationList().stream().map(it->it.getProductId()).collect(Collectors.toList());
        BigDecimal totalPrice = calTotalAmount(items,relationProductIds);
        if(now.before(endTime)&&totalPrice.subtract(minPoint).intValue()>=0){
            return true;
        }
        return false;
    }
    private BigDecimal calTotalAmount(List<CartPromotionItem> items,List<Long> ids){
        BigDecimal total = new BigDecimal(0);
        for(CartPromotionItem item : items){
            if(ids.contains(item.getProductId())){
                BigDecimal price = item.getPrice().subtract(item.getReducePrice());
                price = price.multiply(new BigDecimal(item.getQuantity()));
                total = total.add(price);
            }
        }
        return total;
    }
},
CATEGORY_SPECIFIED(1){
    @Override
    public boolean isCoupon(SmsCouponHistoryDetail couponHistoryDetail, List<CartPromotionItem> items, Date now) {
        SmsCoupon coupon = couponHistoryDetail.getCoupon();
        Date endTime = coupon.getEndTime();
        BigDecimal minPoint = coupon.getMinPoint();
        List<Long> relationCategoryIds = couponHistoryDetail.getProductCategoryRelations().stream().map(it->it.getProductCategoryId()).collect(Collectors.toList());
        BigDecimal total = calTotalAmount(items,relationCategoryIds);
        if(now.before(endTime) && total.subtract(minPoint).intValue()>=0){
            return true;
        }
        return false;
    }
    private BigDecimal calTotalAmount(List<CartPromotionItem> items,List<Long> ids){
        BigDecimal total = new BigDecimal(0);
        for(CartPromotionItem item : items){
            if(ids.contains(item.getProductCategoryId())){
                BigDecimal price = item.getPrice().subtract(item.getReducePrice());
                price = price.multiply(new BigDecimal(item.getQuantity()));
                total = total.add(price);
            }
        }
        return total;
    }
};
    private Integer useType;
    SmsCouponStrategy(Integer useType){
        this.useType = useType;
    }

    public static SmsCouponStrategy valueOfStrategy(Integer type){
        for(SmsCouponStrategy strategy : values()){
            if(strategy.useType.equals(type)){
                return strategy;
            }
        }
        return null;
    }

    public abstract boolean isCoupon(SmsCouponHistoryDetail couponHistoryDetail, List<CartPromotionItem> items, Date now);

}
