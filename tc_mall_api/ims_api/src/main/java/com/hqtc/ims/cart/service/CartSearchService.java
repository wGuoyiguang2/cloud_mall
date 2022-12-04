package com.hqtc.ims.cart.service;

import com.hqtc.common.config.FeignClientService;
import com.hqtc.common.response.ResultData;
import com.hqtc.ims.cart.fallback.CartFallbackFactory;
import com.hqtc.ims.cart.model.bean.CartProductListBean;
import com.hqtc.ims.common.constant.PathConstants;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * description: 商品信息
 * Created by sunjianqiang  18-9-21
 */

@FeignClient(name = FeignClientService.SEARCH,fallbackFactory = CartFallbackFactory.class)
public interface CartSearchService {

    /**
     * 根据sku搜索接口
     */
    @RequestMapping(value = PathConstants.ROUTE_SEARCH_PRODUCT_SKUS, method = RequestMethod.POST)
    ResultData<CartProductListBean> idsQuery(@RequestParam("venderId") Integer venderId,
                                             @RequestParam("skus") String skus,
                                             @RequestParam("pageSize") Integer pageSize);
}

