package com.cibnvideo.oms.tcmallcustomer.controller;

import com.cibnvideo.oms.config.Router;
import com.cibnvideo.oms.tcmallcustomer.bean.VenderPayment;
import com.cibnvideo.oms.tcmallcustomer.service.VenderPaymentService;
import com.hqtc.common.response.ResultData;
import com.hqtc.common.utils.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class VenderPaymentController {

    @Autowired
    VenderPaymentService venderPaymentService;

    @PostMapping(Router.V1_OMS_VENDER_PAYMENT_BATCHSAVE)
    public ResultData addVenderPayMent(@RequestBody List<VenderPayment> venderPayment){
        ResultData resultData = Tools.getThreadResultData();
        resultData.setData(venderPaymentService.batchSave(venderPayment));
        return resultData;
    }

    @PostMapping(Router.V1_OMS_VENDER_PAYMENT_GET)
    public ResultData getVenderPayMents(@RequestBody Long venderId){
        ResultData resultData = Tools.getThreadResultData();
        resultData.setData(venderPaymentService.get(venderId));
        return resultData;
    }

    @PostMapping(Router.V1_OMS_VENDER_PAYMENT_UPDATE)
    public ResultData updateVenderPayMents(@RequestBody VenderPayment venderPayment){
        ResultData resultData = Tools.getThreadResultData();
        resultData.setData(venderPaymentService.update(venderPayment));
        return resultData;
    }

    @PostMapping(Router.V1_OMS_VENDER_PAYMENT_REMOVE)
    public ResultData removeVenderPayMents(@RequestBody VenderPayment venderPayment){
        ResultData resultData = Tools.getThreadResultData();
        resultData.setData(venderPaymentService.remove(venderPayment));
        return resultData;
    }

    @PostMapping(Router.V1_OMS_VENDER_PAYMENT_COUNT)
    public ResultData countVenderPayMents(@RequestBody Map<String,Object> params){
        ResultData resultData = Tools.getThreadResultData();
        resultData.setData(venderPaymentService.count(params));
        return resultData;
    }

}
