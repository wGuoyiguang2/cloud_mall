package com.hqtc.cms.model.service;

import com.hqtc.cms.config.Router;
import com.hqtc.cms.fallback.RecommendFallbackFactory;
import com.hqtc.cms.model.bean.*;
import com.hqtc.common.config.FeignClientService;
import com.hqtc.common.response.ResultData;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

/**
 * Created by makuan on 18-6-27.
 */
@FeignClient(name= FeignClientService.OMSAPI, fallbackFactory = RecommendFallbackFactory.class)
public interface RecommendService {
    @RequestMapping(value = Router.ROUTE_GET_OMS_HOME_RECOMMEND, method = RequestMethod.GET)
    ResultData<List<LayoutRecommendBean>> getHomeRecommend(@RequestParam("venderId") int venderId,
                                                           @RequestParam("status") int status,
                                                           @RequestParam("offset") int offset,
                                                           @RequestParam("limit") int limit,
                                                           @RequestParam("sort") String sort,
                                                           @RequestParam("order") String order);

    @RequestMapping(value = Router.ROUTE_POST_OMS_HISTORY_RECOMMEND, method = RequestMethod.POST)
    ResultData<List<RecommendHistoryBean>> getHistoryRecommend(@RequestBody RecommendRequestBean requestBean);
}
