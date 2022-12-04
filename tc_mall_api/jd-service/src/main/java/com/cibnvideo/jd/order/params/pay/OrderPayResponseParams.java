package com.cibnvideo.jd.order.params.pay;

import com.cibnvideo.jd.common.params.BaseResponseParams;

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

    class PayVo extends BaseResponseParams {
        private Boolean result;

        public Boolean getResult() {
            return result;
        }

        public void setResult(Boolean result) {
            this.result = result;
        }
    }
}
