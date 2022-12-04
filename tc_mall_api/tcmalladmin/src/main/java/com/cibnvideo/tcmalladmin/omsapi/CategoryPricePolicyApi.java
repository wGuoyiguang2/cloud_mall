package com.cibnvideo.tcmalladmin.omsapi;

import com.cibnvideo.config.Router;
import com.cibnvideo.tcmalladmin.model.bean.PriceCategory;
import com.cibnvideo.tcmalladmin.model.bean.PricePolicy;
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
public interface CategoryPricePolicyApi {
    @RequestMapping(value = Router.V1_OMS_PRICE_POLICY_CATEGORY_LIST, method = RequestMethod.GET)
    ResultData<DataList<List<PriceCategory>>> list(@RequestParam("params") Map<String, Object> params);

    @RequestMapping(value = Router.V1_OMS_PRICE_POLICY_CATEGORY_GET, method = RequestMethod.GET)
    ResultData<PricePolicy> get(@RequestParam("id") Integer id);

    @RequestMapping(value = Router.V1_OMS_PRICE_POLICY_CATEGORY_COUNT, method = RequestMethod.GET)
    ResultData<Integer> count(@RequestParam("params") Map<String, Object> params);

    @RequestMapping(value = Router.V1_OMS_PRICE_POLICY_CATEGORY_ADD, method = RequestMethod.POST)
    ResultData<Integer> add(@RequestBody PriceCategory priceCategory);

    @RequestMapping(value = Router.V1_OMS_PRICE_POLICY_CATEGORY_REMOVE, method = RequestMethod.GET)
    ResultData<Integer> remove(@RequestParam("id") Integer id);

    @RequestMapping(value = Router.V1_OMS_PRICE_POLICY_CATEGORY_BATCHREMOVE, method = RequestMethod.POST)
    ResultData<Integer> batchRemove(@RequestBody Map<String, Object> params);
}
