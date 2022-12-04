package com.cibnvideo.oms.tcmallcustomer.controller;

import com.cibnvideo.oms.config.Router;
import com.cibnvideo.oms.tcmallcustomer.bean.ContactUsBean;
import com.cibnvideo.oms.tcmallcustomer.service.ContactUsService;
import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.ResultData;
import com.hqtc.common.utils.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/7/10 21:07
 */
@RestController
public class ContactUsController {
    @Autowired
    private ContactUsService contactUsService;
    @GetMapping(Router.V1_OMS_CONTACTUS_GET_BY_VENDERID)
    public ResultData getContactUsByVenderId(@RequestParam("venderId") Long venderId){
        ResultData<List<ContactUsBean>> resultData= Tools.getThreadResultData();
        List<ContactUsBean> contactUsBeanList=contactUsService.getByVenderId(venderId);
        resultData.setData(contactUsBeanList);
        resultData.setMsg("OK");
        return resultData;
    }
    @GetMapping(value = Router.V1_OMS_CONTACTUS_GET_BY_ID)
    ResultData<ContactUsBean> getContactUsById(@RequestParam("id") Integer id){
        ResultData<ContactUsBean> resultData= Tools.getThreadResultData();
        ContactUsBean contactUsBean=contactUsService.getById(id);
        resultData.setMsg("OK");
        resultData.setData(contactUsBean);
        return resultData;
    }

    @PostMapping(value=Router.V1_OMS_CONTACTUS_UPDATE)
    ResultData<Integer> update(@RequestBody ContactUsBean contactUsBean){
        ResultData<Integer> resultData= Tools.getThreadResultData();
        Integer result=contactUsService.update(contactUsBean);
        resultData.setData(result);
        if(result>0){
            resultData.setError(ErrorCode.SUCCESS);
            resultData.setMsg("OK");
        }else{
            resultData.setError(ErrorCode.WRITE_DATA_ERROR);
            resultData.setMsg("Failed");
        }
        return resultData;
    }
    @PostMapping(Router.V1_OMS_CONTACTUS_ADD)
    ResultData add(@RequestBody ContactUsBean contactUsBean){
        ResultData resultData=Tools.getThreadResultData();
        int result=contactUsService.add(contactUsBean);
        if(result>0){
            resultData.setError(ErrorCode.SUCCESS);
            resultData.setMsg("OK");
        }else{
            resultData.setError(ErrorCode.WRITE_DATA_ERROR);
            resultData.setMsg("Failed");
        }
        return resultData;
    }
}
