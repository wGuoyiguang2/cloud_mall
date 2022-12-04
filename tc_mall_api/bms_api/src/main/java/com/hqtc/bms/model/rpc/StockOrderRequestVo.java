package com.hqtc.bms.model.rpc;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/6/23 15:40
 */
public class StockOrderRequestVo {
    private Long skuId;
    private Integer num;

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}
