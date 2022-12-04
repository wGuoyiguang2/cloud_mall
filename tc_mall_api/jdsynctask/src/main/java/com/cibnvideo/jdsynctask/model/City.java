package com.cibnvideo.jdsynctask.model;

import org.apache.commons.codec.binary.StringUtils;

import java.io.Serializable;

public class City implements Serializable {
	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

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
        } else if (obj instanceof City) {  
        	City cityObj = (City) obj; 
            if (StringUtils.equals(this.getName(), cityObj.getName()) &&
            		(this.cityId ==  null? cityObj.getCityId() == null:this.cityId.equals(cityObj.getCityId())) && 
            		(this.provinceId ==  null? cityObj.getProvinceId() == null:this.provinceId.equals(cityObj.getProvinceId())) ) {  
                return true;  
            }  
  
        }  
        return false;  
    }

	private Integer cityId;
	private Integer provinceId;
	private String name;
}
