package com.hqtc.cms.fallback;

import com.hqtc.cms.model.bean.flashsale.GetOrderDetailResponse;
import com.hqtc.cms.model.bean.flashsale.OrderParams;
import com.hqtc.cms.model.service.BmsService;
import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.ResultData;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import static com.hqtc.common.utils.Tools.getThreadResultData;

/**
 * Created by makuan on 18-6-30.
 */
@Component
public class BmsFallbackFactory implements FallbackFactory<BmsService>{

    @Override
    public BmsService create(Throwable throwable) {
        return new BmsService(){

            @Override
            public ResultData createOrder(OrderParams vo) {
                return errorResponse("bms createOrder failed");
            }

            @Override
            public ResultData<GetOrderDetailResponse> orderDetail(String orderNo, String mac,String cookie) {
                return errorResponse("bms orderDetail failed");
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
