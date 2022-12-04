package com.hqtc.bms.service;

import com.hqtc.bms.config.Router;
import com.hqtc.bms.model.params.OrderInfoResponseParams;
import com.hqtc.bms.model.params.OrderMainBean;
import com.hqtc.bms.service.rpc.ImsAddressServiceFallback;
import com.hqtc.common.config.FeignClientService;
import com.hqtc.common.response.ResultData;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * description:级联地址信息接口
 * Created by laiqingchuang on 18-7-12 .
 */
@FeignClient(name=FeignClientService.IMSAPI,fallbackFactory = ImsAddressServiceFallback.class)
public interface ImsAddressService {

    @RequestMapping(method = RequestMethod.GET, value = Router.ROUTER_ADDRESS_INFO)
    ResultData<OrderInfoResponseParams> getAddressInfo(@RequestParam("countyId") Integer countyId,@RequestParam("townId") Integer townId);

    @RequestMapping(method = RequestMethod.GET, value = Router.ROUTER_INVOICE_DETAIL)
    ResultData<OrderMainBean> getInvoiceInfo(@RequestParam("userId") Integer userId);

}
