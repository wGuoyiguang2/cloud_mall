package com.cibnvideo.tcmalladmin.omsapi;

import com.cibnvideo.config.Router;
import com.cibnvideo.fallback.omsapi.TemplateFallbackFactory;
import com.cibnvideo.tcmalladmin.model.bean.TemplateBean;
import com.hqtc.common.config.FeignClientService;
import com.hqtc.common.response.DataList;
import com.hqtc.common.response.ResultData;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(value = FeignClientService.OMSAPI, fallbackFactory = TemplateFallbackFactory.class)
public interface TemplateApi {

    @RequestMapping(value = Router.V1_OMS_TEMPLATE_LIST, method = RequestMethod.GET)
    ResultData<DataList<List<TemplateBean>>> list(@RequestParam("params") Map<String, Object> params);

    @RequestMapping(value = Router.V1_OMS_TEMPLATE_COUNT, method = RequestMethod.GET)
    ResultData<Integer> count(@RequestParam("params") Map<String, Object> params);

    @RequestMapping(value = Router.V1_OMS_TEMPLATE_GET, method = RequestMethod.GET)
    ResultData<TemplateBean> get(@RequestParam("id") Integer id);

    @RequestMapping(value = Router.V1_OMS_TEMPLATE_ADD, method = RequestMethod.POST)
    ResultData<Integer> add(@RequestBody TemplateBean templateBean);

    @RequestMapping(value = Router.V1_OMS_TEMPLATE_REMOVE, method = RequestMethod.GET)
    ResultData<Integer> remove(@RequestParam("id") Integer id);

    @RequestMapping(value = Router.V1_OMS_TEMPLATE_UPDATE, method = RequestMethod.POST)
    ResultData<Integer> update(@RequestBody TemplateBean templateBean);


}
