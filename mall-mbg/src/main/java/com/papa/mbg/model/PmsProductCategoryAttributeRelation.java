package com.papa.mbg.model;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class PmsProductCategoryAttributeRelation implements Serializable {
    private Long id;

    private Long productCategoryId;

    private Long productAttributeId;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(Long productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public Long getProductAttributeId() {
        return productAttributeId;
    }

    public void setProductAttributeId(Long productAttributeId) {
        this.productAttributeId = productAttributeId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", productCategoryId=").append(productCategoryId);
        sb.append(", productAttributeId=").append(productAttributeId);
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
        PmsProductCategoryAttributeRelation other = (PmsProductCategoryAttributeRelation) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getProductCategoryId() == null ? other.getProductCategoryId() == null : this.getProductCategoryId().equals(other.getProductCategoryId()))
            && (this.getProductAttributeId() == null ? other.getProductAttributeId() == null : this.getProductAttributeId().equals(other.getProductAttributeId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getProductCategoryId() == null) ? 0 : getProductCategoryId().hashCode());
        result = prime * result + ((getProductAttributeId() == null) ? 0 : getProductAttributeId().hashCode());
        return result;
    }
}