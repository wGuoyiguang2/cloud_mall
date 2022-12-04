package com.cibnvideo.oms.recommend.controller;

import com.cibnvideo.oms.config.Router;
import com.cibnvideo.oms.recommend.model.bean.RecommendBean;
import com.cibnvideo.oms.recommend.model.bean.RecommendHistoryRequest;
import com.cibnvideo.oms.recommend.service.RecommendHistoryService;
import com.cibnvideo.oms.recommend.service.RecommendHistoryPeriodService;
import com.hqtc.common.response.DataList;
import com.hqtc.common.response.ResultData;
import com.hqtc.common.utils.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class RecommendHistoryController {

    @Autowired
    private RecommendHistoryService recommendHistoryService;

    @Autowired
    private RecommendHistoryPeriodService recommendHistoryPeriodService;

    @RequestMapping(value = Router.V1_OMS_RECOMMEND_HISTORY_LIST, method = RequestMethod.POST)
    ResultData list(@RequestBody Map<String, Object> params){
        ResultData resultData = Tools.getThreadResultData();
        List<RecommendBean> recommendBeanList = recommendHistoryService.list(params);
        int count = recommendHistoryService.count(params);
        DataList<List<RecommendBean>> result = Tools.getThreadDataList();
        result.setData(recommendBeanList);
        result.setTotalRows(count);
        resultData.setData(result);
        return resultData;
    }

    @RequestMapping(value = Router.V1_OMS_RECOMMEND_HISTORY_LIST_BY_DAY, method = RequestMethod.POST)
    ResultData listByDay(@RequestBody RecommendHistoryRequest recommendHistoryRequest){
        ResultData resultData = Tools.getThreadResultData();
        List<RecommendBean> recommendBeanList = recommendHistoryService.listByDay(recommendHistoryRequest);
        resultData.setData(recommendBeanList);
        return resultData;
    }

    @RequestMapping(value = Router.V1_OMS_RECOMMEND_HISTORY_GET, method = RequestMethod.GET)
    ResultData get(@RequestParam Integer id){
        ResultData<RecommendBean> resultData = Tools.getThreadResultData();
        resultData.setData(recommendHistoryService.get(id));
        return resultData;
    }

    @RequestMapping(value = Router.V1_OMS_RECOMMEND_HISTORY_PERIOD, method = RequestMethod.GET)
    ResultData getPeriod(@RequestParam Integer venderId){
        ResultData<Integer> resultData = Tools.getThreadResultData();
        resultData.setData(recommendHistoryPeriodService.getRecommendHistoryPeroid(venderId));
        return resultData;
    }

    @RequestMapping(value = Router.V1_OMS_RECOMMEND_HISTORY_PERIOD_UPDATE, method = RequestMethod.GET)
    ResultData updatePeriod(@RequestParam("venderId") Integer venderId, @RequestParam("period") Integer period){
        ResultData<Integer> resultData = Tools.getThreadResultData();
        resultData.setData(recommendHistoryPeriodService.updatePeriod(venderId, period));
        return resultData;
    }

    @RequestMapping(value = Router.V1_OMS_RECOMMEND_HISTORY_COUNT_TODAY, method = RequestMethod.GET)
    ResultData countToday(@RequestParam Map<String, Object> params){
        ResultData resultData = Tools.getThreadResultData();
        resultData.setData(recommendHistoryService.countToday(params));
        return resultData;
    }

    @RequestMapping(value = Router.V1_OMS_RECOMMEND_HISTORY_ADD, method = RequestMethod.POST)
    ResultData add(@RequestBody RecommendBean recommendBean){
        ResultData<Integer> resultData = Tools.getThreadResultData();
        resultData.setData(recommendHistoryService.save(recommendBean));
        return resultData;
    }

    @RequestMapping(value = Router.V1_OMS_RECOMMEND_HISTORY_REMOVE, method = RequestMethod.GET)
    ResultData remove(@RequestParam Integer id){
        ResultData<Integer> resultData = Tools.getThreadResultData();
        resultData.setData(recommendHistoryService.remove(id));
        return resultData;
    }

    @RequestMapping(value = Router.V1_OMS_RECOMMEND_HISTORY_UPDATE, method = RequestMethod.POST)
    ResultData update(@RequestBody RecommendBean recommendBean){
        ResultData<Integer> resultData = Tools.getThreadResultData();
        resultData.setData(recommendHistoryService.update(recommendBean));
        return resultData;
    }

    @RequestMapping(value = Router.V1_OMS_RECOMMEND_HISTORY_UPDATE_STATUS, method = RequestMethod.GET)
    ResultData updateStatus(@RequestParam("id") int id, @RequestParam("status") int status){
        ResultData<Integer> resultData = Tools.getThreadResultData();
        resultData.setData(recommendHistoryService.updateStatus(id, status));
        return resultData;
    }
}
