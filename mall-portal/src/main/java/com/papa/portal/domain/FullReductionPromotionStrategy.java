package com.papa.portal.domain;

import cn.hutool.core.collection.CollUtil;
import com.papa.mbg.model.OmsCartItem;
import com.papa.mbg.model.PmsProduct;
import com.papa.mbg.model.PmsProductFullReduction;
import com.papa.mbg.model.PmsSkuStock;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class FullReductionPromotionStrategy extends AbstractPromotionStrategy{

    @Override
    public List<CartPromotionItem> promotionAlgorithm(PromotionProduct promotion, List<OmsCartItem> cartItems) {
        List<CartPromotionItem> list = new ArrayList<>();
        //计算出同一spu商品的原始总价
        BigDecimal totalPrice = getTotalPrice(cartItems,promotions);
        PmsProductFullReduction fullReduction = getFullReduction(totalPrice,promotion.getFullReductionList());

        if(fullReduction!=null) {
            for (OmsCartItem item : cartItems) {
                CartPromotionItem cartPromotionItem = new CartPromotionItem();
                BeanUtils.copyProperties(item, cartPromotionItem);
                PmsSkuStock skuStock = getSkuById(promotion, item.getProductSkuId());
                BigDecimal originPrice = skuStock.getPrice();
                cartPromotionItem.setPrice(originPrice);
                cartPromotionItem.setReducePrice(originPrice.divide(totalPrice).multiply(fullReduction.getReducePrice()));
                cartPromotionItem.setGrowth(promotion.getGiftGrowth());
                cartPromotionItem.setIntegration(promotion.getGiftPoint());
                String message = getPromotionMessage(fullReduction);
                cartPromotionItem.setPromotionMessage(message);
                list.add(cartPromotionItem);
            }
        }else{
            list = handNoReduce(promotion,cartItems);
        }
        return list;
    }
    private String getPromotionMessage(PmsProductFullReduction fullReduction){
        StringBuilder sb = new StringBuilder();
        sb.append("满减优惠");
        sb.append("满").append(fullReduction.getFullPrice()).append("元").append("减").append(fullReduction.getReducePrice()).append("元");
        return sb.toString();
    }

    private PmsProductFullReduction getFullReduction(BigDecimal totalPrice,List<PmsProductFullReduction> fullReductions){
        if(CollUtil.isNotEmpty(fullReductions)){
            fullReductions.sort(new Comparator<PmsProductFullReduction>() {
                @Override
                public int compare(PmsProductFullReduction o1, PmsProductFullReduction o2) {
                    return o2.getFullPrice().subtract(o1.getFullPrice()).intValue();
                }
            });
            for(PmsProductFullReduction fullReduction:fullReductions){
                if(totalPrice.subtract(fullReduction.getFullPrice()).intValue()>=0){
                    return fullReduction;
                }
            }
            return null;
        }
        return null;
    }
    private BigDecimal getTotalPrice(List<OmsCartItem> cartItems,List<PromotionProduct> promotionProducts){
        BigDecimal price = new BigDecimal(0);
        for(OmsCartItem item:cartItems){
            PromotionProduct promotionProduct = getPromotionById(item.getProductId(),promotionProducts);
            PmsSkuStock skuStock = getSkuById(promotionProduct,item.getProductSkuId());
            if(skuStock!=null){
                price.add(skuStock.getPrice().multiply(new BigDecimal(item.getQuantity())));
            }
        }
        return price;


    }
    private PromotionProduct getPromotionById(Long productId,List<PromotionProduct> promotionProducts){
        PromotionProduct result = null;
        if(CollUtil.isNotEmpty(promotionProducts)){
            for(PromotionProduct p:promotionProducts){
                if(p.getId().equals(productId)){
                    result = p;
                    return result;
                }
            }
        }
        return null;
    }
}
