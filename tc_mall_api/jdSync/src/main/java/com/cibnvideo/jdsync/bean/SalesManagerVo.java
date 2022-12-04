package com.cibnvideo.jdsync.bean;

import java.math.BigDecimal;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/8/9 10:22
 */
public class SalesManagerVo {
    private Long productId;
    private String productName;
    private String brandName;//jdsync
    private String category;
    private BigDecimal productPrice;
    private BigDecimal payPrice;
    private BigDecimal agreePrice;
    private Integer productStatus;//oms
    private Integer count;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
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

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public BigDecimal getPayPrice() {
        return payPrice;
    }

    public void setPayPrice(BigDecimal payPrice) {
        this.payPrice = payPrice;
    }

    public BigDecimal getAgreePrice() {
        return agreePrice;
    }

    public void setAgreePrice(BigDecimal agreePrice) {
        this.agreePrice = agreePrice;
    }

    public Integer getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(Integer productStatus) {
        this.productStatus = productStatus;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
