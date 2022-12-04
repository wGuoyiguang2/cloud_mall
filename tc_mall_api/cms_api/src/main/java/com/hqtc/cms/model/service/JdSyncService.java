package com.hqtc.cms.model.service;

import com.hqtc.cms.config.Router;
import com.hqtc.cms.fallback.JdSyncFallbackFactory;
import com.hqtc.cms.model.bean.ProductSynchronizationBean;
import com.hqtc.cms.model.bean.search.ProductCtime;
import com.hqtc.common.config.FeignClientService;
import com.hqtc.common.response.DataList;
import com.hqtc.common.response.ResultData;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/7/18 11:40
 */
@FeignClient(name = FeignClientService.JDSYNC ,fallbackFactory = JdSyncFallbackFactory.class)
public interface JdSyncService {
    @PostMapping(Router.V1_JDSYNC_PRODUCT_CTIME_GET)
    public ResultData<List<ProductCtime>> getProductCtime(@RequestBody List<Long> skus);

    @PostMapping(Router.V1_JDSYNC_PRODUCT_DETAIL_LIST)
    public ResultData<List<ProductSynchronizationBean>> getProductDetailList(@RequestParam("skus") String skus);

}
