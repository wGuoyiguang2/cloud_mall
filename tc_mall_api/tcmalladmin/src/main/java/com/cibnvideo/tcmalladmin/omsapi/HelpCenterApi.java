package com.cibnvideo.tcmalladmin.omsapi;

import com.cibnvideo.config.Router;
import com.cibnvideo.fallback.omsapi.HelpCenterFallbackFactory;
import com.cibnvideo.tcmalladmin.model.bean.HelpCenterInfoVo;
import com.hqtc.common.config.FeignClientService;
import com.hqtc.common.response.DataList;
import com.hqtc.common.response.ResultData;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/7/11 15:45
 */
@FeignClient(value = FeignClientService.OMSAPI,fallbackFactory = HelpCenterFallbackFactory.class)
public interface HelpCenterApi {
    @GetMapping(Router.V1_OMS_HELPCENTER_LIST)
    DataList<List<HelpCenterInfoVo>> helpCenterList(@RequestParam Map<String, Object> params);

    @GetMapping(Router.V1_OMS_HELPCENTER_GET_QA_BY_ID)
    ResultData<HelpCenterInfoVo> getQAById(@RequestParam("id") Integer id);

    @PostMapping(Router.V1_OMS_HELPCENTER_UPDATE_QA_BY_ID)
    ResultData<Integer> updateQA(@RequestParam Map<String, Object> params);

    @PostMapping(Router.V1_OMS_HELPCENTER_UPDATE_TYPE_BY_ID)
    ResultData<Integer> updateType(@RequestParam("typeName") String typeName, @RequestParam("id") Integer id);

    @PostMapping(Router.V1_OMS_HELPCENTER_DELETE_QA_BY_ID)
    ResultData<Integer> deleteQAAndType(@RequestParam("id") Integer id);

    @PostMapping(Router.V1_OMS_HELPCENTER_ADD_QA)
    ResultData<Integer> addQA(@RequestParam Map<String, Object> params);

    @PostMapping(Router.V1_OMS_HELPCENTER_ADD_TYPE)
    ResultData<Integer> addType(@RequestParam Map<String, Object> params);

    @PostMapping(Router.V1_OMS_HELPCENTER_LIST_TYPE)
    ResultData<List<HelpCenterInfoVo>> listAllType(@RequestParam("venderId") Long venderId);

    @PostMapping(Router.V1_OMS_HELPCENTER_TYPE_GET)
    ResultData<HelpCenterInfoVo> getTypeById(@RequestParam("typeId") Integer typeId);

    @PostMapping(Router.V1_OMS_HELPCENTER_DELETE_TYPE)
    ResultData<Integer> deleteType(@RequestParam("typeId") Integer typeId);
}
