package com.cibnvideo.jd.order.service.impl;

import com.cibnvideo.jd.common.constants.JdMethodConstants;
import com.cibnvideo.jd.common.service.impl.BaseServiceImpl;
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
import com.cibnvideo.jd.order.service.OrderService;
import com.google.gson.Gson;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/6/25 11:06
 */
@Service
public class OrderServiceImpl extends BaseServiceImpl implements OrderService {
    Gson gson = new Gson();

    /**
     * 获取订单信息（父订单和子订单查询结果有差异）
     *
     * @param requestParams
     * @return
     */
    @Override
    public String getOrderInfo(OrderRequestParams requestParams) {
        return this.request(JdMethodConstants.getSelectOrder(), requestParams);
    }

    @Override
    public SubmitOrderResponseParams submitOrder(SubmitOrderRequestParams requestParams) {
        String json = this.request(JdMethodConstants.getSubmitOrder(), requestParams);
        return gson.fromJson(json, SubmitOrderResponseParams.class);
    }

    @Override
    public ConfirmOccupyStockResponseParams confirmOccupyStock(ConfirmOccupyStockRequestParams requestParams) {
        String json = this.request(JdMethodConstants.getConfirmOccupyStock(), requestParams);
        return gson.fromJson(json, ConfirmOccupyStockResponseParams.class);
    }

    @Override
    public OrderPayResponseParams orderPay(OrderPayRequestParams requestParams) {
        String json = this.request(JdMethodConstants.getOrderPay(), requestParams);
        return gson.fromJson(json, OrderPayResponseParams.class);
    }

    @Override
    public CancelOrderResponseParams cancelOrder(CancelOrderRequestParams requestParams) {
        String json = this.request(JdMethodConstants.getCancelOrder(), requestParams);
        return gson.fromJson(json, CancelOrderResponseParams.class);
    }

    @Override
    public OrderIdResponseParams getOrderId(OrderIdRequestParams requestParams) {
        String json = this.request(JdMethodConstants.getGetJdOrderId(), requestParams);
        return gson.fromJson(json, OrderIdResponseParams.class);
    }

    @Override
    public OrderTrackResponseParams getOrderTrack(OrderTrackRequestParams requestParams) {
        String json = this.request(JdMethodConstants.getOrderTrack(), requestParams);
        return gson.fromJson(json, OrderTrackResponseParams.class);
    }

    @Override
    public BalanceResponseParams getBalance(BalanceRequestParams requestParams) {
        String json = this.request(JdMethodConstants.getBALANCE(), requestParams);
        return gson.fromJson(json, BalanceResponseParams.class);
    }

    @Override
    public String getJincaiCredit() {
        return this.request(JdMethodConstants.getJincaiInfo(), null);
    }

    @Override
    public String getBalanceInfo(BalanceInfoRequestParams requestParams) {
        return this.request(JdMethodConstants.getBalanceInfo(), requestParams);
    }
}
