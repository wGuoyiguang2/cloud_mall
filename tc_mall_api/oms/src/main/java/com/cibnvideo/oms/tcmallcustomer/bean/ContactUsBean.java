package com.cibnvideo.oms.tcmallcustomer.bean;

import java.util.Date;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/7/10 20:42
 */
public class ContactUsBean {
    private Integer id;
    private Long venderid;
    private String hotline;
    private String url;
    private Date ctime;
    private Date utime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getVenderid() {
        return venderid;
    }

    public void setVenderid(Long venderid) {
        this.venderid = venderid;
    }

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

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    public Date getUtime() {
        return utime;
    }

    public void setUtime(Date utime) {
        this.utime = utime;
    }
}
