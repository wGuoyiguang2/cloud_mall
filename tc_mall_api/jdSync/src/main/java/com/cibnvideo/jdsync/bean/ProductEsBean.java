package com.cibnvideo.jdsync.bean;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ProductEsBean implements Serializable {
    private Long sku;
    private String name = "";
    private int cat0;
    private String cat0name;
    private int cat1;
    private String cat1name;
    private int cat2;
    private String cat2name;
    private String catename;
    private String cate;
    private String brandname;
    private int sales;
    private String imagepath = "";
    private int state;
    private BigDecimal price;
    private BigDecimal agreeprice;
    private BigDecimal jdprice;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private Date ctime;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private Date utime;

    public Long getSku() {
        return sku;
    }

    public void setSku(Long sku) {
        this.sku = sku;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCat0() {
        return cat0;
    }

    public void setCat0(int cat0) {
        this.cat0 = cat0;
    }

    public String getCat0name() {
        return cat0name;
    }

    public void setCat0name(String cat0name) {
        this.cat0name = cat0name;
    }

    public int getCat1() {
        return cat1;
    }

    public void setCat1(int cat1) {
        this.cat1 = cat1;
    }

    public String getCat1name() {
        return cat1name;
    }

    public void setCat1name(String cat1name) {
        this.cat1name = cat1name;
    }

    public int getCat2() {
        return cat2;
    }

    public void setCat2(int cat2) {
        this.cat2 = cat2;
    }

    public String getCat2name() {
        return cat2name;
    }

    public void setCat2name(String cat2name) {
        this.cat2name = cat2name;
    }

    public String getCatename() {
        return catename;
    }

    public void setCatename(String catename) {
        this.catename = catename;
    }

    public String getCate() {
        return cate;
    }

    public void setCate(String cate) {
        this.cate = cate;
    }

    public String getBrandname() {
        return brandname;
    }

    public void setBrandname(String brandname) {
        this.brandname = brandname;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getAgreeprice() {
        return agreeprice;
    }

    public void setAgreeprice(BigDecimal tradeprice) {
        this.agreeprice = agreeprice;
    }

    public BigDecimal getJdprice() {
        return jdprice;
    }

    public void setJdprice(BigDecimal jdprice) {
        this.jdprice = jdprice;
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
