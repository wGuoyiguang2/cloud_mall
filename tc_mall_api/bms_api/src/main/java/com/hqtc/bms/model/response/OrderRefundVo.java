package com.hqtc.bms.model.response;

import java.math.BigDecimal;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2019/2/17 15:47
 */
public class OrderRefundVo {
    private String parentId;
    private String orderSn;
    private String tradeNo;
    private String unqId;
    private String payOrderSn;
    private Long productId;
    private String productName;
    private BigDecimal price;
    private BigDecimal payPrice;
    private BigDecimal cardPrice;
    private Integer refundStatus;
    private String failedReason;

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getUnqId() {
        return unqId;
    }

    public void setUnqId(String unqId) {
        this.unqId = unqId;
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

    public String getPayOrderSn() {
        return payOrderSn;
    }

    public void setPayOrderSn(String payOrderSn) {
        this.payOrderSn = payOrderSn;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
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

    public BigDecimal getCardPrice() {
        return cardPrice;
    }

    public void setCardPrice(BigDecimal cardPrice) {
        this.cardPrice = cardPrice;
    }

    public Integer getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(Integer refundStatus) {
        this.refundStatus = refundStatus;
    }

    public String getFailedReason() {
        return failedReason;
    }

    public void setFailedReason(String failedReason) {
        this.failedReason = failedReason;
    }
}
