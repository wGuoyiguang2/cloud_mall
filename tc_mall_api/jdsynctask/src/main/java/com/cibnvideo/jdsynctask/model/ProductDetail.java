package com.cibnvideo.jdsynctask.model;

import org.apache.commons.codec.binary.StringUtils;

import java.io.Serializable;

public class ProductDetail implements Serializable {
    public String getSaleUnit() {
        return saleUnit;
    }

    public void setSaleUnit(String saleUnit) {
        this.saleUnit = saleUnit;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getProductArea() {
        return productArea;
    }

    public void setProductArea(String productArea) {
        this.productArea = productArea;
    }

    public String getWareQD() {
        return wareQD;
    }

    public void setWareQD(String wareQD) {
        this.wareQD = wareQD;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Long getSku() {
        return sku;
    }

    public void setSku(Long sku) {
        this.sku = sku;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getUpc() {
        return upc;
    }

    public void setUpc(String upc) {
        this.upc = upc;
    }

    public String getAppintroduce() {
        return appintroduce;
    }

    public void setAppintroduce(String appintroduce) {
        this.appintroduce = appintroduce;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        } else if (obj instanceof ProductDetail) {
            ProductDetail productObj = (ProductDetail) obj;
            if (StringUtils.equals(this.getSaleUnit(), productObj.getSaleUnit()) &&
                    StringUtils.equals(this.getWeight(), productObj.getWeight()) &&
                    StringUtils.equals(this.getProductArea(), productObj.getProductArea()) &&
                    StringUtils.equals(this.getWareQD(), productObj.getWareQD()) &&
                    StringUtils.equals(this.getImagePath(), productObj.getImagePath()) &&
                    StringUtils.equals(this.getParam(), productObj.getParam()) &&
                    StringUtils.equals(this.getBrandName(), productObj.getBrandName()) &&
                    StringUtils.equals(this.getUpc(), productObj.getUpc()) &&
                    StringUtils.equals(this.getAppintroduce(), productObj.getAppintroduce()) &&
                    StringUtils.equals(this.getCategory(), productObj.getCategory()) &&
                    StringUtils.equals(this.getName(), productObj.getName()) &&
                    StringUtils.equals(this.getIntroduction(), productObj.getIntroduction()) &&
                    (this.state == null ? productObj.getState() == null : this.state.equals(productObj.getState())) &&
                    (this.sku == null ? productObj.getSku() == null : this.sku.equals(productObj.getSku()))) {
                return true;
            }

        }
        return false;
    }

    private String saleUnit;
    private String weight;
    private String productArea;
    private String wareQD;
    private String imagePath;
    private String param;
    private Integer state;
    private Long sku;
    private String brandName;
    private String upc;
    private String appintroduce;
    private String category;
    private String name;
    private String introduction;
}
