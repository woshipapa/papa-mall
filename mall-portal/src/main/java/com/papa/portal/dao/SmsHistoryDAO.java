package com.papa.portal.dao;

import com.papa.portal.domain.SmsCouponHistoryDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SmsHistoryDAO {

    List<SmsCouponHistoryDetail> getDetailList(@Param("id") Long memberId);
}
