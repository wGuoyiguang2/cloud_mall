package com.hqtc.bms.model.params;

import java.io.Serializable;

/**
 * description:订单商品表
 * Created by laiqingchuang on 18-7-25 .
 */
public class OrderProductBean  implements Serializable {
    private String orderSn;     //平台订单号
    private String childTradeNo;//子订单号
    private Integer orderState; //订单状态

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public String getChildTradeNo() {
        return childTradeNo;
    }

    public void setChildTradeNo(String childTradeNo) {
        this.childTradeNo = childTradeNo;
    }

    public Integer getOrderState() {
        return orderState;
    }

    public void setOrderState(Integer orderState) {
        this.orderState = orderState;
    }
}
