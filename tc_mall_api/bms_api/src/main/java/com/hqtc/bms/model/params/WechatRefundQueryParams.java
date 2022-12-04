package com.hqtc.bms.model.params;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * 微信退款查询的参数 https://pay.weixin.qq.com/wiki/doc/api/micropay.php?chapter=9_5
 * appid: 应用(小程序或公众号)ID
 * mchId: 商户号
 * skey: 商户平台秘钥
 *
 * offset: 偏移量,部分退款超过十次是可以使用
 *
 * -----------------------以下参数四选一，查询优先级 refund_id > out_refund_no > transaction_id > out_trade_no
 *
 * refundId: 微信退款单号
 * outRefundNo: 商户退款单号
 * transactionId： 微信订单号
 * outTradeNo： 商户订单号
 *
 * -----------------------
 *
 * Created by wanghaoyang on 19-1-29.
 */
public class WechatRefundQueryParams {

    @NotNull(message = "请传入正确的appid")
    @NotBlank(message = "请传入正确的appid")
    private String appId;
    @NotNull(message = "请传入正确的mchId")
    @NotBlank(message = "请传入正确的mchId")
    private String mchId;
    @NotNull(message = "请传入正确的skey")
    @NotBlank(message = "请传入正确的skey")
    private String skey;
    private String refundId = "";
    private String outRefundNo = "";
    private String transactionId = "";
    private String outTradeNo = "";
    private int offset = 0;

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

    public String getSkey() {
        return skey;
    }

    public void setSkey(String skey) {
        this.skey = skey;
    }

    public String getRefundId() {
        return refundId;
    }

    public void setRefundId(String refundId) {
        this.refundId = refundId;
    }

    public String getOutRefundNo() {
        return outRefundNo;
    }

    public void setOutRefundNo(String outRefundNo) {
        this.outRefundNo = outRefundNo;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    @Override
    public String toString() {
        return "WechatRefundQueryParams{" +
                "appId='" + appId + '\'' +
                ", mchId='" + mchId + '\'' +
                ", skey='" + skey + '\'' +
                ", refundId='" + refundId + '\'' +
                ", outRefundNo='" + outRefundNo + '\'' +
                ", transactionId='" + transactionId + '\'' +
                ", outTradeNo='" + outTradeNo + '\'' +
                ", offset=" + offset +
                '}';
    }
}
