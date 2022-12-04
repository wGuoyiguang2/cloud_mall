package com.cibnvideo.tcmalladmin.bmsapi;

import com.cibnvideo.common.utils.Result;
import com.cibnvideo.config.Router;
import com.cibnvideo.fallback.bmsapi.InvoiceFallbackFactory;
import com.cibnvideo.tcmalladmin.model.bean.InvoiceInfoVo;
import com.cibnvideo.tcmalladmin.model.bean.InvoiceManagerVo;
import com.hqtc.common.config.FeignClientService;
import com.hqtc.common.response.ResultData;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/7/20 20:39
 */
@FeignClient(value = FeignClientService.BMSAPI,fallbackFactory = InvoiceFallbackFactory.class)
public interface InvoiceApi {

    @PostMapping(Router.BMS_INVOICE_MANAGER_LIST)
    Result<InvoiceManagerVo> invoiceList(@RequestParam Map<String, Object> params);
    @PostMapping(Router.BMS_INVOICE_INFO_MANAGER_GET)
    InvoiceInfoVo getInvoiceInfo(@RequestParam("venderid") Long venderId);
    @PostMapping(Router.BMS_INVOICE_MANAGER_REMOVE)
    ResultData removeInvoices(@RequestParam("invoiceIds") String invoiceIds);
}
