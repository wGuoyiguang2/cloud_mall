package com.cibnvideo.jd.aftersale.params.apply;

/**
 * description:返件信息实体
 * Created by laiqingchuang on 18-7-11 .
 */
public class AfterSaleReturnwareDto {
    private Integer returnwareType;     //返件方式 自营配送(10),第三方配送(20);换、修这两种情况必填（默认值）
    private Integer returnwareProvince; //返件省  换、修这两种情况必填
    private Integer returnwareCity;     //返件市
    private Integer returnwareCounty;   //返件县
    private Integer returnwareVillage;  //返件乡镇
    private String returnwareAddress;   //返件街道地址 换、修这两种情况必填

    public Integer getReturnwareType() {
        return returnwareType;
    }

    public void setReturnwareType(Integer returnwareType) {
        this.returnwareType = returnwareType;
    }

    public Integer getReturnwareProvince() {
        return returnwareProvince;
    }

    public void setReturnwareProvince(Integer returnwareProvince) {
        this.returnwareProvince = returnwareProvince;
    }

    public Integer getReturnwareCity() {
        return returnwareCity;
    }

    public void setReturnwareCity(Integer returnwareCity) {
        this.returnwareCity = returnwareCity;
    }

    public Integer getReturnwareCounty() {
        return returnwareCounty;
    }

    public void setReturnwareCounty(Integer returnwareCounty) {
        this.returnwareCounty = returnwareCounty;
    }

    public Integer getReturnwareVillage() {
        return returnwareVillage;
    }

    public void setReturnwareVillage(Integer returnwareVillage) {
        this.returnwareVillage = returnwareVillage;
    }

    public String getReturnwareAddress() {
        return returnwareAddress;
    }

    public void setReturnwareAddress(String returnwareAddress) {
        this.returnwareAddress = returnwareAddress;
    }
}
