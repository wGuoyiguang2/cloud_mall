package com.cibnvideo.jd.goods.params.product.sku;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/6/29 17:07
 */
public class SkuByPageRequestParams {
    private String pageNum;//商品编号
    private String pageNo;//页码数

    public String getPageNum() {
        return pageNum;
    }

    public void setPageNum(String pageNum) {
        this.pageNum = pageNum;
    }

    public String getPageNo() {
        return pageNo;
    }

    public void setPageNo(String pageNo) {
        this.pageNo = pageNo;
    }
}
