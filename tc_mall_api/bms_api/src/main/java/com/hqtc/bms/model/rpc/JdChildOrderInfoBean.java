package com.hqtc.bms.model.rpc;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

/**
 * Created by wanghaoyang on 18-7-31.
 */
public class JdChildOrderInfoBean {
    private BigInteger pOrder;
    private int jdOrderState;
    private int orderState;
    private BigInteger jdOrderId;
    private int state;
    private BigDecimal freight;
    private int submitState;
    private BigDecimal orderPrice;
    private BigDecimal orderNakedPrice;
    private int type;
    private BigDecimal orderTaxPrice;
    private List<JdSkuInfoBean> sku;

    public BigInteger getpOrder() {
        return pOrder;
    }

    public void setpOrder(BigInteger pOrder) {
        this.pOrder = pOrder;
    }

    public int getJdOrderState() {
        return jdOrderState;
    }

    public void setJdOrderState(int jdOrderState) {
        this.jdOrderState = jdOrderState;
    }

    public int getOrderState() {
        return orderState;
    }

    public void setOrderState(int orderState) {
        this.orderState = orderState;
    }

    public BigInteger getJdOrderId() {
        return jdOrderId;
    }

    public void setJdOrderId(BigInteger jdOrderId) {
        this.jdOrderId = jdOrderId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public BigDecimal getFreight() {
        return freight;
    }

    public void setFreight(BigDecimal freight) {
        this.freight = freight;
    }

    public int getSubmitState() {
        return submitState;
    }

    public void setSubmitState(int submitState) {
        this.submitState = submitState;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public BigDecimal getOrderTaxPrice() {
        return orderTaxPrice;
    }

    public void setOrderTaxPrice(BigDecimal orderTaxPrice) {
        this.orderTaxPrice = orderTaxPrice;
    }

    public List<JdSkuInfoBean> getSku() {
        return sku;
    }

    public void setSku(List<JdSkuInfoBean> sku) {
        this.sku = sku;
    }
}
