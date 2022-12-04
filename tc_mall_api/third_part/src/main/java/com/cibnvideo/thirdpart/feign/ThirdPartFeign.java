package com.cibnvideo.thirdpart.feign;

import com.cibnvideo.thirdpart.config.Router;
import com.cibnvideo.thirdpart.feign.fallback.ThirdPartFallbackFactory;
import com.cibnvideo.thirdpart.model.bean.SyncJDSitePriceResponse;
import com.hqtc.common.config.FeignClientService;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Description: 第三方服务调用接口
 * @Author: WangBin
 * @Date: 2019/1/21 18:26
 */
@FeignClient(value= FeignClientService.THIRD_PART,url =Router.ROUTE_GET_THIRD_PART_JD_PRICE,fallbackFactory = ThirdPartFallbackFactory.class)
public interface ThirdPartFeign {
    @GetMapping
    List<SyncJDSitePriceResponse> getJDSitePrice(@RequestParam("skuIds") String skuIds);
}
