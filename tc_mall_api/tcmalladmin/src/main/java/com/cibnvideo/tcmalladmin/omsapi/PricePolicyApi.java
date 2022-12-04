package com.cibnvideo.tcmalladmin.omsapi;

import com.cibnvideo.config.Router;
import com.cibnvideo.tcmalladmin.model.bean.PricePolicy;
import com.hqtc.common.config.FeignClientService;
import com.hqtc.common.response.DataList;
import com.hqtc.common.response.ResultData;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(FeignClientService.OMSAPI)
public interface PricePolicyApi {
    @RequestMapping(value = Router.V1_OMS_PRICE_POLICY_LIST, method = RequestMethod.GET)
    ResultData<DataList<List<PricePolicy>>> list(@RequestParam("params") Map<String, Object> params);

    @RequestMapping(value = Router.V1_OMS_PRICE_POLICY_GET, method = RequestMethod.GET)
    ResultData<PricePolicy> get(@RequestParam("id") Integer id);

    @RequestMapping(value = Router.V1_OMS_PRICE_POLICY_COUNT, method = RequestMethod.GET)
    ResultData<Integer> count(@RequestParam("params") Map<String, Object> params);

    @RequestMapping(value = Router.V1_OMS_PRICE_POLICY_ADD, method = RequestMethod.POST)
    ResultData<Integer> add(@RequestBody PricePolicy pricePolicy);

    @RequestMapping(value = Router.V1_OMS_PRICE_POLICY_REMOVE, method = RequestMethod.GET)
    ResultData<Integer> remove(@RequestParam("id") Integer id);

    @RequestMapping(value = Router.V1_OMS_PRICE_POLICY_BATCHREMOVE, method = RequestMethod.POST)
    ResultData<Integer> batchRemove(@RequestBody List<Integer> ids);

    @RequestMapping(value = Router.V1_OMS_PRICE_POLICY_UPDATE, method = RequestMethod.POST)
    ResultData<Integer> update(@RequestBody PricePolicy pricePolicy);

    @RequestMapping(value = Router.V1_OMS_PRICE_POLICY_LIST_BY_COLLECTIONID, method = RequestMethod.GET)
    ResultData<List<PricePolicy>> getPricePolicesByCollectionId(@PathVariable("venderId") Integer venderId, @RequestParam("collectionId") Integer collectionId);
}
