package com.hqtc.ims.cart.model.bean;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * description: 购物车bean
 * Created by sunjianqiang  18-9-25
 */
public class CartProductBean implements Serializable {
    private Long sku;
    private String name = "";
    private BigDecimal price;
    private String imagePath = "";
    private Integer   count;
    private Integer  state;

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

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
