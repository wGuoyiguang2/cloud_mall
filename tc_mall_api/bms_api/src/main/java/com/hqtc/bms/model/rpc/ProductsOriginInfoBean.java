package com.hqtc.bms.model.rpc;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by wanghaoyang on 18-7-4.
 */
public class ProductsOriginInfoBean implements Serializable {
    private long sku;
    private String name;
    private BigDecimal price;
    private String imagePath;
    private int state;
    private String brandName;
    private String category;
    private BigDecimal jdprice;

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

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public BigDecimal getJdprice() {
        return jdprice;
    }

    public void setJdprice(BigDecimal jdprice) {
        this.jdprice = jdprice;
    }
}
