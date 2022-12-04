package com.hqtc.searchtask.omsapi;

import com.hqtc.common.config.FeignClientService;
import com.hqtc.common.response.ResultData;
import com.hqtc.searchtask.config.Router;
import com.hqtc.searchtask.fallback.omsapi.VenderSettlementFallbackFactory;
import com.hqtc.searchtask.model.bean.SellPriceResult;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(value = FeignClientService.OMSAPI, fallbackFactory = VenderSettlementFallbackFactory.class)
public interface VenderSettlementApi {


    @RequestMapping(value = Router.V1_OMS_PRODUCT_BATCH_PRICE, method = RequestMethod.POST)
    ResultData<List<SellPriceResult>> getBatchPrice(@PathVariable("venderId") Integer venderId, List<Long> skus);

    @RequestMapping(value = Router.V1_OMS_PRODUCT_PRICE, method = RequestMethod.POST)
    ResultData<SellPriceResult> getPrice(@PathVariable("venderId") Integer venderId, Long sku);

    @RequestMapping(value = Router.V1_OMS_LIST_VENDERID, method = RequestMethod.GET)
    ResultData<List<Integer>> listVenderId();
}
