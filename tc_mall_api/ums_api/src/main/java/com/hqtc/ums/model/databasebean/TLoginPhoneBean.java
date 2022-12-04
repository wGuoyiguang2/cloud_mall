package com.hqtc.ums.model.databasebean;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by wanghaoyang on 18-10-11.
 */
public class TLoginPhoneBean implements Serializable {
    private int id;
    private int userid;
    private String phone;
    private String password;
    private Date ctime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }
}
