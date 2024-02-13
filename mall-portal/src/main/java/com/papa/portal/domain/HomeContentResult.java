package com.papa.portal.domain;

import com.papa.mbg.model.CmsSubject;
import com.papa.mbg.model.PmsBrand;
import com.papa.mbg.model.PmsProduct;
import com.papa.mbg.model.SmsHomeAdvertise;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
//首页对象，会展示轮播广告，推荐品牌，秒杀活动，新品以及热销推荐，以及推荐的专题
@Data
public class HomeContentResult {
    @ApiModelProperty("轮播广告列表")
    private List<SmsHomeAdvertise> advertiseList;

    @ApiModelProperty("推荐品牌")
    private List<PmsBrand> brandList;

    @ApiModelProperty("秒杀活动")
    List<FlashPromotionProduct> homeFlashPromotion;

    @ApiModelProperty("新品推荐")
    private List<PmsProduct> newProductList;
    @ApiModelProperty("人气推荐")
    private List<PmsProduct> hotProductList;
    @ApiModelProperty("推荐专题")
    private List<CmsSubject> subjectList;
}
