package com.hqtc.searchtask.fallback.jdsyncapi;



import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.DataList;
import com.hqtc.common.response.ResultData;
import com.hqtc.searchtask.jdsyncapi.ProductApi;
import com.hqtc.searchtask.model.bean.ProductDo;
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
            public ResultData<ProductDo> getProductForEs(Long sku) {
                return errorResponse("getProductForEs failed");
            }

            @Override
            public ResultData<DataList<List<ProductDo>>> listProductForEs(Map<String, Object> params) {
                return errorResponse("listProductForEs failed");
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
