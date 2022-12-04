package com.cibnvideo.jd.aftersale.params.cancel;

import com.cibnvideo.jd.common.params.BaseResponseParams;

/**
 * description:取消服务
 * Created by laiqingchuang on 18-7-11 .
 */
public class CancelServiceResponseParams {

    private OrderVo biz_afterSale_auditCancel_query_response;

    public OrderVo getBiz_afterSale_auditCancel_query_response() {
        return biz_afterSale_auditCancel_query_response;
    }

    public void setBiz_afterSale_auditCancel_query_response(OrderVo biz_afterSale_auditCancel_query_response) {
        this.biz_afterSale_auditCancel_query_response = biz_afterSale_auditCancel_query_response;
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
