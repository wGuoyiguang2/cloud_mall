package com.cibnvideo.jdsynctask.model;

import java.io.Serializable;
import java.util.List;

public class CategoryResult implements Serializable {
	public List<CategoryResultInfo> getCategorys() {
		return categorys;
	}

	public void setCategorys(List<CategoryResultInfo> categorys) {
		this.categorys = categorys;
	}

	public Integer getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(Integer totalRows) {
		this.totalRows = totalRows;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	private List<CategoryResultInfo> categorys;
	private Integer totalRows;
	private Integer pageNo;
	private Integer pageSize;
}
