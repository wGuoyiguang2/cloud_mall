package com.cibnvideo.thirdpart.model.bean;

import java.math.BigDecimal;


/**
 * @Description:
 * @Author: WangBin
 * @Date: 2019/1/21 17:23
 */
public class ProductBean{
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
