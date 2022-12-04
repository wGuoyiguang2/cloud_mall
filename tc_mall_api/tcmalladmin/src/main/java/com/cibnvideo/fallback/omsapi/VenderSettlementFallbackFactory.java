package com.cibnvideo.fallback.omsapi;

import com.cibnvideo.common.entity.VenderSettlement;
import com.cibnvideo.common.utils.Result;
import com.cibnvideo.tcmalladmin.model.bean.SellPriceResult;
import com.cibnvideo.tcmalladmin.model.bean.VenderInvoiceManagerVo;
import com.cibnvideo.tcmalladmin.model.bean.VenderOrderManagerVo;
import com.cibnvideo.tcmalladmin.omsapi.VenderSettlementApi;
import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.ResultData;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static com.hqtc.common.utils.Tools.getThreadResultData;


@Component
public class VenderSettlementFallbackFactory implements FallbackFactory<VenderSettlementApi> {
    private Logger logger  = LoggerFactory.getLogger(getClass());
    @Override
    public VenderSettlementApi create(Throwable throwable) {
        return new VenderSettlementApi(){

            @Override
            public ResultData<Integer> addVenderSettlement(VenderSettlement venderSettlement) {
                return errorResponse("oms addVenderSettlement FeignClient Request Failed");
            }

            @Override
            public ResultData<VenderSettlement> getVenderSettlement(Long venderId) {
                return errorResponse("oms getVenderSettlement FeignClient Request Failed");
            }

            @Override
            public ResultData<Integer> updateVenderSettlement(VenderSettlement venderSettlement) {
                return errorResponse("oms updateVenderSettlement FeignClient Request Failed");
            }

            @Override
            public ResultData<Integer> removeVenderSettlement(Long venderId) {
                return errorResponse("oms removeVenderSettlement FeignClient Request Failed");
            }

            @Override
            public ResultData<List<SellPriceResult>> getBatchPrice(Integer venderId, List<Long> skus) {
                return errorResponse("oms getBatchPrice FeignClient Request Failed");
            }

            @Override
            public ResultData<SellPriceResult> getPrice(Integer venderId, Long sku) {
                return errorResponse("oms getPrice FeignClient Request Failed");
            }

            @Override
            public ResultData<BigDecimal> balanceGet(Integer venderId) {
                return errorResponse("oms balanceGet FeignClient Request Failed");
            }
            @Override
            public ResultData<Integer> balanceAdd(Integer venderId, BigDecimal value) {
                return errorResponse("oms balanceAdd FeignClient Request Failed");
            }
            @Override
            public ResultData<Integer> balanceReduce(Integer venderId, BigDecimal value) {
                return errorResponse("oms balanceReduce FeignClient Request Failed");
            }

            @Override
            public Result<VenderOrderManagerVo> listVenderOrderManager(Map<String, Object> params) {
                errorResponse("oms listVenderOrderManager FeignClient Request Failed");
                return null;
            }

            @Override
            public Result<VenderOrderManagerVo> listVenderSettlementByParams(Map<String, Object> params) {
                errorResponse("oms listVenderSettlementByParams FeignClient Request Failed");
                return null;
            }

            @Override
            public Result<VenderInvoiceManagerVo> listVenderInvoiceManager(Map<String, Object> params) {
                errorResponse("oms listVenderInvoiceManager FeignClient Request Failed");
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
