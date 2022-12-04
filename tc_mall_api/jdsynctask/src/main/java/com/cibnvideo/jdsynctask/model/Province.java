package com.cibnvideo.jdsynctask.model;

import org.apache.commons.codec.binary.StringUtils;

import java.io.Serializable;

public class Province implements Serializable {
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

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        } else if (obj instanceof Province) {
            Province provinceObj = (Province) obj;
            if (StringUtils.equals(this.getName(), provinceObj.getName()) &&
                    (this.provinceId == null ? provinceObj.getProvinceId() == null : this.provinceId.equals(provinceObj.getProvinceId()))) {
                return true;
            }

        }
        return false;
    }

    private Integer provinceId;
    private String name;
}
