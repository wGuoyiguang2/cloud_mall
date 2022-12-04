package com.cibnvideo.tcmalladmin.omsapi;

import com.cibnvideo.common.utils.Result;
import com.cibnvideo.config.Router;
import com.cibnvideo.fallback.omsapi.FreeFreightFallbackFactory;
import com.cibnvideo.tcmalladmin.model.bean.FreeFreightBean;
import com.hqtc.common.config.FeignClientService;
import com.hqtc.common.response.ResultData;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/8/8 14:37
 */
@FeignClient(value = FeignClientService.OMSAPI,fallbackFactory = FreeFreightFallbackFactory.class)
public interface FreeFreightApi {
    @PostMapping(Router.V1_OMS_FREEFREIGHT_MANAGER_LIST)
    ResultData<Result<FreeFreightBean>> listFreeFreight(@RequestParam Map<String, Object> params);

    @PostMapping(Router.V1_OMS_FREEFREIGHT_MANAGER_ADD)
    ResultData addFreeFreight(@RequestParam Map<String, Object> params);

    @PostMapping(Router.V1_OMS_FREEFREIGHT_MANAGER_GETBYID)
    ResultData<FreeFreightBean> getFreeFreightById(@RequestParam("id") Integer id);

    @PostMapping(Router.V1_OMS_FREEFREIGHT_MANAGER_UPDATE)
    ResultData updateManagerFreeFreight(@RequestParam("id") Map<String, Object> params);

    @PostMapping(Router.V1_OMS_FREEFREIGHT_SUGGEST_PRICE)
    ResultData<Double> getSuggestPrice(@RequestParam("venderId") Long venderId);
}
