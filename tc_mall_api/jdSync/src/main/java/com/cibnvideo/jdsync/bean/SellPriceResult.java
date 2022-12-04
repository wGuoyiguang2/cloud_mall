package com.cibnvideo.jdsync.bean;

import java.io.Serializable;
import java.math.BigDecimal;

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
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        } else if (obj instanceof SellPriceResult) {
            SellPriceResult sellPriceObj = (SellPriceResult) obj;
            if ((this.skuId == null ? sellPriceObj.getSkuId() == null : this.skuId.equals(sellPriceObj.getSkuId())) &&
                    (this.jdPrice == null ? sellPriceObj.getJdPrice() == null : this.jdPrice.equals(sellPriceObj.getJdPrice())) &&
                    (this.price == null ? sellPriceObj.getPrice() == null : this.price.equals(sellPriceObj.getPrice()))) {
                return true;
            }

        }
        return false;
    }

    private BigDecimal price;
    private BigDecimal jdPrice;
    private Long skuId;
}
