package com.cibnvideo.jdsynctask.fallback.thirdpartapi;

import com.cibnvideo.jdsynctask.model.JDSitePriceBean;
import com.cibnvideo.jdsynctask.thirdpartapi.ThirdPartApi;
import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.ResultData;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.hqtc.common.utils.Tools.getThreadResultData;

@Component
public class ThirdPartFallbackFactory implements FallbackFactory<ThirdPartApi> {

    @Override
    public ThirdPartApi create(Throwable throwable) {
        return new ThirdPartApi() {
            @Override
            public ResultData<List<JDSitePriceBean>> listJDSitePrice(List<Long> skuList) {
                return errorResponse("ThirdPart service feign failed: listJDSitePrice");
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
