package com.hqtc.bms.model.params;

import java.io.Serializable;

/**
 * description:申请售后服务（退货、换货、维修）
 * Created by laiqingchuang on 18-7-11 .
 */
public class ApplyAfterSaleResponseParams extends BaseResponseParams implements Serializable {
    private Boolean result;

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }
}