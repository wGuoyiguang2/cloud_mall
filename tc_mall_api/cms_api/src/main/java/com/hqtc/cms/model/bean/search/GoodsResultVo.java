package com.hqtc.cms.model.bean.search;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/7/7 17:02
 */
public class GoodsResultVo {
    private Long sku;
    private String name;
    private BigDecimal jdPrice;
    private BigDecimal price;
    private String imagePath;
    private String brandName;

    public Long getSku() {
        return sku;
    }

    public void setSku(Long sku) {
        this.sku = sku;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

}
