package com.papa.mbg.model;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

public class PmsComment implements Serializable {
    private Long id;

    private Long productId;

    private String memberNickName;

    private String productName;

    private Integer star;

    private String memberIp;

    private Date createTime;

    private Integer showStatus;

    private String productAttribute;

    private Integer collectCouont;

    private Integer readCount;

    private String pics;

    private String memberIcon;

    private Integer replayCount;

    private String content;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getMemberNickName() {
        return memberNickName;
    }

    public void setMemberNickName(String memberNickName) {
        this.memberNickName = memberNickName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getStar() {
        return star;
    }

    public void setStar(Integer star) {
        this.star = star;
    }

    public String getMemberIp() {
        return memberIp;
    }

    public void setMemberIp(String memberIp) {
        this.memberIp = memberIp;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getShowStatus() {
        return showStatus;
    }

    public void setShowStatus(Integer showStatus) {
        this.showStatus = showStatus;
    }

    public String getProductAttribute() {
        return productAttribute;
    }

    public void setProductAttribute(String productAttribute) {
        this.productAttribute = productAttribute;
    }

    public Integer getCollectCouont() {
        return collectCouont;
    }

    public void setCollectCouont(Integer collectCouont) {
        this.collectCouont = collectCouont;
    }

    public Integer getReadCount() {
        return readCount;
    }

    public void setReadCount(Integer readCount) {
        this.readCount = readCount;
    }

    public String getPics() {
        return pics;
    }

    public void setPics(String pics) {
        this.pics = pics;
    }

    public String getMemberIcon() {
        return memberIcon;
    }

    public void setMemberIcon(String memberIcon) {
        this.memberIcon = memberIcon;
    }

    public Integer getReplayCount() {
        return replayCount;
    }

    public void setReplayCount(Integer replayCount) {
        this.replayCount = replayCount;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", productId=").append(productId);
        sb.append(", memberNickName=").append(memberNickName);
        sb.append(", productName=").append(productName);
        sb.append(", star=").append(star);
        sb.append(", memberIp=").append(memberIp);
        sb.append(", createTime=").append(createTime);
        sb.append(", showStatus=").append(showStatus);
        sb.append(", productAttribute=").append(productAttribute);
        sb.append(", collectCouont=").append(collectCouont);
        sb.append(", readCount=").append(readCount);
        sb.append(", pics=").append(pics);
        sb.append(", memberIcon=").append(memberIcon);
        sb.append(", replayCount=").append(replayCount);
        sb.append(", content=").append(content);
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
        PmsComment other = (PmsComment) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getProductId() == null ? other.getProductId() == null : this.getProductId().equals(other.getProductId()))
            && (this.getMemberNickName() == null ? other.getMemberNickName() == null : this.getMemberNickName().equals(other.getMemberNickName()))
            && (this.getProductName() == null ? other.getProductName() == null : this.getProductName().equals(other.getProductName()))
            && (this.getStar() == null ? other.getStar() == null : this.getStar().equals(other.getStar()))
            && (this.getMemberIp() == null ? other.getMemberIp() == null : this.getMemberIp().equals(other.getMemberIp()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getShowStatus() == null ? other.getShowStatus() == null : this.getShowStatus().equals(other.getShowStatus()))
            && (this.getProductAttribute() == null ? other.getProductAttribute() == null : this.getProductAttribute().equals(other.getProductAttribute()))
            && (this.getCollectCouont() == null ? other.getCollectCouont() == null : this.getCollectCouont().equals(other.getCollectCouont()))
            && (this.getReadCount() == null ? other.getReadCount() == null : this.getReadCount().equals(other.getReadCount()))
            && (this.getPics() == null ? other.getPics() == null : this.getPics().equals(other.getPics()))
            && (this.getMemberIcon() == null ? other.getMemberIcon() == null : this.getMemberIcon().equals(other.getMemberIcon()))
            && (this.getReplayCount() == null ? other.getReplayCount() == null : this.getReplayCount().equals(other.getReplayCount()))
            && (this.getContent() == null ? other.getContent() == null : this.getContent().equals(other.getContent()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getProductId() == null) ? 0 : getProductId().hashCode());
        result = prime * result + ((getMemberNickName() == null) ? 0 : getMemberNickName().hashCode());
        result = prime * result + ((getProductName() == null) ? 0 : getProductName().hashCode());
        result = prime * result + ((getStar() == null) ? 0 : getStar().hashCode());
        result = prime * result + ((getMemberIp() == null) ? 0 : getMemberIp().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getShowStatus() == null) ? 0 : getShowStatus().hashCode());
        result = prime * result + ((getProductAttribute() == null) ? 0 : getProductAttribute().hashCode());
        result = prime * result + ((getCollectCouont() == null) ? 0 : getCollectCouont().hashCode());
        result = prime * result + ((getReadCount() == null) ? 0 : getReadCount().hashCode());
        result = prime * result + ((getPics() == null) ? 0 : getPics().hashCode());
        result = prime * result + ((getMemberIcon() == null) ? 0 : getMemberIcon().hashCode());
        result = prime * result + ((getReplayCount() == null) ? 0 : getReplayCount().hashCode());
        result = prime * result + ((getContent() == null) ? 0 : getContent().hashCode());
        return result;
    }
}