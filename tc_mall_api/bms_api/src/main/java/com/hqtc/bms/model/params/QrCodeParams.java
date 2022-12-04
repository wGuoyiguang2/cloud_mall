package com.hqtc.bms.model.params;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by wanghaoyang on 18-7-5.
 */
public class QrCodeParams {
    @NotNull(message = "请传入正确的userId")
    @Min(value = 1, message = "请传入正确的userId")
    private int userId;

    @NotNull(message = "请传入正确的venderId")
    @Min(value = 1, message = "请传入正确的venderId")
    private int venderId;

    @NotBlank(message = "请传入正确的orderSn")
    private String orderSn;

    @Min(value = 1, message = "请传入正确的支付类型payType:3小程序,2支付宝,1微信")
    @Max(value = 3, message = "请传入正确的支付类型payType:3小程序,2支付宝,1微信")
    private int payType = 1;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getVenderId() {
        return venderId;
    }

    public void setVenderId(int venderId) {
        this.venderId = venderId;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }
}
