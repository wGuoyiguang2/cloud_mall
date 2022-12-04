package com.hqtc.bms.model.rpc;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @Author: wanghaoyang
 * @Description:
 * @Date: Created in 下午1:58 18-1-18
 */
public class PayParams {
    //订单号
    private String orderSn;
    //总金额
    private Integer actualPrice;
    //
    private String sign;
    //商品id
    private String goodsId = "";
    //回调地址
    private String notifyUrl = "";
    //商品名称
    private String subject="甜橙购物";
    //商品想请
    private String goodsDetail = "";
    //门店设备号
    private String deviceInfo = "";
    //商品描述
    private String body = "甜橙购物商品";
    //附加数据
    private String attach = "";
    //门店ID DNS5188 > 随便写的,此项Alipay必填
    private String storeId = "DNS5188";
    //支付超时，定义为120分钟,单位： 毫秒
    private int timeoutExpress = 120 * 60 * 1000;
    //userId
    private int userId;
    //appId
    private String appId;
    //partnerId
    private String partnerId;

    private String openId;

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getGoodsDetail() {
        return goodsDetail;
    }

    public void setGoodsDetail(String goodsDetail) {
        this.goodsDetail = goodsDetail;
    }

    public String getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public Integer getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(Integer actualPrice) {
        this.actualPrice = actualPrice;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public int getTimeoutExpress() {
        return timeoutExpress;
    }

    public void setTimeoutExpress(int timeoutExpress) {
        this.timeoutExpress = timeoutExpress;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
}
