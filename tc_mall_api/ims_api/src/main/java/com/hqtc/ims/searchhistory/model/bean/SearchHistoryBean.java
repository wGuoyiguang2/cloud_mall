package com.hqtc.ims.searchhistory.model.bean;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/7/6 10:19
 */
public class SearchHistoryBean {
    private Integer id;
    private Integer userId;
    private String mac;
    private String keyword;
    private String ctime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
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

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }
}
