package com.hqtc.bms.model.database;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by wanghaoyang on 18-9-26.
 */
public class TOrderCardBean implements Serializable {
    private int id;
    private String order_sn;
    private String card_no;
    private BigDecimal balance;
    private BigDecimal use_fee;
    private Date ctime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public String getCard_no() {
        return card_no;
    }

    public void setCard_no(String card_no) {
        this.card_no = card_no;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getUse_fee() {
        return use_fee;
    }

    public void setUse_fee(BigDecimal use_fee) {
        this.use_fee = use_fee;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }
}
