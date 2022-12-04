package com.hqtc.bms.model.params;

import java.io.Serializable;

/**
 * description:校验某订单中某商品是否可以提交售后服务
 * Created by laiqingchuang on 18-7-10 .
 */
public class IsCanAfterSaleResponseParams extends BaseResponseParams implements Serializable {

    private Integer result;

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }
}
