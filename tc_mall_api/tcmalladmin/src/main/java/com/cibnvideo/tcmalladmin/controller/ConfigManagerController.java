package com.cibnvideo.tcmalladmin.controller;

import com.cibnvideo.common.annotation.Log;
import com.cibnvideo.common.controller.BaseController;
import com.cibnvideo.common.utils.Result;
import com.cibnvideo.tcmalladmin.model.bean.ConfigManagerVo;
import com.cibnvideo.tcmalladmin.omsapi.ConfigApi;
import com.hqtc.common.response.ResultData;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @Description: 客户配置管理
 * @Author: WangBin
 * @Date: 2018/8/8 13:53
 */
@Controller
public class ConfigManagerController extends BaseController{

    @Autowired
    private ConfigApi configApi;
    @Log("请求访问联系我们列表页面")
    @RequiresPermissions("mall:config:list")
    @GetMapping("/v1/tcmalladmin/configList")
    public String configList() {
        return "tcmalladmin/configmanager/config";
    }

    @GetMapping("/v1/tcmalladmin/configList0")
    @RequiresPermissions("mall:config:list")
    @ResponseBody
    public Result<ConfigManagerVo> listConfig(@RequestParam Map<String,Object> params) {
        Long venderId=this.getVenderId();
        if(venderId==null){
            return null;
        }
        params.put("venderId",venderId);
        ResultData<Result<ConfigManagerVo>> result=configApi.listConfig(params);
        return result.getData();
    }

    @GetMapping("/v1/tcmalladmin/config/add")
    @RequiresPermissions("mall:config:add")
    public String addConfig(Model model){
        return "tcmalladmin/configmanager/addConfig";
    }
    @PostMapping("/v1/tcmalladmin/config/add")
    @RequiresPermissions("mall:config:add")
    @ResponseBody
    public ResultData addConfig(@RequestParam Map<String, Object> params){
        Long venderId=this.getVenderId();
        if(venderId==null){
            return null;
        }
        params.put("venderId",venderId);
        ResultData resultData=configApi.addConfig(params);
        return resultData;
    }
    @GetMapping("/v1/tcmalladmin/config/edit")
    @RequiresPermissions("mall:config:edit")
    public String editConfig(Model model,@RequestParam("id") Integer id){
        ResultData<ConfigManagerVo> resultData=configApi.getById(id);
        model.addAttribute("bean",resultData.getData());
        return "tcmalladmin/configmanager/editConfig";
    }
    @PostMapping("/v1/tcmalladmin/config/edit")
    @RequiresPermissions("mall:config:edit")
    @ResponseBody
    public ResultData updateConfig(@RequestParam Map<String,Object> params){
        ResultData resultData=configApi.updateManagerConfig(params);
        return resultData;
    }
    @GetMapping("/v1/tcmalladmin/config/delete")
    @RequiresPermissions("mall:config:remove")
    @ResponseBody
    public ResultData removeConfig(@RequestParam("id") Integer id){
        ResultData resultData=configApi.deleteById(id);
        return resultData;
    }
}
