package com.cibnvideo.tcmalladmin.controller;

import com.cibnvideo.common.annotation.Log;
import com.cibnvideo.common.controller.BaseController;
import com.cibnvideo.common.utils.R;
import com.cibnvideo.common.utils.Result;
import com.cibnvideo.tcmalladmin.model.bean.TemplateBean;
import com.cibnvideo.tcmalladmin.omsapi.LayoutApi;
import com.cibnvideo.tcmalladmin.omsapi.TemplateApi;
import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.DataList;
import com.hqtc.common.response.ResultData;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class TemplateManagerController extends BaseController {

    @Autowired
    TemplateApi templateApi;

    @Autowired
    LayoutApi layoutApi;

    @Log("访问模板列表页面")
    @RequiresPermissions("mall:template:list")
    @GetMapping("/v1/tcmalladmin/templatemanager/templates")
    public String templateManager(Model model){

        return "tcmalladmin/templatemanager/templates";
    }

    @Log("获取模板列表")
    @RequiresPermissions("mall:template:list")
    @GetMapping("/v1/tcmalladmin/templatemanager/templatelist")
    @ResponseBody()
    Result<TemplateBean> list(@RequestParam Map<String, Object> params) {
        Result<TemplateBean> result = new Result<TemplateBean>();
        result.setTotal(0);
        params.put("venderId", this.getVenderId().intValue());
        ResultData<DataList<List<TemplateBean>>> resultData = templateApi.list(params);
        if(resultData.getError() == ErrorCode.SUCCESS){
            DataList<List<TemplateBean>> dataList = resultData.getData();
            result.setRows(dataList.getData());
            result.setTotal(dataList.getTotalRows());
            return result;
        }else {
            return result;
        }
    }

    @Log("模板预览")
    @RequiresPermissions("mall:template:list")
    @GetMapping("/v1/tcmalladmin/templatemanager/templatepreview")
    public String templatePreview(Model model, @RequestParam Integer id){
        ResultData<TemplateBean> resultData = templateApi.get(id);
        if(resultData.getError() == ErrorCode.SUCCESS){
            TemplateBean templateBean = resultData.getData();
            model.addAttribute("template", templateBean);
            return "tcmalladmin/templatemanager/templatepreview";
        }else {
            return "error/500";
        }

    }

    @Log("访问添加页面")
    @RequiresPermissions("mall:template:add")
    @GetMapping("/v1/tcmalladmin/templatemanager/add")
    public String addTemplate(){

        return "tcmalladmin/templatemanager/add";
    }

    @Log("添加模板")
    @RequiresPermissions("mall:template:add")
    @PostMapping("/v1/tcmalladmin/templatemanager/add")
    @ResponseBody
    public R saveTemplate(TemplateBean templateBean){
        templateBean.setVenderId(this.getVenderId().intValue());
        templateBean.setCtime(new Date());
        templateBean.setUtime(new Date());
        ResultData<Integer> resultData = templateApi.add(templateBean);
        if(resultData.getError() == ErrorCode.SUCCESS){
            Integer code = resultData.getData();
            if(code > 0){
                return R.ok();
            }else {
                return R.error("模板保存失败");
            }
        }else {
            return R.error("模板保存失败");
        }
    }
    @Log("删除模板")
    @RequiresPermissions("mall:template:remove")
    @GetMapping("/v1/tcmalladmin/templatemanager/remove")
    @ResponseBody
    public R removeTemplate(Integer id){
        Long venderId = this.getVenderId();
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("venderId", venderId);
        params.put("templateId", id);
        ResultData<Integer> resultDataLayout = layoutApi.count(params);
        if(resultDataLayout.getError() == ErrorCode.SUCCESS){
            Integer count = resultDataLayout.getData();
            if(count != null && count > 0){
                return R.error("删除失败,模板正在使用中");
            }else {
                ResultData<Integer> resultData = templateApi.remove(id);
                if(resultData.getError() == ErrorCode.SUCCESS){
                    if(resultData.getData() > 0){
                        return R.ok();
                    }else {
                        return R.error("模板删除失败");
                    }
                }else {
                    return R.error("服务端模板删除失败");
                }
            }
        }else {
            return R.error(resultDataLayout.getMsg());
        }

    }
}
