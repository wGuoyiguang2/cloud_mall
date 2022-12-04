package com.cibnvideo.fallback.omsapi;

import com.cibnvideo.tcmalladmin.model.bean.RecommendBean;
import com.cibnvideo.tcmalladmin.omsapi.RecommendHistoryApi;
import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.DataList;
import com.hqtc.common.response.ResultData;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import static com.hqtc.common.utils.Tools.getThreadResultData;

/**
 * @Author: likai
 * @description description
 * @Date: 18-9-11 下午3:30
 */
@Component
public class RecommendHistoryFallbackFactory implements FallbackFactory<RecommendHistoryApi> {
    @Override
    public RecommendHistoryApi create(Throwable throwable) {
        return new RecommendHistoryApi() {
            @Override
            public ResultData<DataList<List<RecommendBean>>> list(Map<String, Object> params) {
                return errorResponse("RecommendHistoryApi list fallback");
            }

            @Override
            public ResultData<Integer> countToday(Map<String, Object> params) {
                return errorResponse("RecommendHistoryApi list fallback");
            }

            @Override
            public ResultData<RecommendBean> get(Integer id) {
                return errorResponse("RecommendHistoryApi countToday fallback");
            }

            @Override
            public ResultData<Integer> add(RecommendBean recommendBean) {
                return errorResponse("RecommendHistoryApi add fallback");
            }

            @Override
            public ResultData<Integer> remove(Integer id) {
                return errorResponse("RecommendHistoryApi remove fallback");
            }

            @Override
            public ResultData<Integer> update(RecommendBean recommendBean) {
                return errorResponse("RecommendHistoryApi update fallback");
            }

            @Override
            public ResultData<Integer> updateStatus(Integer id, Integer status) {
                return errorResponse("RecommendHistoryApi updateStatus fallback");
            }

            @Override
            public ResultData<Integer> getPeriod(Integer venderId) {
                return errorResponse("RecommendHistoryApi getPeriod fallback");
            }

            @Override
            public ResultData<Integer> updatePeriod(Integer venderId, Integer period) {
                return errorResponse("RecommendHistoryApi updatePeriod fallback");
            }

            private ResultData errorResponse(String msg){
                ResultData result = getThreadResultData();
                result.setMsg(msg);
                result.setError(ErrorCode.SERVER_EXCEPTION);
                return result;
            }
        };
    }
}
