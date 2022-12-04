package com.cibnvideo.oms.jdsyncapi;

import com.cibnvideo.oms.fallback.jdsyncapi.ProductFallbackFactory;
import com.cibnvideo.oms.bean.ProductInfo;
import com.cibnvideo.oms.bean.SellPriceResult;
import com.cibnvideo.oms.config.Router;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.hqtc.common.config.FeignClientService;
import com.hqtc.common.response.ResultData;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = FeignClientService.JDSYNC, fallbackFactory = ProductFallbackFactory.class)
public interface ProductApi {

	@RequestMapping(value = Router.V1_JDSYNC_PRODUCT_BATCH_PRICE, method = RequestMethod.POST)
	ResultData<List<SellPriceResult>> getBatchPrice(@RequestBody List<Long> skus);

	@RequestMapping(value = Router.V1_JDSYNC_PRODUCT_PRICE, method = RequestMethod.POST)
	ResultData<SellPriceResult> getPrice(@RequestBody Long sku);

	@RequestMapping(value = Router.V1_JDSYNC_PRODUCT_BRANDNAME_BY_SKUS, method = RequestMethod.POST)
	ResultData<List<String>> getBrandNamesBySkus(@RequestBody List<Long> skus);

	@RequestMapping(value = Router.V1_JDSYNC_PRODUCT_DETAILINFO, method = RequestMethod.GET)
	ResultData<ProductInfo> getProductInfo(@RequestParam("sku") Long sku);

	@RequestMapping(value = Router.V1_JDSYNC_PRODUCT_DETAILINFO_BY_SKUS, method = RequestMethod.POST)
	ResultData<List<ProductInfo>> getProductInfoBySkus(@RequestBody List<Long> skus);
	

}
