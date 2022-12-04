package com.cibnvideo.jd.goods.params.stock;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/6/22 20:56
 */
public class StockRequestParams {
    private String area;
    private String sku;
    public String getArea() {
        return area;
    }
    public void setArea(String area) {
        this.area = area;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }
}
