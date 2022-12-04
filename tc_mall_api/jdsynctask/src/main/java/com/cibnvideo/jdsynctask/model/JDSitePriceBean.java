package com.cibnvideo.jdsynctask.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2019/1/21 17:18
 */
public class JDSitePriceBean implements Serializable{
    private Long sku;
    private BigDecimal price;

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
