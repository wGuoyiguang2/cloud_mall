package com.cibnvideo.tcmalladmin.controller;

import com.cibnvideo.common.annotation.Log;
import com.cibnvideo.common.controller.BaseController;
import com.cibnvideo.common.utils.R;
import com.cibnvideo.common.utils.Result;
import com.cibnvideo.tcmalladmin.model.bean.LayoutBean;
import com.cibnvideo.tcmalladmin.model.bean.TemplateBean;
import com.cibnvideo.tcmalladmin.omsapi.LayoutApi;
import com.cibnvideo.tcmalladmin.omsapi.RecommendApi;
import com.cibnvideo.tcmalladmin.omsapi.TemplateApi;
import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.DataList;
import com.hqtc.common.response.ResultData;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
public class LayoutManagerController extends BaseController {

    @Autowired
    TemplateApi templateApi;

    @Autowired
    LayoutApi layoutApi;

    @Autowired
    RecommendApi recommendApi;


    @Log("访问布局列表页面")
    @RequiresPermissions("mall:layout:list")
    @GetMapping("/v1/tcmalladmin/layoutmanager/layouts")
    public String layoutManager(Model model){

        return "tcmalladmin/layoutmanager/layouts";
    }

    @Log("获取布局列表")
    @RequiresPermissions("mall:layout:list")
    @GetMapping("/v1/tcmalladmin/layoutmanager/layoutlist")
    @ResponseBody()
    Result<LayoutBean> list(@RequestParam Map<String, Object> params) {
        Result<LayoutBean> result = new Result<LayoutBean>();
        result.setTotal(0);
        params.put("venderId", this.getVenderId().intValue());
        ResultData<DataList<List<LayoutBean>>> resultData = layoutApi.list(params);
        if(resultData.getError() == ErrorCode.SUCCESS){
            DataList<List<LayoutBean>> dataList = resultData.getData();
            List<LayoutBean> layoutBeans = dataList.getData();
            result.setRows(dataList.getData());
            result.setTotal(dataList.getTotalRows());
            return result;
        }else {
            return result;
        }
    }
    @Log("布局模板预览")
    @RequiresPermissions("mall:layout:list")
    @GetMapping("/v1/tcmalladmin/layoutmanager/layoutpreview")
    public String layoutPreview(Model model, @RequestParam Integer id){
        Integer venderId = this.getVenderId().intValue();
        HashMap<String, Object> recommendParams = new HashMap<String, Object>();
        recommendParams.put("venderId", venderId);
        recommendParams.put("layoutId", id);
        ResultData<LayoutBean> resultData = layoutApi.get(id);
        if(resultData.getError() == ErrorCode.SUCCESS){
            LayoutBean layoutBean = resultData.getData();
            if(layoutBean != null){
                Integer templateId = layoutBean.getTemplateId();
                ResultData<TemplateBean> resultDataTemp = templateApi.get(templateId);
                if(resultDataTemp.getError() == ErrorCode.SUCCESS){
                    TemplateBean templateBean = resultDataTemp.getData();
                    if(templateBean != null){
                        model.addAttribute("template", resultDataTemp.getData());
                        model.addAttribute("layout", layoutBean);
                        return "tcmalladmin/layoutmanager/layoutpreview";
                    }else {
                        return "error/500";
                    }

                }else {
                    return "error/500";
                }
            }else {
                return "error/500";
            }
        }else {
            return "error/500";
        }

    }
    @Log("访问添加布局页面")
    @RequiresPermissions("mall:layout:add")
    @GetMapping("/v1/tcmalladmin/layoutmanager/add")
    public String addLayout(Model model){
        Integer venderId = this.getVenderId().intValue();
        HashMap<String, Object> params = new HashMap<String, Object>(1);
        params.put("venderId", venderId);
        ResultData<DataList<List<TemplateBean>>> resultData = templateApi.list(params);
        if(resultData.getError() == ErrorCode.SUCCESS){
            DataList<List<TemplateBean>> templates = resultData.getData();
            if(templates != null){
                List<TemplateBean> templatelist = templates.getData();
                model.addAttribute("templatelist", templatelist);
            }else {
                model.addAttribute("templatelist", new ArrayList<TemplateBean>());
            }


        }else {
            model.addAttribute("templatelist", new ArrayList<TemplateBean>());
        }
        return "tcmalladmin/layoutmanager/add";
    }

    @Log("添加布局")
    @RequiresPermissions("mall:layout:add")
    @PostMapping("/v1/tcmalladmin/layoutmanager/add")
    @ResponseBody
    public R saveLayout(LayoutBean layoutBean){
        layoutBean.setVenderId(this.getVenderId().intValue());
        layoutBean.setCtime(new Date());
        layoutBean.setUtime(new Date());
        ResultData<Integer> resultData = layoutApi.add(layoutBean);
        if(resultData.getError() == ErrorCode.SUCCESS){
            Integer code = resultData.getData();
            if(code > 0){
                return R.ok();
            }else {
                return R.error("布局保存失败");
            }
        }else {
            return R.error("布局保存失败");
        }
    }

    @Log("访问拷贝布局页面")
    @RequiresPermissions("mall:layout:add")
    @GetMapping("/v1/tcmalladmin/layoutmanager/copy")
    public String copyLayout(Model model){
        Integer venderId = this.getVenderId().intValue();
        HashMap<String, Object> params = new HashMap<String, Object>(1);
        params.put("parentId", 0);
        params.put("share", 1);
        ResultData<DataList<List<LayoutBean>>> resultData = layoutApi.list(params);
        if(resultData.getError() == ErrorCode.SUCCESS) {
            DataList<List<LayoutBean>> dataList = resultData.getData();
            if(dataList != null) {
                model.addAttribute("layoutList", dataList.getData());
            }
        }
        return "tcmalladmin/layoutmanager/copy";
    }

    @Log("拷贝布局")
    @RequiresPermissions("mall:layout:add")
    @PostMapping("/v1/tcmalladmin/layoutmanager/copybyid")
    @ResponseBody
    public R copySaveLayout(Integer parentId){
        ResultData<Integer> resultData = layoutApi.copyByLayoutId(getVenderId().intValue(), parentId);
        if(resultData.getError() == ErrorCode.SUCCESS){
            Integer code = resultData.getData();
            if(code > 0){
                return R.ok();
            }else {
                return R.error("布局复制失败");
            }
        }else {
            return R.error("布局复制失败");
        }
    }

    @Log("删除布局")
    @RequiresPermissions("mall:layout:remove")
    @GetMapping("/v1/tcmalladmin/layoutmanager/remove")
    @ResponseBody
    public R removeLayout(Integer id){
        ResultData<Integer> resultData = layoutApi.remove(id);
        if(resultData.getError() == ErrorCode.SUCCESS){
            if(resultData.getData() > 0){
                return R.ok();
            }else {
                return R.error("布局删除失败");
            }
        }else {
            return R.error(resultData.getMsg());
        }
    }
    @Log("访问编辑布局页面")
    @RequiresPermissions("mall:layout:edit")
    @GetMapping("/v1/tcmalladmin/layoutmanager/edit")
    public String editLayout(@RequestParam("id") Integer id, Model model){
        Integer venderId = this.getVenderId().intValue();
        HashMap<String, Object> params = new HashMap<String, Object>(1);
        ResultData<LayoutBean> resultData = layoutApi.get(id);
        if(resultData.getError() == ErrorCode.SUCCESS){
            LayoutBean layoutBean = resultData.getData();
            if(layoutBean != null){
                model.addAttribute("layout", layoutBean);
                params.put("id", layoutBean.getTemplateId());
                ResultData<DataList<List<TemplateBean>>> resultDataTemp = templateApi.list(params);
                if(resultDataTemp.getError() == ErrorCode.SUCCESS){
                    DataList<List<TemplateBean>> templates = resultDataTemp.getData();
                    if(templates != null){
                        List<TemplateBean> templatelist = templates.getData();
                        model.addAttribute("templatelist", templatelist);
                    }else {
                        model.addAttribute("templatelist", new ArrayList<TemplateBean>());
                    }
                    return "tcmalladmin/layoutmanager/edit";
                }else {
                    return "error/500";
                }
            }else {
                return "error/500";
            }
        }else {
            return "error/500";
        }
    }
    @Log("编辑布局")
    @RequiresPermissions("mall:layout:edit")
    @PostMapping("/v1/tcmalladmin/layoutmanager/edit")
    @ResponseBody
    public R updateLayout(LayoutBean layoutBean){
        Integer venderId = this.getVenderId().intValue();
        Integer id = layoutBean.getId();
        layoutBean.setUtime(new Date());
        ResultData<LayoutBean> resultDataLayout = layoutApi.get(id);
        if(resultDataLayout.getError() == ErrorCode.SUCCESS){
            LayoutBean lb = resultDataLayout.getData();
            if(lb != null && lb.getId().equals(id)){
                ResultData<Integer> resultData = layoutApi.update(layoutBean);
                if(resultData.getError() == ErrorCode.SUCCESS){
                    if(resultData.getData() != null && resultData.getData() > 0){
                        return R.ok();
                    }else {
                        return R.error("修改失败");
                    }
                }else {
                    return R.error(resultData.getMsg());
                }
            }else {
                return R.error("布局不存在");
            }
        }else {
            return R.error(resultDataLayout.getMsg());
        }
    }
}
