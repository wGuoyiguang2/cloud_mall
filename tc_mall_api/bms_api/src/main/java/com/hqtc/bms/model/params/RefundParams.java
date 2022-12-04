package com.hqtc.bms.model.params;

import java.util.List;
import java.util.Map;

/**
 * Created by wanghaoyang on 18-7-23.
 */
public class RefundParams {
    //微信订单号
    private String transactionId;
    //商户订单号
    private String outTradeNo;
    //退款订单号
    private String outRefundNo;
    //订单金额
    private int totalFee;
    //退款金额
    private int refundFee;
    //退款原因
    private String refundReason;
    //退款回调
    private String notifyUrl;
    //支付方式
    private int payType;
    //商品信息
    private List<Map<String, Object>> goodInfos;

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

    public int getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(int totalFee) {
        this.totalFee = totalFee;
    }

    public int getRefundFee() {
        return refundFee;
    }

    public void setRefundFee(int refundFee) {
        this.refundFee = refundFee;
    }

    public String getRefundReason() {
        return refundReason;
    }

    public void setRefundReason(String refundReason) {
        this.refundReason = refundReason;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getOutRefundNo() {
        return outRefundNo;
    }

    public void setOutRefundNo(String outRefundNo) {
        this.outRefundNo = outRefundNo;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public List<Map<String, Object>> getGoodInfos() {
        return goodInfos;
    }

    public void setGoodInfos(List<Map<String, Object>> goodInfos) {
        this.goodInfos = goodInfos;
    }
}
