package com.cibnvideo.oms.recommend.controller;

import com.cibnvideo.oms.config.Router;
import com.cibnvideo.oms.recommend.model.bean.LayoutBean;
import com.cibnvideo.oms.recommend.service.LayoutService;
import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.DataList;
import com.hqtc.common.response.ResultData;
import com.hqtc.common.utils.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class LayoutController {

    @Autowired
    private LayoutService layoutService;

    @RequestMapping(value = Router.V1_OMS_LAYOUT_LIST, method = RequestMethod.GET)
    ResultData list(@RequestParam Map<String, Object> params){
        ResultData resultData = Tools.getThreadResultData();
        List<LayoutBean> layoutBeanList = layoutService.list(params);
        int count = layoutService.count(params);
        DataList<List<LayoutBean>> result = Tools.getThreadDataList();
        result.setData(layoutBeanList);
        result.setTotalRows(count);
        resultData.setData(result);
        return resultData;
    }

    @RequestMapping(value = Router.V1_OMS_LAYOUT_COUNT, method = RequestMethod.GET)
    ResultData count(@RequestParam Map<String, Object> params){
        ResultData resultData = Tools.getThreadResultData();
        int count = layoutService.count(params);
        resultData.setData(count);
        return resultData;
    }

    @RequestMapping(value = Router.V1_OMS_LAYOUT_GET, method = RequestMethod.GET)
    ResultData get(@RequestParam Integer id){
        ResultData<LayoutBean> resultData = Tools.getThreadResultData();
        resultData.setData(layoutService.get(id));
        return resultData;
    }

    @RequestMapping(value = Router.V1_OMS_LAYOUT_ADD, method = RequestMethod.POST)
    ResultData add(@RequestBody LayoutBean layoutBean){
        ResultData<Integer> resultData = Tools.getThreadResultData();
        layoutBean.setParentId(0);
        layoutBean.setShare(0);
        layoutBean.setAutoSync(0);
        resultData.setData(layoutService.save(layoutBean));
        return resultData;
    }

    @RequestMapping(value = Router.V1_OMS_LAYOUT_REMOVE, method = RequestMethod.GET)
    ResultData remove(@RequestParam Integer id) throws Exception{
        ResultData<Integer> resultData = Tools.getThreadResultData();
        resultData.setData(layoutService.remove(id));
        return resultData;
    }

    @RequestMapping(value = Router.V1_OMS_LAYOUT_UPDATE, method = RequestMethod.POST)
    ResultData update(@RequestBody LayoutBean layoutBean){
        ResultData<Integer> resultData = Tools.getThreadResultData();
        resultData.setData(layoutService.update(layoutBean));
        return resultData;
    }

    @RequestMapping(value = Router.V1_OMS_LAYOUT_COPY_BY_LAYOUTID, method = RequestMethod.GET)
    ResultData copyByLayoutId(@RequestParam Integer venderId, @RequestParam Integer parentId){
        ResultData<Integer> resultData = Tools.getThreadResultData();
        try {
            resultData.setData(layoutService.copyByLayoutId(venderId, parentId));
        } catch (Exception e) {
            resultData.setError(ErrorCode.SERVER_EXCEPTION);
            resultData.setMsg(e.getMessage());
        }
        return resultData;
    }
}
