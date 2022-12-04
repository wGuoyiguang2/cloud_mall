package com.cibnvideo.jdsynctask.model;

import java.io.Serializable;

public class PageNumInfo implements Serializable {
public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPage_num() {
		return page_num;
	}
	public void setPage_num(String page_num) {
		this.page_num = page_num;
	}
private String name;
private String page_num;

}
