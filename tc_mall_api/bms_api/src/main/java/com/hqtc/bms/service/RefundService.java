package com.hqtc.bms.service;

import com.hqtc.bms.model.database.TOrderBean;
import com.hqtc.bms.model.database.TOrderRefundBean;
import com.hqtc.bms.model.database.TOrderVenderRefundBean;
import com.hqtc.bms.model.params.MultipleRefundParams;
import com.hqtc.common.response.ResultData;

import java.util.List;
import java.util.Map;

/**
 * Created by wanghaoyang on 18-7-27.
 */
public interface RefundService {

    /**
     * 退款给用户
     * add by wanghaoyang at 20180727
     * @param orderSn 凭平台订单号
     * @param products 商品信息
     * @param refundReason 退款原因
     * */
    ResultData userRefund(String orderSn, Map<String, Integer> products, String refundReason, String eventId);

    /**
     * 插入退款记录
     * add by wanghaoyang at 20180727
     * @param orderRefundBean
     * */
    int addUserRefund(TOrderRefundBean orderRefundBean);

    /**
     * 批量插入退款记录
     * add by wanghaoyang at 20180727
     * */
    int addUserRefund(List<TOrderRefundBean> orderRefundBeans);

    /**
     * 用户退款成功回调通知
     * add by wanghaoyang at 20180727
     * @param refundNo 平台退款单号
     * @param refundTradeNo 第三方退款单号
     * */
    String userRefundNotify(String refundNo, String refundTradeNo);

    /**
     * 获取某个订单中某类商品成功退款的详情
     * add by wanghaoyang at 20180727
     * @param orderSn 平台订单号
     * @param product 商品ID
     * @return 退款单详情
     * */
    List<TOrderRefundBean> getSuccessRefundByOrderSnAndProduct(String orderSn, long product);

    /**
     * 查询某个订单中某种商品成功退款  给用户   的商品数量
     * add by wanghaoyang at 20180801
     * @param orderSn 平台订单号
     * @param product 商品id
     * @return 商品数量
     * */
    int getRefundedProductCount(String orderSn, long product);

    /**
     * 退款给商家
     * add by wanghaoyang at 20180806
     * @param orderSn 凭平台订单号
     * @param products 商品信息
     * @param refundReason 退款原因
     * */
    ResultData venderRefund(String orderSn, Map<String, Integer> products, String refundReason, String eventId);

    /**
     * 退款(先退给商家,再退给用户)
     * add by wanghaoyang at 20180813
     * @param jdOrderId 京东订单号
     * */
    ResultData refund(String jdOrderId);

    /**
     * 退款成功后,删除对应的京东消息
     * add by wanghaoyang at 20180814
     * */
    ResultData successRefundHandler(List<TOrderRefundBean> tOrderRefundBean);

    /**
     * 退款(退款给大客户, 退款给用户)
     * 此方法为幂等性方法
     * add by wanghaoyang at 20180827
     * */
    ResultData multipleRefund(MultipleRefundParams multipleRefundParams);

    /**
     * 查询某个事件下大客户的退款记录
     * add by wanghaoyang at 20180827
     * @param orderSn 平台订单号
     * @param eventId 事件唯一id
     * @return 本次退款详情
     * */
    TOrderVenderRefundBean getVenderRefund(String orderSn, String eventId);

    /**
     * 插入向大客户的退款记录
     * add by wanghaoyang at 20180827
     * @param tOrderVenderRefundBean 退款详情
     * @return 0失败|1成功
     * */
    int addVenderRefund(TOrderVenderRefundBean tOrderVenderRefundBean);

    /**
     * 查询某个事件下向用户退款的记录
     * add by wanghaoyang at 20180828
     * @param orderSn 平台订单号
     * @param eventId 事件唯一id
     * @return 本次退款详情
     * */
    List<TOrderRefundBean> getEventRefundBean(String orderSn, String eventId);

    /**
     * 获取某个大客户下某些商品的退款的数量
     * add by wanghaoyang at 20180830
     * @param productId 商品id,如 “123123,2342,2342345”
     * @param venderId 大客户id
     * @return 商品的数量
     * */
    List<Map<String, Object>> getRefundProductVolumn(String productId, int venderId);

    /**
     * 支付中各方失败等原因发起的退款(非拒收，退货等用户行为)
     * add by wanghaoyang at 20180929
     * @param orderBean 订单信息
     * */
    ResultData payFailUserRefund(TOrderBean orderBean);

    /**
     * 购物卡退款
     * add by wanghaoyang at 20181009
     * @param orderSn 平台订单号
     * @param refundNo 退款单号
     * */
    ResultData cardRefund(String orderSn, String refundNo);

    /**
     * 退款(处理拒收消息的退款流程)
     * add by wanghaoyang at 20190131
     * @param jdOrderId 京东订单号
     * @param jdMessageId 京东拒签的消息id
     * */
    ResultData denyReceiveRefund(String jdOrderId, String jdMessageId);

    /**
     * 根据订单号和退款单号查询退款信息
     * add by wanghaoyang at 20190215
     * @param orderSn 订单号
     * @param refundNo 退款单号
     * */
    List<TOrderRefundBean> findRefundByOrderSnAndRefundNo(String orderSn, String refundNo);

    /**
     * 查询退款状态
     * add by wanghaoyang at 20190215
     * @param orderSn 平台订单号
     * @param eventId 事件id
     * */
    ResultData refundStatusSearch(String orderSn, String eventId);

    /**
     * 用户退款重试
     * add by wanghaoyang at 20190217
     * @param orderSn 平台订单号
     * @param eventId 事件id
     * */
    ResultData userRefundRetry(String orderSn, String eventId);

    /**
     * 大客户退款重试
     * add by wanghaoyang at 20190217
     * @param orderSn 平台订单号
     * @param eventId 事件id
     * */
    ResultData venderRefundRetry(String orderSn, String eventId);
}
