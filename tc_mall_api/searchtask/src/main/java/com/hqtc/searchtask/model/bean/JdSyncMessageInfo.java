package com.hqtc.searchtask.model.bean;


import java.io.Serializable;

public class JdSyncMessageInfo implements Serializable {
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

    @Override
    public String toString(){
        return "type:" + type + "\n"
                + "sku:" + sku;
    }
}
