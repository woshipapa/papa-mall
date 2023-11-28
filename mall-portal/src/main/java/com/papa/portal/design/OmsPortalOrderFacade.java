package com.papa.portal.design;

import com.papa.common.exception.Asserts;
import com.papa.mbg.mapper.PmsSkuStockMapper;
import com.papa.mbg.model.*;
import com.papa.portal.domain.CartPromotionItem;
import com.papa.portal.domain.ConfirmOrderResult;
import com.papa.portal.domain.OrderParam;
import com.papa.portal.domain.SmsCouponHistoryDetail;
import com.papa.portal.service.*;
import javafx.scene.layout.BorderPane;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Component
public class OmsPortalOrderFacade {


    @Resource
    private UmsMemberService umsMemberService;

    @Resource
    private OmsCartItemService omsCartItemService;

    @Resource
    private UmsMemberReceiveAddressService memberReceiveAddressService;

    @Resource
    private UmsMemberCouponService umsMemberCouponService;


    @Resource
    private UmsIntegrationConsumeSettingService umsIntegrationConsumeSettingService;

    private CompletableFuture<List<CartPromotionItem>> getCartPromotionItem(Long memberId,List<Long> ids){
        return CompletableFuture.supplyAsync(()->{
            return omsCartItemService.listPromotion(memberId,ids);
        });
    }

    private CompletableFuture<List<UmsMemberReceiveAddress>> getMemberReceiveAddress(Long memberId){
        return CompletableFuture.supplyAsync(()->{
            return memberReceiveAddressService.getReceiveAddressList(memberId);
        });
    }
    private CompletableFuture<List<SmsCouponHistoryDetail>> getCouponHistoryDetailAsync(Long memberId,List<CartPromotionItem> items,Integer type){
        return CompletableFuture.supplyAsync(()->{
           return umsMemberCouponService.listCart(memberId,items,type);
        });
    }

    private CompletableFuture<UmsIntegrationConsumeSetting> getIntegrationConsumeSettingAsync(){
        return CompletableFuture.supplyAsync(()->{
            return umsIntegrationConsumeSettingService.getConsumeSetting();
        });
    }
    private CompletableFuture<ConfirmOrderResult.CalcAmount> getAmountAsync(List<CartPromotionItem> items){
        return CompletableFuture.supplyAsync(()->{
            return calCartAmount(items);
        });
    }
    public ConfirmOrderResult generateConfirmOrder(List<Long> ids)  {
        ConfirmOrderResult confirmOrderResult = new ConfirmOrderResult();

        UmsMember member = umsMemberService.getCurrentMember();
        Long memberId = member.getId();
        CompletableFuture<List<CartPromotionItem>> cartPromotionItemFuture = getCartPromotionItem(memberId,ids);
        CompletableFuture<List<UmsMemberReceiveAddress>> memberReceiveAddressFuture = getMemberReceiveAddress(memberId);
        CompletableFuture<List<SmsCouponHistoryDetail>> couponHistoryDetailFuture = null;
        try {
            couponHistoryDetailFuture = getCouponHistoryDetailAsync(memberId,cartPromotionItemFuture.get(),1);
            CompletableFuture<UmsIntegrationConsumeSetting> integrationConsumeSettingCompletableFuture = getIntegrationConsumeSettingAsync();
            CompletableFuture<ConfirmOrderResult.CalcAmount> calcAmountCompletableFuture = getAmountAsync(cartPromotionItemFuture.get());
            CompletableFuture<Void> allOf = CompletableFuture.allOf(calcAmountCompletableFuture,cartPromotionItemFuture,memberReceiveAddressFuture,couponHistoryDetailFuture,integrationConsumeSettingCompletableFuture);
            allOf.join();
            List<CartPromotionItem> cartPromotionItems = cartPromotionItemFuture.get();
            List<UmsMemberReceiveAddress> umsMemberReceiveAddresses = memberReceiveAddressFuture.get();
            List<SmsCouponHistoryDetail> smsCouponHistoryDetails = couponHistoryDetailFuture.get();
            UmsIntegrationConsumeSetting umsIntegrationConsumeSetting = integrationConsumeSettingCompletableFuture.get();
            ConfirmOrderResult.CalcAmount calcAmount = calcAmountCompletableFuture.get();
            //购物车信息
            confirmOrderResult.setCartPromotionItemList(cartPromotionItems);
            //收货地址列表
            confirmOrderResult.setMemberReceiveAddressList(umsMemberReceiveAddresses);
            //可用优惠券列表
            confirmOrderResult.setCouponHistoryDetailList(smsCouponHistoryDetails);
            //获取用户积分
            confirmOrderResult.setMemberIntegration(member.getIntegration());
            //获取积分设置规则
            confirmOrderResult.setIntegrationConsumeSetting(umsIntegrationConsumeSetting);
            //计算总金额，活动优惠，实际应付
            confirmOrderResult.setCalcAmount(calcAmount);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        return confirmOrderResult;
    }


    private ConfirmOrderResult.CalcAmount calCartAmount(List<CartPromotionItem> cartPromotionItemList){
        ConfirmOrderResult.CalcAmount calcAmount = new ConfirmOrderResult.CalcAmount();
        calcAmount.setFreightAmount(new BigDecimal(0));
        BigDecimal totalAmount = new BigDecimal(0);
        BigDecimal reduceAmount = new BigDecimal(0);
        for(CartPromotionItem item : cartPromotionItemList){
            totalAmount = totalAmount.add(item.getPrice().multiply(new BigDecimal(item.getQuantity())));
            reduceAmount = reduceAmount.add(item.getReducePrice().multiply(new BigDecimal(item.getQuantity())));
        }
        calcAmount.setTotalAmount(totalAmount);
        calcAmount.setPromotionAmount(reduceAmount);
        calcAmount.setPayAmount(totalAmount.subtract(reduceAmount));
        return calcAmount;
    }




    public Map<String, Object> generateOrder(OrderParam param){
        List<Long> cartIds = param.getCartIds();
        UmsMember member = umsMemberService.getCurrentMember();
        List<CartPromotionItem> cartItems = omsCartItemService.listPromotion(member.getId(),cartIds);
        List<OmsOrderItem> orderItems = new ArrayList<>();
        for(CartPromotionItem item : cartItems){
            //完成购物车中商品基本信息拷贝到订单item中
            OmsOrderItem orderItem = copyCartItemToOrderItem(item);
            orderItems.add(orderItem);
        }
        //检查购物车商品的库存是否足够
        if(!hasStock(cartItems)){
            Asserts.failed("库存不足，无法下单");
        }


        Long couponId = param.getCouponId();
        if(couponId == null){
            for(OmsOrderItem orderItem : orderItems){
                orderItem.setCouponAmount(new BigDecimal(0));
            }
        }else{
            //使用了优惠券
            SmsCouponHistoryDetail couponHistoryDetail = getUseCoupon(cartItems,couponId);
            if(couponHistoryDetail == null){
                Asserts.failed("优惠券不能使用");
            }
            //给使用优惠券的单品进行均摊优惠券的金额，设置couponAmount
            handleCouponAmount(orderItems,couponHistoryDetail);
        }


        Integer useIntegration = param.getUseIntegration();
        if(useIntegration == null || useIntegration == 0){
            for(OmsOrderItem item : orderItems){
                item.setIntegrationAmount(new BigDecimal(0));
            }
        }else{
            BigDecimal totalAmount = calcTotalAmount(orderItems);
            //得到了积分抵扣的金额，如果不满足条件可能就是0，无法抵扣
            BigDecimal integrationAmount = getUseIntegrationAmount(useIntegration,totalAmount,member,couponId!=null);
            if(integrationAmount.compareTo(new BigDecimal(0)) == 0){
                Asserts.failed("积分不可用");
            }else{
                for(OmsOrderItem item : orderItems) {
                    BigDecimal perAmount = item.getProductPrice().divide(totalAmount,3,RoundingMode.HALF_EVEN).multiply(integrationAmount);
                    item.setIntegrationAmount(perAmount);
                }
            }

        }

        //计算每个商品实际应付的金额
        handleRealAmount(orderItems);

        //锁住库存
        lockStock(cartItems);


    }
    
    private OmsOrderItem copyCartItemToOrderItem(CartPromotionItem item){
        OmsOrderItem orderItem = new OmsOrderItem();
        orderItem.setProductCategoryId(item.getProductCategoryId());
        orderItem.setProductBrand(item.getProductBrand());
        orderItem.setPromotionName(item.getProductName());
        orderItem.setProductId(item.getProductId());
        orderItem.setProductPic(item.getProductPic());
        orderItem.setProductQuantity(item.getQuantity());
        orderItem.setProductSkuId(item.getProductSkuId());
        orderItem.setProductSkuCode(item.getProductSkuCode());
        orderItem.setProductSn(item.getProductSn());
        orderItem.setProductPrice(item.getPrice());
        orderItem.setPromotionName(item.getPromotionMessage());
        orderItem.setPromotionAmount(item.getReducePrice());
        orderItem.setGiftGrowth(item.getGrowth());
        orderItem.setGiftIntegration(item.getIntegration());
        return orderItem;
    }

    private boolean hasStock(List<CartPromotionItem> promotionItems){
        for(CartPromotionItem item : promotionItems){
            if(item.getRealStock() == null || item.getRealStock() <= 0|| item.getRealStock() <= item.getQuantity()) return false;
        }
        return true;
    }

    private SmsCouponHistoryDetail getUseCoupon(List<CartPromotionItem> items,Long couponId){
        Long memberId = umsMemberService.getCurrentMember().getId();
        List<SmsCouponHistoryDetail> smsCouponHistoryDetails = umsMemberCouponService.listCart(memberId, items, 1);
        for(SmsCouponHistoryDetail couponHistoryDetail : smsCouponHistoryDetails){
            if(couponHistoryDetail.getCoupon().getId().equals(couponId)) return couponHistoryDetail;
        }
        return null;
    }

    private void handleCouponAmount(List<OmsOrderItem> orderItemList,SmsCouponHistoryDetail couponHistoryDetail){
        SmsCoupon coupon = couponHistoryDetail.getCoupon();
        Integer type = coupon.getUseType();
        switch (type){
            case 0:{
                //全场通用
                calcPerCouponAmount(orderItemList,coupon);
            };break;
            case 1:{
                List<OmsOrderItem> relationOrderItems = getCouponOrderItemByRelation(orderItemList,couponHistoryDetail,0);
                calcPerCouponAmount(relationOrderItems,coupon);
            };break;
            case 2:{
                List<OmsOrderItem> relationOrderItems = getCouponOrderItemByRelation(orderItemList,couponHistoryDetail,1);
                calcPerCouponAmount(relationOrderItems,coupon);
            };break;
            default:break;
        }
    }

    private void calcPerCouponAmount(List<OmsOrderItem> items,SmsCoupon coupon){
        BigDecimal totalAmount = calcTotalAmount(items);
        for(OmsOrderItem item : items){
            BigDecimal couponAmount = item.getProductPrice().divide(totalAmount,3, RoundingMode.HALF_EVEN).multiply(coupon.getAmount());
            item.setCouponAmount(couponAmount);
        }
    }

    private BigDecimal calcTotalAmount(List<OmsOrderItem> items){
        BigDecimal total = new BigDecimal(0);
        for(OmsOrderItem item : items){
            total = total.add(item.getProductPrice().multiply(new BigDecimal(item.getProductQuantity())));
        }
        return total;
    }

    private List<OmsOrderItem> getCouponOrderItemByRelation(List<OmsOrderItem> orderItemList,SmsCouponHistoryDetail smsCouponHistoryDetail,Integer type){
        List<OmsOrderItem> relationOrderItems = new ArrayList<>();
        if(type == 0){
            List<SmsCouponProductCategoryRelation> productCategoryRelations = smsCouponHistoryDetail.getProductCategoryRelations();
            List<Long> categoryIds = productCategoryRelations.stream().map(SmsCouponProductCategoryRelation::getProductCategoryId).collect(Collectors.toList());
            orderItemList.stream().forEach(
                    it->{
                        if(categoryIds.contains(it.getProductCategoryId())) relationOrderItems.add(it);
                        else it.setCouponAmount(new BigDecimal(0));
                    }
            );
        }else if(type == 1){
            List<SmsCouponProductRelation> productRelations = smsCouponHistoryDetail.getProductRelationList();
            List<Long> productIds = productRelations.stream().map(it->it.getProductId()).collect(Collectors.toList());
            orderItemList.stream().forEach(
                    it->{
                        if(productIds.contains(it.getProductId())) relationOrderItems.add(it);
                        else it.setCouponAmount(new BigDecimal(0));
                    }
            );

        }
        return relationOrderItems;
    }

    private BigDecimal getUseIntegrationAmount(Integer useIntegration,BigDecimal totalAmount,UmsMember member,boolean hasCoupon){
        BigDecimal zero = new BigDecimal(0);
        Integer hasIntegration = member.getIntegration();
        if(useIntegration.compareTo(hasIntegration)>0){
            //使用的积分超过了用户所拥有的积分
            return zero;
        }
        //获取积分的使用规则
        UmsIntegrationConsumeSetting consumeSetting = umsIntegrationConsumeSettingService.getConsumeSetting();
        if(hasCoupon && consumeSetting.getCouponStatus().equals(0)){
            //如果使用了优惠券，并且积分的使用规则中指明不可以与优惠券一起用，就不可以使用积分
            return zero;
        }

        if(useIntegration.compareTo(consumeSetting.getUseUnit())<0){
            //使用的积分没有达到使用门槛
            return zero;
        }
        //获得积分抵扣的金额
        BigDecimal integrationAmount = new BigDecimal(useIntegration).divide(new BigDecimal(consumeSetting.getUseUnit()),2,RoundingMode.HALF_EVEN);
        //获取积分能够抵扣的最大金额
        BigDecimal maxPercent = new BigDecimal(consumeSetting.getMaxPercentPerOrder()).divide(new BigDecimal(100),2,RoundingMode.HALF_EVEN);
        BigDecimal maxAmount = totalAmount.multiply(maxPercent);
        if(integrationAmount.compareTo(maxAmount) > 0){
            return zero;
        }
        return integrationAmount;
    }

    private void handleRealAmount(List<OmsOrderItem> orderItems){
        for(OmsOrderItem item : orderItems){
            BigDecimal realAmount = item.getProductPrice().subtract(item.getPromotionAmount())
                    .subtract(item.getCouponAmount()).subtract(item.getIntegrationAmount());
            item.setRealAmount(realAmount);
        }
    }

    @Resource
    private PmsSkuStockMapper skuStockMapper;
    private void lockStock(List<CartPromotionItem> cartPromotionItemList){
        for(CartPromotionItem item : cartPromotionItemList){
            Long skuId = item.getProductSkuId();
            PmsSkuStock skuStock = skuStockMapper.selectByPrimaryKey(skuId);
            skuStock.setLockStock(skuStock.getLockStock()+item.getQuantity());//锁定库存增加
            skuStockMapper.updateByPrimaryKeySelective(skuStock);
        }
    }
}
