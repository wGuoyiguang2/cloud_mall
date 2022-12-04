package com.hqtc.cms.model.bean;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * description:
 * Created by laiqingchuang on 18-8-14 .
 */
public class FuwuSkuDetailBean implements Serializable {
    private Integer bindSkuId; //保障服务skuId 
    private String bindSkuName;//保障服务sku名称（6字内）
    private Boolean favor;     //是否是优惠保障服务
    private BigDecimal price;  //保障服务sku价格
    private Integer sortIndex; //显示排序
    private String tip;        //保障服务说明提示语（20字内）

    public Integer getBindSkuId() {
        return bindSkuId;
    }

    public void setBindSkuId(Integer bindSkuId) {
        this.bindSkuId = bindSkuId;
    }

    public String getBindSkuName() {
        return bindSkuName;
    }

    public void setBindSkuName(String bindSkuName) {
        this.bindSkuName = bindSkuName;
    }

    public Boolean getFavor() {
        return favor;
    }

    public void setFavor(Boolean favor) {
        this.favor = favor;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getSortIndex() {
        return sortIndex;
    }

    public void setSortIndex(Integer sortIndex) {
        this.sortIndex = sortIndex;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }
}
