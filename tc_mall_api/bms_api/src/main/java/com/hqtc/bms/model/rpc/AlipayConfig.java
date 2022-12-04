package com.hqtc.bms.model.rpc;

/**
 * @Author: wanghaoyang
 * @Description:
 * @Date: Created in 下午1:14 18-1-23
 */
public class AlipayConfig {
    /*
        支付宝网关名、partnerId和appId
     */
    private String openApiDomain;

    private String mcloudApiDomain;

    /*
     *  url签名密钥
     */
    private String aliapyAES;

    /*
        开发者ID
     */
    private String appId;

    /*
        RSA私钥
     */
    private String privateKey;

    /*
        支付宝公钥
     */
    private String alipayPublicKey;

    /*
        签名类型: RSA->SHA1withRsa,RSA2->SHA256withRsa
     */
    private String signType = "RSA2";

    /*
        当面付最大查询次数
     */
    private int maxQueryRetry = 5;

    /*
        当面付查询间隔（毫秒）
     */
    private int queryDuration = 5000;

    /*
        当面付最大撤销次数
     */
    private int maxCancelRetry = 3;

    /*
        当面付撤销间隔（毫秒）
     */
    private int cancelDuration = 2000;

    /*
        交易保障线程第一次调度延迟
     */
    private int heartbeatDelay = 5;

    /*
        调度间隔（秒）
     */
    private int heartbeatDuration = 900;

    public String getOpenApiDomain() {
        return openApiDomain;
    }

    public void setOpenApiDomain(String openApiDomain) {
        this.openApiDomain = openApiDomain;
    }

    public String getMcloudApiDomain() {
        return mcloudApiDomain;
    }

    public void setMcloudApiDomain(String mcloudApiDomain) {
        this.mcloudApiDomain = mcloudApiDomain;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getAlipayPublicKey() {
        return alipayPublicKey;
    }

    public void setAlipayPublicKey(String alipayPublicKey) {
        this.alipayPublicKey = alipayPublicKey;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public int getMaxQueryRetry() {
        return maxQueryRetry;
    }

    public void setMaxQueryRetry(int maxQueryRetry) {
        this.maxQueryRetry = maxQueryRetry;
    }

    public int getQueryDuration() {
        return queryDuration;
    }

    public void setQueryDuration(int queryDuration) {
        this.queryDuration = queryDuration;
    }

    public int getMaxCancelRetry() {
        return maxCancelRetry;
    }

    public void setMaxCancelRetry(int maxCancelRetry) {
        this.maxCancelRetry = maxCancelRetry;
    }

    public int getCancelDuration() {
        return cancelDuration;
    }

    public void setCancelDuration(int cancelDuration) {
        this.cancelDuration = cancelDuration;
    }

    public int getHeartbeatDelay() {
        return heartbeatDelay;
    }

    public void setHeartbeatDelay(int heartbeatDelay) {
        this.heartbeatDelay = heartbeatDelay;
    }

    public int getHeartbeatDuration() {
        return heartbeatDuration;
    }

    public void setHeartbeatDuration(int heartbeatDuration) {
        this.heartbeatDuration = heartbeatDuration;
    }

    public String getAliapyAES() {
        return aliapyAES;
    }

    public void setAliapyAES(String aliapyAES) {
        this.aliapyAES = aliapyAES;
    }
}
