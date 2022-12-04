package com.cibnvideo.oms.tcmallcustomer.bean;

import java.io.Serializable;

public class VenderPayment implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long venderId;
    //客户端付款方式 0:微信 1:支付宝
    private Integer payType;

    private String appId;

    private String mchId;

    private String publicKey;

    private String privateKey;

    private Integer status;

    public VenderPayment(){
        this.status = 0;
    }

    public Long getVenderId() {
        return venderId;
    }

    public void setVenderId(Long venderId) {
        this.venderId = venderId;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
