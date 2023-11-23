package com.papa.mbg.mapper;

import com.papa.mbg.model.PmsSkuStockAttributeValue;
import com.papa.mbg.model.PmsSkuStockAttributeValueExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PmsSkuStockAttributeValueMapper {
    int countByExample(PmsSkuStockAttributeValueExample example);

    int deleteByExample(PmsSkuStockAttributeValueExample example);

    int deleteByPrimaryKey(Long id);

    int insert(PmsSkuStockAttributeValue record);

    int insertSelective(PmsSkuStockAttributeValue record);

    List<PmsSkuStockAttributeValue> selectByExampleWithBLOBs(PmsSkuStockAttributeValueExample example);

    List<PmsSkuStockAttributeValue> selectByExample(PmsSkuStockAttributeValueExample example);

    PmsSkuStockAttributeValue selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") PmsSkuStockAttributeValue record, @Param("example") PmsSkuStockAttributeValueExample example);

    int updateByExampleWithBLOBs(@Param("record") PmsSkuStockAttributeValue record, @Param("example") PmsSkuStockAttributeValueExample example);

    int updateByExample(@Param("record") PmsSkuStockAttributeValue record, @Param("example") PmsSkuStockAttributeValueExample example);

    int updateByPrimaryKeySelective(PmsSkuStockAttributeValue record);

    int updateByPrimaryKeyWithBLOBs(PmsSkuStockAttributeValue record);

    int updateByPrimaryKey(PmsSkuStockAttributeValue record);
}