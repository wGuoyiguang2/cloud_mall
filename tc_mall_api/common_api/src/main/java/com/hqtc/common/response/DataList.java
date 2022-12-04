package com.hqtc.common.response;

import java.io.Serializable;

public class DataList<T> implements Serializable {
	public int getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	private int totalRows;
	private T data;
}
