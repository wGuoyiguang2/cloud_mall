package com.hqtc.bms.model.rpc;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by wanghaoyang on 18-7-13.
 */
public class OrderRepVo2 {

    private String jdOrderId;
    private BigDecimal orderPrice;//": 11,
    private BigDecimal orderNakedPrice;//": 11,
    private BigDecimal orderTaxPrice;//": 11,
    private BigDecimal freight;//": 5
    private List<SkuVo> sku;

    public String getJdOrderId() {
        return jdOrderId;
    }

    public void setJdOrderId(String jdOrderId) {
        this.jdOrderId = jdOrderId;
    }

    public BigDecimal getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(BigDecimal orderPrice) {
        this.orderPrice = orderPrice;
    }

    public BigDecimal getOrderNakedPrice() {
        return orderNakedPrice;
    }

    public void setOrderNakedPrice(BigDecimal orderNakedPrice) {
        this.orderNakedPrice = orderNakedPrice;
    }

    public BigDecimal getOrderTaxPrice() {
        return orderTaxPrice;
    }

    public void setOrderTaxPrice(BigDecimal orderTaxPrice) {
        this.orderTaxPrice = orderTaxPrice;
    }

    public BigDecimal getFreight() {
        return freight;
    }

    public void setFreight(BigDecimal freight) {
        this.freight = freight;
    }

    public List<SkuVo> getSku() {
        return sku;
    }

    public void setSku(List<SkuVo> sku) {
        this.sku = sku;
    }
}

