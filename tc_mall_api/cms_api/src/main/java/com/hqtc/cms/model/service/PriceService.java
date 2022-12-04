package com.hqtc.cms.model.service;

import com.hqtc.cms.fallback.PriceFallbackFactory;
import com.hqtc.cms.model.bean.search.SellPriceResult;
import com.hqtc.common.config.FeignClientService;
import com.hqtc.common.response.ResultData;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/7/7 15:18
 */
@FeignClient(name = FeignClientService.OMSAPI ,fallbackFactory = PriceFallbackFactory.class)
public interface PriceService {
    @PostMapping("/v1/oms/product/batchprice/{venderId}")
    public ResultData<List<SellPriceResult>> getBatchPrices(@PathVariable("venderId") Long venderId, @RequestBody List<Long> skus);
}
