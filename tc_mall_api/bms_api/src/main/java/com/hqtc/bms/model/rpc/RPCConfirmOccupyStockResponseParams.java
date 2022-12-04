package com.hqtc.bms.model.rpc;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/7/4 16:18
 */
public class RPCConfirmOccupyStockResponseParams {
    private RPCStockVo biz_order_occupyStock_confirm_response;

    public RPCStockVo getBiz_order_occupyStock_confirm_response() {
        return biz_order_occupyStock_confirm_response;
    }

    public void setBiz_order_occupyStock_confirm_response(RPCStockVo biz_order_occupyStock_confirm_response) {
        this.biz_order_occupyStock_confirm_response = biz_order_occupyStock_confirm_response;
    }
}
