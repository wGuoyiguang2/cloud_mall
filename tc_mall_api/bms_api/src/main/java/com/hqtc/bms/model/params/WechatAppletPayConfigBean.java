package com.hqtc.bms.model.params;

/**
 * Created by wanghaoyang on 18-12-4.
 */

public class WechatAppletPayConfigBean {
    private String appid;
    private String appSecret;
    private String mechid;
    private String apikey;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getMechid() {
        return mechid;
    }

    public void setMechid(String mechid) {
        this.mechid = mechid;
    }

    public String getApikey() {
        return apikey;
    }

    public void setApikey(String apikey) {
        this.apikey = apikey;
    }
}
