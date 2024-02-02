package com.papa.portal.dao;

import com.papa.portal.domain.PmsSkuInfo;
import com.papa.portal.domain.PromotionProduct;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductDAO {
    public List<PromotionProduct>  getPromotionList(@Param("ids") List<Long> ids);


    public List<PmsSkuInfo> getSkuInfoList(@Param("id") Long productId);

//    public List<PmsProductAttributeValue> getProductAttributeValueList(@Param("id") Long productId);
}
