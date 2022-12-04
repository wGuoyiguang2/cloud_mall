package com.hqtc.bms.model.database;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by wanghaoyang on 18-7-3.
 */
public class TOrderProductBean implements Serializable {
    private int id;
    private String order_sn;
    private String child_trade_no;
    private long product_id;
    private String name;
    private BigDecimal agree_price;
    private BigDecimal price;
    private BigDecimal pay_price;
    private int count;
    private String ctime;
    private int taxrate;
    private int order_state;
    private String utime;

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

    public BigDecimal getAgree_price() {
        return agree_price;
    }

    public void setAgree_price(BigDecimal agree_price) {
        this.agree_price = agree_price;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPay_price() {
        return pay_price;
    }

    public void setPay_price(BigDecimal pay_price) {
        this.pay_price = pay_price;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public int getTaxrate() {
        return taxrate;
    }

    public void setTaxrate(int taxrate) {
        this.taxrate = taxrate;
    }

    public String getChild_trade_no() {
        return child_trade_no;
    }

    public void setChild_trade_no(String child_trade_no) {
        this.child_trade_no = child_trade_no;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getOrder_state() {
        return order_state;
    }

    public void setOrder_state(int order_state) {
        this.order_state = order_state;
    }

    public String getUtime() {
        return utime;
    }

    public void setUtime(String utime) {
        this.utime = utime;
    }
}
