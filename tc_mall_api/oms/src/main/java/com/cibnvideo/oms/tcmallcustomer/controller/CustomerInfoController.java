package com.cibnvideo.oms.tcmallcustomer.controller;

import com.cibnvideo.oms.config.Router;
import com.cibnvideo.oms.tcmallcustomer.bean.CustomerInfo;
import com.cibnvideo.oms.tcmallcustomer.service.CustomerInfoService;
import com.hqtc.common.response.ResultData;
import com.hqtc.common.utils.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class CustomerInfoController {

    @Autowired
    CustomerInfoService customerInfoService;

    @GetMapping(Router.V1_OMS_CUSTOMERINFO_GET)
    public ResultData getCustomerInfo(@RequestParam("venderId") int venderId){
        ResultData resultData = Tools.getThreadResultData();
        CustomerInfo customerInfo = customerInfoService.get(venderId);
        if(customerInfo != null){
            resultData.setData(customerInfoService.get(venderId));
        }
        return resultData;
    }

    @PostMapping(Router.V1_OMS_CUSTOMERINFO_UPDATE)
    public ResultData updateCustomerInfo(@RequestBody Map<String,Object> params){
        ResultData resultData = Tools.getThreadResultData();
        resultData.setData(customerInfoService.update(params));
        return resultData;
    }

    @PostMapping(Router.V1_OMS_CUSTOMERINFO_ADD)
    public ResultData addCustomerInfo(@RequestBody Map<String,Object> params){
        ResultData resultData = Tools.getThreadResultData();
        resultData.setData(customerInfoService.add(params));
        return resultData;
    }

    @PostMapping(Router.V1_OMS_CUSTOMERINFO_DELETE)
    public ResultData deleteCustomerInfo(@RequestParam("venderId") int venderId){
        ResultData resultData = Tools.getThreadResultData();
        resultData.setData(customerInfoService.delete(venderId));
        return resultData;
    }
}
