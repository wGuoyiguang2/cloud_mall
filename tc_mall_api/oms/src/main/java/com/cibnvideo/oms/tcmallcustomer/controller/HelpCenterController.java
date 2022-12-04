package com.cibnvideo.oms.tcmallcustomer.controller;

import com.cibnvideo.oms.config.Router;
import com.cibnvideo.oms.tcmallcustomer.bean.ExplainBean;
import com.cibnvideo.oms.tcmallcustomer.bean.HelpCenter;
import com.cibnvideo.oms.tcmallcustomer.bean.HelpCenterInfo;
import com.cibnvideo.oms.tcmallcustomer.service.HelpCenterInfoService;
import com.cibnvideo.oms.tcmallcustomer.service.HelpCenterService;
import com.hqtc.common.response.ResultData;
import com.hqtc.common.utils.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class HelpCenterController {
    @Autowired
    HelpCenterService helpCenterService;
    @Autowired
    HelpCenterInfoService helpCenterInfoService;

    @GetMapping(Router.V1_OMS_HELPCENTER_GET)
    public ResultData getHelpCenter(@RequestParam("venderId") int venderId) {
        ResultData resultData = Tools.getThreadResultData();
        List<HelpCenter> helpCenters= helpCenterService.get(venderId);
        if(helpCenters != null && helpCenters.size()!=0)
        {
            HelpCenterInfo helpCenterInfo = new HelpCenterInfo();
            helpCenterInfo.setDataList(helpCenters);
            List<ExplainBean> intro = helpCenterInfoService.get(helpCenters.get(0).getid());
            helpCenterInfo.setIntroList(intro != null ? intro: new ArrayList<>());
            resultData.setData(helpCenterInfo);
        }
        return resultData;
    }

    @PostMapping(Router.V1_OMS_HELPCENTER_UPDATE)
    public ResultData updateHelpCenter(@RequestBody Map<String,Object> params){
        ResultData resultData = Tools.getThreadResultData();
        resultData.setData(helpCenterService.update(params));
        return resultData;
    }

    @PostMapping(Router.V1_OMS_HELPCENTER_DELETE)
    public ResultData deleteHelpCenter(@RequestParam int id){
        ResultData resultData = Tools.getThreadResultData();
        resultData.setData(helpCenterService.delete(id));
        return resultData;
    }

    @PostMapping(Router.V1_OMS_HELPCENTER_CUSTOMER_DELETE)
    public ResultData deleteHelpCenterCustomer(@RequestParam("venderId") int venderId){
        ResultData resultData = Tools.getThreadResultData();
        resultData.setData(helpCenterService.deleteCustomer(venderId));
        return resultData;
    }

    @PostMapping(Router.V1_OMS_HELPCENTER_ADD)
    public ResultData addHelpCenter(@RequestBody Map<String,Object> params){
        ResultData resultData = Tools.getThreadResultData();
        resultData.setData(helpCenterService.add(params));
        return resultData;
    }
}
