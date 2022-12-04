package com.cibnvideo.tcmalladmin.omsapi;

import com.cibnvideo.common.utils.Result;
import com.cibnvideo.config.Router;
import com.cibnvideo.fallback.omsapi.HotSearchFallbackFactory;
import com.cibnvideo.tcmalladmin.model.bean.HotSearchBean;
import com.hqtc.common.config.FeignClientService;
import com.hqtc.common.response.ResultData;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/7/21 16:11
 */
@FeignClient(value = FeignClientService.OMSAPI,fallbackFactory = HotSearchFallbackFactory.class)
public interface HotSearchApi {
    @PostMapping(Router.V1_OMS_HOTSEARCH_MANAGER_LIST)
    ResultData<Result<HotSearchBean>> listManagerHotSearch(@RequestParam Map<String, Object> params);

    @PostMapping(Router.V1_OMS_HOTSEARCH_ADD)
    ResultData addHotSearch(@RequestParam Map<String, Object> params);

    @GetMapping(Router.V1_OMS_HOTSEARCH_GET)
    ResultData<HotSearchBean> getById(@RequestParam("id") Integer id);

    @PostMapping(Router.V1_OMS_HOTSEARCH_UPDATE)
    ResultData updateManagerHotSearch(@RequestParam Map<String, Object> params);

    @PostMapping(Router.V1_OMS_HOTSEARCH_DELETE)
    ResultData deleteById(@RequestParam("id") Integer id);
}
