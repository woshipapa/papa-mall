package com.papa.portal.dao;

import com.papa.mbg.model.OmsOrderItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PortalOrderDAO {

    public int updateSkuStock(@Param("list") List<OmsOrderItem> orderItemList);

    public List<OmsOrderItem> getOrderItems(@Param("id") Long orderId);
}
