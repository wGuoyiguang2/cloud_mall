package com.hqtc.bms.service.rpc;

import com.hqtc.bms.model.params.*;
import com.hqtc.bms.service.AfterSaleJdService;
import com.hqtc.common.config.ErrorCode;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by laiqingchuang on 18-9-21 .
 */
@Component
public class AfterSaleJdServiceFallback implements FallbackFactory<AfterSaleJdService> {

    @Override
    public AfterSaleJdService create(Throwable throwable) {
        return new AfterSaleJdService() {
            @Override
            public Map isCanAfterSale(Long jdOrderId, Long skuId) {
                return errorResponse();
            }

            @Override
            public Map getJdMethod(Long jdOrderId, Long skuId) {
                return errorResponse();
            }

            @Override
            public Map getJdCustomerType(Long jdOrderId, Long skuId) {
                return errorResponse();
            }

            @Override
            public Map getServiceListPage(Long jdOrderId, Integer pageIndex, Integer pageSize) {
                return errorResponse();
            }

            @Override
            public Map getServiceDetail(ServiceRequestParams requestParams) {
                return errorResponse();
            }

            @Override
            public Map cancelService(ServiceRequestParams requestParams) {
                return errorResponse();
            }

            @Override
            public Map sendSku(Integer afsServiceId, BigDecimal freightMoney, String expressCompany, String deliverDate, String expressCode) {
                return errorResponse();
            }

            @Override
            public Map applyAfterSale(ApplyAfterSaleRequestParams requestParams) {
                return errorResponse();
            }

            @Override
            public Map getOrderTrack(OrderTrackRequestParams param) {
                return errorResponse();
            }

            private Map errorResponse() {
                Map<String, Object> map = new HashMap<>();
                map.put("msg","FeignClient Request jd_service Failed");
                map.put("error",ErrorCode.SERVER_EXCEPTION);
                return map;
            }
        };
    }
}