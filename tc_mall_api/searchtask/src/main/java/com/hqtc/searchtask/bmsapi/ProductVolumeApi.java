package com.hqtc.searchtask.bmsapi;

import com.hqtc.common.config.FeignClientService;
import com.hqtc.common.response.ResultData;
import com.hqtc.searchtask.config.Router;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(value = FeignClientService.BMSAPI)
public interface ProductVolumeApi {
    @RequestMapping(value = Router.ROUTER_PRODUCT_SALED_COUNT, method = RequestMethod.POST)
    ResultData<Map<String, Integer>> getProductSalesVolume(@RequestParam("products") String products, @RequestParam("venderId") int venderId, @RequestParam("resultType") int resultType);
}
