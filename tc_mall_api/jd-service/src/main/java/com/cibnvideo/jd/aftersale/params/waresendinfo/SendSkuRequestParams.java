package com.cibnvideo.jd.aftersale.params.waresendinfo;

import java.math.BigDecimal;

/**
 * description:填写客户发运信息
 * Created by laiqingchuang on 18-7-11 .
 */
public class SendSkuRequestParams {
    private Integer afsServiceId;     //服务单号
    private BigDecimal freightMoney;  //运费
    private String expressCompany;    //发运公司
    private String deliverDate;       //发货日期 20171010
    private String expressCode;       //货运单号

    public Integer getAfsServiceId() {
        return afsServiceId;
    }

    public void setAfsServiceId(Integer afsServiceId) {
        this.afsServiceId = afsServiceId;
    }

    public BigDecimal getFreightMoney() {
        return freightMoney;
    }

    public void setFreightMoney(BigDecimal freightMoney) {
        this.freightMoney = freightMoney;
    }

    public String getExpressCompany() {
        return expressCompany;
    }

    public void setExpressCompany(String expressCompany) {
        this.expressCompany = expressCompany;
    }

    public String getDeliverDate() {
        return deliverDate;
    }

    public void setDeliverDate(String deliverDate) {
        this.deliverDate = deliverDate;
    }

    public String getExpressCode() {
        return expressCode;
    }

    public void setExpressCode(String expressCode) {
        this.expressCode = expressCode;
    }
}


