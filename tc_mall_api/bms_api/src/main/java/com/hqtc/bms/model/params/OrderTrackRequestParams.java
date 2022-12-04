package com.hqtc.bms.model.params;

import java.io.Serializable;

/**
 * description:查询配送信息
 * Created by laiqingchuang on 18-7-10 .
 */
public class OrderTrackRequestParams implements Serializable {
    private String jdOrderId;

    public String getJdOrderId() {
        return jdOrderId;
    }

    public void setJdOrderId(String jdOrderId) {
        this.jdOrderId = jdOrderId;
    }
}
