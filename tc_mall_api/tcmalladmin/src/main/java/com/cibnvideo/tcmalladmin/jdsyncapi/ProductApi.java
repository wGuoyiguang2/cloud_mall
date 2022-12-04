package com.cibnvideo.tcmalladmin.jdsyncapi;

import com.cibnvideo.config.Router;
import com.cibnvideo.fallback.jdsyncapi.ProductFallbackFactory;
import com.cibnvideo.tcmalladmin.model.bean.ProductInfo;
import com.cibnvideo.tcmalladmin.model.bean.SalesManagerProductDetailVo;
import com.cibnvideo.tcmalladmin.model.bean.SalesManagerVo;
import com.cibnvideo.tcmalladmin.model.bean.SellPriceResult;
import com.hqtc.common.config.FeignClientService;
import com.hqtc.common.response.DataList;
import com.hqtc.common.response.ResultData;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(value = FeignClientService.JDSYNC, fallbackFactory = ProductFallbackFactory.class)
public interface ProductApi {
	
	@RequestMapping(value = Router.V1_JDSYNC_PRODUCT_DETAILINFOLIST, method = RequestMethod.GET)
	ResultData<DataList<List<ProductInfo>>> detailInfoList(@RequestParam("params") Map<String, Object> params);

	@RequestMapping(value = Router.V1_JDSYNC_PRODUCT_DETAILINFOSEARCH, method = RequestMethod.POST)
	ResultData<DataList<List<ProductInfo>>> detailSearchList(@RequestBody Map<String, Object> params);
	
	@RequestMapping(value = Router.V1_JDSYNC_PRODUCT_DETAILINFO, method = RequestMethod.GET)
	ResultData<ProductInfo> detailInfo(@RequestParam("sku") long sku);

	@RequestMapping(value = Router.V1_JDSYNC_PRODUCT_PRICE, method = RequestMethod.POST)
	ResultData<SellPriceResult> getPrice(@RequestBody long sku);

	@RequestMapping(value = Router.V1_JDSYNC_PRODUCT_BRANDNAME_BY_CAT, method = RequestMethod.GET)
	ResultData<List<String>> getBrandNamesByCat2(@RequestParam("params") Map<String, Object> params);

	@GetMapping(value=Router.V1_JDSYNC_SALES_MANAGER__PRODUCT_DETAIL)
	ResultData<SalesManagerProductDetailVo> salesManagerProductDetail(@RequestParam("sku") Long sku);

	@PostMapping(value=Router.V1_JDSYNC_SALES_MANAGER_LIST)
    ResultData<DataList<List<SalesManagerVo>>> salesManagerProductDetailList(@RequestParam Map<String, Object> params);

	@PostMapping(Router.V1_JDSYNC_PRODUCT_DETAILINFO_BY_SKUS)
	ResultData<List<ProductInfo>> getProductInfoBySkus(@RequestBody List<Long> skus);

	@GetMapping(value = Router.V1_JDSYNC_PRODUCT_VIDEO_ADD)
	ResultData<Integer> productVideoAdd(@RequestParam("sku") Long sku, @RequestParam("videoPath") String videoPath);
}
