package com.cibnvideo.fallback.omsapi;

import com.cibnvideo.tcmalladmin.model.bean.RecommendBean;
import com.cibnvideo.tcmalladmin.omsapi.RecommendApi;
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
 * @Date: 18-9-11 下午3:34
 */
@Component
public class RecommendFallbackFactory implements FallbackFactory<RecommendApi> {
    @Override
    public RecommendApi create(Throwable throwable) {
        return new RecommendApi() {
            @Override
            public ResultData<DataList<List<RecommendBean>>> list(Map<String, Object> params) {
                return errorResponse("RecommendApi list fallback");
            }

            @Override
            public ResultData<Integer> count(Map<String, Object> params) {
                return errorResponse("RecommendApi count fallback");
            }

            @Override
            public ResultData<RecommendBean> get(Integer id) {
                return errorResponse("RecommendApi get fallback");
            }

            @Override
            public ResultData<RecommendBean> getbyposition(Integer venderId, Integer layoutId, Integer position) {
                return errorResponse("RecommendApi getbyposition fallback");
            }

            @Override
            public ResultData<Integer> add(RecommendBean recommendBean) {
                return errorResponse("RecommendApi add fallback");
            }

            @Override
            public ResultData<Integer> remove(Integer id) {
                return errorResponse("RecommendApi remove fallback");
            }

            @Override
            public ResultData<Integer> update(RecommendBean recommendBean) {
                return errorResponse("RecommendApi update fallback");
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
