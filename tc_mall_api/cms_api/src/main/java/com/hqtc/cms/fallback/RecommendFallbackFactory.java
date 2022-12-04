package com.hqtc.cms.fallback;

import com.hqtc.cms.model.bean.RecommendRequestBean;
import com.hqtc.cms.model.service.RecommendService;
import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.ResultData;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import static com.hqtc.common.utils.Tools.getThreadResultData;


/**
 * Created by makuan on 18-6-30.
 */
@Component
public class RecommendFallbackFactory implements FallbackFactory<RecommendService> {
    @Override
    public RecommendService create(Throwable throwable) {
        return new RecommendService(){
            @Override
            public ResultData getHomeRecommend(int venderId, int status, int offset, int limit, String sort, String order) {
                return errorResponse();
            }

            @Override
            public ResultData getHistoryRecommend(RecommendRequestBean requestBean) {
                return errorResponse();
            }

            private ResultData errorResponse(){
                ResultData result = getThreadResultData();
                result.setMsg("FeignClient Request OMS Failed");
                result.setError(ErrorCode.SERVER_EXCEPTION);
                return result;
            }
        };
    }
}
