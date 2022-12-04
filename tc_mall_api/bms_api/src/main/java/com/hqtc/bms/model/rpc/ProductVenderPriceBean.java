package com.hqtc.bms.model.rpc;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by wanghaoyang on 18-7-4.
 */
public class ProductVenderPriceBean implements Serializable{

    private Long skuId;
    private BigDecimal price;
    private BigDecimal jdPrice;
    private BigDecimal tradePrice;

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public BigDecimal getJdPrice() {
        return jdPrice;
    }

    public void setJdPrice(BigDecimal jdPrice) {
        this.jdPrice = jdPrice;
    }

    public BigDecimal getTradePrice() {
        return tradePrice;
    }

    public void setTradePrice(BigDecimal tradePrice) {
        this.tradePrice = tradePrice;
    }
}
