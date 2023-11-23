package com.papa.mbg.model;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class PmsProductAttributeCategory implements Serializable {
    private Long id;

    private String name;

    private Integer attributeCount;

    private Integer paramCount;

    private Long productCategoryId = 0L;

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

    public Integer getAttributeCount() {
        return attributeCount;
    }

    public void setAttributeCount(Integer attributeCount) {
        this.attributeCount = attributeCount;
    }

    public Integer getParamCount() {
        return paramCount;
    }

    public void setParamCount(Integer paramCount) {
        this.paramCount = paramCount;
    }

    public Long getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(Long productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", attributeCount=").append(attributeCount);
        sb.append(", paramCount=").append(paramCount);
        sb.append(", productCategoryId=").append(productCategoryId);
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
        PmsProductAttributeCategory other = (PmsProductAttributeCategory) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getAttributeCount() == null ? other.getAttributeCount() == null : this.getAttributeCount().equals(other.getAttributeCount()))
            && (this.getParamCount() == null ? other.getParamCount() == null : this.getParamCount().equals(other.getParamCount()))
            && (this.getProductCategoryId() == null ? other.getProductCategoryId() == null : this.getProductCategoryId().equals(other.getProductCategoryId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getAttributeCount() == null) ? 0 : getAttributeCount().hashCode());
        result = prime * result + ((getParamCount() == null) ? 0 : getParamCount().hashCode());
        result = prime * result + ((getProductCategoryId() == null) ? 0 : getProductCategoryId().hashCode());
        return result;
    }
}