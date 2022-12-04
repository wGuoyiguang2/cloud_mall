package com.cibnvideo.jd.order.service;

import com.cibnvideo.jd.common.service.BaseService;
import com.cibnvideo.jd.order.params.balance.BalanceInfoRequestParams;
import com.cibnvideo.jd.order.params.balance.BalanceRequestParams;
import com.cibnvideo.jd.order.params.balance.BalanceResponseParams;
import com.cibnvideo.jd.order.params.cancel.CancelOrderRequestParams;
import com.cibnvideo.jd.order.params.cancel.CancelOrderResponseParams;
import com.cibnvideo.jd.order.params.confirm.ConfirmOccupyStockRequestParams;
import com.cibnvideo.jd.order.params.confirm.ConfirmOccupyStockResponseParams;
import com.cibnvideo.jd.order.params.orderid.OrderIdRequestParams;
import com.cibnvideo.jd.order.params.orderid.OrderIdResponseParams;
import com.cibnvideo.jd.order.params.orderinfo.OrderRequestParams;
import com.cibnvideo.jd.order.params.pay.OrderPayRequestParams;
import com.cibnvideo.jd.order.params.pay.OrderPayResponseParams;
import com.cibnvideo.jd.order.params.submit.SubmitOrderRequestParams;
import com.cibnvideo.jd.order.params.submit.SubmitOrderResponseParams;
import com.cibnvideo.jd.order.params.track.OrderTrackRequestParams;
import com.cibnvideo.jd.order.params.track.OrderTrackResponseParams;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/6/25 11:06
 */
public interface OrderService extends BaseService {
    String getOrderInfo(OrderRequestParams requestParams);

    SubmitOrderResponseParams submitOrder(SubmitOrderRequestParams orderRequestParams);

    ConfirmOccupyStockResponseParams confirmOccupyStock(ConfirmOccupyStockRequestParams requestParams);

    OrderPayResponseParams orderPay(OrderPayRequestParams requestParams);

    CancelOrderResponseParams cancelOrder(CancelOrderRequestParams requestParams);

    OrderIdResponseParams getOrderId(OrderIdRequestParams requestParams);

    OrderTrackResponseParams getOrderTrack(OrderTrackRequestParams requestParams);

    BalanceResponseParams getBalance(BalanceRequestParams requestParams);

    String getJincaiCredit();

    String getBalanceInfo(BalanceInfoRequestParams requestParams);
}
