package com.hqtc.bms.model.rpc;


import java.io.InputStream;

/**
 * @Author: wanghaoyang
 * @Description:
 * @Date: Created in 上午9:48 18-1-22
 */
public class WXPayConfig {

    /*
        是否使用沙箱环境
     */
    private boolean useSandbox = false;

    /*
        公众账号ID
     */
    private String appID = "";

    /*
        商户号
     */
    private String mchID = "";

    /*
        API密钥
     */
    private String key = "";

    /*
        HTTP(S) 连接超时时间，单位毫秒
     */
    private int HttpConnectTimeoutMs = 0;

    /*
        HTTP(S) 读数据超时时间，单位毫秒
     */
    private int HttpReadTimeoutMs;

    /*
        进行健康上报的线程的数量
     */
    private int reportWorkerNum;

    /*
       健康上报缓存消息的最大数量。会有线程去独立上报
       粗略计算：加入一条消息200B，10000消息占用空间 2000 KB，约为2MB，可以接受
     */
    private int reportBatchSize;

    /*
        签名方式
     */
    private String signType;

    //退款证书
    private String certStream = null;


    public void setSignType(String signType) {
        this.signType = signType;
    }




    public boolean isUseSandbox() {
        return useSandbox;
    }

    public void setUseSandbox(boolean useSandbox) {
        this.useSandbox = useSandbox;
    }

    public String getAppID() {
        return appID;
    }

    public void setAppID(String appID) {
        this.appID = appID;
    }

    public String getMchID() {
        return mchID;
    }

    public void setMchID(String mchID) {
        this.mchID = mchID;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getHttpConnectTimeoutMs() {
        return HttpConnectTimeoutMs;
    }

    public void setHttpConnectTimeoutMs(int httpConnectTimeoutMs) {
        HttpConnectTimeoutMs = httpConnectTimeoutMs;
    }

    public int getHttpReadTimeoutMs() {
        return HttpReadTimeoutMs;
    }

    public void setHttpReadTimeoutMs(int httpReadTimeoutMs) {
        HttpReadTimeoutMs = httpReadTimeoutMs;
    }

    public int getReportWorkerNum() {
        return reportWorkerNum;
    }

    public void setReportWorkerNum(int reportWorkerNum) {
        this.reportWorkerNum = reportWorkerNum;
    }

    public int getReportBatchSize() {
        return reportBatchSize;
    }

    public void setReportBatchSize(int reportBatchSize) {
        this.reportBatchSize = reportBatchSize;
    }

    public String getSignType() {
        return signType;
    }

    public void setCertStream(String certStream) {
        this.certStream = certStream;
    }

    public String getCertStream() {
        return certStream;
    }
}
