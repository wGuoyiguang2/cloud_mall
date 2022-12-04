package com.hqtc.bms.model.rpc;

/**
 * Created by wanghaoyang on 18-7-17.
 */
public class StockVo {

    private String areaId;//	String	配送地址ID
    private Long skuId;//	Long	商品ID
    private Integer stockStateId;//	Integer	库存状态编号 33,39,40,36,34
    private String stockStateDesc;//	String	库存状态描述
    private Integer remainNum;//	Integer	剩余数量 -1代表未知：

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public Integer getStockStateId() {
        return stockStateId;
    }

    public void setStockStateId(Integer stockStateId) {
        this.stockStateId = stockStateId;
    }

    public String getStockStateDesc() {
        return stockStateDesc;
    }

    public void setStockStateDesc(String stockStateDesc) {
        this.stockStateDesc = stockStateDesc;
    }

    public Integer getRemainNum() {
        return remainNum;
    }

    public void setRemainNum(Integer remainNum) {
        this.remainNum = remainNum;
    }
}
