package com.hqtc.bms.model.params;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by wanghaoyang on 19-1-9.
 */
public class VenderOrderNotifyParams {
    private int venderId;

    @NotNull(message = "请传入正确的outTradeNo")
    @NotBlank(message = "请传入正确的outTradeNo")
    @Size(min = 10, max = 32, message = "请传入正确的outTradeNo")
    private String outTradeNo;

    @NotNull(message = "请传入正确的orderSn")
    @NotBlank(message = "请传入正确的orderSn")
    private String orderSn;
    private int totalFee;

    @NotNull(message = "请传入正确的payType")
    @Min(value = 1, message = "请传入正确的payType")
    private int payType;

    public int getVenderId() {
        return venderId;
    }

    public void setVenderId(int venderId) {
        this.venderId = venderId;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public int getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(int totalFee) {
        this.totalFee = totalFee;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }
}
