package com.papa.portal.domain;

import com.papa.mbg.model.OmsOrder;
import com.papa.mbg.model.OmsOrderItem;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class OmsOrderDetail extends OmsOrder {
    @ApiModelProperty("订单商品列表")
    List<OmsOrderItem> orderItemList;
}
