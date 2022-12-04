package com.cibnvideo.jdsynctask.model;

import java.io.Serializable;

public class Sku implements Serializable {
public SkuResponse getBiz_product_sku_query_response() {
		return biz_product_sku_query_response;
	}

	public void setBiz_product_sku_query_response(SkuResponse biz_product_sku_query_response) {
		this.biz_product_sku_query_response = biz_product_sku_query_response;
	}

private SkuResponse biz_product_sku_query_response;
}
