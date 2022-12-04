package com.cibnvideo.oms.tcmallcustomer.bean;

import java.math.BigDecimal;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/7/21 17:59
 */
public class AccountInfoVo {
    private Long venderId;
    private BigDecimal needPayMoney;
    private BigDecimal hasPayMoney;
    private BigDecimal sumPayMoney;

    public Long getVenderId() {
        return venderId;
    }

    public void setVenderId(Long venderId) {
        this.venderId = venderId;
    }

    public BigDecimal getNeedPayMoney() {
        return needPayMoney;
    }

    public void setNeedPayMoney(BigDecimal needPayMoney) {
        this.needPayMoney = needPayMoney;
    }

    public BigDecimal getHasPayMoney() {
        return hasPayMoney;
    }

    public void setHasPayMoney(BigDecimal hasPayMoney) {
        this.hasPayMoney = hasPayMoney;
    }

    public BigDecimal getSumPayMoney() {
        return sumPayMoney;
    }

    public void setSumPayMoney(BigDecimal sumPayMoney) {
        this.sumPayMoney = sumPayMoney;
    }
}
