package com.papa.portal.service;

import com.papa.mbg.model.OmsCartItem;
import com.papa.mbg.model.OmsCartItemExample;
import com.papa.portal.domain.CartPromotionItem;

import java.util.List;

public interface OmsCartItemService {

    /**
     * cartItem是购物车中的一个单位，
     * 这里添加会先查找是否有相同的sku的商品有的话数量增加，没有的话新增
     * @param cartItem
     * @return
     */
    int add(OmsCartItem cartItem);

    List<OmsCartItem> list(Long memberId);

    List<CartPromotionItem> listPromotion(Long memberId,List<Long> ids);

    int delete(Long memberId,List<Long> ids);

    int updateQuantity(Long memberId,Long cartItemId,Integer count);

    int clear(Long memberId);

}
