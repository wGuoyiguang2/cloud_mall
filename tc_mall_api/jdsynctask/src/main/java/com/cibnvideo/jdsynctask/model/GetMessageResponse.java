package com.cibnvideo.jdsynctask.model;

import java.util.List;

public class GetMessageResponse  extends BaseResponseParams{
    private List<GetMessageResponseResult> result;

    public List<GetMessageResponseResult> getResult() {
        return result;
    }

    public void setResult(List<GetMessageResponseResult> result) {
        this.result = result;
    }
}
