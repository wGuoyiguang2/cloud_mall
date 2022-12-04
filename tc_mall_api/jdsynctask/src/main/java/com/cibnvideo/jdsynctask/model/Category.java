package com.cibnvideo.jdsynctask.model;

import java.io.Serializable;

public class Category implements Serializable {
	public CategoryResponse getJd_biz_product_getcategorys_response() {
		return jd_biz_product_getcategorys_response;
	}

	public void setJd_biz_product_getcategorys_response(CategoryResponse jd_biz_product_getcategorys_response) {
		this.jd_biz_product_getcategorys_response = jd_biz_product_getcategorys_response;
	}

	private CategoryResponse jd_biz_product_getcategorys_response;
}
