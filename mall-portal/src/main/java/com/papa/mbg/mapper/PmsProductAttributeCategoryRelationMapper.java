package com.papa.mbg.mapper;

import com.papa.mbg.model.PmsProductAttributeCategoryRelation;
import com.papa.mbg.model.PmsProductAttributeCategoryRelationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PmsProductAttributeCategoryRelationMapper {
    int countByExample(PmsProductAttributeCategoryRelationExample example);

    int deleteByExample(PmsProductAttributeCategoryRelationExample example);

    int deleteByPrimaryKey(Long id);

    int insert(PmsProductAttributeCategoryRelation record);

    int insertSelective(PmsProductAttributeCategoryRelation record);

    List<PmsProductAttributeCategoryRelation> selectByExample(PmsProductAttributeCategoryRelationExample example);

    PmsProductAttributeCategoryRelation selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") PmsProductAttributeCategoryRelation record, @Param("example") PmsProductAttributeCategoryRelationExample example);

    int updateByExample(@Param("record") PmsProductAttributeCategoryRelation record, @Param("example") PmsProductAttributeCategoryRelationExample example);

    int updateByPrimaryKeySelective(PmsProductAttributeCategoryRelation record);

    int updateByPrimaryKey(PmsProductAttributeCategoryRelation record);
}