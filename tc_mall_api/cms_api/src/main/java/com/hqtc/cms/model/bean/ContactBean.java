package com.hqtc.cms.model.bean;

import java.io.Serializable;

/**
 * Created by makuan on 18-6-26.
 */
public class ContactBean implements Serializable{
    private String hotline = "";
    private String url = "";

    public String getHotline() {
        return hotline;
    }

    public void setHotline(String hotline) {
        this.hotline = hotline;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
