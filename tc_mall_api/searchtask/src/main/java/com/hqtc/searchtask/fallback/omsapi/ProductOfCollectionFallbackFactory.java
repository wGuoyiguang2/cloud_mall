package com.hqtc.searchtask.fallback.omsapi;

import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.DataList;
import com.hqtc.common.response.ResultData;
import com.hqtc.searchtask.model.bean.ProductOfCollection;
import com.hqtc.searchtask.omsapi.ProductOfCollectionApi;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import static com.hqtc.common.utils.Tools.getThreadResultData;

/**
 * @Author: likai
 * @description description
 * @Date: 19-1-22 下午3:32
 */
@Component
public class ProductOfCollectionFallbackFactory implements FallbackFactory<ProductOfCollectionApi> {
    @Override
    public ProductOfCollectionApi create(Throwable throwable) {
        return new ProductOfCollectionApi() {
            @Override
            public ResultData<DataList<List<ProductOfCollection>>> listProductOfCollection(Map<String, Object> params) {
                return errorResponse("oms feign client: listProductOfCollection failed");
            }

            @Override
            public ResultData<Integer> getCollectionIdBySku(Integer venderId, Long sku) {
                return errorResponse("oms feign client: getCollectionIdBySku failed");
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
