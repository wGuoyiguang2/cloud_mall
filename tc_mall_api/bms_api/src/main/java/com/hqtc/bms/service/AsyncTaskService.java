package com.hqtc.bms.service;

import java.util.List;

/**
 * Created by wanghaoyang on 18-7-18.
 */
public interface AsyncTaskService {

    /**
     * 同步京东订单状态,写入t_order.order_state字段
     * @param jdOrderIds 京东订单列表
     * */
    void syncOrderState(List<String> jdOrderIds);
}
