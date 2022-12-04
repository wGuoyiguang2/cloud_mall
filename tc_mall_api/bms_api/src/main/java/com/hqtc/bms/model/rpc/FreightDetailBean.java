package com.hqtc.bms.model.rpc;

import java.math.BigDecimal;

/**
 * Created by wanghaoyang on 18-7-20.
 */
public class FreightDetailBean {

    private BigDecimal freight;//	bigdecimal   	总运费
    private BigDecimal baseFreight;//	bigdecimal   	基础运费
    private BigDecimal remoteRegionFreight;//	bigdecimal   	偏远运费
    private String remoteSku;//	string	需收取偏远运费的sku
    private BigDecimal priceDifference = new BigDecimal(0);// 距包邮的价格差

    public BigDecimal getFreight() {
        return freight;
    }

    public void setFreight(BigDecimal freight) {
        this.freight = freight;
    }

    public BigDecimal getBaseFreight() {
        return baseFreight;
    }

    public void setBaseFreight(BigDecimal baseFreight) {
        this.baseFreight = baseFreight;
    }

    public BigDecimal getRemoteRegionFreight() {
        return remoteRegionFreight;
    }

    public void setRemoteRegionFreight(BigDecimal remoteRegionFreight) {
        this.remoteRegionFreight = remoteRegionFreight;
    }

    public String getRemoteSku() {
        return remoteSku;
    }

    public void setRemoteSku(String remoteSku) {
        this.remoteSku = remoteSku;
    }

    public BigDecimal getPriceDifference() {
        return priceDifference;
    }

    public void setPriceDifference(BigDecimal priceDifference) {
        this.priceDifference = priceDifference;
    }
}
