package com.cibnvideo.tcmalladmin.omsapi;

import com.cibnvideo.config.Router;
import com.cibnvideo.fallback.omsapi.ProductRemoveFallbackFactory;
import com.cibnvideo.tcmalladmin.model.bean.ProductRemove;
import com.hqtc.common.config.FeignClientService;
import com.hqtc.common.response.DataList;
import com.hqtc.common.response.ResultData;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(value = FeignClientService.OMSAPI, fallbackFactory = ProductRemoveFallbackFactory.class)
public interface ProductRemoveApi {
    @RequestMapping(value = Router.V1_OMS_PRODUCT_REMOVE_LIST, method = RequestMethod.GET)
    ResultData<DataList<List<ProductRemove>>> list(@RequestParam("params") Map<String, Object> params);

    @RequestMapping(value = Router.V1_OMS_PRODUCT_REMOVE_SKUS_BY_VENDERID, method = RequestMethod.GET)
    ResultData<List<Long>> listSkusByVenderId(@PathVariable("venderId") Integer venderId);

    @RequestMapping(value = Router.V1_OMS_PRODUCT_REMOVE_GET, method = RequestMethod.GET)
    ResultData<ProductRemove> get(@RequestParam("id") Integer id);

    @RequestMapping(value = Router.V1_OMS_PRODUCT_REMOVE_COUNT, method = RequestMethod.GET)
    ResultData<Integer> count(@RequestParam("params") Map<String, Object> params);

    @RequestMapping(value = Router.V1_OMS_PRODUCT_REMOVE_ADD, method = RequestMethod.POST)
    ResultData<Integer> add(@RequestBody ProductRemove productRemove);

    @RequestMapping(value = Router.V1_OMS_PRODUCT_REMOVE_REMOVE, method = RequestMethod.GET)
    ResultData<Integer> remove(@RequestParam("id") Integer id);

    @RequestMapping(value = Router.V1_OMS_PRODUCT_REMOVE_BATCHREMOVE, method = RequestMethod.POST)
    ResultData<Integer> batchRemove(@RequestBody Map<String, Object> params);
}
