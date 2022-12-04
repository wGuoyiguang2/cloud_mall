package com.cibnvideo.fallback.omsapi;

import com.cibnvideo.common.utils.Result;
import com.cibnvideo.tcmalladmin.model.bean.FreeFreightBean;
import com.cibnvideo.tcmalladmin.omsapi.FreeFreightApi;
import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.ResultData;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.hqtc.common.utils.Tools.getThreadResultData;


@Component
public class FreeFreightFallbackFactory implements FallbackFactory<FreeFreightApi> {
    private Logger logger  = LoggerFactory.getLogger(getClass());
    @Override
    public FreeFreightApi create(Throwable throwable) {
        return new FreeFreightApi(){
            @Override
            public ResultData<Result<FreeFreightBean>> listFreeFreight(Map<String, Object> params) {
                return errorResponse("oms listFreeFreight failed");
            }

            @Override
            public ResultData addFreeFreight(Map<String, Object> params) {
                return errorResponse("oms addFreeFreight failed");
            }

            @Override
            public ResultData<FreeFreightBean> getFreeFreightById(Integer id) {
                return errorResponse("oms getFreeFreightById failed");
            }

            @Override
            public ResultData updateManagerFreeFreight(Map<String, Object> params) {
                return errorResponse("oms updateManagerFreeFreight failed");
            }

            @Override
            public ResultData<Double> getSuggestPrice(Long venderId) {
                return errorResponse("oms getSuggestPrice failed");
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
