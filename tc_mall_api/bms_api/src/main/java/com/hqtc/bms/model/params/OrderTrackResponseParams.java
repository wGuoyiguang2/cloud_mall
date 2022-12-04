package com.hqtc.bms.model.params;

import java.io.Serializable;

/**
 * description:查询配送信息
 * Created by laiqingchuang on 18-7-10 .
 */
public class OrderTrackResponseParams extends BaseResponseParams implements Serializable {

    private OrderTrackDto result;

    public OrderTrackDto getResult() {
        return result;
    }

    public void setResult(OrderTrackDto result) {
        this.result = result;
    }
}
