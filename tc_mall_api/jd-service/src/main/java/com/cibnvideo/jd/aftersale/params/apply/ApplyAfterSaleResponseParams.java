package com.cibnvideo.jd.aftersale.params.apply;

import com.cibnvideo.jd.common.params.BaseResponseParams;

/**
 * description:申请售后服务（退货、换货、维修）
 * Created by laiqingchuang on 18-7-11 .
 */
public class ApplyAfterSaleResponseParams {
    private OrderVo biz_afterSale_afsApply_create_response;

    public OrderVo getBiz_afterSale_afsApply_create_response() {
        return biz_afterSale_afsApply_create_response;
    }

    public void setBiz_afterSale_afsApply_create_response(OrderVo biz_afterSale_afsApply_create_response) {
        this.biz_afterSale_afsApply_create_response = biz_afterSale_afsApply_create_response;
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
