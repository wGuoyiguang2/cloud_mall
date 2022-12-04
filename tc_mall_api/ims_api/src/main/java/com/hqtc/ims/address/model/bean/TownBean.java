package com.hqtc.ims.address.model.bean;

import java.io.Serializable;

/**
 * description:乡
 * Created by laiqingchuang on 18-7-9 .
 */
public class TownBean implements Serializable {
    private Integer provinceId;
    private Integer cityId;
    private Integer countyId;
    private Integer townId;
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

    public Integer getTownId() {
        return townId;
    }

    public void setTownId(Integer townId) {
        this.townId = townId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
