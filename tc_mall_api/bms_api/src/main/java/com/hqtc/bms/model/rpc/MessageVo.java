package com.hqtc.bms.model.rpc;

import java.util.List;

/**
 * Created by wanghaoyang on 18-8-10.
 */
public class MessageVo extends BaseResponseParams{
    private List<MessageRepVo> result;

    public List<MessageRepVo> getResult() {
        return result;
    }

    public void setResult(List<MessageRepVo> result) {
        this.result = result;
    }
}
