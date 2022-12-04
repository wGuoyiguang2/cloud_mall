package com.cibnvideo.jdsynctask.model;


import java.io.Serializable;

public class MessageInfo implements Serializable {
    private Integer type;
    private Long sku;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getSku() {
        return sku;
    }

    public void setSku(Long sku) {
        this.sku = sku;
    }
}
