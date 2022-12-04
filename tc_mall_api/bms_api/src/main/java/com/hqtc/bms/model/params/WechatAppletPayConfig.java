package com.hqtc.bms.model.params;

import io.swagger.models.auth.In;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wanghaoyang on 18-12-4.
 */
@Component
@ConfigurationProperties(prefix = "wechatApplet")
public class WechatAppletPayConfig {
    private Map<Integer, WechatAppletPayConfigBean> lists = new HashMap(20);

    public Map<Integer, WechatAppletPayConfigBean> getLists() {
        return lists;
    }

    public void setLists(Map<Integer, WechatAppletPayConfigBean> lists) {
        this.lists = lists;
    }
}
