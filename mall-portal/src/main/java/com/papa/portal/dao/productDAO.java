package com.papa.portal.dao;

import com.papa.portal.domain.PromotionProduct;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface productDAO {
    public List<PromotionProduct>  getPromotionList(@Param("ids") List<Long> ids);


}
