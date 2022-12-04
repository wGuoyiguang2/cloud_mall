package com.hqtc.bms.model.rpc;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/7/4 16:37
 */
public class CancelOrderResponseParams {

    private CancelOrderVo biz_order_cancelorder_response;

    public CancelOrderVo getBiz_order_cancelorder_response() {
        return biz_order_cancelorder_response;
    }

    public void setBiz_order_cancelorder_response(CancelOrderVo biz_order_cancelorder_response) {
        this.biz_order_cancelorder_response = biz_order_cancelorder_response;
    }
}
