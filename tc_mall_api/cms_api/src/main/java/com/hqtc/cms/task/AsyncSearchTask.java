package com.hqtc.cms.task;

import com.hqtc.cms.model.bean.search.SyncSearchHistoryBean;
import com.hqtc.cms.model.service.SyncSearchHistoryService;
import com.hqtc.common.response.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * Created by makuan on 18-7-6.
 */
@Component
public class AsyncSearchTask {

    @Autowired
    SyncSearchHistoryService syncSearchHistoryService;

    @Async
    public ResultData syncSearchHistory(SyncSearchHistoryBean syncSearchHistoryBean) throws Exception {
        return syncSearchHistoryService.syncSearchHistory(syncSearchHistoryBean);
    }
}
