package com.papa.portal.domain;

import com.papa.mbg.model.PmsProduct;
import com.papa.mbg.model.PmsProductAttributeValue;
import lombok.Data;

import java.util.List;
@Data
public class CartProductInfo extends PmsProduct {

    private List<PmsProductAttributeValue> productAttributeValues;

    private List<PmsSkuInfo> skuInfos;
}
