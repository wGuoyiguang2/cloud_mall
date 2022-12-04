package com.cibnvideo.jd.goods.params.stock;

import java.util.List;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/6/23 15:38
 */
public class StockOrderRequestParams {
    private List<StockOrderRequestVo> skuNums;
    private String area;

    public List<StockOrderRequestVo> getSkuNums() {
        return skuNums;
    }

    public void setSkuNums(List<StockOrderRequestVo> skuNums) {
        this.skuNums = skuNums;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}
