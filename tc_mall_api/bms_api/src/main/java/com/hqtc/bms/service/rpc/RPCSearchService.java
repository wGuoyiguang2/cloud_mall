package com.hqtc.bms.service.rpc;

import com.hqtc.bms.config.RPCRouter;
import com.hqtc.bms.model.params.SkuSearchParamsBean;
import com.hqtc.common.config.FeignClientService;
import com.hqtc.common.response.ResultData;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by wanghaoyang on 18-10-10.
 */
@FeignClient(name = FeignClientService.SEARCH, fallback = RPCSearchServiceFallback.class)
public interface RPCSearchService {

    @PostMapping(value= RPCRouter.ROUTER_SEARCH_PRODUCT)
    ResultData searchProduct(@RequestParam("venderId")int venderId, @RequestParam("skus")String skus
            , @RequestParam("pageNum")int pageNum, @RequestParam("pageSize")int pageSize);
}
