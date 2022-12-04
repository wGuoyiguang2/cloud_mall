package com.hqtc.bms.model.params;

import java.io.Serializable;

/**
 * description:填写客户发运信息
 * Created by laiqingchuang on 18-7-11 .
 */
public class SendSkuResponseParams extends BaseResponseParams implements Serializable {
    private Boolean result;

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }
}