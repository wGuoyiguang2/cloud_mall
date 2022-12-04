package com.cibnvideo.thirdpart.feign;

import com.cibnvideo.thirdpart.config.Router;
import com.cibnvideo.thirdpart.feign.fallback.JdSyncFallbackFactory;
import com.hqtc.common.config.FeignClientService;
import com.hqtc.common.response.ResultData;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @Description: JdSync调用接口
 * @Author: WangBin
 * @Date: 2019/1/21 18:26
 */
@FeignClient(value=FeignClientService.JDSYNC,fallbackFactory = JdSyncFallbackFactory.class)
public interface JdSyncFeign {
    @GetMapping(Router.V1_JDSYNC_PRODUCT_COUNT)
    ResultData<Integer> getCount(@RequestParam Map<String, Object> params);
    @GetMapping(Router.V1_JDSYNC_PRODUCT_SKULIST)
    ResultData<String> getSkus(@RequestParam Map<String, Object> params);
}
