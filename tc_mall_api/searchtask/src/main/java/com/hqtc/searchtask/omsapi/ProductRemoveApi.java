package com.hqtc.searchtask.omsapi;

import com.hqtc.common.config.FeignClientService;
import com.hqtc.common.response.ResultData;
import com.hqtc.searchtask.config.Router;
import com.hqtc.searchtask.fallback.omsapi.ProductRemoveFallbackFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(value = FeignClientService.OMSAPI, fallbackFactory = ProductRemoveFallbackFactory.class)
public interface ProductRemoveApi {

    @RequestMapping(value = Router.V1_OMS_PRODUCT_REMOVE_COUNT, method = RequestMethod.GET)
    ResultData<Integer> count(@RequestParam Map<String, Object> params);
}
