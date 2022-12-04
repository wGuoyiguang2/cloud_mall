package com.hqtc.bms.service.rpc;

import com.hqtc.bms.model.params.RefundUserParams;
import com.hqtc.bms.service.BmsOrderSercice;
import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.ResultData;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;
import static com.hqtc.common.utils.Tools.getThreadResultData;

/**
 * Created by laiqingchuang on 18-9-21 .
 */
@Component
public class BmsOrderSerciceFallback implements FallbackFactory<BmsOrderSercice> {

    @Override
    public BmsOrderSercice create(Throwable throwable) {
        return new BmsOrderSercice() {
            @Override
            public ResultData getOrderInfo(String tradeNo, Long sku) {
                return errorResponse();
            }

            @Override
            public ResultData refund(RefundUserParams refundParam) {
                return errorResponse();
            }

            private ResultData errorResponse() {
                ResultData result = getThreadResultData();
                result.setMsg("FeignClient Request bms_api Failed");
                result.setError(ErrorCode.SERVER_EXCEPTION);
                return result;
            }
        };
    }
}
