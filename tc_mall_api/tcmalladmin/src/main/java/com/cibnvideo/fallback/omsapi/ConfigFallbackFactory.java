package com.cibnvideo.fallback.omsapi;

import com.cibnvideo.common.utils.Result;
import com.cibnvideo.tcmalladmin.model.bean.ConfigManagerVo;
import com.cibnvideo.tcmalladmin.omsapi.ConfigApi;
import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.ResultData;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.hqtc.common.utils.Tools.getThreadResultData;


@Component
public class ConfigFallbackFactory implements FallbackFactory<ConfigApi> {
    private Logger logger  = LoggerFactory.getLogger(getClass());
    @Override
    public ConfigApi create(Throwable throwable) {
        return new ConfigApi(){

            @Override
            public ResultData<Result<ConfigManagerVo>> listConfig(Map<String, Object> params) {
                return  errorResponse("oms listConfig failed");
            }

            @Override
            public ResultData addConfig(Map<String, Object> params) {
                return errorResponse("oms addConfig failed");
            }

            @Override
            public ResultData<ConfigManagerVo> getById(Integer id) {
                return  errorResponse("oms getById failed");
            }

            @Override
            public ResultData updateManagerConfig(Map<String, Object> params) {
                return  errorResponse("oms updateManagerConfig failed");
            }

            @Override
            public ResultData deleteById(Integer id) {
                return  errorResponse("oms deleteById failed");
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
