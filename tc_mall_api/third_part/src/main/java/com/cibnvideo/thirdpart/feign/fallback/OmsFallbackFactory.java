package com.cibnvideo.thirdpart.feign.fallback;

import com.cibnvideo.thirdpart.feign.OmsFeign;
import com.cibnvideo.thirdpart.model.bean.ProductRemove;
import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.ResultData;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.hqtc.common.utils.Tools.getThreadResultData;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2019/1/21 17:23
 */
@Component
public class OmsFallbackFactory implements FallbackFactory<OmsFeign> {

    @Override
    public OmsFeign create(Throwable throwable) {
        return new OmsFeign() {
            @Override
            public ResultData removeProducts(List<ProductRemove> productRemoves) {
                return errorResponse();
            }

            @Override
            public ResultData addProducts(List<ProductRemove> productRemoves) {
                return errorResponse();
            }

            private ResultData errorResponse() {
                ResultData result = getThreadResultData();
                result.setMsg("FeignClient Request OMS Failed");
                result.setError(ErrorCode.SERVER_EXCEPTION);
                return result;
            }

        };
    }
}
