package com.hqtc.bms.model.rpc;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/6/25 10:51
 */
public class OrderRequestParams {
    private String jdOrderId;
    private String queryExts;//参数值可以为orderType,jdOrderState

    public String getJdOrderId() {
        return jdOrderId;
    }

    public void setJdOrderId(String jdOrderId) {
        this.jdOrderId = jdOrderId;
    }

    public String getQueryExts() {
        return queryExts;
    }

    public void setQueryExts(String queryExts) {
        this.queryExts = queryExts;
    }
}
