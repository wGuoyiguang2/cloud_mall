package com.cibnvideo.jdsynctask.model;

import org.apache.commons.codec.binary.StringUtils;

import java.io.Serializable;

public class CategoryResultInfo implements Serializable {
	public Integer getCatId() {
		return catId;
	}

	public void setCatId(Integer catId) {
		this.catId = catId;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCatClass() {
		return catClass;
	}

	public void setCatClass(Integer catClass) {
		this.catClass = catClass;
	}


	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		} else if (obj instanceof CategoryResultInfo) {
			CategoryResultInfo categoryResultInfoObj = (CategoryResultInfo) obj;
			if ((this.catId == null ? categoryResultInfoObj.getCatId() == null : this.catId.equals(categoryResultInfoObj.getCatId()))
					&& (this.parentId == null ? categoryResultInfoObj.getParentId() == null : this.parentId.equals(categoryResultInfoObj.getParentId()))
					&& (this.catClass == null ? categoryResultInfoObj.getCatClass() == null : this.catClass.equals(categoryResultInfoObj.getCatClass()))
					&& (this.state == null ? categoryResultInfoObj.getState() == null : this.state.equals(categoryResultInfoObj.getState()))
					&& StringUtils.equals(this.getName(), categoryResultInfoObj.getName())) {
				return true;
			}

		}
		return false;
	}

	private Integer catId;
	private Integer parentId;
	private String name;
	private Integer catClass;
	private Integer state;
}
