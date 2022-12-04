package com.cibnvideo.common.utils;

import java.util.List;

public class Result<T> {
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public List<T> getRows() {
		return rows;
	}
	public void setRows(List<T> rows) {
		this.rows = rows;
	}
	private int total;
	private List<T> rows;
}
