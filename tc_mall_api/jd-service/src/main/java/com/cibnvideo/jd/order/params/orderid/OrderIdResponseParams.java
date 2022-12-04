package com.cibnvideo.jd.order.params.orderid;

import com.cibnvideo.jd.common.params.BaseResponseParams;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/7/13 17:34
 */
public class OrderIdResponseParams {
    private OrderIdVo biz_order_jdOrderIDByThridOrderID_query_response;

    public OrderIdVo getBiz_order_jdOrderIDByThridOrderID_query_response() {
        return biz_order_jdOrderIDByThridOrderID_query_response;
    }

    public void setBiz_order_jdOrderIDByThridOrderID_query_response(OrderIdVo biz_order_jdOrderIDByThridOrderID_query_response) {
        this.biz_order_jdOrderIDByThridOrderID_query_response = biz_order_jdOrderIDByThridOrderID_query_response;
    }

    class OrderIdVo extends BaseResponseParams{
        private String result;

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }
    }
}
