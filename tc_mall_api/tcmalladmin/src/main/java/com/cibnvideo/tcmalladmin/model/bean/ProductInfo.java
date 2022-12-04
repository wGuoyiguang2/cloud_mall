package com.cibnvideo.tcmalladmin.model.bean;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;

public class ProductInfo extends BaseRowModel {

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

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public String getStateStr() {
        return state == 0? "下架":"上架";
    }

    public void setStateStr(String stateStr) {
        this.stateStr = stateStr;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getSkuStr() {
        return sku.toString();
    }

    public void setSkuStr(String skuStr) {
        this.skuStr = skuStr;
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

    public BigDecimal getTradePrice() {
        return tradePrice;
    }

    public void setTradePrice(BigDecimal tradePrice) {
        this.tradePrice = tradePrice;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        } else if (obj instanceof ProductInfo) {
            ProductInfo productObj = (ProductInfo) obj;
            if (StringUtils.equals(this.getImagePath(), productObj.getImagePath()) &&
                    StringUtils.equals(this.getVideoPath(), productObj.getVideoPath()) &&
                    StringUtils.equals(this.getBrandName(), productObj.getBrandName()) &&
                    StringUtils.equals(this.getCategory(), productObj.getCategory()) &&
                    StringUtils.equals(this.getName(), productObj.getName()) &&
                    (this.state == null ? productObj.getState() == null : this.state.equals(productObj.getState())) &&
                    (this.sku == null ? productObj.getSku() == null : this.sku.equals(productObj.getSku())) &&
                    (this.jdPrice == null ? productObj.getJdPrice() == null : this.jdPrice.equals(productObj.getJdPrice())) &&
                    (this.price == null ? productObj.getPrice() == null : this.price.equals(productObj.getPrice()))) {
                return true;
            }

        }
        return false;
    }

    @ExcelProperty(value = "商品ID", index = 0)
    private String skuStr;

    private Long sku;

    @ExcelProperty(value = "商品名", index = 1)
    private String name;

    @ExcelProperty(value = "分类", index = 2)
    private String category;

    @ExcelProperty(value = "品牌", index = 3)
    private String brandName;

    @ExcelProperty(value = "图片", index = 4)
    private String imagePath;

    @ExcelProperty(value = "短视频", index = 5)
    private String videoPath;

    @ExcelProperty(value = "零售价", index = 6)
    private BigDecimal price;

    @ExcelProperty(value = "批发价", index = 7)
    private BigDecimal tradePrice;

    @ExcelProperty(value = "京东价", index = 8)
    private BigDecimal jdPrice;

    @ExcelProperty(value = "状态", index = 9)
    private String stateStr;

    private Integer state;

}
