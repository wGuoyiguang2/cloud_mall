package com.hqtc.searchtask.fallback.omsapi;

import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.ResultData;
import com.hqtc.searchtask.model.bean.SellPriceResult;
import com.hqtc.searchtask.omsapi.VenderSettlementApi;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.hqtc.common.utils.Tools.getThreadResultData;

/**
 * @Author: likai
 * @description description
 * @Date: 19-1-22 下午3:38
 */
@Component
public class VenderSettlementFallbackFactory implements FallbackFactory<VenderSettlementApi> {
    @Override
    public VenderSettlementApi create(Throwable throwable) {
        return new VenderSettlementApi() {
            @Override
            public ResultData<List<SellPriceResult>> getBatchPrice(Integer venderId, List<Long> skus) {
                return errorResponse("oms feign client: venderSettlement getBatchPrice failed");
            }

            @Override
            public ResultData<SellPriceResult> getPrice(Integer venderId, Long sku) {
                return errorResponse("oms feign client: venderSettlement getPrice failed");
            }

            @Override
            public ResultData<List<Integer>> listVenderId() {
                return errorResponse("oms feign client: venderSettlement listVenderId failed");
            }
        };
    }

    private ResultData errorResponse(String msg){
        ResultData result = getThreadResultData();
        result.setMsg(msg);
        result.setError(ErrorCode.SERVER_EXCEPTION);
        return result;
    }
}
