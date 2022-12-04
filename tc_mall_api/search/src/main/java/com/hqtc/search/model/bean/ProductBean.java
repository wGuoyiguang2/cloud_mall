package com.hqtc.search.model.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

/**
 * Created by makuan on 18-8-9.
 */
public class ProductBean implements Serializable {
    private long sku;
    private String name = "";
    @JsonIgnore
    private String brandname = "";
    private String brandName = "";
    @JsonIgnore
    private float jdprice;
    private float jdPrice;
    @JsonIgnore
    private float agreeprice;
    private float agreePrice;
    private float price;
    @JsonIgnore
    private String imagepath = "";
    private String imagePath = "";
    private Integer state;

    public static final String INDEX_NAME = "tcmall_";
    public static final String TYPE = "product";

    public long getSku() {
        return sku;
    }

    public void setSku(long sku) {
        this.sku = sku;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrandname() {
        return brandname;
    }

    public void setBrandname(String brandname) {
        this.brandname = brandname;
    }

    public String getBrandName() {
        return brandname;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public float getJdprice() {
        return jdprice;
    }

    public void setJdprice(float jdprice) {
        this.jdprice = jdprice;
    }

    public float getJdPrice() {
        return jdprice;
    }

    public void setJdPrice(float jdPrice) {
        this.jdPrice = jdPrice;
    }

    public float getAgreeprice() {
        return agreeprice;
    }

    public void setAgreeprice(float agreeprice) {
        this.agreeprice = agreeprice;
    }

    public float getAgreePrice() {
        return agreeprice;
    }

    public void setAgreePrice(float agreePrice) {
        this.agreePrice = agreePrice;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }

    public String getImagePath() {
        return imagepath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
