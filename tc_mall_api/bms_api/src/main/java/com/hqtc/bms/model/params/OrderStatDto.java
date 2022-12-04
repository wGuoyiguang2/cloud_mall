package com.hqtc.bms.model.params;

import java.io.Serializable;

/**
 * description:JD订单状态
 * Created by laiqingchuang on 18-7-17 .
 */
public class OrderStatDto implements Serializable {

    private String jdOrderId;
    private String jdOrderState;
    private String jdOrderStateDesc;
    private Integer state;   //物流状态 0是新建  1是妥投   2是拒收

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

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
