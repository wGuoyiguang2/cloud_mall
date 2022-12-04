package com.cibnvideo.tcmalladmin.jdsyncapi;

import com.cibnvideo.config.Router;
import com.cibnvideo.fallback.jdsyncapi.CategoryFallbackFactory;
import com.cibnvideo.tcmalladmin.model.bean.CategoryResultInfo;
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

@FeignClient(value = FeignClientService.JDSYNC, fallbackFactory = CategoryFallbackFactory.class)
public interface CategoryApi {

	@RequestMapping(value = Router.V1_JDSYNC_CATEGORY_LIST, method = RequestMethod.GET)
	ResultData<DataList<List<CategoryResultInfo>>> categoryList(@RequestParam Map<String, Object> params);

	@RequestMapping(value = Router.V1_JDSYNC_CATEGORY_GET, method = RequestMethod.GET)
	ResultData<CategoryResultInfo> categoryGet(@RequestParam("catId") Integer catId);


	@RequestMapping(value = Router.V1_JDSYNC_CATEGORY_LIST_BY_CATIDS, method = RequestMethod.POST)
	ResultData<List<CategoryResultInfo>> categoryListByCatIds(@RequestBody Integer[] catIds);
}
