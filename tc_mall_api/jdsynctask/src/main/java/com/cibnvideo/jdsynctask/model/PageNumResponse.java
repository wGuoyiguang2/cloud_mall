package com.cibnvideo.jdsynctask.model;

import java.io.Serializable;
import java.util.List;

public class PageNumResponse extends BaseResponseParams implements Serializable {
	private List<PageNumInfo> result;

	public List<PageNumInfo> getResult() {
		return result;
	}

	public void setResult(List<PageNumInfo> result) {
		this.result = result;
	}
}
