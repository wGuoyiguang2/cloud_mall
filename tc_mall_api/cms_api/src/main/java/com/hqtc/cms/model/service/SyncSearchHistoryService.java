package com.hqtc.cms.model.service;

import com.hqtc.cms.config.Router;
import com.hqtc.cms.fallback.SyncSearchHistoryFallbackFactory;
import com.hqtc.cms.model.bean.search.SyncSearchHistoryBean;
import com.hqtc.common.config.FeignClientService;
import com.hqtc.common.response.ResultData;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by makuan on 18-7-6.
 */
@FeignClient(name = FeignClientService.IMSAPI,fallbackFactory = SyncSearchHistoryFallbackFactory.class)
public interface SyncSearchHistoryService {

    @RequestMapping(value = Router.ROUTE_SYNC_SEARCH_HISTORY, method = RequestMethod.POST)
    ResultData syncSearchHistory(@RequestBody SyncSearchHistoryBean syncSearchHistoryBean);
}
