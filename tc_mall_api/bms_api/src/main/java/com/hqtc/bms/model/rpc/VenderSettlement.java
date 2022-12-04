package com.hqtc.bms.model.rpc;

import java.io.Serializable;
import java.math.BigDecimal;

public class VenderSettlement implements Serializable {
    private static final long serialVersionUID = 1L;

    public BigDecimal getPricePercent() {
        return pricePercent;
    }

    public void setPricePercent(BigDecimal pricePercent) {
        this.pricePercent = pricePercent;
    }

    public Integer getSettlementType() {
        return settlementType;
    }

    public void setSettlementType(Integer settlementType) {
        this.settlementType = settlementType;
    }

    public String getSettlementAccount() {
        return settlementAccount;
    }

    public void setSettlementAccount(String settlementAccount) {
        this.settlementAccount = settlementAccount;
    }

    public Integer getSettlementPeriod() {
        return settlementPeriod;
    }

    public void setSettlementPeriod(Integer settlementPeriod) {
        this.settlementPeriod = settlementPeriod;
    }

    public Long getVenderId() {
        return venderId;
    }

    public void setVenderId(Long venderId) {
        this.venderId = venderId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    private Long venderId;
    //商品批发系统数
    private BigDecimal pricePercent;
    //结算方式 0:实时结算 1:按月结算 2:预付款
    private Integer settlementType;
    //结算账户
    private String settlementAccount;
    //结算周期,0:自动扣款 1:线下结算
    private Integer settlementPeriod;
    //预付款结算模式下账户余额
    private BigDecimal balance;
}
