package com.hqtc.bms.model.params;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by wanghaoyang on 18-7-5.
 */
public class OrderDetailParams {

    @NotBlank(message = "请传入正确的订单号")
    private String orderSn;
    private String mac1;

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public String getMac1() {
        return mac1;
    }

    public void setMac1(String mac1) {
        this.mac1 = mac1;
    }
}
