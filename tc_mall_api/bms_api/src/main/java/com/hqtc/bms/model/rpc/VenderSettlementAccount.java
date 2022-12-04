package com.hqtc.bms.model.rpc;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class VenderSettlementAccount implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private Integer venderId;

    private Integer type;

    private String orderSn;//订单号

    private String tradeNo;//京东订单号

    private BigDecimal agreePrice;//批发价

    private BigDecimal price;//商品零售价

    private BigDecimal payPrice;//客户支付金额

    private BigDecimal cardPrice;//卡支付金额

    private BigDecimal freight;//运费

    private Integer payType;//客户支付类型 1:微信|2:支付宝

    private Integer isSettle;//是否结算 0:未结算|1:已结算

    private Date ctime;

    private Date utime;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVenderId() {
        return venderId;
    }

    public void setVenderId(Integer venderId) {
        this.venderId = venderId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public BigDecimal getAgreePrice() {
        return agreePrice;
    }

    public void setAgreePrice(BigDecimal agreePrice) {
        this.agreePrice = agreePrice;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPayPrice() {
        return payPrice;
    }

    public void setPayPrice(BigDecimal payPrice) {
        this.payPrice = payPrice;
    }

    public BigDecimal getFreight() {
        return freight;
    }

    public void setFreight(BigDecimal freight) {
        this.freight = freight;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public Integer getIsSettle() {
        return isSettle;
    }

    public void setIsSettle(Integer isSettle) {
        this.isSettle = isSettle;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    public Date getUtime() {
        return utime;
    }

    public void setUtime(Date utime) {
        this.utime = utime;
    }

    public BigDecimal getCardPrice() {
        return cardPrice;
    }

    public void setCardPrice(BigDecimal cardPrice) {
        this.cardPrice = cardPrice;
    }
}
