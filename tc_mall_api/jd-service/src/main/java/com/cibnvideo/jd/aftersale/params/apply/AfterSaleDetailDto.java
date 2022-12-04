package com.cibnvideo.jd.aftersale.params.apply;

/**
 * description:申请单明细实体
 * Created by laiqingchuang on 18-7-11 .
 */
public class AfterSaleDetailDto {
    private Long skuId;                  //商品编号
    private Integer skuNum;              //申请数量

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public Integer getSkuNum() {
        return skuNum;
    }

    public void setSkuNum(Integer skuNum) {
        this.skuNum = skuNum;
    }
}