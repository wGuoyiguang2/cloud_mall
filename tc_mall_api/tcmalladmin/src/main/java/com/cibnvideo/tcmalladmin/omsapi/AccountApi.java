package com.cibnvideo.tcmalladmin.omsapi;

import com.cibnvideo.common.utils.Result;
import com.cibnvideo.config.Router;
import com.cibnvideo.fallback.omsapi.AccountFallbackFactory;
import com.cibnvideo.tcmalladmin.model.bean.AccountInfoVo;
import com.cibnvideo.tcmalladmin.model.bean.AccountManagerVo;
import com.cibnvideo.tcmalladmin.model.bean.VenderAccountVo;
import com.hqtc.common.config.FeignClientService;
import com.hqtc.common.response.DataList;
import com.hqtc.common.response.ResultData;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/7/21 16:11
 */
@FeignClient(value = FeignClientService.OMSAPI,fallbackFactory = AccountFallbackFactory.class)
public interface AccountApi {
    @PostMapping(Router.OMS_ACCOUNT_MANAGER_LIST)
    Result<AccountManagerVo> accountList(@RequestParam Map<String, Object> params);
    @PostMapping(Router.OMS_ACCOUNT_INFO_GET)
    AccountInfoVo getAccountInfo(@RequestParam("venderid") Long venderId);
    @PostMapping(Router.OMS_ACCOUNT_GET_ACCOUNT_LIST)
    ResultData<List<AccountManagerVo>> getOrderAccount(@RequestBody List<String> orderList);
    @PostMapping(Router.OMS_ACCOUNT_SETTLE)
    ResultData settleAccount(@RequestParam("startTime") String startTime, @RequestParam("endTime") String endTime, @RequestParam("venderId")Long venderId);
    @PostMapping(Router.OMS_ACCOUNT_GET_VENDER_ACCOUNT_LIST)
    ResultData<DataList<List<VenderAccountVo>>> getVenderAccountInfo(@RequestParam Map<String, Object> map);
}
