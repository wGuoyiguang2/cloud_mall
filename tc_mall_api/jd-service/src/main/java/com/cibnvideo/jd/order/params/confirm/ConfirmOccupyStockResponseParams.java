package com.cibnvideo.jd.order.params.confirm;

import com.cibnvideo.jd.common.params.BaseResponseParams;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/7/4 16:18
 */
public class ConfirmOccupyStockResponseParams {
    private StockVo biz_order_occupyStock_confirm_response;

    public StockVo getBiz_order_occupyStock_confirm_response() {
        return biz_order_occupyStock_confirm_response;
    }

    public void setBiz_order_occupyStock_confirm_response(StockVo biz_order_occupyStock_confirm_response) {
        this.biz_order_occupyStock_confirm_response = biz_order_occupyStock_confirm_response;
    }

    class StockVo extends BaseResponseParams{
        private Boolean result;

        public Boolean getResult() {
            return result;
        }

        public void setResult(Boolean result) {
            this.result = result;
        }
    }
}
