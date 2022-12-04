package com.hqtc.bms.service.rpc;

import com.hqtc.bms.service.ImsAddressService;
import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.ResultData;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;
import static com.hqtc.common.utils.Tools.getThreadResultData;

/**
 * Created by laiqingchuang on 18-9-21 .
 */
@Component
public class ImsAddressServiceFallback implements FallbackFactory<ImsAddressService> {
    @Override
    public ImsAddressService create(Throwable throwable) {
        return new ImsAddressService() {
            @Override
            public ResultData getAddressInfo(Integer countyId, Integer townId) {
                return errorResponse();
            }

            @Override
            public ResultData getInvoiceInfo(Integer userId) {
                return errorResponse();
            }

            private ResultData errorResponse() {
                ResultData result = getThreadResultData();
                result.setMsg("FeignClient Request ims_api Failed");
                result.setError(ErrorCode.SERVER_EXCEPTION);
                return result;
            }
        };
    }
}
