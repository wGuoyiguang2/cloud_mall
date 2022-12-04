package com.cibnvideo.jdsynctask.model;

import org.apache.commons.codec.binary.StringUtils;

import java.io.Serializable;

public class Town implements Serializable {
	public Integer getTownId() {
		return townId;
	}

	public void setTownId(Integer townId) {
		this.townId = townId;
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
        } else if (obj instanceof Town) {
        	Town townObj = (Town) obj; 
            if (StringUtils.equals(this.getName(), townObj.getName()) &&
            		(this.townId ==  null? townObj.getTownId() == null:this.townId.equals(townObj.getTownId())) && 
            		(this.countyId ==  null? townObj.getCountyId() == null:this.countyId.equals(townObj.getCountyId())) && 
            		(this.cityId ==  null? townObj.getCityId() == null:this.cityId.equals(townObj.getCityId())) && 
            		(this.provinceId ==  null? townObj.getProvinceId() == null:this.provinceId.equals(townObj.getProvinceId())) ) {  
                return true;  
            }  
  
        }  
        return false;  
    }

	private Integer townId;
	private Integer countyId;
	private Integer cityId;
	private Integer provinceId;
	private String name;
}
