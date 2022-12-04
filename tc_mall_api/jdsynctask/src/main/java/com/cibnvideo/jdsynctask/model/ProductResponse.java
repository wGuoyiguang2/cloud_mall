package com.cibnvideo.jdsynctask.model;

import java.io.Serializable;

public class ProductResponse extends BaseResponseParams implements Serializable {
    private ProductResult result;

    public ProductResult getResult() {
        return result;
    }

    public void setResult(ProductResult result) {
        this.result = result;
    }
}
