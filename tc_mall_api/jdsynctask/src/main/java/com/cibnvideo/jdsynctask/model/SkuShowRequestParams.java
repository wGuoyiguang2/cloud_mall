package com.cibnvideo.jdsynctask.model;

public class SkuShowRequestParams {
    private String sku;
    private Boolean isShow;// 	boolean 	N	true	false:查询商品基本信息； true:商品基本信息 + 商品售后信息 + 移动商品详情介绍信息

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Boolean getShow() {
        return isShow;
    }

    public void setShow(Boolean show) {
        isShow = show;
    }
}
