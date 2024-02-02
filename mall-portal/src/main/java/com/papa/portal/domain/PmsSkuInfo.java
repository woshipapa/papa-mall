package com.papa.portal.domain;

import com.papa.mbg.model.PmsSkuStock;
import com.papa.mbg.model.PmsSkuStockAttributeValue;
import lombok.Data;

@Data
public class PmsSkuInfo {

    private PmsSkuStock skuStock;

    private PmsSkuStockAttributeValue skuStockAttributeValue;
}
