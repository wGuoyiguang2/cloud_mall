package com.hqtc.cms.model.bean;

import java.util.List;

/**
 * description:
 * Created by laiqingchuang on 18-7-18 .
 */
public class GoodsVo {
    private String imagePath;   //标签图片地址
    private String saleValue;   //标签名称
    private List<String> skuIds;//当前标签下的同类商品skuId

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getSaleValue() {
        return saleValue;
    }

    public void setSaleValue(String saleValue) {
        this.saleValue = saleValue;
    }

    public List<String> getSkuIds() {
        return skuIds;
    }

    public void setSkuIds(List<String> skuIds) {
        this.skuIds = skuIds;
    }
}
