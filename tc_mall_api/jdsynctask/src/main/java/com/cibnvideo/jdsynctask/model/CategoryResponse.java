package com.cibnvideo.jdsynctask.model;

import java.io.Serializable;

public class CategoryResponse extends BaseResponseParams implements Serializable {
	private CategoryResult result;

	public CategoryResult getResult() {
		return result;
	}

	public void setResult(CategoryResult result) {
		this.result = result;
	}
}
