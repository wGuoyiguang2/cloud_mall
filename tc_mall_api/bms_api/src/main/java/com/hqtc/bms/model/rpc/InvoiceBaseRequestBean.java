package com.hqtc.bms.model.rpc;

import java.io.Serializable;

/**
 * description:请求参数基类
 * Created by laiqingchuang on 18-7-3 .
 */
public class InvoiceBaseRequestBean implements Serializable {
    private String identity;          //身份认证
    private InvoiceRequestBean order; //订单信息

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public InvoiceRequestBean getOrder() {
        return order;
    }

    public void setOrder(InvoiceRequestBean order) {
        this.order = order;
    }
}
