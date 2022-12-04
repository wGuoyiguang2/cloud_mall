package com.cibnvideo.thirdpart.feign.fallback;

import com.cibnvideo.thirdpart.feign.JdSyncFeign;
import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.ResultData;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.hqtc.common.utils.Tools.getThreadResultData;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2019/1/21 17:23
 */
@Component
public class JdSyncFallbackFactory implements FallbackFactory<JdSyncFeign> {

    @Override
    public JdSyncFeign create(Throwable throwable) {
        return new JdSyncFeign() {

            @Override
            public ResultData<Integer> getCount(Map<String, Object> params) {
                return errorResponse();
            }

            @Override
            public ResultData<String> getSkus(Map<String, Object> params) {
                return errorResponse();
            }

            private ResultData errorResponse() {
                ResultData result = getThreadResultData();
                result.setMsg("FeignClient Request JdSync Failed");
                result.setError(ErrorCode.SERVER_EXCEPTION);
                return result;
            }

        };
    }
}
