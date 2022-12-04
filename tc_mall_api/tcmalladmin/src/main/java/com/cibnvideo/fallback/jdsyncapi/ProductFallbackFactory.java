package com.cibnvideo.fallback.jdsyncapi;

import com.cibnvideo.tcmalladmin.jdsyncapi.ProductApi;
import com.cibnvideo.tcmalladmin.model.bean.ProductInfo;
import com.cibnvideo.tcmalladmin.model.bean.SalesManagerProductDetailVo;
import com.cibnvideo.tcmalladmin.model.bean.SalesManagerVo;
import com.cibnvideo.tcmalladmin.model.bean.SellPriceResult;
import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.DataList;
import com.hqtc.common.response.ResultData;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import static com.hqtc.common.utils.Tools.getThreadResultData;

@Component
public class ProductFallbackFactory implements FallbackFactory<ProductApi> {
    private Logger logger  = LoggerFactory.getLogger(getClass());
    @Override
    public ProductApi create(Throwable throwable) {
        return new ProductApi(){

            @Override
            public ResultData<DataList<List<ProductInfo>>> detailInfoList(Map<String, Object> params) {
                return errorResponse("jdsync detailInfoList FeignClient Request Failed");
            }

            @Override
            public ResultData<DataList<List<ProductInfo>>> detailSearchList(Map<String, Object> params) {
                return errorResponse("jdsync detailSearchList FeignClient Request Failed");
            }

            @Override
            public ResultData<ProductInfo> detailInfo(long sku) {
                return errorResponse("jdsync detailInfo FeignClient Request Failed");
            }

            @Override
            public ResultData<SellPriceResult> getPrice(long sku) {
                return errorResponse("jdsync getPriceBatch FeignClient Request Failed");
            }

            @Override
            public ResultData<List<String>> getBrandNamesByCat2(Map<String, Object> params) {
                return errorResponse("jdsync getBrandNamesByCat2 FeignClient Request Failed");
            }

            @Override
            public ResultData<SalesManagerProductDetailVo> salesManagerProductDetail(Long sku) {
                return errorResponse("jdsync salesManagerProductDetail FeignClient Request Failed");
            }

            @Override
            public ResultData<DataList<List<SalesManagerVo>>> salesManagerProductDetailList(Map<String, Object> params) {
                return errorResponse("jdsync salesManagerProductDetailList FeignClient Request Failed");
            }

            @Override
            public ResultData<List<ProductInfo>> getProductInfoBySkus(List<Long> skus) {
                return errorResponse("jdsync getProductInfoBySkus FeignClient Request Failed");
            }

            @Override
            public ResultData<Integer> productVideoAdd(Long sku, String videoPath) {
                return errorResponse("jdsync productVideoAdd FeignClient Request Failed");
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
