package com.papa.portal.service;

import com.papa.mbg.model.OmsCartItem;
import com.papa.portal.domain.CartPromotionItem;

import java.util.List;

public interface OmsPromotionService {
    List<CartPromotionItem> calCartPromotion(List<OmsCartItem> cartItems);
}
