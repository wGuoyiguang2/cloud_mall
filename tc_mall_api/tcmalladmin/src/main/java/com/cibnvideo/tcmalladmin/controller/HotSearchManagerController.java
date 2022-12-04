package com.cibnvideo.tcmalladmin.controller;

import com.cibnvideo.common.controller.BaseController;
import com.cibnvideo.common.utils.Result;
import com.cibnvideo.tcmalladmin.model.bean.HotSearchBean;
import com.cibnvideo.tcmalladmin.omsapi.HotSearchApi;
import com.hqtc.common.config.ErrorCode;
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
 * @Description:
 * @Author: WangBin
 * @Date: 2018/8/13 14:05
 */
@Controller
public class HotSearchManagerController extends BaseController{

    @Autowired
    private HotSearchApi hotSearchApi;
    @RequiresPermissions("mall:hotsearch:list")
    @GetMapping("/v1/tcmalladmin/hotSearchHtml")
    public String hotSearchHtml(Model model) {
       return "tcmalladmin/hotsearchmanager/hotsearch";
    }
    @GetMapping("/v1/tcmalladmin/hotsearchList")
    @RequiresPermissions("mall:hotsearch:list")
    @ResponseBody
    public Result<HotSearchBean> listHotSearch(@RequestParam Map<String,Object> params) {
        Long venderId=this.getVenderId();
        if(venderId==null){
            return null;
        }
        params.put("venderId",venderId);
        Result<HotSearchBean> result=new Result<>();
        ResultData<Result<HotSearchBean>> resultData=hotSearchApi.listManagerHotSearch(params);
        if(resultData.getError()!= ErrorCode.SUCCESS){
            return result;
        }
        result=resultData.getData();
        return result;
    }

    @GetMapping("/v1/tcmalladmin/hotsearch/add")
    @RequiresPermissions("mall:hotsearch:add")
    public String addHotSearch(Model model){
        return "tcmalladmin/hotsearchmanager/addHotSearch";
    }
    @PostMapping("/v1/tcmalladmin/hotsearch/add")
    @RequiresPermissions("mall:hotsearch:add")
    @ResponseBody
    public ResultData addHotSearch(@RequestParam Map<String, Object> params){
        Long venderId=this.getVenderId();
        if(venderId==null){
            return null;
        }
        params.put("venderId",venderId);
        ResultData resultData=hotSearchApi.addHotSearch(params);
        return resultData;
    }
    @GetMapping("/v1/tcmalladmin/hotsearch/edit")
    @RequiresPermissions("mall:hotsearch:edit")
    public String editHotSearch(Model model,@RequestParam("id") Integer id){
        ResultData<HotSearchBean> resultData=hotSearchApi.getById(id);
        model.addAttribute("bean",resultData.getData());
        return "tcmalladmin/hotsearchmanager/editHotSearch";
    }
    @PostMapping("/v1/tcmalladmin/hotsearch/edit")
    @RequiresPermissions("mall:hotsearch:edit")
    @ResponseBody
    public ResultData updateHotSearch(@RequestParam Map<String,Object> params){
        ResultData resultData=hotSearchApi.updateManagerHotSearch(params);
        return resultData;
    }
    @GetMapping("/v1/tcmalladmin/hotsearch/delete")
    @RequiresPermissions("mall:hotsearch:remove")
    @ResponseBody
    public ResultData removeHotSearch(@RequestParam("id") Integer id){
        ResultData resultData=hotSearchApi.deleteById(id);
        return resultData;
    }
}
