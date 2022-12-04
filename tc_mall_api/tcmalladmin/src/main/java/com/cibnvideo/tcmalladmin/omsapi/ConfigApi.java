package com.cibnvideo.tcmalladmin.omsapi;

import com.cibnvideo.common.utils.R;
import com.cibnvideo.common.utils.Result;
import com.cibnvideo.config.Router;
import com.cibnvideo.fallback.omsapi.ConfigFallbackFactory;
import com.cibnvideo.tcmalladmin.model.bean.ConfigManagerVo;
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
 * @Date: 2018/8/8 14:37
 */
@FeignClient(value = FeignClientService.OMSAPI,fallbackFactory = ConfigFallbackFactory.class)
public interface ConfigApi {
    @PostMapping(Router.V1_OMS_CONFIG_MANAGER_LIST)
    ResultData<Result<ConfigManagerVo>> listConfig(@RequestParam Map<String, Object> params);
    @PostMapping(Router.V1_OMS_CONFIG_MANAGER_ADD)
    ResultData addConfig(@RequestParam Map<String, Object> params);
    @GetMapping(Router.V1_OMS_CONFIG_MANAGER_GETBYID)
    ResultData<ConfigManagerVo> getById(@RequestParam("id") Integer id);
    @PostMapping(Router.V1_OMS_CONFIG_MANAGER_UPDATE)
    ResultData updateManagerConfig(@RequestParam Map<String, Object> params);
    @GetMapping(Router.V1_OMS_CONFIG_MANAGER_DELETE)
    ResultData deleteById(@RequestParam("id") Integer id);
}
