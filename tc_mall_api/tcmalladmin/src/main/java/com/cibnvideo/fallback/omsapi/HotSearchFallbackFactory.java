package com.cibnvideo.fallback.omsapi;

import com.cibnvideo.common.utils.Result;
import com.cibnvideo.tcmalladmin.model.bean.HotSearchBean;
import com.cibnvideo.tcmalladmin.omsapi.HotSearchApi;
import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.ResultData;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.hqtc.common.utils.Tools.getThreadResultData;


@Component
public class HotSearchFallbackFactory implements FallbackFactory<HotSearchApi> {
    private Logger logger  = LoggerFactory.getLogger(getClass());
    @Override
    public HotSearchApi create(Throwable throwable) {
        return new HotSearchApi(){

            @Override
            public ResultData<Result<HotSearchBean>> listManagerHotSearch(Map<String, Object> params) {
                return errorResponse("oms listManagerHotSearch failed");
            }

            @Override
            public ResultData addHotSearch(Map<String, Object> params) {
                return errorResponse("oms addHotSearch failed");
            }

            @Override
            public ResultData<HotSearchBean> getById(Integer id) {
                return errorResponse("oms getById failed");
            }

            @Override
            public ResultData updateManagerHotSearch(Map<String, Object> params) {
                return errorResponse("oms updateManagerHotSearch failed");
            }

            @Override
            public ResultData deleteById(Integer id) {
                return errorResponse("oms deleteById failed");
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
