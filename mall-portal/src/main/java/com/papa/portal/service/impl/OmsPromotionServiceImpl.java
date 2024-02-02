package com.papa.portal.service.impl;
import cn.hutool.core.collection.CollUtil;
import com.papa.mbg.model.OmsCartItem;
import com.papa.portal.dao.ProductDAO;
import com.papa.portal.domain.*;
import com.papa.portal.service.OmsPromotionService;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class OmsPromotionServiceImpl implements OmsPromotionService {
    @Override
    public List<CartPromotionItem> calCartPromotion(List<OmsCartItem> cartItems) {
        //根据商品的id也就是spu进行分组，相同的商品优惠是相同的
        Map<Long,List<OmsCartItem>> map = groupByProductId(cartItems);
        //得到了购物车中选中商品的优惠政策
        List<PromotionProduct> promotions = getPromotions(cartItems);
        AbstractPromotionStrategy.setPromotionProducts(promotions);
        List<CartPromotionItem> cartPromotionItems = new ArrayList<>();
        for(Map.Entry<Long,List<OmsCartItem>> entry:map.entrySet()){
            Long productId = entry.getKey();
            PromotionProduct promotion = getPromotionById(productId,promotions);
            List<OmsCartItem> items = entry.getValue();
            Integer promotionType = promotion.getPromotionType();
            //这里利用策略工厂，根据促销类型选取相应的促销策略，返回了对于这种的促销类型该使用哪一种计算方式
            PromotionStrategy promotionStrategy = PromotionFactory.getPromotionStrategy(promotionType);
            List<CartPromotionItem> cartPromotions = promotionStrategy.promotionAlgorithm(promotion,items);
            cartPromotionItems.addAll(cartPromotions);
        }
        return cartPromotionItems;
    }

    /**
     * 根据商品的id进行分组计算
     * @param cartItems
     * @return
     */
    private Map<Long,List<OmsCartItem>> groupByProductId(List<OmsCartItem> cartItems){
        Map<Long,List<OmsCartItem>> map = new TreeMap<>();
        for(OmsCartItem item:cartItems){
            List<OmsCartItem> list = map.get(item.getProductId());
            if(CollUtil.isEmpty(list)){
                list = new ArrayList<>();
                list.add(item);
                map.put(item.getProductId(),list);
            }else{
                list.add(item);
            }
        }
        return map;
    }

    @Resource
    private ProductDAO productdao;
    private List<PromotionProduct> getPromotions(List<OmsCartItem> cartItems){
        List<Long> ids =cartItems.stream().map(it->it.getProductId()).collect(Collectors.toList());
        return productdao.getPromotionList(ids);
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
