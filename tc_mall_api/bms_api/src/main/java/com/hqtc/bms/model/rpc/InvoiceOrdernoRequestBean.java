package com.hqtc.bms.model.rpc;

import java.io.Serializable;
import java.util.List;

/**
 * description:根据订单号查询发票请求流水号接口
 * Created by laiqingchuang on 18-7-4 .
 */
public class InvoiceOrdernoRequestBean implements Serializable {
    private String identity;      //身份认证
    private List<String> orderno; //订单编号

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public List<String> getOrderno() {
        return orderno;
    }

    public void setOrderno(List<String> orderno) {
        this.orderno = orderno;
    }
}
