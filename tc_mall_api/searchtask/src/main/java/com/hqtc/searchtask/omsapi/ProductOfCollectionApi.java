package com.hqtc.searchtask.omsapi;

import com.hqtc.common.config.FeignClientService;
import com.hqtc.common.response.DataList;
import com.hqtc.common.response.ResultData;
import com.hqtc.searchtask.config.Router;
import com.hqtc.searchtask.fallback.omsapi.ProductOfCollectionFallbackFactory;
import com.hqtc.searchtask.model.bean.ProductOfCollection;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(value = FeignClientService.OMSAPI, fallbackFactory = ProductOfCollectionFallbackFactory.class)
public interface ProductOfCollectionApi {

    @RequestMapping(value = Router.V1_OMS_PRODUCT_OF_COLLECTION_LIST, method = RequestMethod.GET)
    ResultData<DataList<List<ProductOfCollection>>> listProductOfCollection(@RequestParam Map<String, Object> params);

    @RequestMapping(value = Router.V1_OMS_PRODUCT_OF_COLLECTION_ID_BY_SKU, method = RequestMethod.GET)
    ResultData<Integer> getCollectionIdBySku(@RequestParam("venderId") Integer venderId, @RequestParam("sku") Long sku);
}
