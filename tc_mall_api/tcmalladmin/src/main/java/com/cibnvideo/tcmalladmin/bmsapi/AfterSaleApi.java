package com.cibnvideo.tcmalladmin.bmsapi;

import com.cibnvideo.common.utils.Result;
import com.cibnvideo.config.Router;
import com.cibnvideo.fallback.bmsapi.AfterSaleFallbackFactory;
import com.cibnvideo.tcmalladmin.model.bean.AfterSaleVo;
import com.hqtc.common.config.FeignClientService;
import com.hqtc.common.response.ResultData;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @Description: 售后管理
 * @Author: WangBin
 * @Date: 2018/7/16 15:19
 */
@FeignClient(value = FeignClientService.BMSAPI,fallbackFactory = AfterSaleFallbackFactory.class)
public interface AfterSaleApi {
    @PostMapping(Router.V1_BMS_AFTER_SALES_MANAGER_LIST)
    ResultData<Result<AfterSaleVo>> listManagerAfterSale(@RequestParam Map<String, Object> params);
}
