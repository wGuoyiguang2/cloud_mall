package com.cibnvideo.tcmalladmin.omsapi;

import com.cibnvideo.config.Router;
import com.cibnvideo.fallback.omsapi.RecommendHistoryFallbackFactory;
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

@FeignClient(value = FeignClientService.OMSAPI, fallbackFactory = RecommendHistoryFallbackFactory.class)
public interface RecommendHistoryApi {

    @RequestMapping(value = Router.V1_OMS_RECOMMEND_HISTORY_LIST, method = RequestMethod.POST)
    ResultData<DataList<List<RecommendBean>>> list(@RequestBody Map<String, Object> params);

    @RequestMapping(value = Router.V1_OMS_RECOMMEND_HISTORY_COUNT_TODAY, method = RequestMethod.GET)
    ResultData<Integer> countToday(@RequestParam("params") Map<String, Object> params);

    @RequestMapping(value = Router.V1_OMS_RECOMMEND_HISTORY_GET, method = RequestMethod.GET)
    ResultData<RecommendBean> get(@RequestParam("id") Integer id);

    @RequestMapping(value = Router.V1_OMS_RECOMMEND_HISTORY_ADD, method = RequestMethod.POST)
    ResultData<Integer> add(@RequestBody RecommendBean recommendBean);

    @RequestMapping(value = Router.V1_OMS_RECOMMEND_HISTORY_REMOVE, method = RequestMethod.GET)
    ResultData<Integer> remove(@RequestParam("id") Integer id);

    @RequestMapping(value = Router.V1_OMS_RECOMMEND_HISTORY_UPDATE, method = RequestMethod.POST)
    ResultData<Integer> update(@RequestBody RecommendBean recommendBean);

    @RequestMapping(value = Router.V1_OMS_RECOMMEND_HISTORY_UPDATE_STATUS, method = RequestMethod.GET)
    ResultData<Integer> updateStatus(@RequestParam("id") Integer id, @RequestParam("status") Integer status);

    @RequestMapping(value = Router.V1_OMS_RECOMMEND_HISTORY_PERIOD, method = RequestMethod.GET)
    ResultData<Integer> getPeriod(@RequestParam("venderId") Integer venderId);

    @RequestMapping(value = Router.V1_OMS_RECOMMEND_HISTORY_PERIOD_UPDATE, method = RequestMethod.GET)
    ResultData<Integer> updatePeriod(@RequestParam("venderId") Integer venderId,@RequestParam("period") Integer period);


}
