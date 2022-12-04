package com.hqtc.bms.model.database;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by wanghaoyang on 18-8-28.
 */
public class TOrderVenderRefundBean implements Serializable {
    private int id;
    private int venderid;
    private String order_sn;
    private BigDecimal refund_price;
    private int refund_status;
    private String unq_id;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date ctime;

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

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public BigDecimal getRefund_price() {
        return refund_price;
    }

    public void setRefund_price(BigDecimal refund_price) {
        this.refund_price = refund_price;
    }

    public int getRefund_status() {
        return refund_status;
    }

    public void setRefund_status(int refund_status) {
        this.refund_status = refund_status;
    }

    public String getUnq_id() {
        return unq_id;
    }

    public void setUnq_id(String unq_id) {
        this.unq_id = unq_id;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }
}
