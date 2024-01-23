package com.papa.mbg.mapper;

import com.papa.mbg.model.OmsOrderStatusChange;
import com.papa.mbg.model.OmsOrderStatusChangeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OmsOrderStatusChangeMapper {
    int countByExample(OmsOrderStatusChangeExample example);

    int deleteByExample(OmsOrderStatusChangeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(OmsOrderStatusChange record);

    int insertSelective(OmsOrderStatusChange record);

    List<OmsOrderStatusChange> selectByExample(OmsOrderStatusChangeExample example);

    OmsOrderStatusChange selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") OmsOrderStatusChange record, @Param("example") OmsOrderStatusChangeExample example);

    int updateByExample(@Param("record") OmsOrderStatusChange record, @Param("example") OmsOrderStatusChangeExample example);

    int updateByPrimaryKeySelective(OmsOrderStatusChange record);

    int updateByPrimaryKey(OmsOrderStatusChange record);
}