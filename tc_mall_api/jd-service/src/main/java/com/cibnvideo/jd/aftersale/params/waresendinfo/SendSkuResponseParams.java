package com.cibnvideo.jd.aftersale.params.waresendinfo;

import com.cibnvideo.jd.common.params.BaseResponseParams;

/**
 * description:填写客户发运信息
 * Created by laiqingchuang on 18-7-11 .
 */
public class SendSkuResponseParams {

    private OrderVo biz_afterSale_sendSku_update_response;

    public OrderVo getBiz_afterSale_sendSku_update_response() {
        return biz_afterSale_sendSku_update_response;
    }

    public void setBiz_afterSale_sendSku_update_response(OrderVo biz_afterSale_sendSku_update_response) {
        this.biz_afterSale_sendSku_update_response = biz_afterSale_sendSku_update_response;
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