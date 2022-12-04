package com.cibnvideo.oms.bean;

import org.apache.commons.codec.binary.StringUtils;

import java.io.Serializable;
import java.math.BigDecimal;

public class ProductInfo implements Serializable {

	public BigDecimal getJdPrice() {
		return jdPrice;
	}

	public void setJdPrice(BigDecimal jdPrice) {
		this.jdPrice = jdPrice;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Long getSku() {
		return sku;
	}

	public void setSku(Long sku) {
		this.sku = sku;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
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
        } else if (obj instanceof ProductInfo) {  
        	ProductInfo productObj = (ProductInfo) obj; 
            if (StringUtils.equals(this.getImagePath(), productObj.getImagePath()) &&
            		StringUtils.equals(this.getBrandName(), productObj.getBrandName()) &&
            		StringUtils.equals(this.getCategory(), productObj.getCategory()) &&
            		StringUtils.equals(this.getName(), productObj.getName()) &&
            		(this.state ==  null? productObj.getState() == null:this.state.equals(productObj.getState())) &&
            		(this.sku == null ? productObj.getSku() == null: this.sku.equals(productObj.getSku()))&&
            		(this.jdPrice ==  null? productObj.getJdPrice() == null:this.jdPrice.compareTo(productObj.getJdPrice()) == 0) &&
            		(this.price ==  null? productObj.getPrice() == null:this.price.compareTo(productObj.getPrice()) == 0) ) {
                return true;  
            }  
  
        }  
        return false;  
    }

	private String imagePath;
	private Integer state;
	private Long sku;
	private String brandName;
	private String category;
	private String name;
	private BigDecimal jdPrice;
	private BigDecimal price;
}
