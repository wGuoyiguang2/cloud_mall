package com.cibnvideo.jdsynctask.thirdpartapi;

import com.cibnvideo.jdsynctask.config.Router;
import com.cibnvideo.jdsynctask.fallback.thirdpartapi.ThirdPartFallbackFactory;
import com.cibnvideo.jdsynctask.model.JDSitePriceBean;
import com.hqtc.common.config.FeignClientService;
import com.hqtc.common.response.ResultData;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value = FeignClientService.THIRD_PART, fallbackFactory = ThirdPartFallbackFactory.class)
public interface ThirdPartApi {
    @PostMapping(Router.V1_3RD_PART_JD_SITE_PRICE_LIST)
    ResultData<List<JDSitePriceBean>> listJDSitePrice(@RequestBody List<Long> skuList);
}
