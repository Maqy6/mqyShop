package com.shop.pojo;

public class ProductType {
    private Integer typeId;     //商品类型ID

    private String typeName;    //商品类型名称

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName == null ? null : typeName.trim();
    }
}