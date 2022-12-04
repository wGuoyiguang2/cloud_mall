package com.cibnvideo.tcmalladmin.controller;

import com.cibnvideo.common.controller.BaseController;
import com.cibnvideo.common.utils.Result;
import com.cibnvideo.tcmalladmin.model.bean.FreeFreightBean;
import com.cibnvideo.tcmalladmin.omsapi.FreeFreightApi;
import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.ResultData;
import com.hqtc.common.utils.Tools;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/7/10 18:19
 */
@Controller
public class FreeFreightManagerController extends BaseController {
    @Autowired
    private FreeFreightApi freeFreightApi;
    @RequiresPermissions("mall:freeFreight:list")
    @GetMapping("/v1/tcmalladmin/freeFreightHtml")
    public String freeFreightHtml() {
        return "tcmalladmin/freeFreightmanager/freeFreight";
    }
    @RequiresPermissions("mall:freeFreight:list")
    @GetMapping("/v1/tcmalladmin/freeFreight/list")
    @ResponseBody
    public Result listfreeFreight(@RequestParam Map<String,Object> params) {
        Long venderId=this.getVenderId();
        if(venderId==null){
            return null;
        }
        params.put("venderId",venderId);
        ResultData<Result<FreeFreightBean>> result=freeFreightApi.listFreeFreight(params);
        return result.getData();
    }

    @GetMapping("/v1/tcmalladmin/freeFreight/add")
    @RequiresPermissions("mall:freeFreight:add")
    public String addFreeFreight(Model model){
        model.addAttribute("suggestPrice","建议包邮价不低于:"+this.getSuggestPrice());
        return "tcmalladmin/freeFreightmanager/addFreeFreight";
    }
    @PostMapping("/v1/tcmalladmin/freeFreight/add")
    @RequiresPermissions("mall:freeFreight:add")
    @ResponseBody
    public ResultData addFreeFreight(@RequestParam Map<String, Object> params){
        Long venderId=this.getVenderId();
        if(venderId==null){
            return null;
        }
        params.put("venderId",venderId);
        ResultData resultData=freeFreightApi.addFreeFreight(params);
        return resultData;
    }
    @RequiresPermissions("mall:freeFreight:edit")
    @GetMapping("/v1/tcmalladmin/freeFreight/edit")
    public String getFreeFreightById(@RequestParam("id") Integer id,Model model){
        ResultData<FreeFreightBean> resultData=freeFreightApi.getFreeFreightById(id);
        FreeFreightBean bean=resultData.getData();
        if(bean==null) {
            bean=new FreeFreightBean();
        }
        model.addAttribute("bean",bean);
        model.addAttribute("suggestPrice","建议包邮价不低于:"+this.getSuggestPrice());
        return "tcmalladmin/freeFreightmanager/editFreeFreight";
    }
    @PostMapping("/v1/tcmalladmin/freeFreight/edit")
    @RequiresPermissions("mall:freeFreight:edit")
    @ResponseBody
    public ResultData updateFreeFreight(@RequestParam Map<String,Object> params){
        Long venderId=this.getVenderId();
        if(venderId==null){
            return null;
        }
        params.put("venderId",venderId);
        ResultData resultData=freeFreightApi.updateManagerFreeFreight(params);
        return resultData;
    }
    private Double getSuggestPrice(){
        Long venderId=this.getVenderId();
        ResultData<Double> resultData=freeFreightApi.getSuggestPrice(venderId);
        return resultData.getData();
    }
}
