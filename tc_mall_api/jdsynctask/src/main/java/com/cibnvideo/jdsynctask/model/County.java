package com.cibnvideo.jdsynctask.model;

import org.apache.commons.codec.binary.StringUtils;

import java.io.Serializable;

public class County implements Serializable {
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCountyId() {
		return countyId;
	}

	public void setCountyId(Integer countyId) {
		this.countyId = countyId;
	}

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

	@Override
    public boolean equals(Object obj) {  
        if (obj == null) {  
            return false;  
        } else if (obj instanceof County) {  
        	County countyObj = (County) obj; 
            if (StringUtils.equals(this.getName(), countyObj.getName()) &&
            		(this.countyId ==  null? countyObj.getCountyId() == null:this.countyId.equals(countyObj.getCountyId())) && 
            		(this.cityId ==  null? countyObj.getCityId() == null:this.cityId.equals(countyObj.getCityId())) && 
            		(this.provinceId ==  null? countyObj.getProvinceId() == null:this.provinceId.equals(countyObj.getProvinceId())) ) {  
                return true;  
            }  
  
        }  
        return false;  
    }

	private Integer countyId;
	private Integer cityId;
	private Integer provinceId;
	private String name;
}
