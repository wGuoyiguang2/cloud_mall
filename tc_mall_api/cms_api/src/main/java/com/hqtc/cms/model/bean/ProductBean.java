package com.hqtc.cms.model.bean;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by laiqingchuang on 18-8-9.
 */
public class ProductBean implements Serializable {
    private Long sku;
    private String name = "";
    private String brandName = "";
    private BigDecimal jdPrice;
    private BigDecimal price;
    private String imagePath = "";

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

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public BigDecimal getJdPrice() {
        return jdPrice;
    }

    public void setJdPrice(BigDecimal jdPrice) {
        this.jdPrice = jdPrice;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
