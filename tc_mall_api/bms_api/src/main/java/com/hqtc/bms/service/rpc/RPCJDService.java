package com.hqtc.bms.service.rpc;

import com.hqtc.bms.config.RPCRouter;
import com.hqtc.bms.model.rpc.*;
import com.hqtc.common.config.FeignClientService;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.xml.xpath.XPathConstants;

/**
 * Created by wanghaoyang on 18-7-9.
 */
@FeignClient(name = FeignClientService.JDSERVICE, fallback = RPCJDServiceFallback.class)
public interface RPCJDService {

    @PostMapping(value= RPCRouter.JD_ORDER_SUBMIT)
    SubmitOrderResponseParams submitOrder(@RequestBody SubmitOrderRequestParams requestParams);

    @PostMapping(path= RPCRouter.ORDER_PAY)
    OrderPayResponseParams orderPay(@RequestBody OrderPayRequestParams requestParams);

    @PostMapping(path= RPCRouter.ORDER_INFO_GET)
    String getOrderInfo(@RequestBody OrderRequestParams requestParams);

    @PostMapping(path= RPCRouter.CONFIRM_OCCUPY_STOCK)
    RPCConfirmOccupyStockResponseParams confirmOccupyStock(@RequestBody ConfirmOccupyStockRequestParams confirmOccupyStockRequestParams);

    @PostMapping(path = RPCRouter.GOODS_ORDER_STOCK_GET)
    StockOrderResponseParams getOrderStocks(@RequestBody StockOrderRequestParams requestParams);

    @PostMapping(path= RPCRouter.ORDER_CANCEL)
    CancelOrderResponseParams cancelOrder(@RequestBody CancelOrderRequestParams requestParams);

    @PostMapping(path = RPCRouter.GOODS_FREIGHT_GET)
    FreightResponseParams getFreight(@RequestBody FreightRequestParams requestParams);

    @PostMapping(path = RPCRouter.GOODS_MESSAGE_GET)
    GetMessageResponseParams getMessage(@RequestBody GetMessageRequestParams requestParams);

    @PostMapping(path = RPCRouter.GOODS_MESSAGE_DELETE)
    DeleteMessageResponseParams deleteMessage(@RequestBody DeleteMessageRequestParams requestParams);
}
