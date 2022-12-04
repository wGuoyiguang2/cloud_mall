package com.hqtc.bms.model.params;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * Created by wanghaoyang on 19-1-30.
 * tradeNo:支付宝交易号
 * outTradeNo:商户订单号
 * outRequestNo: 平台退款单号
 */
public class AlipayRefundQueryParams {

    @NotNull(message = "请传入正确的appId")
    @NotBlank(message = "请传入正确的appId")
    private String appId;
    private String aliapyAES;
    private String openApiDomain;
    private String signType;
    @NotNull(message = "请传入正确的alipayPublicKey")
    @NotBlank(message = "请传入正确的alipayPublicKey")
    private String alipayPublicKey;
    @NotNull(message = "请传入正确的privateKey")
    @NotBlank(message = "请传入正确的privateKey")
    private String privateKey;


    private String tradeNo;
    private String outTradeNo;
    private String outRequestNo;
    private String outRefundNo;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAliapyAES() {
        return aliapyAES;
    }

    public void setAliapyAES(String aliapyAES) {
        this.aliapyAES = aliapyAES;
    }

    public String getOpenApiDomain() {
        return openApiDomain;
    }

    public void setOpenApiDomain(String openApiDomain) {
        this.openApiDomain = openApiDomain;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getAlipayPublicKey() {
        return alipayPublicKey;
    }

    public void setAlipayPublicKey(String alipayPublicKey) {
        this.alipayPublicKey = alipayPublicKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getOutRequestNo() {
        return outRequestNo;
    }

    public void setOutRequestNo(String outRequestNo) {
        this.outRequestNo = outRequestNo;
    }

    public String getOutRefundNo() {
        return outRefundNo;
    }

    public void setOutRefundNo(String outRefundNo) {
        this.outRefundNo = outRefundNo;
    }

    @Override
    public String toString() {
        return "AlipayRefundQueryParams{" +
                "appId='" + appId + '\'' +
                ", aliapyAES='" + aliapyAES + '\'' +
                ", openApiDomain='" + openApiDomain + '\'' +
                ", signType='" + signType + '\'' +
                ", alipayPublicKey='" + alipayPublicKey + '\'' +
                ", privateKey='" + privateKey + '\'' +
                ", tradeNo='" + tradeNo + '\'' +
                ", outTradeNo='" + outTradeNo + '\'' +
                ", outRequestNo='" + outRequestNo + '\'' +
                ", outRefundNo='" + outRefundNo + '\'' +
                '}';
    }
}
