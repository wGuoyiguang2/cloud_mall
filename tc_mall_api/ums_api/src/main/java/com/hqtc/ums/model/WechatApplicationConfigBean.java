package com.hqtc.ums.model;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by wanghaoyang on 18-10-11.
 */
@Component
@ConfigurationProperties(prefix = "wechat")
public class WechatApplicationConfigBean {
    private Map<String, Map<String, String>> applet;
    private Map<String, Map<String, String>> subscription;

    public Map<String, Map<String, String>> getApplet() {
        return applet;
    }

    public void setApplet(Map<String, Map<String, String>> applet) {
        this.applet = applet;
    }

    public Map<String, Map<String, String>> getSubscription() {
        return subscription;
    }

    public void setSubscription(Map<String, Map<String, String>> subscription) {
        this.subscription = subscription;
    }
}
