package com.hqtc.bms.service.rpc;

import com.hqtc.bms.config.RPCRouter;
import com.hqtc.bms.model.rpc.ProductsOriginInfoBean;
import com.hqtc.common.config.FeignClientService;
import com.hqtc.common.response.ResultData;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by wanghaoyang on 18-7-4.
 */
@FeignClient(name = FeignClientService.JDSYNC, fallback = RPCProductInfoServiceFallback.class)
public interface RPCProductInfoService {
    @RequestMapping(value = RPCRouter.ROUTER_PRODUCT_INFO, method = RequestMethod.GET)
    ResultData<List<ProductsOriginInfoBean>> getHomeRecommend(@RequestParam("skus") String sku);
}
