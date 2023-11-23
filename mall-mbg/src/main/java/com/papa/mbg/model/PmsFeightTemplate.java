package com.papa.mbg.model;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;

public class PmsFeightTemplate implements Serializable {
    private Long id;

    private String name;

    private Integer chargeType;

    private BigDecimal firstWeight;

    private BigDecimal firstFee;

    private BigDecimal continueWeight;

    private BigDecimal continmeFee;

    private String dest;

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

    public Integer getChargeType() {
        return chargeType;
    }

    public void setChargeType(Integer chargeType) {
        this.chargeType = chargeType;
    }

    public BigDecimal getFirstWeight() {
        return firstWeight;
    }

    public void setFirstWeight(BigDecimal firstWeight) {
        this.firstWeight = firstWeight;
    }

    public BigDecimal getFirstFee() {
        return firstFee;
    }

    public void setFirstFee(BigDecimal firstFee) {
        this.firstFee = firstFee;
    }

    public BigDecimal getContinueWeight() {
        return continueWeight;
    }

    public void setContinueWeight(BigDecimal continueWeight) {
        this.continueWeight = continueWeight;
    }

    public BigDecimal getContinmeFee() {
        return continmeFee;
    }

    public void setContinmeFee(BigDecimal continmeFee) {
        this.continmeFee = continmeFee;
    }

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", chargeType=").append(chargeType);
        sb.append(", firstWeight=").append(firstWeight);
        sb.append(", firstFee=").append(firstFee);
        sb.append(", continueWeight=").append(continueWeight);
        sb.append(", continmeFee=").append(continmeFee);
        sb.append(", dest=").append(dest);
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
        PmsFeightTemplate other = (PmsFeightTemplate) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getChargeType() == null ? other.getChargeType() == null : this.getChargeType().equals(other.getChargeType()))
            && (this.getFirstWeight() == null ? other.getFirstWeight() == null : this.getFirstWeight().equals(other.getFirstWeight()))
            && (this.getFirstFee() == null ? other.getFirstFee() == null : this.getFirstFee().equals(other.getFirstFee()))
            && (this.getContinueWeight() == null ? other.getContinueWeight() == null : this.getContinueWeight().equals(other.getContinueWeight()))
            && (this.getContinmeFee() == null ? other.getContinmeFee() == null : this.getContinmeFee().equals(other.getContinmeFee()))
            && (this.getDest() == null ? other.getDest() == null : this.getDest().equals(other.getDest()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getChargeType() == null) ? 0 : getChargeType().hashCode());
        result = prime * result + ((getFirstWeight() == null) ? 0 : getFirstWeight().hashCode());
        result = prime * result + ((getFirstFee() == null) ? 0 : getFirstFee().hashCode());
        result = prime * result + ((getContinueWeight() == null) ? 0 : getContinueWeight().hashCode());
        result = prime * result + ((getContinmeFee() == null) ? 0 : getContinmeFee().hashCode());
        result = prime * result + ((getDest() == null) ? 0 : getDest().hashCode());
        return result;
    }
}