package com.papa.portal.dao;

import com.papa.mbg.model.OmsOrderItem;
import com.papa.portal.domain.OmsOrderDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PortalOrderDAO {

    public int releaseSkuStockLock(@Param("list") List<OmsOrderItem> orderItemList);

    public int updateSkuStock(@Param("list") List<OmsOrderItem> orderItemList);

    public List<OmsOrderItem> getOrderItems(@Param("id") Long orderId);

    public List<OmsOrderDetail> getTimeOutOrders(@Param("minute") Integer overTime);

    public int updateStatus(@Param("ids") List<Long> ids,@Param("status") Integer status);
}
