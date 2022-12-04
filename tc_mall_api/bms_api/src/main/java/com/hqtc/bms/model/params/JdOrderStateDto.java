package com.hqtc.bms.model.params;

import java.io.Serializable;

/**
 * description:JD订单信息
 * Created by laiqingchuang on 19-2-15 .
 */
public class JdOrderStateDto implements Serializable {
    private String jdOrderId;
    private String jdOrderState;
    private String jdOrderStateDesc;

    public String getJdOrderId() {
        return jdOrderId;
    }

    public void setJdOrderId(String jdOrderId) {
        this.jdOrderId = jdOrderId;
    }

    public String getJdOrderState() {
        return jdOrderState;
    }

    public void setJdOrderState(String jdOrderState) {
        this.jdOrderState = jdOrderState;
    }

    public String getJdOrderStateDesc() {
        return jdOrderStateDesc;
    }

    public void setJdOrderStateDesc(String jdOrderStateDesc) {
        this.jdOrderStateDesc = jdOrderStateDesc;
    }
}
