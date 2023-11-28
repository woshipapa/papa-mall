package com.papa.portal.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode
public class OrderParam {

    @ApiModelProperty("所使用的优惠券id")
    private Long couponId;

    @ApiModelProperty("所使用的积分")
    private Integer useIntegration;

    @ApiModelProperty("收货地址id")
    private Long memberReceiveId;

    @ApiModelProperty("购物车中选中的商品")
    private List<Long> cartIds;


    @ApiModelProperty("所使用的支付方式")
    private Integer payType;
}
