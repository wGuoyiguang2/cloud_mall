package com.cibnvideo.jdsynctask.model;

import java.io.Serializable;

public class Product implements Serializable {
    public ProductResponse getBiz_product_detail_query_response() {
        return biz_product_detail_query_response;
    }

    public void setBiz_product_detail_query_response(ProductResponse biz_product_detail_query_response) {
        this.biz_product_detail_query_response = biz_product_detail_query_response;
    }

    private ProductResponse biz_product_detail_query_response;
}
