package com.papa.mbg.mapper;

import com.papa.mbg.model.PmsProductCategoryBrandRelation;
import com.papa.mbg.model.PmsProductCategoryBrandRelationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PmsProductCategoryBrandRelationMapper {
    int countByExample(PmsProductCategoryBrandRelationExample example);

    int deleteByExample(PmsProductCategoryBrandRelationExample example);

    int deleteByPrimaryKey(Long id);

    int insert(PmsProductCategoryBrandRelation record);

    int insertSelective(PmsProductCategoryBrandRelation record);

    List<PmsProductCategoryBrandRelation> selectByExample(PmsProductCategoryBrandRelationExample example);

    PmsProductCategoryBrandRelation selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") PmsProductCategoryBrandRelation record, @Param("example") PmsProductCategoryBrandRelationExample example);

    int updateByExample(@Param("record") PmsProductCategoryBrandRelation record, @Param("example") PmsProductCategoryBrandRelationExample example);

    int updateByPrimaryKeySelective(PmsProductCategoryBrandRelation record);

    int updateByPrimaryKey(PmsProductCategoryBrandRelation record);
}