package com.cibnvideo.fallback.bmsapi;

import com.cibnvideo.common.utils.Result;
import com.cibnvideo.tcmalladmin.bmsapi.SalesApi;
import com.cibnvideo.tcmalladmin.model.bean.InvoiceInfoVo;
import com.cibnvideo.tcmalladmin.model.bean.InvoiceManagerVo;
import com.cibnvideo.tcmalladmin.model.bean.SalesAmountVo;
import com.cibnvideo.tcmalladmin.model.bean.SalesManagerVo;
import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.ResultData;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.hqtc.common.utils.Tools.getThreadResultData;


@Component
public class SalesFallbackFactory implements FallbackFactory<SalesApi> {
    private Logger logger  = LoggerFactory.getLogger(getClass());
    @Override
    public SalesApi create(Throwable throwable) {
        return new SalesApi(){

            @Override
            public ResultData<SalesAmountVo> getSalesAmount(Long venderId) {
                return errorResponse("bms getSalesAmount failed");
            }

            @Override
            public ResultData<Result<SalesManagerVo>> salesManagerList(Map<String, Object> params) {
                return errorResponse("bms salesManagerList failed");
            }

            @Override
            public ResultData<SalesManagerVo> getSalesManagerDetail(Long sku) {
                return errorResponse("bms getSalesManagerDetail failed");
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
