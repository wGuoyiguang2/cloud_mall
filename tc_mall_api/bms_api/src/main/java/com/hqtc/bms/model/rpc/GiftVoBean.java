package com.hqtc.bms.model.rpc;

/**
 * Created by wanghaoyang on 18-7-20.
 */
public class GiftVoBean extends BaseResponseParams {

    private FreightDetailBean result;

    public FreightDetailBean getResult() {
        return result;
    }

    public void setResult(FreightDetailBean result) {
        this.result = result;
    }

}
