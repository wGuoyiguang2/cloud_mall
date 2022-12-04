package com.hqtc.ums.model;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 甜橙视频公众号的相关配置
 * Created by wanghaoyang on 18-11-1.
 */
@Component("YouXiangWecahtSubScriptionConfigBean")
@ConfigurationProperties(prefix = "wechat.subscription.jinriyouxiang")
public class YouXiangWecahtSubScriptionConfigBean implements WechatSubScriptionConfig{
    private String appid;
    private String appSecret;
    private String token;
    private String apiKey;

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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getWechatAppId(){
        return this.appid;
    }
    public String getWechatSecret(){
        return this.appSecret;
    }
    public String getWechatToken(){
        return this.token;
    }
    public String getWechatApiKey(){
        return this.apiKey;
    }

}
