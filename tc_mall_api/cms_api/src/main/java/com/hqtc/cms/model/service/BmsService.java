package com.hqtc.cms.model.service;

import com.hqtc.cms.config.Router;
import com.hqtc.cms.fallback.BmsFallbackFactory;
import com.hqtc.cms.model.bean.flashsale.GetOrderDetailResponse;
import com.hqtc.cms.model.bean.flashsale.OrderParams;
import com.hqtc.cms.model.bean.flashsale.OrderResponse;
import com.hqtc.common.config.FeignClientService;
import com.hqtc.common.response.ResultData;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/8/16 17:06
 */
@FeignClient(name= FeignClientService.BMSAPI, fallbackFactory = BmsFallbackFactory.class )
public interface BmsService {

    @PostMapping(Router.V1_BMS_FLASHSALE_ORDER_CREATE)
    public ResultData<OrderResponse> createOrder(@RequestBody OrderParams orderParams);
    @GetMapping(Router.ROUTER_ORDER_DETAIL)
    ResultData<GetOrderDetailResponse> orderDetail(@RequestParam("orderSn") String orderNo, @RequestParam("mac1") String mac,@RequestParam("cookie") String cookie);
}
