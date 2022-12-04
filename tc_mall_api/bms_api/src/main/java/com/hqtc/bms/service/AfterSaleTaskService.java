package com.hqtc.bms.service;

import com.hqtc.bms.model.params.*;
import com.hqtc.bms.model.response.OrderDetailResponse;
import com.hqtc.common.response.ResultData;

import java.util.ArrayList;
import java.util.List;

/**
 * description:售后物流定时任务
 * Created by laiqingchuang on 18-7-25 .
 */
public interface AfterSaleTaskService {

    /**
     * 获取待收货子订单
     */
    List<OrderProductBean> getOrderProducBeanList();

    /**
     * 更新订单状态
     */
    int updateOrderStat(List<OrderProductBean> list);

    /**
     * 获取订单信息
     */
    OrderInfoResponseParams getOrderInfo(Long jdOrderId);

    /**
     * 添加售后流水信息
     */
    ResultData addOrderAftersale(OrderAftersaleBean bean,int maxSeq,int skuNum);

    /**
     * 更新取消服务单信息
     */
    int updateCancelService(ServiceRequestParams requestParams);

    /**
     * 获取需要更新的记录
     */
    List<Integer> getNeedUpdateList();

    /**
     * 更新售后流水
     */
    int updateAftersaleState(List<OrderAftersaleBean> list);

    /**
     * 根据订单号获取售后列表
     */
    List<OrderAftersaleBean> getAftersaleListByOrderSn(String orderSn, Integer type);

    /**
     * 根据用户id获取售后数量
     */
    int getAftersaleCountByUserId(Integer userId);

    /**
     * 根据用户id获取售后列表
     */
    List<AftersaleBean> getAftersaleListByUserId(Integer userId, Integer offset, Integer limit);

    /**
     * 根据JD订单号获取售后列表
     * @return
     */
    List<AftersaleForeignBean> getAftersaleListByJdOrderId(Long jdOrderId, Integer type);

    /**
     * 更新服务单号
     */
    int updateServiceNos(List<OrderAftersaleBean> orderAftersaleBeanList);

    /**
     * 获取需要更新的服务单
     * @return
     */
    List<OrderAftersaleBean> getUpdateAftersaleList();

    /**
     * 校验是否是该用户下的订单
     * @return
     */
    Boolean checkJdOrderId(Integer userId, Long jdOrderId);
}
