package com.hqtc.cms.model.bean.search;

import java.io.Serializable;

/**
 * Created by makuan on 18-7-6.
 */
public class SyncSearchHistoryBean implements Serializable {
    private int userId;
    private String mac;
    private String keyword;
    private String ctime;

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
