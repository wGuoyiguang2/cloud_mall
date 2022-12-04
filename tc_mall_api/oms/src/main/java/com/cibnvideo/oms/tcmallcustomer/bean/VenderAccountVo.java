package com.cibnvideo.oms.tcmallcustomer.bean;

import java.math.BigDecimal;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/8/1 18:14
 */
public class VenderAccountVo {
    private Long venderId;
    private String venderName;
    private BigDecimal needPayMoney;
    private BigDecimal hasPaidMoney;
    private BigDecimal sumPaidMoney;
    private Integer settlementType;
    private Integer count;

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

    public BigDecimal getNeedPayMoney() {
        return needPayMoney;
    }

    public void setNeedPayMoney(BigDecimal needPayMoney) {
        this.needPayMoney = needPayMoney;
    }

    public BigDecimal getHasPaidMoney() {
        return hasPaidMoney;
    }

    public void setHasPaidMoney(BigDecimal hasPaidMoney) {
        this.hasPaidMoney = hasPaidMoney;
    }

    public BigDecimal getSumPaidMoney() {
        return sumPaidMoney;
    }

    public void setSumPaidMoney(BigDecimal sumPaidMoney) {
        this.sumPaidMoney = sumPaidMoney;
    }

    public Integer getSettlementType() {
        return settlementType;
    }

    public void setSettlementType(Integer settlementType) {
        this.settlementType = settlementType;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
