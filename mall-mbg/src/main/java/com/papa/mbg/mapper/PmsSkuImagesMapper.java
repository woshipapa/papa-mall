package com.papa.mbg.mapper;

import com.papa.mbg.model.PmsSkuImages;
import com.papa.mbg.model.PmsSkuImagesExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PmsSkuImagesMapper {
    int countByExample(PmsSkuImagesExample example);

    int deleteByExample(PmsSkuImagesExample example);

    int deleteByPrimaryKey(Long id);

    int insert(PmsSkuImages record);

    int insertSelective(PmsSkuImages record);

    List<PmsSkuImages> selectByExample(PmsSkuImagesExample example);

    PmsSkuImages selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") PmsSkuImages record, @Param("example") PmsSkuImagesExample example);

    int updateByExample(@Param("record") PmsSkuImages record, @Param("example") PmsSkuImagesExample example);

    int updateByPrimaryKeySelective(PmsSkuImages record);

    int updateByPrimaryKey(PmsSkuImages record);

    int insertBatch(@Param("list") List<PmsSkuImages> images);
}