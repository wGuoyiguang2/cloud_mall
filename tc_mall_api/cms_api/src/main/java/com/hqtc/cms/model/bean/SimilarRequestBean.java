package com.hqtc.cms.model.bean;

/**
 * description:查询同类商品
 * Created by laiqingchuang on 18-7-18 .
 */
public class SimilarRequestBean {

    private String skuId;     //商品id
    private String sku;

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }
}
