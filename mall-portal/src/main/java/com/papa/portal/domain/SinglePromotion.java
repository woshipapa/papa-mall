package com.papa.portal.domain;

import com.papa.mbg.model.OmsCartItem;
import com.papa.mbg.model.PmsSkuStock;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class SinglePromotion extends AbstractPromotionStrategy{


    @Override
    public List<CartPromotionItem> promotionAlgorithm(PromotionProduct promotion, List<OmsCartItem> cartItems) {
        List<CartPromotionItem>  cartList = new ArrayList<>();
        for(OmsCartItem item : cartItems){
            //商品的sku中有促销价格
            CartPromotionItem cartItem = new CartPromotionItem();
            BeanUtils.copyProperties(item,cartItem);
            cartItem.setPromotionMessage("单品促销");
            PmsSkuStock skuStock = getSkuById(promotion,item.getProductSkuId());
            BigDecimal originPrice = skuStock.getPrice();
            BigDecimal promotionPrice = skuStock.getPromotionPrice();
            cartItem.setReducePrice(originPrice.subtract(promotionPrice));
            cartItem.setPrice(originPrice);
            cartItem.setRealStock(skuStock.getStock()-skuStock.getLockStock());
            cartItem.setGrowth(promotion.getGiftGrowth());
            cartItem.setIntegration(promotion.getGiftPoint());
            cartList.add(cartItem);
        }
        return cartList;
    }
//
//    private PmsSkuStock getSkuById(PromotionProduct  promotion,Long skuId){
//        List<PmsSkuStock> skuStocks = promotion.getSkuStockList();
//        for(PmsSkuStock skuStock:skuStocks){
//            if(skuId.equals(skuStock.getId())){
//                return skuStock;
//            }
//        }
//        return null;
//    }
}
