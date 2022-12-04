package com.hqtc.ims.address.model.bean;

import java.io.Serializable;

/**
 * description:省
 * Created by laiqingchuang on 18-7-9 .
 */
public class ProvinceBean implements Serializable {
    private Integer provinceId;
    private String name;

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
