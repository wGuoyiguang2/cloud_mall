package com.hqtc.bms.model.response;

import java.math.BigDecimal;

/**
 * Created by wanghaoyang on 18-7-9.
 */
public class UpperOrderResponse {
    private String jdOrderSn;
    private BigDecimal feight;
    private BigDecimal orderPrice;
    private BigDecimal totalFee;

    public String getJdOrderSn() {
        return jdOrderSn;
    }

    public void setJdOrderSn(String jdOrderSn) {
        this.jdOrderSn = jdOrderSn;
    }

    public BigDecimal getFeight() {
        return feight;
    }

    public void setFeight(BigDecimal feight) {
        this.feight = feight;
    }

    public BigDecimal getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(BigDecimal orderPrice) {
        this.orderPrice = orderPrice;
    }

    public BigDecimal getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(BigDecimal totalFee) {
        this.totalFee = totalFee;
    }
}
