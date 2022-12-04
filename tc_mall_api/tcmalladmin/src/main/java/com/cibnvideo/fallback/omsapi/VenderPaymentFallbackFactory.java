package com.cibnvideo.fallback.omsapi;

import com.cibnvideo.common.entity.VenderPayment;
import com.cibnvideo.tcmalladmin.omsapi.VenderPaymentApi;
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
public class VenderPaymentFallbackFactory implements FallbackFactory<VenderPaymentApi> {
    private Logger logger  = LoggerFactory.getLogger(getClass());
    @Override
    public VenderPaymentApi create(Throwable throwable) {
        return new VenderPaymentApi(){

            @Override
            public ResultData<Integer> batchSaveVenderPayment(List<VenderPayment> venderPayment) {
                return errorResponse("oms batchSaveVenderPayment FeignClient Request Failed");
            }

            @Override
            public ResultData<List<VenderPayment>> getVenderPayments(Long venderId) {
                return errorResponse("oms getVenderPayments FeignClient Request Failed");
            }

            @Override
            public ResultData<Integer> updateVenderPayments(VenderPayment venderPayment) {
                return errorResponse("oms updateVenderPayments FeignClient Request Failed");
            }

            @Override
            public ResultData<Integer> removeVenderPayments(VenderPayment venderPayment) {
                return errorResponse("oms removeVenderPayments FeignClient Request Failed");
            }

            @Override
            public ResultData<Integer> countVenderPayments(Map<String, Object> params) {
                return errorResponse("oms countVenderPayments FeignClient Request Failed");
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
