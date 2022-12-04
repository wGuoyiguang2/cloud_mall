package com.cibnvideo.thirdpart.feign;

import com.cibnvideo.thirdpart.config.Router;
import com.cibnvideo.thirdpart.feign.fallback.SearchFallbackFactory;
import com.cibnvideo.thirdpart.model.bean.ProductBean;
import com.cibnvideo.thirdpart.model.bean.ProductListBean;
import com.hqtc.common.config.FeignClientService;
import com.hqtc.common.response.ResultData;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Description: Search服务调用接口
 * @Author: WangBin
 * @Date: 2019/1/21 18:26
 */
@FeignClient(value=FeignClientService.SEARCH,fallbackFactory = SearchFallbackFactory.class)
public interface SearchFeign {
    @PostMapping(value = Router.ROUTE_SEARCH_PRODUCT_BY_SKU)
    ResultData<ProductListBean<List<ProductBean>>> getBatchPrice(@RequestParam("skus") String skus,@RequestParam("venderId") Integer vendor,@RequestParam("pageSize") Integer pageSize);
}
