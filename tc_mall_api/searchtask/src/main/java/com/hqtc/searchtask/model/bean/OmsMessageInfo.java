package com.hqtc.searchtask.model.bean;


import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

public class OmsMessageInfo implements Serializable {
    private Integer type;
    private Integer venderId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long sku;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer collectionId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer catId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer catType;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getVenderId() {
        return venderId;
    }

    public void setVenderId(Integer venderId) {
        this.venderId = venderId;
    }

    public Long getSku() {
        return sku;
    }

    public void setSku(Long sku) {
        this.sku = sku;
    }

    public Integer getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(Integer collectionId) {
        this.collectionId = collectionId;
    }

    public Integer getCatId() {
        return catId;
    }

    public void setCatId(Integer catId) {
        this.catId = catId;
    }

    public Integer getCatType() {
        return catType;
    }

    public void setCatType(Integer catType) {
        this.catType = catType;
    }

    @Override
    public String toString(){
        return "type:" + type + "\n"
                +"venderId:" + venderId + "\n"
                +"sku:" + sku + "\n"
                +"collectionId:" + collectionId + "\n"
                +"catId:" + catId + "\n"
                +"catType:" + catType;
    }
}
