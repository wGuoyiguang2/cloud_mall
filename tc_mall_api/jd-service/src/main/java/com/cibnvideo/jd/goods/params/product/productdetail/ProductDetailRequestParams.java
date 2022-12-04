package com.cibnvideo.jd.goods.params.product.productdetail;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/6/29 14:10
 */
public class ProductDetailRequestParams {
    private Long sku;//	Long	Y	880110	商品编号，只支持单个查询
    private Boolean isShow;// 	boolean 	N	true	false:查询商品基本信息； true:商品基本信息 + 商品售后信息 + 移动商品详情介绍信息

    public Long getSku() {
        return sku;
    }

    public void setSku(Long sku) {
        this.sku = sku;
    }

    public Boolean getShow() {
        return isShow;
    }

    public void setShow(Boolean show) {
        isShow = show;
    }
}
