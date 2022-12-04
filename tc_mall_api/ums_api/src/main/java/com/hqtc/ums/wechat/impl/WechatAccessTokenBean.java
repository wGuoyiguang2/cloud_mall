package com.hqtc.ums.wechat.impl;

import java.io.Serializable;

/**
 * Created by wanghaoyang on 18-11-2.
 */
public class WechatAccessTokenBean implements Serializable{
    private String access_token;
    private int expires_in;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }
}
