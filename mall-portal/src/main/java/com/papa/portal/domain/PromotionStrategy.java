package com.papa.portal.domain;

import com.papa.mbg.model.OmsCartItem;

import java.util.List;

public interface PromotionStrategy {

    public List<CartPromotionItem> promotionAlgorithm(PromotionProduct promotion, List<OmsCartItem> cartItems);
}
