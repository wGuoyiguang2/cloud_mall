package com.hqtc.searchtask.jdsyncapi;

import com.hqtc.common.config.FeignClientService;
import com.hqtc.common.response.DataList;
import com.hqtc.common.response.ResultData;
import com.hqtc.searchtask.config.Router;
import com.hqtc.searchtask.fallback.jdsyncapi.ProductFallbackFactory;
import com.hqtc.searchtask.model.bean.ProductDo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(value = FeignClientService.JDSYNC, fallbackFactory = ProductFallbackFactory.class)
public interface ProductApi {
	
	@RequestMapping(value = Router.V1_JDSYNC_PRODUCT_ES_GET, method = RequestMethod.GET)
	ResultData<ProductDo> getProductForEs(@RequestParam("sku") Long sku);

	@RequestMapping(value = Router.V1_JDSYNC_PRODUCT_ES_LIST, method = RequestMethod.POST)
	ResultData<DataList<List<ProductDo>>> listProductForEs(@RequestBody Map<String, Object> params);

}
