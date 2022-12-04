package com.hqtc.cms.fallback;

import com.hqtc.cms.model.bean.search.SyncSearchHistoryBean;
import com.hqtc.cms.model.service.SyncSearchHistoryService;
import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.ResultData;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import static com.hqtc.common.utils.Tools.getThreadResultData;

@Component
public class SyncSearchHistoryFallbackFactory implements FallbackFactory<SyncSearchHistoryService> {
    private Logger logger  = LoggerFactory.getLogger(getClass());
    @Override
    public SyncSearchHistoryService create(Throwable throwable) {
        return new SyncSearchHistoryService() {
            @Override
            public ResultData syncSearchHistory(SyncSearchHistoryBean syncSearchHistoryBean){
                return errorResponse();
            }
        };
    }

    private ResultData errorResponse() {
        ResultData result = getThreadResultData();
        result.setMsg("FeignClient Request IMS Failed");
        result.setError(ErrorCode.SERVER_EXCEPTION);
        return result;
    }
}
