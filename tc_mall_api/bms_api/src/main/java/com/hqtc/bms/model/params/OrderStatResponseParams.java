package com.hqtc.bms.model.params;

import java.io.Serializable;

/**
 * description:订单状态
 * Created by laiqingchuang on 18-7-17 .
 */
public class OrderStatResponseParams implements Serializable {
    private OrderStatDto result;

    public OrderStatDto getResult() {
        return result;
    }

    public void setResult(OrderStatDto result) {
        this.result = result;
    }
}
