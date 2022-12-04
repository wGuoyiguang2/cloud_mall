package com.hqtc.ims.address.model.bean;

import java.io.Serializable;

/**
 * description:区
 * Created by laiqingchuang on 18-7-9 .
 */
public class CountyBean implements Serializable {
    private Integer provinceId;
    private Integer cityId;
    private Integer countyId;
    private String name;

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getCountyId() {
        return countyId;
    }

    public void setCountyId(Integer countyId) {
        this.countyId = countyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}