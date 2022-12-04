package com.hqtc.ims.address.model.bean;

import java.io.Serializable;

/**
 * description:市
 * Created by laiqingchuang on 18-7-9 .
 */
public class CityBean implements Serializable {
    private Integer provinceId;
    private Integer cityId;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
