package com.hqtc.ims.favorite.service;

import com.hqtc.common.config.FeignClientService;
import com.hqtc.common.response.ResultData;
import com.hqtc.ims.favorite.model.bean.SellPriceResult;
import com.hqtc.ims.favorite.service.impl.PriceFallbackFactory;
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
