package com.hqtc.bms.model.rpc;

import java.io.Serializable;
import java.util.List;

/**
 * description:开发票请求参数
 * Created by laiqingchuang on 18-7-3 .
 */
public class InvoiceRequestBean implements Serializable {
    private String buyername;   //购方名称
    private String taxnum;      //购方税号(企业要填,个人可为空)
    private String phone;       //购方手机 (开票成功会短信提醒购方)
    private String address;     //购方地址(企业要填,个人可为空)
    private String account;     //购方银行账号(企业要填,个人可为空)
    private String orderno;     //订单号
    private String invoicedate; //单据时间
    private String clerk;       //开票员
    private String saleaccount; //销方银行账号
    private String salephone;   //销方电话
    private String saleaddress; //销方地址
    private String saletaxnum;  //销方税号
    private String kptype;      //开票类型:1,正票;2,红票
    private String message;     //备注(冲红时,必须在备注中注明“对 应正数发票代码:XXXXXXXXX号码:YYYYYYYY”文案,其中“X”为发票代码,“Y”为发票号码,否则接口会自动添加该文案)
    private String payee;       //收款人
    private String checker;     //复核人
    private String fpdm;        //对应蓝票发票代码(红票必填,不满12 位请左补 0)
    private String fphm;        //对应蓝票发票号码(红票必填,不满8 位请左补 0)
    private String tsfs;        //推送方式 :-1,不推送 ;0,邮箱;1,手机(默认);2,邮箱、手机
    private String email;       //推送邮箱(tsfs为0或2时,此项为必填)
    private String qdbz;        //清单标志:0,根据项目 1名称数,自动产生清单;1,将项目信息打印至清单
    private String qdxmmc;      //清单项目名称 : 打印清单时对应发票票面项目名称,注意:税总要求清单项目名称为(详见销货清单)
    private String dkbz;        //代开标志(默认为0)
    private String deptid;      //部门门店 id(诺诺系统中的 id)
    private String clerkid;     //开票员 id(诺诺系统中的 id)
    private String invoiceLine; //发票种类(默认为电票 p)
    private String cpybz;       //成品油标志:0 非成品油,1 成品油,
    private List<InvoiceDetailRequestBean> detail;//电子发票明细

    private String tradeNo;     //京东订单号
    private String venderId;    //客户id
    private Integer userId;
    private Integer countyId;
    private Integer townId;
    private String detailAddr;   //详细地址

    public Integer getCountyId() {
        return countyId;
    }

    public void setCountyId(Integer countyId) {
        this.countyId = countyId;
    }
    public Integer getTownId() {
        return townId;
    }

    public void setTownId(Integer townId) {
        this.townId = townId;
    }

    public String getDetailAddr() {
        return detailAddr;
    }

    public void setDetailAddr(String detailAddr) {
        this.detailAddr = detailAddr;
    }

    public String getBuyername() {
        return buyername;
    }

    public void setBuyername(String buyername) {
        this.buyername = buyername;
    }

    public String getTaxnum() {
        return taxnum;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setTaxnum(String taxnum) {
        this.taxnum = taxnum;
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

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public String getInvoicedate() {
        return invoicedate;
    }

    public void setInvoicedate(String invoicedate) {
        this.invoicedate = invoicedate;
    }

    public String getClerk() {
        return clerk;
    }

    public void setClerk(String clerk) {
        this.clerk = clerk;
    }

    public String getSaleaccount() {
        return saleaccount;
    }

    public void setSaleaccount(String saleaccount) {
        this.saleaccount = saleaccount;
    }

    public String getSalephone() {
        return salephone;
    }

    public void setSalephone(String salephone) {
        this.salephone = salephone;
    }

    public String getSaleaddress() {
        return saleaddress;
    }

    public void setSaleaddress(String saleaddress) {
        this.saleaddress = saleaddress;
    }

    public String getSaletaxnum() {
        return saletaxnum;
    }

    public void setSaletaxnum(String saletaxnum) {
        this.saletaxnum = saletaxnum;
    }

    public String getKptype() {
        return kptype;
    }

    public void setKptype(String kptype) {
        this.kptype = kptype;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPayee() {
        return payee;
    }

    public void setPayee(String payee) {
        this.payee = payee;
    }

    public String getChecker() {
        return checker;
    }

    public void setChecker(String checker) {
        this.checker = checker;
    }

    public String getFpdm() {
        return fpdm;
    }

    public void setFpdm(String fpdm) {
        this.fpdm = fpdm;
    }

    public String getFphm() {
        return fphm;
    }

    public void setFphm(String fphm) {
        this.fphm = fphm;
    }

    public String getTsfs() {
        return tsfs;
    }

    public void setTsfs(String tsfs) {
        this.tsfs = tsfs;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQdbz() {
        return qdbz;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getVenderId() {
        return venderId;
    }

    public void setVenderId(String venderId) {
        this.venderId = venderId;
    }

    public void setQdbz(String qdbz) {
        this.qdbz = qdbz;
    }

    public String getQdxmmc() {
        return qdxmmc;
    }

    public void setQdxmmc(String qdxmmc) {
        this.qdxmmc = qdxmmc;
    }

    public String getDkbz() {
        return dkbz;
    }

    public void setDkbz(String dkbz) {
        this.dkbz = dkbz;
    }

    public String getDeptid() {
        return deptid;
    }

    public void setDeptid(String deptid) {
        this.deptid = deptid;
    }

    public String getClerkid() {
        return clerkid;
    }

    public void setClerkid(String clerkid) {
        this.clerkid = clerkid;
    }

    public String getInvoiceLine() {
        return invoiceLine;
    }

    public void setInvoiceLine(String invoiceLine) {
        this.invoiceLine = invoiceLine;
    }

    public String getCpybz() {
        return cpybz;
    }

    public void setCpybz(String cpybz) {
        this.cpybz = cpybz;
    }

    public List<InvoiceDetailRequestBean> getDetail() {
        return detail;
    }

    public void setDetail(List<InvoiceDetailRequestBean> detail) {
        this.detail = detail;
    }
}