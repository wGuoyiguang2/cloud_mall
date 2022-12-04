package com.cibnvideo.tcmalladmin.omsapi;

import com.cibnvideo.config.Router;
import com.cibnvideo.tcmalladmin.model.bean.ProductOfCollection;
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
public interface ProductOfCollectionApi {
    @RequestMapping(value = Router.V1_OMS_PRODUCT_OF_COLLECTION_LIST, method = RequestMethod.GET)
    ResultData<DataList<List<ProductOfCollection>>> list(@RequestParam("params") Map<String, Object> params);

    @RequestMapping(value = Router.V1_OMS_PRODUCT_OF_COLLECTION_GET, method = RequestMethod.GET)
    ResultData<ProductOfCollection> get(@RequestParam("id") Integer id);

    @RequestMapping(value = Router.V1_OMS_PRODUCT_OF_COLLECTION_COUNT, method = RequestMethod.GET)
    ResultData<Integer> count(@RequestParam("params") Map<String, Object> params);

    @RequestMapping(value = Router.V1_OMS_PRODUCT_OF_COLLECTION_ADD, method = RequestMethod.POST)
    ResultData<Integer> add(@RequestBody ProductOfCollection productOfCollection);

    @RequestMapping(value = Router.V1_OMS_PRODUCT_OF_COLLECTION_BATCH_ADD, method = RequestMethod.POST)
    ResultData<Integer> batchAdd(@RequestBody List<ProductOfCollection> productOfCollections);

    @RequestMapping(value = Router.V1_OMS_PRODUCT_OF_COLLECTION_REMOVE, method = RequestMethod.GET)
    ResultData<Integer> remove(@RequestParam("id") Integer id);

    @RequestMapping(value = Router.V1_OMS_PRODUCT_OF_COLLECTION_BATCH_REMOVE, method = RequestMethod.POST)
    ResultData<Integer> batchRemove(@RequestBody Map<String, Object> map);

}
