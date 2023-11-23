package com.papa.mbg.model;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;

public class UmsMemberRuleSetting implements Serializable {
    private Long id;

    private Integer continueSignDay;

    private Integer continueSignPoint;

    private BigDecimal consumePerPoint;

    private BigDecimal lowOrderAmount;

    private Integer maxPointPerOrder;

    private Integer type;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getContinueSignDay() {
        return continueSignDay;
    }

    public void setContinueSignDay(Integer continueSignDay) {
        this.continueSignDay = continueSignDay;
    }

    public Integer getContinueSignPoint() {
        return continueSignPoint;
    }

    public void setContinueSignPoint(Integer continueSignPoint) {
        this.continueSignPoint = continueSignPoint;
    }

    public BigDecimal getConsumePerPoint() {
        return consumePerPoint;
    }

    public void setConsumePerPoint(BigDecimal consumePerPoint) {
        this.consumePerPoint = consumePerPoint;
    }

    public BigDecimal getLowOrderAmount() {
        return lowOrderAmount;
    }

    public void setLowOrderAmount(BigDecimal lowOrderAmount) {
        this.lowOrderAmount = lowOrderAmount;
    }

    public Integer getMaxPointPerOrder() {
        return maxPointPerOrder;
    }

    public void setMaxPointPerOrder(Integer maxPointPerOrder) {
        this.maxPointPerOrder = maxPointPerOrder;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", continueSignDay=").append(continueSignDay);
        sb.append(", continueSignPoint=").append(continueSignPoint);
        sb.append(", consumePerPoint=").append(consumePerPoint);
        sb.append(", lowOrderAmount=").append(lowOrderAmount);
        sb.append(", maxPointPerOrder=").append(maxPointPerOrder);
        sb.append(", type=").append(type);
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
        UmsMemberRuleSetting other = (UmsMemberRuleSetting) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getContinueSignDay() == null ? other.getContinueSignDay() == null : this.getContinueSignDay().equals(other.getContinueSignDay()))
            && (this.getContinueSignPoint() == null ? other.getContinueSignPoint() == null : this.getContinueSignPoint().equals(other.getContinueSignPoint()))
            && (this.getConsumePerPoint() == null ? other.getConsumePerPoint() == null : this.getConsumePerPoint().equals(other.getConsumePerPoint()))
            && (this.getLowOrderAmount() == null ? other.getLowOrderAmount() == null : this.getLowOrderAmount().equals(other.getLowOrderAmount()))
            && (this.getMaxPointPerOrder() == null ? other.getMaxPointPerOrder() == null : this.getMaxPointPerOrder().equals(other.getMaxPointPerOrder()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getContinueSignDay() == null) ? 0 : getContinueSignDay().hashCode());
        result = prime * result + ((getContinueSignPoint() == null) ? 0 : getContinueSignPoint().hashCode());
        result = prime * result + ((getConsumePerPoint() == null) ? 0 : getConsumePerPoint().hashCode());
        result = prime * result + ((getLowOrderAmount() == null) ? 0 : getLowOrderAmount().hashCode());
        result = prime * result + ((getMaxPointPerOrder() == null) ? 0 : getMaxPointPerOrder().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        return result;
    }
}