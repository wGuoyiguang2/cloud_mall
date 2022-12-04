package com.cibnvideo.oms.tcmallcustomer.bean;

import java.math.BigDecimal;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/7/24 15:02
 */
public class VenderOrderManagerVo {
    private Integer venderId;
    private String venderName;
    private BigDecimal pricePercent;
    private Integer payType;
    private BigDecimal soldMoney;
    private BigDecimal accountBalance;
    private BigDecimal paidMoney;
    private BigDecimal unPaidMoney;
    private Integer orderCount;

    public BigDecimal getPricePercent() {
        return pricePercent;
    }

    public void setPricePercent(BigDecimal pricePercent) {
        this.pricePercent = pricePercent;
    }

    public Integer getVenderId() {
        return venderId;
    }

    public void setVenderId(Integer venderId) {
        this.venderId = venderId;
    }

    public String getVenderName() {
        return venderName;
    }

    public void setVenderName(String venderName) {
        this.venderName = venderName;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public BigDecimal getSoldMoney() {
        return soldMoney;
    }

    public void setSoldMoney(BigDecimal soldMoney) {
        this.soldMoney = soldMoney;
    }

    public BigDecimal getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(BigDecimal accountBalance) {
        this.accountBalance = accountBalance;
    }

    public BigDecimal getPaidMoney() {
        return paidMoney;
    }

    public void setPaidMoney(BigDecimal paidMoney) {
        this.paidMoney = paidMoney;
    }

    public BigDecimal getUnPaidMoney() {
        return unPaidMoney;
    }

    public void setUnPaidMoney(BigDecimal unPaidMoney) {
        this.unPaidMoney = unPaidMoney;
    }

    public Integer getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(Integer orderCount) {
        this.orderCount = orderCount;
    }
}
