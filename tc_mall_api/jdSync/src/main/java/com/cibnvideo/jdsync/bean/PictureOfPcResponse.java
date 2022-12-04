package com.cibnvideo.jdsync.bean;

import org.apache.commons.codec.binary.StringUtils;

import java.io.Serializable;

public class PictureOfPcResponse implements Serializable {
	public Long getSku() {
		return sku;
	}

	public void setSku(Long sku) {
		this.sku = sku;
	}

	public String getPropCode() {
		return propCode;
	}

	public void setPropCode(String propCode) {
		this.propCode = propCode;
	}

	public String getWareQD() {
		return wareQD;
	}

	public void setWareQD(String wareQD) {
		this.wareQD = wareQD;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getwReadMe() {
		return wReadMe;
	}

	public void setwReadMe(String wReadMe) {
		this.wReadMe = wReadMe;
	}

	public String getShouhou() {
		return shouhou;
	}

	public void setShouhou(String shouhou) {
		this.shouhou = shouhou;
	}

	public String getWdis() {
		return wdis;
	}

	public void setWdis(String wdis) {
		this.wdis = wdis;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		} else if (obj instanceof PictureOfPcResponse) {
			PictureOfPcResponse pictureOfPcObj = (PictureOfPcResponse) obj;
			if ((this.sku == null ? pictureOfPcObj.getSku() == null : this.sku.equals(pictureOfPcObj.getSku()))
					&& StringUtils.equals(this.getPropCode(), pictureOfPcObj.getPropCode())
					&& StringUtils.equals(this.getWareQD(), pictureOfPcObj.getWareQD())
					&& StringUtils.equals(this.getService(), pictureOfPcObj.getService())
					&& StringUtils.equals(this.getCode(), pictureOfPcObj.getCode())
					&& StringUtils.equals(this.getwReadMe(), pictureOfPcObj.getwReadMe())
					&& StringUtils.equals(this.getShouhou(), pictureOfPcObj.getShouhou())
					&& StringUtils.equals(this.getWdis(), pictureOfPcObj.getWdis())) {
				return true;
			}

		}
		return false;
	}

	private Long sku;
	private String propCode;
	private String wareQD;
	private String service;
	private String code;
	private String wReadMe;
	private String shouhou;
	private String wdis;
}
