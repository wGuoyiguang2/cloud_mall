package com.cibnvideo.jdsynctask.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class SellPriceResult implements Serializable {
    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SellPriceResult that = (SellPriceResult) o;
        return (price == null ? that.price == null : price.compareTo(that.price) == 0) &&
                (jdPrice == null ? that.jdPrice == null : jdPrice.compareTo(that.jdPrice) == 0) &&
                Objects.equals(skuId, that.skuId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(price, jdPrice, skuId);
    }

    private BigDecimal price;
    private BigDecimal jdPrice;
    private Long skuId;
}
