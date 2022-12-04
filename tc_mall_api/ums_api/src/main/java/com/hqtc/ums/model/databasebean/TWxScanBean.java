package com.hqtc.ums.model.databasebean;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by wanghaoyang on 18-11-2.
 */
public class TWxScanBean implements Serializable {
    private int id;
    private String appid;
    private String openid;
    private String ticket;
    private int type;
    private Date ctime;
    private Date scantime;
    private String value;
    private String valuemd5num;
    private String source;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValuemd5num() {
        return valuemd5num;
    }

    public void setValuemd5num(String valuemd5num) {
        this.valuemd5num = valuemd5num;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    public Date getScantime() {
        return scantime;
    }

    public void setScantime(Date scantime) {
        this.scantime = scantime;
    }
}
