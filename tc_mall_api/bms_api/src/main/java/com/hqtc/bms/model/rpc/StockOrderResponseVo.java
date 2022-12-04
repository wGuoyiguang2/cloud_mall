package com.hqtc.bms.model.rpc;

import java.util.List;

/**
 * Created by wanghaoyang on 18-7-17.
 */
public class StockOrderResponseVo extends BaseResponseParams {
    private List<StockVo> result;

    public List<StockVo> getResult() {
        return result;
    }

    public void setResult(List<StockVo> result) {
        this.result = result;
    }
}
