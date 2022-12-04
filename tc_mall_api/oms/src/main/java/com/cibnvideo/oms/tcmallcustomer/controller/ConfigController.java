package com.cibnvideo.oms.tcmallcustomer.controller;

import com.cibnvideo.oms.bean.Result;
import com.cibnvideo.oms.config.Router;
import com.cibnvideo.oms.tcmallcustomer.bean.ConfigManagerVo;
import com.cibnvideo.oms.tcmallcustomer.service.ConfigService;
import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.ResultData;
import com.hqtc.common.utils.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/8/8 14:55
 */
@RestController
public class ConfigController {

    @Autowired
    private ConfigService configService;
    @PostMapping(Router.V1_OMS_CONFIG_MANAGER_LIST)
    public ResultData<Result<ConfigManagerVo>> listManagerConfig(@RequestParam Map<String, Object> params){
        ResultData resultData= Tools.getThreadResultData();
        Result<ConfigManagerVo> result=new Result<>();
        List<ConfigManagerVo> list=configService.listManagerConfig(params);
        int total=configService.countManagerConfig(params);
        result.setTotal(total);
        result.setRows(list);
        resultData.setData(result);
        return resultData;
    }
    @PostMapping(Router.V1_OMS_CONFIG_MANAGER_ADD)
    public ResultData addConfig(@RequestParam Map<String, Object> params){
        ResultData resultData= Tools.getThreadResultData();
        int count=configService.addConfig(params);
        if(count>0){
            resultData.setMsg("添加成功！");
        }else{
            resultData.setMsg("添加失败！");
            resultData.setError(ErrorCode.WRITE_DATA_ERROR);
        }
        return resultData;
    }
    @GetMapping(Router.V1_OMS_CONFIG_MANAGER_GETBYID)
    public ResultData getById(@RequestParam("id") Integer id){
        ResultData resultData=Tools.getThreadResultData();
        ConfigManagerVo configManagerVo=configService.getById(id);
        resultData.setData(configManagerVo);
        return resultData;
    }
    @PostMapping(Router.V1_OMS_CONFIG_MANAGER_UPDATE)
    public ResultData updateManagerConfig(@RequestParam Map<String,Object> params){
        ResultData resultData=Tools.getThreadResultData();
        int count=configService.updateManagerConfig(params);
        if(count>0){
            resultData.setMsg("更新成功！");
        }else{
            resultData.setError(ErrorCode.WRITE_DATA_ERROR);
            resultData.setMsg("更新失败！");
        }
        return  resultData;
    }
    @GetMapping(Router.V1_OMS_CONFIG_MANAGER_DELETE)
    public ResultData deleteById(@RequestParam("id") Integer id){
        ResultData resultData=Tools.getThreadResultData();
        int count=configService.deleteById(id);
        if(count>0){
            resultData.setMsg("删除成功！");
        }else{
            resultData.setError(ErrorCode.WRITE_DATA_ERROR);
            resultData.setMsg("删除失败！");
        }
        return  resultData;
    }
}
