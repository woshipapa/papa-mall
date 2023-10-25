package com.papa.portal.domain;

import com.papa.mbg.model.OmsCartItem;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode
public class CartPromotionItem extends OmsCartItem {
    private String promotionMessage;

    private BigDecimal reducePrice;

    private Integer realStock;

    private Integer integration;

    private Integer growth;



}
