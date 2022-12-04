package com.hqtc.bms.model.params;

import java.io.Serializable;

/**
 * description:发票信息
 * Created by laiqingchuang on 18-7-12 .
 */
public class InvoiceresultDto implements Serializable {

    private String c_status;      //开票状态
    private String c_fpdm;        //发票代码
    private Double c_bhsje;       //不含税金额
    private Long c_kprq;          //开票日期
    private String c_orderno;     //订单编号
    private String c_invoiceid;   //发票主键
    private String c_msg;         //开票信息,成功或者失败的信息
    private String c_fpqqlsh;     //发票请求流水号
    private String c_buyername;   //购方名称
    private String c_fphm;        //发票号码
    private String c_resultmsg;   //结果信息
    private String c_url;         //发票 pdf 地址
    private String c_jym;         //校验码
    private String c_jpg_url;     //发票详情地址
    private Double c_hjse;        //合计税额
    private String c_taxnum;      //购方税号
    private String c_invoice_line;//发票类型

    public String getC_status() {
        return c_status;
    }

    public void setC_status(String c_status) {
        this.c_status = c_status;
    }

   public String getC_fpdm() {
        return c_fpdm;
    }

    public void setC_fpdm(String c_fpdm) {
        this.c_fpdm = c_fpdm;
    }

    public Long getC_kprq() {
        return c_kprq;
    }

    public void setC_kprq(Long c_kprq) {
        this.c_kprq = c_kprq;
    }

    public String getC_orderno() {
        return c_orderno;
    }

    public void setC_orderno(String c_orderno) {
        this.c_orderno = c_orderno;
    }

    public String getC_invoiceid() {
        return c_invoiceid;
    }

    public void setC_invoiceid(String c_invoiceid) {
        this.c_invoiceid = c_invoiceid;
    }

    public String getC_msg() {
        return c_msg;
    }

    public void setC_msg(String c_msg) {
        this.c_msg = c_msg;
    }

    public String getC_fpqqlsh() {
        return c_fpqqlsh;
    }

    public void setC_fpqqlsh(String c_fpqqlsh) {
        this.c_fpqqlsh = c_fpqqlsh;
    }

    public String getC_buyername() {
        return c_buyername;
    }

    public void setC_buyername(String c_buyername) {
        this.c_buyername = c_buyername;
    }

    public String getC_fphm() {
        return c_fphm;
    }

    public void setC_fphm(String c_fphm) {
        this.c_fphm = c_fphm;
    }

    public String getC_resultmsg() {
        return c_resultmsg;
    }

    public void setC_resultmsg(String c_resultmsg) {
        this.c_resultmsg = c_resultmsg;
    }

    public String getC_url() {
        return c_url;
    }

    public void setC_url(String c_url) {
        this.c_url = c_url;
    }

    public String getC_jym() {
        return c_jym;
    }

    public void setC_jym(String c_jym) {
        this.c_jym = c_jym;
    }

    public String getC_jpg_url() {
        return c_jpg_url;
    }

    public void setC_jpg_url(String c_jpg_url) {
        this.c_jpg_url = c_jpg_url;
    }

    public Double getC_bhsje() {
        return c_bhsje;
    }

    public void setC_bhsje(Double c_bhsje) {
        this.c_bhsje = c_bhsje;
    }

    public Double getC_hjse() {
        return c_hjse;
    }

    public void setC_hjse(Double c_hjse) {
        this.c_hjse = c_hjse;
    }

    public String getC_taxnum() {
        return c_taxnum;
    }

    public void setC_taxnum(String c_taxnum) {
        this.c_taxnum = c_taxnum;
    }

    public String getC_invoice_line() {
        return c_invoice_line;
    }

    public void setC_invoice_line(String c_invoice_line) {
        this.c_invoice_line = c_invoice_line;
    }
}
