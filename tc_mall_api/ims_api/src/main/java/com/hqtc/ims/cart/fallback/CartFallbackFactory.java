package com.hqtc.ims.cart.fallback;

import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.ResultData;
import com.hqtc.ims.cart.model.bean.CartProductListBean;
import com.hqtc.ims.cart.service.CartSearchService;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


import static com.hqtc.common.utils.Tools.getThreadResultData;

/**
 * Created by sunjianqiang  18-9-21
 */
@Component
public class CartFallbackFactory implements FallbackFactory<CartSearchService> {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public CartSearchService create(Throwable throwable) {
        return new CartSearchService() {
            @Override
            public ResultData idsQuery(Integer venderId, String skus, Integer pageSize) {
                return errorResponse();
            }
        };

    }
    private ResultData errorResponse() {
            ResultData result = getThreadResultData();
            result.setMsg("FeignClient Request search Failed");
            result.setError(ErrorCode.SERVER_EXCEPTION);
            return result;
        }
}
