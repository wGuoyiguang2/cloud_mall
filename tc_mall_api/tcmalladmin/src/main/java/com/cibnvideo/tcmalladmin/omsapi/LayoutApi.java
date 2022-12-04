package com.cibnvideo.tcmalladmin.omsapi;

import com.cibnvideo.config.Router;
import com.cibnvideo.tcmalladmin.model.bean.LayoutBean;
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

@FeignClient(FeignClientService.OMSAPI)
public interface LayoutApi {

    @RequestMapping(value = Router.V1_OMS_LAYOUT_LIST, method = RequestMethod.GET)
    ResultData<DataList<List<LayoutBean>>> list(@RequestParam("params") Map<String, Object> params);

    @RequestMapping(value = Router.V1_OMS_LAYOUT_COUNT, method = RequestMethod.GET)
    ResultData<Integer> count(@RequestParam("params") Map<String, Object> params);

    @RequestMapping(value = Router.V1_OMS_LAYOUT_GET, method = RequestMethod.GET)
    ResultData<LayoutBean> get(@RequestParam("id") Integer id);

    @RequestMapping(value = Router.V1_OMS_LAYOUT_ADD, method = RequestMethod.POST)
    ResultData<Integer> add(@RequestBody LayoutBean layoutBean);

    @RequestMapping(value = Router.V1_OMS_LAYOUT_REMOVE, method = RequestMethod.GET)
    ResultData<Integer> remove(@RequestParam("id") Integer id);

    @RequestMapping(value = Router.V1_OMS_LAYOUT_UPDATE, method = RequestMethod.POST)
    ResultData<Integer> update(@RequestBody LayoutBean layoutBean);

    @RequestMapping(value = Router.V1_OMS_LAYOUT_COPY_BY_LAYOUTID, method = RequestMethod.GET)
    ResultData<Integer> copyByLayoutId(@RequestParam("venderId") Integer venderId, @RequestParam("parentId") Integer parentId);


}
