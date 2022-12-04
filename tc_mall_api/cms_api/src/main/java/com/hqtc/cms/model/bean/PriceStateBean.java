package com.hqtc.cms.model.bean;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by laiqingchuang on 19-1-25 .
 */
public class PriceStateBean implements Serializable {
    private Long sku;
    private Integer state;
    private BigDecimal price;	  //零售价

    public Long getSku() {
        return sku;
    }

    public void setSku(Long sku) {
        this.sku = sku;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
