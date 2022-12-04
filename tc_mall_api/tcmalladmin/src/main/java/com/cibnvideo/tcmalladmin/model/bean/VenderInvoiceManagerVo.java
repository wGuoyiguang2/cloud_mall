package com.cibnvideo.tcmalladmin.model.bean;

import java.math.BigDecimal;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/7/24 18:21
 */
public class VenderInvoiceManagerVo {
    private Long venderId;
    private String venderName; //admin
    private BigDecimal pricePercent; //oms
    private Integer opened; //bms
    private Integer canceled;//bms
    private Integer count;//bms

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

    public BigDecimal getPricePercent() {
        return pricePercent;
    }

    public void setPricePercent(BigDecimal pricePercent) {
        this.pricePercent = pricePercent;
    }

    public Integer getOpened() {
        return opened;
    }

    public void setOpened(Integer opened) {
        this.opened = opened;
    }

    public Integer getCanceled() {
        return canceled;
    }

    public void setCanceled(Integer canceled) {
        this.canceled = canceled;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
