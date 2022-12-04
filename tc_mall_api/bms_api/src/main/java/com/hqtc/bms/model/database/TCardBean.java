package com.hqtc.bms.model.database;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by wanghaoyang on 18-9-20.
 */
public class TCardBean implements Serializable {
    private int id;
    private int venderid;
    private String batch_no;
    private String card_no;
    private String passwd;
    private BigDecimal face_value;
    private BigDecimal balance;
    private int bind_user;
    private int bind_type;
    private int bind_vender = 0;
    private int status;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date ctime;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date stime;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date etime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVenderid() {
        return venderid;
    }

    public void setVenderid(int venderid) {
        this.venderid = venderid;
    }

    public String getBatch_no() {
        return batch_no;
    }

    public void setBatch_no(String batch_no) {
        this.batch_no = batch_no;
    }

    public String getCard_no() {
        return card_no;
    }

    public void setCard_no(String card_no) {
        this.card_no = card_no;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public BigDecimal getFace_value() {
        return face_value;
    }

    public void setFace_value(BigDecimal face_value) {
        this.face_value = face_value;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public int getBind_user() {
        return bind_user;
    }

    public void setBind_user(int bind_user) {
        this.bind_user = bind_user;
    }

    public int getBind_type() {
        return bind_type;
    }

    public void setBind_type(int bind_type) {
        this.bind_type = bind_type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    public Date getStime() {
        return stime;
    }

    public void setStime(Date stime) {
        this.stime = stime;
    }

    public Date getEtime() {
        return etime;
    }

    public void setEtime(Date etime) {
        this.etime = etime;
    }

    public int getBind_vender() {
        return bind_vender;
    }

    public void setBind_vender(int bind_vender) {
        this.bind_vender = bind_vender;
    }
}
