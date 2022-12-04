package com.hqtc.bms.service;

import com.hqtc.bms.config.Router;
import com.hqtc.bms.model.params.RefundUserParams;
import com.hqtc.bms.model.response.OrderDetailResponse;
import com.hqtc.bms.service.rpc.BmsOrderSerciceFallback;
import com.hqtc.common.config.FeignClientService;
import com.hqtc.common.response.ResultData;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * description:根据订单号商品编号获取订单信息
 * Created by laiqingchuang on 18-7-12 .
 */
@FeignClient(name=FeignClientService.BMSAPI,fallbackFactory = BmsOrderSerciceFallback.class)
public interface BmsOrderSercice {

    @RequestMapping(method = RequestMethod.GET, value = Router.ROUTER_PRODUCT_ORDER_DETAIL)
    ResultData<OrderDetailResponse> getOrderInfo(@RequestParam("tradeNo") String tradeNo,
                                                 @RequestParam("sku") Long sku);

    @RequestMapping(method = RequestMethod.POST,value = Router.ROUTER_REFUND)
    ResultData refund(@RequestBody RefundUserParams refundParam);
}
