package com.cibnvideo.tcmalladmin.bmsapi;

import com.cibnvideo.common.utils.Result;
import com.cibnvideo.config.Router;
import com.cibnvideo.fallback.bmsapi.SalesFallbackFactory;
import com.cibnvideo.tcmalladmin.model.bean.SalesAmountVo;
import com.cibnvideo.tcmalladmin.model.bean.SalesManagerVo;
import com.hqtc.common.config.FeignClientService;
import com.hqtc.common.response.ResultData;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @Description: 销量
 * @Author: WangBin
 * @Date: 2018/8/9 10:43
 */
@FeignClient(value = FeignClientService.BMSAPI,fallbackFactory = SalesFallbackFactory.class)
public interface SalesApi {
    @GetMapping(Router.V1_BMS_PRODUCT_SALES_AMOUNT)
    ResultData<SalesAmountVo> getSalesAmount(@RequestParam("venderId") Long venderId);
    @PostMapping(Router.V1_BMS_PRODUCT_SALES_LIST)
    ResultData<Result<SalesManagerVo>> salesManagerList(@RequestParam Map<String, Object> params);
    @GetMapping(Router.V1_BMS_PRODUCT_SALES_GET)
    ResultData<SalesManagerVo> getSalesManagerDetail(@RequestParam("sku") Long sku);
}
