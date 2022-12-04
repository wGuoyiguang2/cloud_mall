package com.cibnvideo.tcmalladmin.omsapi;

import com.cibnvideo.config.Router;
import com.cibnvideo.fallback.omsapi.RecommendFallbackFactory;
import com.cibnvideo.tcmalladmin.model.bean.RecommendBean;
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

@FeignClient(value = FeignClientService.OMSAPI, fallbackFactory = RecommendFallbackFactory.class)
public interface RecommendApi {

    @RequestMapping(value = Router.V1_OMS_RECOMMEND_LIST, method = RequestMethod.GET)
    ResultData<DataList<List<RecommendBean>>> list(@RequestParam("params") Map<String, Object> params);

    @RequestMapping(value = Router.V1_OMS_RECOMMEND_COUNT, method = RequestMethod.GET)
    ResultData<Integer> count(@RequestParam("params") Map<String, Object> params);

    @RequestMapping(value = Router.V1_OMS_RECOMMEND_GET, method = RequestMethod.GET)
    ResultData<RecommendBean> get(@RequestParam("id") Integer id);

    @RequestMapping(value = Router.V1_OMS_RECOMMEND_GET_BY_POSITION, method = RequestMethod.GET)
    ResultData<RecommendBean> getbyposition(@RequestParam("venderId") Integer venderId, @RequestParam("layoutId") Integer layoutId, @RequestParam("position") Integer position);

    @RequestMapping(value = Router.V1_OMS_RECOMMEND_ADD, method = RequestMethod.POST)
    ResultData<Integer> add(@RequestBody RecommendBean recommendBean);

    @RequestMapping(value = Router.V1_OMS_RECOMMEND_REMOVE, method = RequestMethod.GET)
    ResultData<Integer> remove(@RequestParam("id") Integer id);

    @RequestMapping(value = Router.V1_OMS_RECOMMEND_UPDATE, method = RequestMethod.POST)
    ResultData<Integer> update(@RequestBody RecommendBean recommendBean);


}
