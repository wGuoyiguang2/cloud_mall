package com.cibnvideo.tcmalladmin.model.bean;

import java.math.BigDecimal;

/**
 * @Description: 购物卡批次管理vo
 * @Author: WangBin
 * @Date: 2018/9/19 16:49
 */
public class CardBatchVo {
    private Integer id;
    private Integer venderid;
    private String batchNo;
    private String cardName;
    private Integer cardType;
    private Integer count;
    private BigDecimal faceValue;
    private BigDecimal sumMoney;
    private BigDecimal usedMoney;
    private BigDecimal balance;
    private Integer bindVender;
    private Integer bindCount;
    private Integer unBindCount;
    private Integer activedCount;
    private String company;
    private Integer createType;
    private String ctime;
    private String stime;
    private String etime;
    private String operator;

    public Integer getBindVender() {
        return bindVender;
    }

    public void setBindVender(Integer bindVender) {
        this.bindVender = bindVender;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public BigDecimal getSumMoney() {
        return sumMoney;
    }

    public void setSumMoney(BigDecimal sumMoney) {
        this.sumMoney = sumMoney;
    }

    public BigDecimal getUsedMoney() {
        return usedMoney;
    }

    public void setUsedMoney(BigDecimal usedMoney) {
        this.usedMoney = usedMoney;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Integer getBindCount() {
        return bindCount;
    }

    public void setBindCount(Integer bindCount) {
        this.bindCount = bindCount;
    }

    public Integer getUnBindCount() {
        return unBindCount;
    }

    public void setUnBindCount(Integer unBindCount) {
        this.unBindCount = unBindCount;
    }

    public Integer getActivedCount() {
        return activedCount;
    }

    public void setActivedCount(Integer activedCount) {
        this.activedCount = activedCount;
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

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public Integer getCardType() {
        return cardType;
    }

    public void setCardType(Integer cardType) {
        this.cardType = cardType;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public BigDecimal getFaceValue() {
        return faceValue;
    }

    public void setFaceValue(BigDecimal faceValue) {
        this.faceValue = faceValue;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Integer getCreateType() {
        return createType;
    }

    public void setCreateType(Integer createType) {
        this.createType = createType;
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
