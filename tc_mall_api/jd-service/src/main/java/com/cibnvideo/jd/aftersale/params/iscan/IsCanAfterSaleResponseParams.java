package com.cibnvideo.jd.aftersale.params.iscan;

import com.cibnvideo.jd.common.params.BaseResponseParams;

/**
 * description:校验某订单中某商品是否可以提交售后服务
 * Created by laiqingchuang on 18-7-10 .
 */
public class IsCanAfterSaleResponseParams {
    private OrderVo biz_afterSale_availableNumberComp_query_response;

    public OrderVo getBiz_afterSale_availableNumberComp_query_response() {
        return biz_afterSale_availableNumberComp_query_response;
    }

    public void setBiz_afterSale_availableNumberComp_query_response(OrderVo biz_afterSale_availableNumberComp_query_response) {
        this.biz_afterSale_availableNumberComp_query_response = biz_afterSale_availableNumberComp_query_response;
    }

    class OrderVo extends BaseResponseParams {
        private Integer result;

        public Integer getResult() {
            return result;
        }

        public void setResult(Integer result) {
            this.result = result;
        }
    }
}
