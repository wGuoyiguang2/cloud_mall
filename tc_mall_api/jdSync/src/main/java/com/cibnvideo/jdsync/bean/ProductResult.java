package com.cibnvideo.jdsync.bean;

import org.apache.commons.codec.binary.StringUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ProductResult implements Serializable {

	public Integer getCat0() {
		return cat0;
	}

	public void setCat0(Integer cat0) {
		this.cat0 = cat0;
	}

	public Integer getCat1() {
		return cat1;
	}

	public void setCat1(Integer cat1) {
		this.cat1 = cat1;
	}

	public Integer getCat2() {
		return cat2;
	}

	public void setCat2(Integer cat2) {
		this.cat2 = cat2;
	}

	public String getSaleUnit() {
		return saleUnit;
	}

	public void setSaleUnit(String saleUnit) {
		this.saleUnit = saleUnit;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getProductArea() {
		return productArea;
	}

	public void setProductArea(String productArea) {
		this.productArea = productArea;
	}

	public String getWareQD() {
		return wareQD;
	}

	public void setWareQD(String wareQD) {
		this.wareQD = wareQD;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getVideoPath() {
		return videoPath;
	}

	public void setVideoPath(String videoPath) {
		this.videoPath = videoPath;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
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

	public String getUpc() {
		return upc;
	}

	public void setUpc(String upc) {
		this.upc = upc;
	}

	public String getAppintroduce() {
		return appintroduce;
	}

	public void setAppintroduce(String appintroduce) {
		this.appintroduce = appintroduce;
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

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getJdPrice() {
		return jdPrice;
	}

	public void setJdPrice(BigDecimal jdPrice) {
		this.jdPrice = jdPrice;
	}

	public Date getCtime() {
		return ctime;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

	public Date getUtime() {
		return utime;
	}

	public void setUtime(Date utime) {
		this.utime = utime;
	}

	@Override
	public boolean equals(Object obj) {
        if (obj == null) {  
            return false;  
        } else if (obj instanceof ProductResult) {  
        	ProductResult productObj = (ProductResult) obj; 
            if (StringUtils.equals(this.getSaleUnit(), productObj.getSaleUnit()) &&
            		StringUtils.equals(this.getWeight(), productObj.getWeight()) &&
            		StringUtils.equals(this.getProductArea(), productObj.getProductArea()) &&
            		StringUtils.equals(this.getWareQD(), productObj.getWareQD()) &&
            		StringUtils.equals(this.getImagePath(), productObj.getImagePath()) &&
            		StringUtils.equals(this.getParam(), productObj.getParam()) &&
            		StringUtils.equals(this.getBrandName(), productObj.getBrandName()) &&
            		StringUtils.equals(this.getUpc(), productObj.getUpc()) &&
            		StringUtils.equals(this.getAppintroduce(), productObj.getAppintroduce()) &&
            		StringUtils.equals(this.getCategory(), productObj.getCategory()) &&
            		StringUtils.equals(this.getName(), productObj.getName()) &&
            		StringUtils.equals(this.getIntroduction(), productObj.getIntroduction()) &&
            		(this.state ==  null? productObj.getState() == null:this.state.equals(productObj.getState())) &&
            		(this.sku == null ? productObj.getSku() == null: this.sku.equals(productObj.getSku()))&&
            		(this.cat0 ==  null? productObj.getCat0() == null:this.cat0.equals(productObj.getCat0())) &&
            		(this.cat1 ==  null? productObj.getCat1() == null:this.cat1.equals(productObj.getCat1())) &&
            		(this.cat2 ==  null? productObj.getCat2() == null:this.cat2.equals(productObj.getCat2()))) {
                return true;  
            }  
  
        }  
        return false;  
    }

	private String saleUnit;
	private String weight="";
	private String productArea="";
	private String wareQD="";
	private String imagePath="";
	private String videoPath="";
	private String param="";
	private Integer state;
	private Long sku;
	private String brandName="";
	private String upc="";
	private String appintroduce="";
	private String category="";
	private Integer cat0;
	private Integer cat1;
	private Integer cat2;
	private BigDecimal price;
	private BigDecimal jdPrice;
	private String name="";
	private String introduction="";
	private Date ctime;
	private Date utime;
}
