package com.cibnvideo.fallback.bmsapi;

import com.cibnvideo.common.utils.Result;
import com.cibnvideo.tcmalladmin.bmsapi.OrderProductApi;
import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.ResultData;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import static com.hqtc.common.utils.Tools.getThreadResultData;


@Component
public class OrderProductFallbackFactory implements FallbackFactory<OrderProductApi> {
    private Logger logger  = LoggerFactory.getLogger(getClass());
    @Override
    public OrderProductApi create(Throwable throwable) {
        return new OrderProductApi(){

            @Override
            public Result<String> getOrderNo(Map<String, Object> params) {
                errorResponse("bms getOrderNo failed");
                return null;
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
