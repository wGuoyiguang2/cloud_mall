package com.cibnvideo.fallback.bmsapi;

import com.cibnvideo.common.utils.Result;
import com.cibnvideo.tcmalladmin.bmsapi.OrderApi;
import com.cibnvideo.tcmalladmin.model.bean.OrderManagerVo;
import com.cibnvideo.tcmalladmin.model.bean.OrderRefundVo;
import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.ResultData;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import static com.hqtc.common.utils.Tools.getThreadResultData;


@Component
public class OrderFallbackFactory implements FallbackFactory<OrderApi> {
    private Logger logger  = LoggerFactory.getLogger(getClass());
    @Override
    public OrderApi create(Throwable throwable) {
        return new OrderApi(){

            @Override
            public Result<OrderManagerVo> orderList(Map<String, Object> params) {
                errorResponse("interface orderList failed");
                return null;
            }

            @Override
            public int countOrderByVenderId(Long venderId) {
                errorResponse("bms interface countOrderByVenderId failed");
                return 0;
            }

            @Override
            public ResultData<List<Map<String, Long>>> getProductSalesVolume(String products) {
                return errorResponse("bms interface getProductSalesVolume");
            }

            @Override
            public ResultData<Result<OrderRefundVo>> listOrderRefund(Map<String, Object> params) {
                return errorResponse("bms interface listOrderRefund");
            }

            @Override
            public ResultData refundUserRetry(String orderSn, String eventId) {
                return null;
            }

            @Override
            public ResultData refundVenderRetry(String orderSn, String eventId) {
                return null;
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
