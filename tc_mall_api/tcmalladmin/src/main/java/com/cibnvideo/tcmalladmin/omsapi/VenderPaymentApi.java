package com.cibnvideo.tcmalladmin.omsapi;

import com.cibnvideo.common.entity.VenderPayment;
import com.cibnvideo.config.Router;
import com.cibnvideo.fallback.omsapi.VenderPaymentFallbackFactory;
import com.hqtc.common.config.FeignClientService;
import com.hqtc.common.response.ResultData;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(value = FeignClientService.OMSAPI, fallbackFactory = VenderPaymentFallbackFactory.class)
public interface VenderPaymentApi {
    @RequestMapping(value = Router.V1_OMS_VENDER_PAYMENT_BATCHSAVE, method = RequestMethod.POST)
    ResultData<Integer> batchSaveVenderPayment(List<VenderPayment> venderPayment);

    @RequestMapping(value = Router.V1_OMS_VENDER_PAYMENT_GET, method = RequestMethod.POST)
    ResultData<List<VenderPayment>> getVenderPayments(Long venderId);

    @RequestMapping(value = Router.V1_OMS_VENDER_PAYMENT_UPDATE, method = RequestMethod.POST)
    ResultData<Integer> updateVenderPayments(VenderPayment venderPayment);

    @RequestMapping(value = Router.V1_OMS_VENDER_PAYMENT_REMOVE, method = RequestMethod.POST)
    ResultData<Integer> removeVenderPayments(VenderPayment venderPayment);

    @RequestMapping(value = Router.V1_OMS_VENDER_PAYMENT_COUNT, method = RequestMethod.POST)
    ResultData<Integer> countVenderPayments(Map<String, Object> params);
}
