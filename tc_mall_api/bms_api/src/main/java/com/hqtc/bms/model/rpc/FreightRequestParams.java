package com.hqtc.bms.model.rpc;

import java.util.List;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/6/29 21:13
 */
public class FreightRequestParams {
    private List<FreightRequestSkuVo> sku;
    private Integer province;//	Integer	Y	京东一级地址编号
    private Integer city;//	Integer	Y	京东二级地址编号
    private Integer county;//	Integer	Y	京东三级地址编号
    private Integer town;//	Integer	Y	京东编号
    private Integer paymentType;//京东支付方式

    public Integer getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Integer paymentType) {
        this.paymentType = paymentType;
    }

    public List<FreightRequestSkuVo> getSku() {
        return sku;
    }

    public void setSku(List<FreightRequestSkuVo> sku) {
        this.sku = sku;
    }

    public Integer getProvince() {
        return province;
    }

    public void setProvince(Integer province) {
        this.province = province;
    }

    public Integer getCity() {
        return city;
    }

    public void setCity(Integer city) {
        this.city = city;
    }

    public Integer getCounty() {
        return county;
    }

    public void setCounty(Integer county) {
        this.county = county;
    }

    public Integer getTown() {
        return town;
    }

    public void setTown(Integer town) {
        this.town = town;
    }
}
