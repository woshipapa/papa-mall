package com.papa.portal.domain;

import com.papa.mbg.model.OmsCartItem;
import com.papa.mbg.model.PmsSkuStock;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractPromotionStrategy implements PromotionStrategy{
    protected static List<PromotionProduct> promotions;
    protected PmsSkuStock getSkuById(PromotionProduct  promotion, Long skuId){
        List<PmsSkuStock> skuStocks = promotion.getSkuStockList();
        for(PmsSkuStock skuStock:skuStocks){
            if(skuId.equals(skuStock.getId())){
                return skuStock;
            }
        }
        return null;
    }
    protected List<CartPromotionItem> handNoReduce(PromotionProduct promotionProduct, List<OmsCartItem> cartItems){
        List<CartPromotionItem> list = new ArrayList<>();
        for(OmsCartItem item:cartItems){
            CartPromotionItem cartPromotionItem = new CartPromotionItem();
            BeanUtils.copyProperties(item,cartPromotionItem);
            PmsSkuStock skuStock = getSkuById(promotionProduct,item.getProductSkuId());
            BigDecimal originPrice = skuStock.getPrice();
            cartPromotionItem.setPrice(originPrice);
            cartPromotionItem.setGrowth(promotionProduct.getGiftGrowth());
            cartPromotionItem.setIntegration(promotionProduct.getGiftPoint());
            cartPromotionItem.setReducePrice(new BigDecimal(0));
            cartPromotionItem.setPromotionMessage("无优惠");
            cartPromotionItem.setRealStock(skuStock.getStock()-skuStock.getLockStock());
            list.add(cartPromotionItem);
        }

        return list;
    }
    public static void setPromotionProducts(List<PromotionProduct> promotionProducts) {
        promotions = promotionProducts;
    }
}
