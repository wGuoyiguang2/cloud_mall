package com.hqtc.bms.model.params;

import java.util.Date;

/**
 * description:发票内容bean
 * Created by laiqingchuang on 18-7-19 .
 */
public class OrderInvoiceBean {
    
    private Integer venderid;    //客户id
    private Integer userId;      //用户id
    private String orderSn;      //平台订单号
    private String tradeNo;      //京东订单号
    private String childTradeNo;//子订单号
    private String invoiceId;    //发票单号
    private String serialNo;     //流水号
    private Integer invoiceType; //发票类型 1:正票|2:红票
    private Double invoicePrice; //发票不含税价格
    private Double tax;          //合计税额
    private String phone;        //购方手机
    private String email;        //邮箱
    private String address;      //购方地址
    private String invoiceHead;  //发票抬头
    private String invoiceCode;  //发票代码
    private String invoiceNo;    //发票号码
    private String taxnum;       //纳税人识别号
    private Integer status;      //开票状态 2:开票并签章成功|20:未开票|21:提交服务器开票成功|22:提交服务器开票失败|24:签章失败
    private Date ctime;          //发票创建时间
    private Date utime;          //发票更新时间

    public String getChildTradeNo() {
        return childTradeNo;
    }

    public void setChildTradeNo(String childTradeNo) {
        this.childTradeNo = childTradeNo;
    }

    public Integer getVenderid() {
        return venderid;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public void setVenderid(Integer venderid) {
        this.venderid = venderid;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public String getTaxnum() {
        return taxnum;
    }

    public void setTaxnum(String taxnum) {
        this.taxnum = taxnum;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public Integer getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(Integer invoiceType) {
        this.invoiceType = invoiceType;
    }

    public Double getInvoicePrice() {
        return invoicePrice;
    }

    public void setInvoicePrice(Double invoicePrice) {
        this.invoicePrice = invoicePrice;
    }

    public Double getTax() {
        return tax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getInvoiceHead() {
        return invoiceHead;
    }

    public void setInvoiceHead(String invoiceHead) {
        this.invoiceHead = invoiceHead;
    }

    public String getInvoiceCode() {
        return invoiceCode;
    }

    public void setInvoiceCode(String invoiceCode) {
        this.invoiceCode = invoiceCode;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
