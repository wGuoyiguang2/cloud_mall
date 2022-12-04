package com.cibnvideo.jd.order.controller;

import com.cibnvideo.jd.common.constants.PathConstants;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/6/25 10:19
 */
@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 查询配送信息
     *
     * @param requestParams
     * @return
     */
    @PostMapping(path = PathConstants.ORDER_TRACK_GET)
    public OrderTrackResponseParams getOrderIdByThirdOrderId(@RequestBody OrderTrackRequestParams requestParams) {
        return orderService.getOrderTrack(requestParams);
    }

    /**
     * 通过第三方订单号查询京东订单号
     *
     * @param requestParams
     * @return
     */
    @PostMapping(path = PathConstants.ORDER_ID_GET)
    public OrderIdResponseParams getOrderIdByThirdOrderId(@RequestBody OrderIdRequestParams requestParams) {
        return orderService.getOrderId(requestParams);
    }

    /**
     * 查询订单信息接口（新）
     *
     * @param requestParams
     * @return
     */
    @PostMapping(path = PathConstants.ORDER_INFO_GET)
    public String getOrderInfo(@RequestBody OrderRequestParams requestParams) {
        return orderService.getOrderInfo(requestParams);
    }

    /**
     * 取消订单接口
     *
     * @param requestParams
     * @return
     */
    @PostMapping(path = PathConstants.ORDER_CANCEL)
    public CancelOrderResponseParams cancelOrder(@RequestBody CancelOrderRequestParams requestParams) {
        return orderService.cancelOrder(requestParams);
    }

    /**
     * 发起支付接口
     *
     * @param requestParams
     * @return
     */
    @PostMapping(path = PathConstants.ORDER_PAY)
    public OrderPayResponseParams orderPay(@RequestBody OrderPayRequestParams requestParams) {
        return orderService.orderPay(requestParams);
    }

    /**
     * 确认预占库存订单
     *
     * @param requestParams
     * @return
     */
    @PostMapping(path = PathConstants.CONFIRM_OCCUPY_STOCK)
    public ConfirmOccupyStockResponseParams confirmOccupyStock(@RequestBody ConfirmOccupyStockRequestParams requestParams) {
        return orderService.confirmOccupyStock(requestParams);
    }

    /**
     * 统一下单接口
     *
     * @param requestParams
     * @return
     */
    @PostMapping(path = PathConstants.ORDER_SUBMIT)
    public SubmitOrderResponseParams submitOrder(@RequestBody SubmitOrderRequestParams requestParams) {
        return orderService.submitOrder(requestParams);
    }

    /**
     * 统一余额查询
     *
     * @param requestParams
     * @return
     */
    @PostMapping(path = PathConstants.BALANCE_GET)
    public BalanceResponseParams getBlance(@RequestBody BalanceRequestParams requestParams) {
        return orderService.getBalance(requestParams);
    }

    /**
     * 获取金采明细
     *
     * @return
     */
    @PostMapping(path = PathConstants.BALANCE_GET_JINCAI)
    public String getJincaiCredit() {
        return orderService.getJincaiCredit();
    }

    @PostMapping(path = PathConstants.BALANCE_GET_INFO)
    public String getBalanceInfo(@RequestBody BalanceInfoRequestParams requestParams) {
        return orderService.getBalanceInfo(requestParams);
    }
}
