package com.hqtc.bms.model.rpc;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/7/4 16:26
 */
public class OrderPayResponseParams {
    private PayVo biz_order_doPay_response;

    public PayVo getBiz_order_doPay_response() {
        return biz_order_doPay_response;
    }

    public void setBiz_order_doPay_response(PayVo biz_order_doPay_response) {
        this.biz_order_doPay_response = biz_order_doPay_response;
    }
}
