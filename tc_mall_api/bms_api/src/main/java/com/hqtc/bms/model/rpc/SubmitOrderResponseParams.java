package com.hqtc.bms.model.rpc;

import java.util.List;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/7/4 15:48
 */
public class SubmitOrderResponseParams {


    private OrderVo biz_order_unite_submit_response;

    public OrderVo getBiz_order_unite_submit_response() {
        return biz_order_unite_submit_response;
    }

    public void setBiz_order_unite_submit_response(OrderVo biz_order_unite_submit_response) {
        this.biz_order_unite_submit_response = biz_order_unite_submit_response;
    }
}
