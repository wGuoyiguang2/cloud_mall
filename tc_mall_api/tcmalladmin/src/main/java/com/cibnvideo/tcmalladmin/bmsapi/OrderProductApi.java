package com.cibnvideo.tcmalladmin.bmsapi;

import com.cibnvideo.common.utils.Result;
import com.cibnvideo.config.Router;
import com.cibnvideo.fallback.bmsapi.OrderProductFallbackFactory;
import com.hqtc.common.config.FeignClientService;
import com.hqtc.common.response.ResultData;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @Description: 订单商品
 * @Author: WangBin
 * @Date: 2018/7/16 15:19
 */
@FeignClient(value = FeignClientService.BMSAPI,fallbackFactory = OrderProductFallbackFactory.class)
public interface OrderProductApi {
    @GetMapping(Router.BMS_PRODUCT_GET_ORDERNO)
    Result<String> getOrderNo(@RequestParam Map<String, Object> params);
}
