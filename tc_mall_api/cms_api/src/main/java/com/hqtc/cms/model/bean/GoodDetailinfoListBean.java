package com.hqtc.cms.model.bean;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * description:根据分类获取商品列表bean
 * Created by laiqingchuang on 18-6-23 .
 */
public class GoodDetailinfoListBean implements Serializable {

    private Long sku;           //sku Id
    private String name;        //名称
    private String brandName;   //品牌
    private Integer state;      //上下架状态 0:下架 1:上架
    private String category;    //三级分类信息，以分号“;”分开
	private String imagePath;   //图片地址
    private BigDecimal jdPrice; //京东价
    private BigDecimal price;   //零售价

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

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
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