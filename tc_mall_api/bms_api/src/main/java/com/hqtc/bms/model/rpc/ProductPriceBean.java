package com.hqtc.bms.model.rpc;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by wanghaoyang on 18-7-5.
 */
public class ProductPriceBean implements Serializable {
    private long sku;
    private String name;
    private BigDecimal price;
    private BigDecimal agree_price;
    private BigDecimal pay_price;

    public long getSku() {
        return sku;
    }

    public void setSku(long sku) {
        this.sku = sku;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getAgree_price() {
        return agree_price;
    }

    public void setAgree_price(BigDecimal agree_price) {
        this.agree_price = agree_price;
    }

    public BigDecimal getPay_price() {
        return pay_price;
    }

    public void setPay_price(BigDecimal pay_price) {
        this.pay_price = pay_price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
