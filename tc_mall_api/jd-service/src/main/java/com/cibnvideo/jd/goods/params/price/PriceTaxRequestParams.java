package com.cibnvideo.jd.goods.params.price;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/6/23 16:07
 */
public class PriceTaxRequestParams {
    private String sku;
    private String queryExts;

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getQueryExts() {
        return queryExts;
    }

    public void setQueryExts(String queryExts) {
        this.queryExts = queryExts;
    }
}
