package com.cibnvideo.oms.tcmallcustomer.controller;

import com.cibnvideo.oms.config.Router;
import com.cibnvideo.oms.tcmallcustomer.bean.ExplainBean;
import com.cibnvideo.oms.tcmallcustomer.bean.HelpCenterInfoVo;
import com.cibnvideo.oms.tcmallcustomer.service.HelpCenterInfoService;
import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.DataList;
import com.hqtc.common.response.ResultData;
import com.hqtc.common.utils.Tools;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class HelpCenterInfoController {

    @Autowired
    HelpCenterInfoService helpCenterInfoService;

    @GetMapping(Router.V1_OMS_HELPCENTERINFO_GET)
    public ResultData getHelpCenterInfo(@RequestParam int id){
        ResultData resultData = Tools.getThreadResultData();
        List<ExplainBean> intro = helpCenterInfoService.get(id);
        if (intro != null && intro.size() > 0){
            resultData.setData(intro);
        }
        return resultData;
    }

    @PostMapping(Router.V1_OMS_HELPCENTERINFO_DELETE)
    public ResultData deleteHelpCenterInfo(@RequestParam int id){
        ResultData resultData = Tools.getThreadResultData();
        resultData.setData(helpCenterInfoService.delete(id));
        return resultData;
    }
    @PostMapping(Router.V1_OMS_HELPCENTERINFO_ADD)
    public ResultData addHelpCenterInfo(@RequestBody Map<String,Object> params){
        ResultData resultData = Tools.getThreadResultData();
        resultData.setData(helpCenterInfoService.add(params));
        return resultData;
    }

    @PostMapping(Router.V1_OMS_HELPCENTERINFO_UPDATE)
    public ResultData updateHelpCenterInfo(@RequestBody  Map<String,Object> params){
        ResultData resultData = Tools.getThreadResultData();
        resultData.setData(helpCenterInfoService.update(params));
        return resultData;
    }

    /**
     * 获取分类信息和问答信息列表
     * @param params
     * @return
     */
    @GetMapping(Router.V1_OMS_HELPCENTER_LIST)
    public DataList<List<HelpCenterInfoVo>> helpCenterList(@RequestParam Map<String, Object> params){
        DataList<List<HelpCenterInfoVo>> resultData = Tools.getThreadDataList();
        List<HelpCenterInfoVo> list=helpCenterInfoService.list(params);
        int count=helpCenterInfoService.count(params);
        resultData.setData(list);
        resultData.setTotalRows(count);
        return resultData;
    }

    /**
     * 通过问答id获取问答信息
     * @param id
     * @return
     */
    @GetMapping(Router.V1_OMS_HELPCENTERINFO_GET_QA_BY_ID)
    public ResultData getQAById(@RequestParam("id") int id){
        ResultData resultData = Tools.getThreadResultData();
        HelpCenterInfoVo helpCenterInfoVo = helpCenterInfoService.getQAAndTypeById(id);
        resultData.setData(helpCenterInfoVo);
        return resultData;
    }

    /**
     * 通过id更新问答和分类信息
     * @param params
     * @return
     */
    @PostMapping(Router.V1_OMS_HELPCENTER_UPDATE_QA_BY_ID)
    public ResultData updateQAAndTypeName(@RequestParam Map<String, Object> params){
        ResultData resultData = Tools.getThreadResultData();
        int result= helpCenterInfoService.updateQA(params);
        resultData.setData(result);
        if(result>0){
            resultData.setMsg("更新成功");
            resultData.setError(ErrorCode.SUCCESS);
        }else{
            resultData.setMsg("更新失败");
            resultData.setError(ErrorCode.WRITE_DATA_ERROR);
        }
        return resultData;
    }

    /**
     * 通过问答id删除问答信息
     * @param id
     * @return
     */
    @PostMapping(Router.V1_OMS_HELPCENTER_DELETE_QA_BY_ID)
    public ResultData deleteQAById(@RequestParam("id") Integer id){
        ResultData resultData = Tools.getThreadResultData();
        int result= helpCenterInfoService.deleteQAAndTypeByTypeId(id);
        resultData.setData(result);
        if(result>0){
            resultData.setMsg("删除成功");
            resultData.setError(ErrorCode.SUCCESS);
        }else{
            resultData.setMsg("删除失败");
            resultData.setError(ErrorCode.WRITE_DATA_ERROR);
        }
        return resultData;
    }

    /**
     * 添加问答信息
     * @param params
     * @return
     */
    @PostMapping(Router.V1_OMS_HELPCENTER_ADD_QA)
    ResultData<Integer> addQA(@RequestParam Map<String, Object> params){
        ResultData resultData = Tools.getThreadResultData();
        if(StringUtils.isEmpty((String)params.get("position"))) {
            params.put("position",1);
        }
        int result= helpCenterInfoService.addQA(params);
        resultData.setData(result);
        if(result>0){
            resultData.setMsg("添加问答成功");
            resultData.setError(ErrorCode.SUCCESS);
        }else{
            resultData.setMsg("添加问答失败");
            resultData.setError(ErrorCode.WRITE_DATA_ERROR);
        }
        return resultData;
    }

    /**
     * 添加分类
     * @param params
     * @return
     */

    @PostMapping(Router.V1_OMS_HELPCENTER_ADD_TYPE)
    ResultData<Integer> addType(@RequestParam Map<String, Object> params){
        ResultData resultData = Tools.getThreadResultData();
        int result= helpCenterInfoService.addType(params);
        resultData.setData(result);
        if(result>0){
            resultData.setMsg("添加分类成功");
            resultData.setError(ErrorCode.SUCCESS);
        }else{
            resultData.setMsg("添加分类失败");
            resultData.setError(ErrorCode.WRITE_DATA_ERROR);
        }
        return resultData;
    }
    @PostMapping(Router.V1_OMS_HELPCENTER_LIST_ALL_TYPE)
    public ResultData<List<HelpCenterInfoVo>> listAllType(@RequestParam("venderId") Long venderId){
        ResultData<List<HelpCenterInfoVo>> resultData=Tools.getThreadResultData();
        List<HelpCenterInfoVo> helpCenterInfoList=helpCenterInfoService.listAllType(venderId);
        resultData.setData(helpCenterInfoList);
        return resultData;
    }
    @PostMapping(Router.V1_OMS_HELPCENTER_TYPE_GET)
    public ResultData getTypeById(@RequestParam("typeId") Integer typeId){
        ResultData<HelpCenterInfoVo> resultData=Tools.getThreadResultData();
        HelpCenterInfoVo helpCenterInfoVo=helpCenterInfoService.getTypeById(typeId);
        resultData.setData(helpCenterInfoVo);
        return resultData;
    }
    @PostMapping(Router.V1_OMS_HELPCENTER_DELETE_TYPE)
    public ResultData deleteTypeById(@RequestParam("typeId") Integer typeId){
        ResultData resultData=Tools.getThreadResultData();
        int result=helpCenterInfoService.deleteTypeById(typeId);
        if(result>0){
            resultData.setMsg("删除成功");
            resultData.setError(ErrorCode.SUCCESS);
        }else{
            resultData.setMsg("删除失败");
            resultData.setError(ErrorCode.WRITE_DATA_ERROR);
        }
        return resultData;
    }
}
