package com.hqtc.cms.model.bean.search;

import java.math.BigDecimal;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/7/7 15:21
 */
public class SellPriceResult {
    private BigDecimal price;
    private BigDecimal jdPrice;
    private Long skuId;

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getJdPrice() {
        return jdPrice;
    }

    public void setJdPrice(BigDecimal jdPrice) {
        this.jdPrice = jdPrice;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }
}
