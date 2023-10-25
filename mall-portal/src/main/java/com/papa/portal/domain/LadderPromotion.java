package com.papa.portal.domain;

import cn.hutool.core.collection.CollUtil;
import com.papa.mbg.model.OmsCartItem;
import com.papa.mbg.model.PmsProduct;
import com.papa.mbg.model.PmsProductLadder;
import com.papa.mbg.model.PmsSkuStock;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class LadderPromotion extends AbstractPromotionStrategy{
    @Override
    public List<CartPromotionItem> promotionAlgorithm(PromotionProduct promotion, List<OmsCartItem> cartItems) {
        List<CartPromotionItem> list =  new ArrayList<>();
        //获得相同商品的数量，然后匹配最佳的优惠政策
        int count = getProductCount(cartItems);
        PmsProductLadder ladder = getLadder(count,promotion.getLadderList());
        if(ladder!=null){
            for(OmsCartItem item:cartItems){
                CartPromotionItem cartPromotionItem = new CartPromotionItem();
                BeanUtils.copyProperties(item,cartPromotionItem);
                PmsSkuStock skuStock = getSkuById(promotion,item.getProductSkuId());
                BigDecimal originPrice = skuStock.getPrice();
                cartPromotionItem.setPrice(originPrice);
                cartPromotionItem.setReducePrice(originPrice.subtract(originPrice.multiply(ladder.getDiscount())));
                cartPromotionItem.setGrowth(promotion.getGiftGrowth());
                cartPromotionItem.setIntegration(promotion.getGiftPoint());
                String message = getLadderMessage(ladder);
                cartPromotionItem.setPromotionMessage(message);
                cartPromotionItem.setRealStock(skuStock.getStock()-skuStock.getLockStock());
                list.add(cartPromotionItem);
            }
        }else{
            list = handNoReduce(promotion,cartItems);
        }
        return list;
    }
    private String getLadderMessage(PmsProductLadder ladder){
        StringBuilder sb = new StringBuilder();
        sb.append("满").append(ladder.getCount()).append("件").append("打").append(ladder.getDiscount()).append("折");
        return sb.toString();
    }

    private Integer getProductCount(List<OmsCartItem> items){
        int count = 0 ;
        for(OmsCartItem o:items){
            count+=o.getQuantity();
        }
        return count;
    }
    private PmsProductLadder getLadder(Integer count,List<PmsProductLadder> ladderList){
        if(CollUtil.isNotEmpty(ladderList)){
            //降序排列
            ladderList.sort(new Comparator<PmsProductLadder>() {
                @Override
                public int compare(PmsProductLadder o1, PmsProductLadder o2) {
                    return o2.getCount()-o1.getCount();
                }
            });
            for(PmsProductLadder ladder:ladderList){
                if(count>=ladder.getCount()){
                    return ladder;
                }
            }
        }
        return null;

    }
}
