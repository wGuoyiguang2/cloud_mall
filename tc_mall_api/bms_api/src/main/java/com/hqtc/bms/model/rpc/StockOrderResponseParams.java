package com.hqtc.bms.model.rpc;

import java.util.List;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/6/23 15:45
 */
public class StockOrderResponseParams {
    private StockOrderResponseVo biz_stock_fororder_batget_response;

    public StockOrderResponseVo getBiz_stock_fororder_batget_response() {
        return biz_stock_fororder_batget_response;
    }

    public void setBiz_stock_fororder_batget_response(StockOrderResponseVo biz_stock_fororder_batget_response) {
        this.biz_stock_fororder_batget_response = biz_stock_fororder_batget_response;
    }
}
