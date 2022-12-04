package com.cibnvideo.jd.aftersale.params.apply;

/**
 * description:取件信息实体
 * Created by laiqingchuang on 18-7-11 .
 */
public class AfterSalePickwareDto {
    private String pickwareType;        //取件方式 4上门取件 7客户送货 40客户发货
    private Integer pickwareProvince;   //取件省
    private Integer pickwareCity;       //取件市
    private Integer pickwareCounty;     //取件县
    private Integer pickwareVillage;    //取件乡镇
    private String pickwareAddress;     //取件街道地址

    public String getPickwareType() {
        return pickwareType;
    }

    public void setPickwareType(String pickwareType) {
        this.pickwareType = pickwareType;
    }

    public Integer getPickwareProvince() {
        return pickwareProvince;
    }

    public void setPickwareProvince(Integer pickwareProvince) {
        this.pickwareProvince = pickwareProvince;
    }

    public Integer getPickwareCity() {
        return pickwareCity;
    }

    public void setPickwareCity(Integer pickwareCity) {
        this.pickwareCity = pickwareCity;
    }

    public Integer getPickwareCounty() {
        return pickwareCounty;
    }

    public void setPickwareCounty(Integer pickwareCounty) {
        this.pickwareCounty = pickwareCounty;
    }

    public Integer getPickwareVillage() {
        return pickwareVillage;
    }

    public void setPickwareVillage(Integer pickwareVillage) {
        this.pickwareVillage = pickwareVillage;
    }

    public String getPickwareAddress() {
        return pickwareAddress;
    }

    public void setPickwareAddress(String pickwareAddress) {
        this.pickwareAddress = pickwareAddress;
    }
}
