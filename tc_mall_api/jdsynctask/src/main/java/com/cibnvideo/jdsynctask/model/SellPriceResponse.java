package com.cibnvideo.jdsynctask.model;

import java.io.Serializable;
import java.util.List;

public class SellPriceResponse extends BaseResponseParams implements Serializable {
    private List<SellPriceResult> result;

    public List<SellPriceResult> getResult() {
        return result;
    }

    public void setResult(List<SellPriceResult> result) {
        this.result = result;
    }
}
