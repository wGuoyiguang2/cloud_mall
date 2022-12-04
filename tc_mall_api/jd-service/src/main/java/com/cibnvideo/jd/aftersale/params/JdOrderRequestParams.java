package com.cibnvideo.jd.aftersale.params;

/**
 * description:京东订单信息param
 * Created by laiqingchuang on 18-7-10 .
 */
public class JdOrderRequestParams {
    private Long jdOrderId;    //京东订单号
    private Long skuId;        //京东商品编号
    private Integer pageIndex; //页码
    private Integer pageSize;  //每页记录数

    public Long getJdOrderId() {
        return jdOrderId;
    }

    public void setJdOrderId(Long jdOrderId) {
        this.jdOrderId = jdOrderId;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
