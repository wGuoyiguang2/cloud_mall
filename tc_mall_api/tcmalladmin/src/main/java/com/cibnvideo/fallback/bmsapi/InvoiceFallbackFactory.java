package com.cibnvideo.fallback.bmsapi;

import com.cibnvideo.common.utils.Result;
import com.cibnvideo.tcmalladmin.bmsapi.InvoiceApi;
import com.cibnvideo.tcmalladmin.model.bean.InvoiceInfoVo;
import com.cibnvideo.tcmalladmin.model.bean.InvoiceManagerVo;
import com.cibnvideo.tcmalladmin.model.bean.OrderManagerVo;
import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.ResultData;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.hqtc.common.utils.Tools.getThreadResultData;


@Component
public class InvoiceFallbackFactory implements FallbackFactory<InvoiceApi> {
    private Logger logger  = LoggerFactory.getLogger(getClass());
    @Override
    public InvoiceApi create(Throwable throwable) {
        return new InvoiceApi(){


            @Override
            public Result<InvoiceManagerVo> invoiceList(Map<String, Object> params) {
                errorResponse("bms invoiceList failed");
                return null;
            }

            @Override
            public InvoiceInfoVo getInvoiceInfo(Long venderId) {
                errorResponse("bms getInvoiceInfo failed");
                return null;
            }

            @Override
            public ResultData removeInvoices(String invoiceIds) {
                return errorResponse("bms removeInvoices failed");
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
