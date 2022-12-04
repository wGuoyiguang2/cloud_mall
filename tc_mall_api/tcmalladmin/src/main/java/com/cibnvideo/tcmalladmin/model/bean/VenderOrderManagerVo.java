package com.cibnvideo.tcmalladmin.model.bean;

import java.math.BigDecimal;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/7/24 15:02
 */
public class VenderOrderManagerVo {
    private Long venderId;
    private String venderName;//admin
    private BigDecimal pricePercent;//oms
    private Integer payType;//oms
    private BigDecimal soldMoney;// bms paidMoney+unPaidMoney
    private BigDecimal accountBalance;//oms
    private BigDecimal paidMoney;//bms
    private BigDecimal unPaidMoney;//bms
    private Integer orderCount;//bms

    public BigDecimal getPricePercent() {
        return pricePercent;
    }

    public void setPricePercent(BigDecimal pricePercent) {
        this.pricePercent = pricePercent;
    }

    public Long getVenderId() {
        return venderId;
    }

    public void setVenderId(Long venderId) {
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
