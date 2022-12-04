package com.cibnvideo.jdsynctask.model;

import java.io.Serializable;

public class SkuResponse extends BaseResponseParams implements Serializable {
	private String result;

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
}
