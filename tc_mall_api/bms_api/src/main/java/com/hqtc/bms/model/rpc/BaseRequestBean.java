package com.hqtc.bms.model.rpc;

import java.io.Serializable;

/**
 * description:最终发生post请求bean
 * Created by laiqingchuang on 18-7-4 .
 */
public class BaseRequestBean implements Serializable {
    private String order;

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}