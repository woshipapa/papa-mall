package com.papa.portal.domain;

import com.papa.mbg.model.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
@Data
@EqualsAndHashCode
public class SmsCouponHistoryDetail extends SmsCouponHistory {
    @ApiModelProperty("相关优惠券信息")
    private SmsCoupon coupon;

    @ApiModelProperty("优惠券关联商品")
    private List<SmsCouponProductRelation> productRelationList;

    @ApiModelProperty("优惠券关联商品分类")
    private List<SmsCouponProductCategoryRelation> productCategoryRelations;
}
