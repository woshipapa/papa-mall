package com.papa.portal.domain;

import com.papa.mbg.model.PmsProduct;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class FlashPromotionProduct extends PmsProduct {
    private Long relationId; // 秒杀活动与商品关联的ID
    private Long flashPromotionId; // 秒杀活动ID
    private BigDecimal flashPromotionPrice; // 秒杀价格
    private Integer flashPromotionCount; // 秒杀商品数量
    private Integer flashPromotionLimit; // 每个用户限购数量
    private Date flashPromotionStartDate; // 秒杀开始时间
    private Date flashPromotionEndDate; // 秒杀结束时间
    private String secKillServer; // 可能表示负责处理秒杀逻辑的服务器或服务


}
