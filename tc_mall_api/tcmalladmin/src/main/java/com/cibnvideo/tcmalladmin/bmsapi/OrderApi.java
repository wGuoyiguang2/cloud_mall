package com.cibnvideo.tcmalladmin.bmsapi;

import com.cibnvideo.common.utils.Result;
import com.cibnvideo.config.Router;
import com.cibnvideo.fallback.bmsapi.OrderFallbackFactory;
import com.cibnvideo.tcmalladmin.model.bean.OrderManagerVo;
import com.cibnvideo.tcmalladmin.model.bean.OrderRefundVo;
import com.hqtc.common.config.FeignClientService;
import com.hqtc.common.response.ResultData;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @Description: 订单管理
 * @Author: WangBin
 * @Date: 2018/7/16 15:19
 */
@FeignClient(value = FeignClientService.BMSAPI,fallbackFactory = OrderFallbackFactory.class)
public interface OrderApi {
    @GetMapping(Router.BMS_ORDER_MANAGER_LIST)
    Result<OrderManagerVo> orderList(@RequestParam Map<String, Object> params);
    @GetMapping(Router.BMS_ORDER_SUM_BY_VENDER)
    int countOrderByVenderId(@RequestParam("venderId") Long venderId);
    @PostMapping(Router.V1_BMS_PRODUCT_VOLUME)
    ResultData<List<Map<String, Long>>> getProductSalesVolume(@RequestParam("products")String products);
    @PostMapping(Router.V1_BMS_ORDER_REFUND_LIST)
    ResultData<Result<OrderRefundVo>> listOrderRefund(@RequestParam Map<String, Object> params);
    @PostMapping(value = Router.ROUTER_REFUND_RETRY_USER)
    ResultData refundUserRetry(@RequestParam("orderSn")String orderSn,@RequestParam("eventId")String eventId);
    @PostMapping(value = Router.ROUTER_REFUND_RETRY_VENDER)
    ResultData refundVenderRetry(@RequestParam("orderSn")String orderSn,@RequestParam("eventId")String eventId);
}
