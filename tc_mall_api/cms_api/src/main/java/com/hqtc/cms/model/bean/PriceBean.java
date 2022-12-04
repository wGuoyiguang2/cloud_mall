package com.hqtc.cms.model.bean;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * description:状态价格bean
 * Created by laiqingchuang on 19-1-10 .
 */
public class PriceBean implements Serializable {
    private Long sku;
    private BigDecimal price;	  //零售价

    public Long getSku() {
        return sku;
    }

    public void setSku(Long sku) {
        this.sku = sku;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

}
