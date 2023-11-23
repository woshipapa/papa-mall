package com.papa.mbg.model;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class UmsMemberStatisticsInfo implements Serializable {
    private Long id;

    private Long memberId;

    private BigDecimal consumeAmount;

    private Integer orderCount;

    private Integer couponCount;

    private Integer commentCount;

    private Integer returnOrderCount;

    private Integer loginCount;

    private Integer attendCount;

    private Integer fansCount;

    private Integer collectProductCount;

    private Integer collectSubjectCount;

    private Integer collectTopicCount;

    private Integer collectCommentCount;

    private Integer inviteFriendCount;

    private Date recentOrderTime;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public BigDecimal getConsumeAmount() {
        return consumeAmount;
    }

    public void setConsumeAmount(BigDecimal consumeAmount) {
        this.consumeAmount = consumeAmount;
    }

    public Integer getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(Integer orderCount) {
        this.orderCount = orderCount;
    }

    public Integer getCouponCount() {
        return couponCount;
    }

    public void setCouponCount(Integer couponCount) {
        this.couponCount = couponCount;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public Integer getReturnOrderCount() {
        return returnOrderCount;
    }

    public void setReturnOrderCount(Integer returnOrderCount) {
        this.returnOrderCount = returnOrderCount;
    }

    public Integer getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(Integer loginCount) {
        this.loginCount = loginCount;
    }

    public Integer getAttendCount() {
        return attendCount;
    }

    public void setAttendCount(Integer attendCount) {
        this.attendCount = attendCount;
    }

    public Integer getFansCount() {
        return fansCount;
    }

    public void setFansCount(Integer fansCount) {
        this.fansCount = fansCount;
    }

    public Integer getCollectProductCount() {
        return collectProductCount;
    }

    public void setCollectProductCount(Integer collectProductCount) {
        this.collectProductCount = collectProductCount;
    }

    public Integer getCollectSubjectCount() {
        return collectSubjectCount;
    }

    public void setCollectSubjectCount(Integer collectSubjectCount) {
        this.collectSubjectCount = collectSubjectCount;
    }

    public Integer getCollectTopicCount() {
        return collectTopicCount;
    }

    public void setCollectTopicCount(Integer collectTopicCount) {
        this.collectTopicCount = collectTopicCount;
    }

    public Integer getCollectCommentCount() {
        return collectCommentCount;
    }

    public void setCollectCommentCount(Integer collectCommentCount) {
        this.collectCommentCount = collectCommentCount;
    }

    public Integer getInviteFriendCount() {
        return inviteFriendCount;
    }

    public void setInviteFriendCount(Integer inviteFriendCount) {
        this.inviteFriendCount = inviteFriendCount;
    }

    public Date getRecentOrderTime() {
        return recentOrderTime;
    }

    public void setRecentOrderTime(Date recentOrderTime) {
        this.recentOrderTime = recentOrderTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", memberId=").append(memberId);
        sb.append(", consumeAmount=").append(consumeAmount);
        sb.append(", orderCount=").append(orderCount);
        sb.append(", couponCount=").append(couponCount);
        sb.append(", commentCount=").append(commentCount);
        sb.append(", returnOrderCount=").append(returnOrderCount);
        sb.append(", loginCount=").append(loginCount);
        sb.append(", attendCount=").append(attendCount);
        sb.append(", fansCount=").append(fansCount);
        sb.append(", collectProductCount=").append(collectProductCount);
        sb.append(", collectSubjectCount=").append(collectSubjectCount);
        sb.append(", collectTopicCount=").append(collectTopicCount);
        sb.append(", collectCommentCount=").append(collectCommentCount);
        sb.append(", inviteFriendCount=").append(inviteFriendCount);
        sb.append(", recentOrderTime=").append(recentOrderTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        UmsMemberStatisticsInfo other = (UmsMemberStatisticsInfo) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getMemberId() == null ? other.getMemberId() == null : this.getMemberId().equals(other.getMemberId()))
            && (this.getConsumeAmount() == null ? other.getConsumeAmount() == null : this.getConsumeAmount().equals(other.getConsumeAmount()))
            && (this.getOrderCount() == null ? other.getOrderCount() == null : this.getOrderCount().equals(other.getOrderCount()))
            && (this.getCouponCount() == null ? other.getCouponCount() == null : this.getCouponCount().equals(other.getCouponCount()))
            && (this.getCommentCount() == null ? other.getCommentCount() == null : this.getCommentCount().equals(other.getCommentCount()))
            && (this.getReturnOrderCount() == null ? other.getReturnOrderCount() == null : this.getReturnOrderCount().equals(other.getReturnOrderCount()))
            && (this.getLoginCount() == null ? other.getLoginCount() == null : this.getLoginCount().equals(other.getLoginCount()))
            && (this.getAttendCount() == null ? other.getAttendCount() == null : this.getAttendCount().equals(other.getAttendCount()))
            && (this.getFansCount() == null ? other.getFansCount() == null : this.getFansCount().equals(other.getFansCount()))
            && (this.getCollectProductCount() == null ? other.getCollectProductCount() == null : this.getCollectProductCount().equals(other.getCollectProductCount()))
            && (this.getCollectSubjectCount() == null ? other.getCollectSubjectCount() == null : this.getCollectSubjectCount().equals(other.getCollectSubjectCount()))
            && (this.getCollectTopicCount() == null ? other.getCollectTopicCount() == null : this.getCollectTopicCount().equals(other.getCollectTopicCount()))
            && (this.getCollectCommentCount() == null ? other.getCollectCommentCount() == null : this.getCollectCommentCount().equals(other.getCollectCommentCount()))
            && (this.getInviteFriendCount() == null ? other.getInviteFriendCount() == null : this.getInviteFriendCount().equals(other.getInviteFriendCount()))
            && (this.getRecentOrderTime() == null ? other.getRecentOrderTime() == null : this.getRecentOrderTime().equals(other.getRecentOrderTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getMemberId() == null) ? 0 : getMemberId().hashCode());
        result = prime * result + ((getConsumeAmount() == null) ? 0 : getConsumeAmount().hashCode());
        result = prime * result + ((getOrderCount() == null) ? 0 : getOrderCount().hashCode());
        result = prime * result + ((getCouponCount() == null) ? 0 : getCouponCount().hashCode());
        result = prime * result + ((getCommentCount() == null) ? 0 : getCommentCount().hashCode());
        result = prime * result + ((getReturnOrderCount() == null) ? 0 : getReturnOrderCount().hashCode());
        result = prime * result + ((getLoginCount() == null) ? 0 : getLoginCount().hashCode());
        result = prime * result + ((getAttendCount() == null) ? 0 : getAttendCount().hashCode());
        result = prime * result + ((getFansCount() == null) ? 0 : getFansCount().hashCode());
        result = prime * result + ((getCollectProductCount() == null) ? 0 : getCollectProductCount().hashCode());
        result = prime * result + ((getCollectSubjectCount() == null) ? 0 : getCollectSubjectCount().hashCode());
        result = prime * result + ((getCollectTopicCount() == null) ? 0 : getCollectTopicCount().hashCode());
        result = prime * result + ((getCollectCommentCount() == null) ? 0 : getCollectCommentCount().hashCode());
        result = prime * result + ((getInviteFriendCount() == null) ? 0 : getInviteFriendCount().hashCode());
        result = prime * result + ((getRecentOrderTime() == null) ? 0 : getRecentOrderTime().hashCode());
        return result;
    }
}