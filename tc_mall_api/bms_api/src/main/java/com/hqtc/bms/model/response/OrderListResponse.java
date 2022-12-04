package com.hqtc.bms.model.response;

import com.hqtc.bms.model.database.TOrderBean;

import java.util.List;
import java.util.Map;

/**
 * Created by wanghaoyang on 18-7-14.
 */
public class OrderListResponse extends TOrderBean {
    private List<Map<String, Object>> product;
    private String name;

    public List<Map<String, Object>> getProduct() {
        return product;
    }

    public void setProduct(List<Map<String, Object>> product) {
        this.product = product;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
