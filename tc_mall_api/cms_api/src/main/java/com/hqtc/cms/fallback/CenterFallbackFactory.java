package com.hqtc.cms.fallback;

import com.hqtc.cms.model.service.CenterService;
import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.ResultData;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import static com.hqtc.common.utils.Tools.getThreadResultData;

/**
 * Created by makuan on 18-6-30.
 */
@Component
public class CenterFallbackFactory implements FallbackFactory<CenterService>{

    @Override
    public CenterService create(Throwable throwable) {
        return new CenterService(){
            @Override
            public ResultData getContact(int venderId) {
                return errorResponse();
            }

            @Override
            public ResultData getAssistance(int venderId) {
                return errorResponse();
            }

            @Override
            public ResultData getAssistDetail(int assistId){
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
