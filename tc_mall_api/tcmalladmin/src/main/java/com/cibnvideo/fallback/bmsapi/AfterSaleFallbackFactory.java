package com.cibnvideo.fallback.bmsapi;

import com.cibnvideo.common.utils.Result;
import com.cibnvideo.tcmalladmin.bmsapi.AfterSaleApi;
import com.cibnvideo.tcmalladmin.model.bean.AfterSaleVo;
import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.ResultData;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.hqtc.common.utils.Tools.getThreadResultData;


@Component
public class AfterSaleFallbackFactory implements FallbackFactory<AfterSaleApi> {
    private Logger logger  = LoggerFactory.getLogger(getClass());
    @Override
    public AfterSaleApi create(Throwable throwable) {
        return new AfterSaleApi(){

            @Override
            public ResultData<Result<AfterSaleVo>> listManagerAfterSale(Map<String, Object> params) {
                return errorResponse();
            }

            private ResultData errorResponse(){
                ResultData result = getThreadResultData();
                result.setMsg("afterSale interface failed");
                result.setError(ErrorCode.SERVER_EXCEPTION);
                return result;
            }
        };
    }
}
