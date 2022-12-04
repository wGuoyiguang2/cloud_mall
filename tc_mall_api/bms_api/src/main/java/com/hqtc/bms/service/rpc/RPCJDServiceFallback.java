package com.hqtc.bms.service.rpc;

import com.hqtc.bms.model.rpc.*;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Created by wanghaoyang on 18-7-9.
 */
@Component
public class RPCJDServiceFallback implements RPCJDService {

    @Override
    public SubmitOrderResponseParams submitOrder(SubmitOrderRequestParams requestParams){
        return new SubmitOrderResponseParams();
    }

    @Override
    public OrderPayResponseParams orderPay(@RequestBody OrderPayRequestParams requestParams){
        return null;
    }

    @Override
    public String getOrderInfo(@RequestBody OrderRequestParams requestParams){
        return null;
    }

    @Override
    public RPCConfirmOccupyStockResponseParams confirmOccupyStock(@RequestBody ConfirmOccupyStockRequestParams jdOrderId){
        return null;
    }

    @Override
    public StockOrderResponseParams getOrderStocks(StockOrderRequestParams requestParams){
        return null;
    }

    @Override
    public CancelOrderResponseParams cancelOrder(@RequestBody CancelOrderRequestParams requestParams){
        return null;
    }

    @Override
    public FreightResponseParams getFreight(@RequestBody FreightRequestParams requestParams){
        return null;
    }

    @Override
    public GetMessageResponseParams getMessage(@RequestBody GetMessageRequestParams requestParams){
        return null;
    }

    @Override
    public DeleteMessageResponseParams deleteMessage(@RequestBody DeleteMessageRequestParams requestParams){
        return null;
    }
}
