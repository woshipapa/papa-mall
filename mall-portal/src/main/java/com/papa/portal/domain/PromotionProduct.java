package com.papa.portal.domain;

import com.papa.mbg.model.PmsProduct;
import com.papa.mbg.model.PmsProductFullReduction;
import com.papa.mbg.model.PmsProductLadder;
import com.papa.mbg.model.PmsSkuStock;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
@Data
@EqualsAndHashCode
public class PromotionProduct extends PmsProduct {

    private List<PmsSkuStock> skuStockList;

    private List<PmsProductLadder> ladderList;

    private List<PmsProductFullReduction> fullReductionList;

}
