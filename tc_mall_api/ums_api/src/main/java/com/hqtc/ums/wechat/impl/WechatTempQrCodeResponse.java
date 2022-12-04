package com.hqtc.ums.wechat.impl;

import java.io.Serializable;

/**
 * Created by wanghaoyang on 18-11-2.
 */
public class WechatTempQrCodeResponse implements Serializable {
    private String ticket;
    private int  expire_seconds;
    private String url;

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public int getExpire_seconds() {
        return expire_seconds;
    }

    public void setExpire_seconds(int expire_seconds) {
        this.expire_seconds = expire_seconds;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
