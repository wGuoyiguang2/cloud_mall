package com.cibnvideo.tcmalladmin.model.bean;


import com.hqtc.common.config.ColumnName;

import java.math.BigDecimal;

/**
 * @Description: 购物卡管理vo
 * @Author: WangBin
 * @Date: 2018/9/19 17:40
 */
public class CardVo {
    @ColumnName("序号")
    private Integer id;
    @ColumnName("大客户ID")
    private Integer venderid;
    @ColumnName("批次号")
    private String batchNo;
    @ColumnName("卡号")
    private String cardNo;
    @ColumnName("密码")
    private String passwd;
    @ColumnName("面值")
    private BigDecimal faceValue;
    @ColumnName("余额")
    private BigDecimal balance;
    @ColumnName("绑定用户")
    private String bindUser;
    @ColumnName("绑定方式")
    private String bindType;
    @ColumnName("是否绑定大客户")
    private String bindVender;
    @ColumnName("状态")
    private String status;
    @ColumnName("创建时间")
    private String ctime;
    @ColumnName("开始时间")
    private String stime;
    @ColumnName("结束时间")
    private String etime;

    public String getBindVender() {
        return bindVender;
    }

    public void setBindVender(String bindVender) {
        this.bindVender = bindVender;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVenderid() {
        return venderid;
    }

    public void setVenderid(Integer venderid) {
        this.venderid = venderid;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public BigDecimal getFaceValue() {
        return faceValue;
    }

    public void setFaceValue(BigDecimal faceValue) {
        this.faceValue = faceValue;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getBindUser() {
        return bindUser;
    }

    public void setBindUser(String bindUser) {
        this.bindUser = bindUser;
    }

    public String getBindType() {
        return bindType;
    }

    public void setBindType(String bindType) {
        this.bindType = bindType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public String getStime() {
        return stime;
    }

    public void setStime(String stime) {
        this.stime = stime;
    }

    public String getEtime() {
        return etime;
    }

    public void setEtime(String etime) {
        this.etime = etime;
    }
}
