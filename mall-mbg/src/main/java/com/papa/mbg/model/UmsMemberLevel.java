package com.papa.mbg.model;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;

public class UmsMemberLevel implements Serializable {
    private Long id;

    private String name;

    private Integer growthPoint;

    private Integer defaultStatus;

    private BigDecimal freeFreightPoint;

    private Integer commentGrowthPoint;

    private Integer priviledgeFreeFreight;

    private Integer priviledgeSignIn;

    private Integer priviledgeComment;

    private Integer priviledgePromotion;

    private Integer priviledgeMemberPrice;

    private Integer priviledgeBirthday;

    private String note;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGrowthPoint() {
        return growthPoint;
    }

    public void setGrowthPoint(Integer growthPoint) {
        this.growthPoint = growthPoint;
    }

    public Integer getDefaultStatus() {
        return defaultStatus;
    }

    public void setDefaultStatus(Integer defaultStatus) {
        this.defaultStatus = defaultStatus;
    }

    public BigDecimal getFreeFreightPoint() {
        return freeFreightPoint;
    }

    public void setFreeFreightPoint(BigDecimal freeFreightPoint) {
        this.freeFreightPoint = freeFreightPoint;
    }

    public Integer getCommentGrowthPoint() {
        return commentGrowthPoint;
    }

    public void setCommentGrowthPoint(Integer commentGrowthPoint) {
        this.commentGrowthPoint = commentGrowthPoint;
    }

    public Integer getPriviledgeFreeFreight() {
        return priviledgeFreeFreight;
    }

    public void setPriviledgeFreeFreight(Integer priviledgeFreeFreight) {
        this.priviledgeFreeFreight = priviledgeFreeFreight;
    }

    public Integer getPriviledgeSignIn() {
        return priviledgeSignIn;
    }

    public void setPriviledgeSignIn(Integer priviledgeSignIn) {
        this.priviledgeSignIn = priviledgeSignIn;
    }

    public Integer getPriviledgeComment() {
        return priviledgeComment;
    }

    public void setPriviledgeComment(Integer priviledgeComment) {
        this.priviledgeComment = priviledgeComment;
    }

    public Integer getPriviledgePromotion() {
        return priviledgePromotion;
    }

    public void setPriviledgePromotion(Integer priviledgePromotion) {
        this.priviledgePromotion = priviledgePromotion;
    }

    public Integer getPriviledgeMemberPrice() {
        return priviledgeMemberPrice;
    }

    public void setPriviledgeMemberPrice(Integer priviledgeMemberPrice) {
        this.priviledgeMemberPrice = priviledgeMemberPrice;
    }

    public Integer getPriviledgeBirthday() {
        return priviledgeBirthday;
    }

    public void setPriviledgeBirthday(Integer priviledgeBirthday) {
        this.priviledgeBirthday = priviledgeBirthday;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", growthPoint=").append(growthPoint);
        sb.append(", defaultStatus=").append(defaultStatus);
        sb.append(", freeFreightPoint=").append(freeFreightPoint);
        sb.append(", commentGrowthPoint=").append(commentGrowthPoint);
        sb.append(", priviledgeFreeFreight=").append(priviledgeFreeFreight);
        sb.append(", priviledgeSignIn=").append(priviledgeSignIn);
        sb.append(", priviledgeComment=").append(priviledgeComment);
        sb.append(", priviledgePromotion=").append(priviledgePromotion);
        sb.append(", priviledgeMemberPrice=").append(priviledgeMemberPrice);
        sb.append(", priviledgeBirthday=").append(priviledgeBirthday);
        sb.append(", note=").append(note);
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
        UmsMemberLevel other = (UmsMemberLevel) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getGrowthPoint() == null ? other.getGrowthPoint() == null : this.getGrowthPoint().equals(other.getGrowthPoint()))
            && (this.getDefaultStatus() == null ? other.getDefaultStatus() == null : this.getDefaultStatus().equals(other.getDefaultStatus()))
            && (this.getFreeFreightPoint() == null ? other.getFreeFreightPoint() == null : this.getFreeFreightPoint().equals(other.getFreeFreightPoint()))
            && (this.getCommentGrowthPoint() == null ? other.getCommentGrowthPoint() == null : this.getCommentGrowthPoint().equals(other.getCommentGrowthPoint()))
            && (this.getPriviledgeFreeFreight() == null ? other.getPriviledgeFreeFreight() == null : this.getPriviledgeFreeFreight().equals(other.getPriviledgeFreeFreight()))
            && (this.getPriviledgeSignIn() == null ? other.getPriviledgeSignIn() == null : this.getPriviledgeSignIn().equals(other.getPriviledgeSignIn()))
            && (this.getPriviledgeComment() == null ? other.getPriviledgeComment() == null : this.getPriviledgeComment().equals(other.getPriviledgeComment()))
            && (this.getPriviledgePromotion() == null ? other.getPriviledgePromotion() == null : this.getPriviledgePromotion().equals(other.getPriviledgePromotion()))
            && (this.getPriviledgeMemberPrice() == null ? other.getPriviledgeMemberPrice() == null : this.getPriviledgeMemberPrice().equals(other.getPriviledgeMemberPrice()))
            && (this.getPriviledgeBirthday() == null ? other.getPriviledgeBirthday() == null : this.getPriviledgeBirthday().equals(other.getPriviledgeBirthday()))
            && (this.getNote() == null ? other.getNote() == null : this.getNote().equals(other.getNote()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getGrowthPoint() == null) ? 0 : getGrowthPoint().hashCode());
        result = prime * result + ((getDefaultStatus() == null) ? 0 : getDefaultStatus().hashCode());
        result = prime * result + ((getFreeFreightPoint() == null) ? 0 : getFreeFreightPoint().hashCode());
        result = prime * result + ((getCommentGrowthPoint() == null) ? 0 : getCommentGrowthPoint().hashCode());
        result = prime * result + ((getPriviledgeFreeFreight() == null) ? 0 : getPriviledgeFreeFreight().hashCode());
        result = prime * result + ((getPriviledgeSignIn() == null) ? 0 : getPriviledgeSignIn().hashCode());
        result = prime * result + ((getPriviledgeComment() == null) ? 0 : getPriviledgeComment().hashCode());
        result = prime * result + ((getPriviledgePromotion() == null) ? 0 : getPriviledgePromotion().hashCode());
        result = prime * result + ((getPriviledgeMemberPrice() == null) ? 0 : getPriviledgeMemberPrice().hashCode());
        result = prime * result + ((getPriviledgeBirthday() == null) ? 0 : getPriviledgeBirthday().hashCode());
        result = prime * result + ((getNote() == null) ? 0 : getNote().hashCode());
        return result;
    }
}