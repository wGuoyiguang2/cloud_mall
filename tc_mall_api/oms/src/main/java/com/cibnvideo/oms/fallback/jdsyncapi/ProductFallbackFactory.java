package com.cibnvideo.oms.fallback.jdsyncapi;

import com.cibnvideo.oms.bean.ProductInfo;
import com.cibnvideo.oms.bean.SellPriceResult;
import com.cibnvideo.oms.jdsyncapi.ProductApi;
import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.ResultData;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.hqtc.common.utils.Tools.getThreadResultData;

/**
 * Created by makuan on 18-6-30.
 */
@Component
public class ProductFallbackFactory implements FallbackFactory<ProductApi> {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public ProductApi create(Throwable throwable) {
        return new ProductApi() {

            @Override
            public ResultData<List<SellPriceResult>> getBatchPrice(List<Long> skus) {
                return errorResponse();
            }

            @Override
            public ResultData getPrice(Long sku) {
                return errorResponse();
            }

            @Override
            public ResultData<List<String>> getBrandNamesBySkus(List<Long> skus) {
                return errorResponse();
            }

            @Override
            public ResultData<ProductInfo> getProductInfo(Long sku) {
                return errorResponse();
            }

            @Override
            public ResultData<List<ProductInfo>> getProductInfoBySkus(List<Long> skus) {
                return errorResponse();
            }


            private ResultData errorResponse() {
                ResultData result = getThreadResultData();
                result.setMsg("FeignClient Request JDSYNC Failed");
                result.setError(ErrorCode.SERVER_EXCEPTION);
                return result;
            }

        };
    }
}
