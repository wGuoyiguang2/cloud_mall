package com.cibnvideo.fallback.omsapi;

import com.cibnvideo.tcmalladmin.model.bean.ProductRemove;
import com.cibnvideo.tcmalladmin.omsapi.ProductRemoveApi;
import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.DataList;
import com.hqtc.common.response.ResultData;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import static com.hqtc.common.utils.Tools.getThreadResultData;

/**
 * @Author: likai
 * @description description
 * @Date: 18-9-11 下午3:37
 */
@Component
public class ProductRemoveFallbackFactory implements FallbackFactory<ProductRemoveApi> {
    @Override
    public ProductRemoveApi create(Throwable throwable) {
        return new ProductRemoveApi() {
            @Override
            public ResultData<DataList<List<ProductRemove>>> list(Map<String, Object> params) {
                return errorResponse("ProductRemoveApi list fallback");
            }

            @Override
            public ResultData<List<Long>> listSkusByVenderId(Integer venderId) {
                return errorResponse("ProductRemoveApi listSkusByVenderId fallback");
            }

            @Override
            public ResultData<ProductRemove> get(Integer id) {
                return errorResponse("ProductRemoveApi get fallback");
            }

            @Override
            public ResultData<Integer> count(Map<String, Object> params) {
                return errorResponse("ProductRemoveApi count fallback");
            }

            @Override
            public ResultData<Integer> add(ProductRemove productRemove) {
                return errorResponse("ProductRemoveApi add fallback");
            }

            @Override
            public ResultData<Integer> remove(Integer id) {
                return errorResponse("ProductRemoveApi remove fallback");
            }

            @Override
            public ResultData<Integer> batchRemove(Map<String, Object> params) {
                return errorResponse("ProductRemoveApi batchRemove fallback");
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
