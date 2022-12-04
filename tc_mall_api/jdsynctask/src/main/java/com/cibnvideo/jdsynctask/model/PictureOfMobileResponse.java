package com.cibnvideo.jdsynctask.model;

import org.apache.commons.codec.binary.StringUtils;

import java.io.Serializable;

public class PictureOfMobileResponse implements Serializable {
	public Long getSku() {
		return sku;
	}

	public void setSku(Long sku) {
		this.sku = sku;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
    public boolean equals(Object obj) {  
        if (obj == null) {  
            return false;  
        } else if (obj instanceof PictureOfMobileResponse) {  
        	PictureOfMobileResponse pictureOfMobileObj = (PictureOfMobileResponse) obj; 
            if ((this.sku == null ? pictureOfMobileObj.getSku() == null: this.sku.equals(pictureOfMobileObj.getSku()))&&
            		StringUtils.equals(this.getResult(), pictureOfMobileObj.getResult()) &&
            		StringUtils.equals(this.getCode(), pictureOfMobileObj.getCode()) ) {  
                return true;  
            }  
  
        }  
        return false;  
    }

	private Long sku;
	private String result;
	private String code;
}
