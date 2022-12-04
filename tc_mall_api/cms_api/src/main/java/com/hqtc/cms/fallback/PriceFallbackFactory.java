package com.hqtc.cms.fallback;

import com.hqtc.cms.model.bean.search.SellPriceResult;
import com.hqtc.cms.model.service.PriceService;
import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.ResultData;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.hqtc.common.utils.Tools.getThreadResultData;

@Component
public class PriceFallbackFactory implements FallbackFactory<PriceService> {
    private Logger logger  = LoggerFactory.getLogger(getClass());
    @Override
    public PriceService create(Throwable throwable) {
        return new PriceService() {
            @Override
            public ResultData<List<SellPriceResult>> getBatchPrices(Long venderId, List<Long> skus){
                return errorResponse();
            }
        };
    }

    private ResultData errorResponse() {
        ResultData result = getThreadResultData();
        result.setMsg("FeignClient Request OMS Failed");
        result.setError(ErrorCode.SERVER_EXCEPTION);
        return result;
    }
}
