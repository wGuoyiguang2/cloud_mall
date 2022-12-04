package com.cibnvideo.tcmalladmin.omsapi;

import com.cibnvideo.config.Router;
import com.cibnvideo.tcmalladmin.model.bean.PriceRate;
import com.hqtc.common.config.FeignClientService;
import com.hqtc.common.response.DataList;
import com.hqtc.common.response.ResultData;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(FeignClientService.OMSAPI)
public interface RatePricePolicyApi {
    @RequestMapping(value = Router.V1_OMS_PRICE_POLICY_RATE_LIST, method = RequestMethod.GET)
    ResultData<DataList<List<PriceRate>>> list(@RequestParam("params") Map<String, Object> params);

    @RequestMapping(value = Router.V1_OMS_PRICE_POLICY_RATE_GET, method = RequestMethod.GET)
    ResultData<PriceRate> get(@RequestParam("id") Integer id);

    @RequestMapping(value = Router.V1_OMS_PRICE_POLICY_RATE_COUNT, method = RequestMethod.GET)
    ResultData<Integer> count(@RequestParam("params") Map<String, Object> params);

    @RequestMapping(value = Router.V1_OMS_PRICE_POLICY_RATE_ADD, method = RequestMethod.POST)
    ResultData<Integer> add(@RequestBody PriceRate priceRate);

    @RequestMapping(value = Router.V1_OMS_PRICE_POLICY_RATE_REMOVE, method = RequestMethod.GET)
    ResultData<Integer> remove(@RequestParam("id") Integer id);

    @RequestMapping(value = Router.V1_OMS_PRICE_POLICY_RATE_BATCHREMOVE, method = RequestMethod.POST)
    ResultData<Integer> batchRemove(@RequestBody Map<String, Object> params);
}
