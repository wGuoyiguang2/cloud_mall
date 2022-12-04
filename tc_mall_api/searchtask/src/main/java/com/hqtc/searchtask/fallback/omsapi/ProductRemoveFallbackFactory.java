package com.hqtc.searchtask.fallback.omsapi;

import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.ResultData;
import com.hqtc.searchtask.omsapi.ProductRemoveApi;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.hqtc.common.utils.Tools.getThreadResultData;

/**
 * @Author: likai
 * @description description
 * @Date: 19-1-22 下午3:36
 */
@Component
public class ProductRemoveFallbackFactory implements FallbackFactory<ProductRemoveApi> {

    @Override
    public ProductRemoveApi create(Throwable throwable) {
        return new ProductRemoveApi() {
            @Override
            public ResultData<Integer> count(Map<String, Object> params) {
                return errorResponse("oms feign client: product remove count failed");
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
