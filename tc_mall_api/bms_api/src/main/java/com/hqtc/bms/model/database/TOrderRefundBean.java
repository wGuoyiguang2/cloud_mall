package com.hqtc.bms.model.database;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by wanghaoyang on 18-7-26.
 */
public class TOrderRefundBean implements Serializable {
    private int id;
    private int venderid;
    private int user_id;
    private String order_sn;
    private long product_id;
    private String name;
    private int count;
    private float refund_price;
    private int pay_type;
    private String pay_order_sn;
    private String refund_no;
    private String refund_order_sn;
    private int refund_status;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date ctime;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date utime;
    private String unq_id;
    private float card_refund_price;

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

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(long product_id) {
        this.product_id = product_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public float getRefund_price() {
        return refund_price;
    }

    public void setRefund_price(float refund_price) {
        this.refund_price = refund_price;
    }

    public int getPay_type() {
        return pay_type;
    }

    public void setPay_type(int pay_type) {
        this.pay_type = pay_type;
    }

    public String getPay_order_sn() {
        return pay_order_sn;
    }

    public void setPay_order_sn(String pay_order_sn) {
        this.pay_order_sn = pay_order_sn;
    }

    public String getRefund_order_sn() {
        return refund_order_sn;
    }

    public void setRefund_order_sn(String refund_order_sn) {
        this.refund_order_sn = refund_order_sn;
    }

    public int getRefund_status() {
        return refund_status;
    }

    public void setRefund_status(int refund_status) {
        this.refund_status = refund_status;
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

    public String getRefund_no() {
        return refund_no;
    }

    public void setRefund_no(String refund_no) {
        this.refund_no = refund_no;
    }

    public String getUnq_id() {
        return unq_id;
    }

    public void setUnq_id(String unq_id) {
        this.unq_id = unq_id;
    }

    public float getCard_refund_price() {
        return card_refund_price;
    }

    public void setCard_refund_price(float card_refund_price) {
        this.card_refund_price = card_refund_price;
    }
}
