package com.papa.portal.dao;

import com.papa.mbg.model.OmsOrderItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PortalOrderItemDAO {


    int insertList(@Param("list") List<OmsOrderItem> orderItemList);
}
