package com.cibnvideo.jd.order.params.cancel;

import com.cibnvideo.jd.common.params.BaseResponseParams;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/7/4 16:37
 */
public class CancelOrderResponseParams {

    private OrderVo biz_order_cancelorder_response;

    public OrderVo getBiz_order_cancelorder_response() {
        return biz_order_cancelorder_response;
    }

    public void setBiz_order_cancelorder_response(OrderVo biz_order_cancelorder_response) {
        this.biz_order_cancelorder_response = biz_order_cancelorder_response;
    }

    class OrderVo extends BaseResponseParams {
        private Boolean result;

        public Boolean getResult() {
            return result;
        }

        public void setResult(Boolean result) {
            this.result = result;
        }
    }
}
