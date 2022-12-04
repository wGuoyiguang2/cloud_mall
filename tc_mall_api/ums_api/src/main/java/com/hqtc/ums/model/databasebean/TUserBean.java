package com.hqtc.ums.model.databasebean;


import java.io.Serializable;
import java.util.Date;

/**
 * Created by wanghaoyang on 18-10-11.
 */
public class TUserBean implements Serializable{

    private int id;
    private String nickname;
    private String header;
    private String source;
    private Date ctime;
    private Date utime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
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

    public Date getUtime() {
        return utime;
    }

    public void setUtime(Date utime) {
        this.utime = utime;
    }
}
