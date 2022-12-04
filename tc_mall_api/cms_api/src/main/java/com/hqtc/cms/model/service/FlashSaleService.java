package com.hqtc.cms.model.service;

import com.hqtc.cms.model.bean.flashsale.OrderParams;
import com.hqtc.common.response.ResultData;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/8/16 16:01
 */
public interface FlashSaleService {
    /**
     * 秒杀开始接口
     * @param venderId
     * @param activityId
     * @return
     */
    ResultData flashSaleStart(String venderId,String activityId);

    /**
     * 秒杀结束接口
     * @param venderId
     * @param activityId
     * @return
     */
    ResultData flashSaleEnd(String venderId,String activityId);

    /**
     * 秒杀接口
     * @param vo
     * @return
     */
    ResultData flashSale(OrderParams vo);

    /**
     * 轮询查询订单状态接口
     * @param venderId
     * @param activityId
     * @param requestId
     * @return
     */
    ResultData getOrderState(String venderId, String activityId, String requestId);
}
