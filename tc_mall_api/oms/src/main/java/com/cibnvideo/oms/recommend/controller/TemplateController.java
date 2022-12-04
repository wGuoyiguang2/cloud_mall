package com.cibnvideo.oms.recommend.controller;


import com.cibnvideo.oms.config.Router;
import com.cibnvideo.oms.recommend.model.bean.TemplateBean;
import com.cibnvideo.oms.recommend.service.TemplateService;
import com.hqtc.common.response.DataList;
import com.hqtc.common.response.ResultData;
import com.hqtc.common.utils.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class TemplateController {

    @Autowired
    TemplateService templateService;

    @RequestMapping(value = Router.V1_OMS_TEMPLATE_LIST, method = RequestMethod.GET)
    ResultData list(@RequestParam Map<String, Object> params){
        ResultData<DataList> resultData = Tools.getThreadResultData();
        List<TemplateBean> templateBeanList = templateService.list(params);
        int count = templateService.count(params);
        DataList<List<TemplateBean>> result = Tools.getThreadDataList();
        result.setData(templateBeanList);
        result.setTotalRows(count);
        resultData.setData(result);
        return resultData;
    }

    @RequestMapping(value = Router.V1_OMS_TEMPLATE_GET, method = RequestMethod.GET)
    ResultData get(@RequestParam Integer id){
        ResultData<TemplateBean> resultData = Tools.getThreadResultData();
        resultData.setData(templateService.get(id));
        return resultData;
    }

    @RequestMapping(value = Router.V1_OMS_TEMPLATE_ADD, method = RequestMethod.POST)
    ResultData add(@RequestBody TemplateBean templateBean){
        ResultData<Integer> resultData = Tools.getThreadResultData();
        resultData.setData(templateService.save(templateBean));
        return resultData;
    }

    @RequestMapping(value = Router.V1_OMS_TEMPLATE_REMOVE, method = RequestMethod.GET)
    ResultData remove(@RequestParam Integer id){
        ResultData<Integer> resultData = Tools.getThreadResultData();
        resultData.setData(templateService.remove(id));
        return resultData;
    }

    @RequestMapping(value = Router.V1_OMS_TEMPLATE_UPDATE, method = RequestMethod.POST)
    ResultData update(@RequestBody TemplateBean templateBean){
        ResultData<Integer> resultData = Tools.getThreadResultData();
        resultData.setData(templateService.update(templateBean));
        return resultData;
    }
}
