package com.cibnvideo.tcmalladmin.controller;

import com.cibnvideo.common.annotation.Log;
import com.cibnvideo.common.controller.BaseController;
import com.cibnvideo.common.utils.Result;
import com.cibnvideo.config.Router;
import com.cibnvideo.tcmalladmin.model.bean.HelpCenterInfoVo;
import com.cibnvideo.tcmalladmin.omsapi.HelpCenterApi;
import com.hqtc.common.response.DataList;
import com.hqtc.common.response.ResultData;
import com.hqtc.common.utils.Tools;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/7/10 18:18
 */
@Controller
public class HelpCenterManagerController extends BaseController {

    @Autowired
    private HelpCenterApi helpCenterApi;

    @Log("请求访问帮助中心列表页面")
    @RequiresPermissions("mall:center:html")
    @GetMapping(Router.ADMIN_HELP_CENTER_HTML)
    public String helpCenterHtml() {
        return "tcmalladmin/helpcentermanager/helpcenter";
    }

    @RequiresPermissions("mall:center:html")
    @GetMapping(Router.ADMIN_HELP_CENTER_LIST)
    @ResponseBody
    public Result helpCenterList(@RequestParam Map<String, Object> params) {
        Result<HelpCenterInfoVo> result = new Result<>();
        Long venderId = this.getVenderId();
        if (venderId == null) {
            return null;
        }
        params.put("venderId", venderId);
        DataList<List<HelpCenterInfoVo>> resultData = helpCenterApi.helpCenterList(params);
        result.setRows(resultData.getData());
        result.setTotal(resultData.getTotalRows());
        return result;
    }

    @Log("编辑问答")
    @RequiresPermissions("mall:center:edit")
    @GetMapping(Router.ADMIN_HELP_CENTER_EDIT)
    public String getQAById(@RequestParam(value = "id", required = false) Integer id,
                            @RequestParam(value = "typeId", required = false) Integer typeId,
                            Model model) {
        if (id == null) {
            ResultData<HelpCenterInfoVo> resultData = helpCenterApi.getTypeById(typeId);
            HelpCenterInfoVo helpCenterInfoVo = new HelpCenterInfoVo();
            if (resultData != null) {
                helpCenterInfoVo = resultData.getData();
                if (helpCenterInfoVo != null) {
                    model.addAttribute("bean", helpCenterInfoVo);
                }
            }
            return "tcmalladmin/helpcentermanager/edit";
        }
        ResultData<HelpCenterInfoVo> resultData = helpCenterApi.getQAById(id);
        HelpCenterInfoVo helpCenterInfoVo = new HelpCenterInfoVo();
        if (resultData != null) {
            helpCenterInfoVo = resultData.getData();
            if (helpCenterInfoVo != null) {
                model.addAttribute("bean", helpCenterInfoVo);
            }
        }
        return "tcmalladmin/helpcentermanager/edit";
    }

    @Log("更新问答")
    @RequiresPermissions("mall:center:edit")
    @PostMapping(Router.ADMIN_HELP_CENTER_EDIT)
    @ResponseBody
    public ResultData getQAById(@RequestParam Map<String, Object> params) {
        ResultData<Integer> resultData = Tools.getThreadResultData();
        if (StringUtils.isNotEmpty((String) params.get("QAId"))) {
            resultData = helpCenterApi.updateQA(params);
        } else {
            resultData = helpCenterApi.addQA(params);
        }
        return resultData;
    }

    @Log("更新分类名称")
    @RequiresPermissions("mall:center:edit")
    @PostMapping(Router.ADMIN_HELP_CENTER_EDIT_TYPE_NAME)
    @ResponseBody
    public ResultData updateTypeById(@RequestParam("typeName") String typeName, @RequestParam("id") Integer id) {
        ResultData<Integer> resultData = helpCenterApi.updateType(typeName, id);
        return resultData;
    }

    @Log("删除问答")
    @RequiresPermissions("mall:center:delete")
    @PostMapping(Router.ADMIN_HELP_CENTER_DELETE)
    @ResponseBody
    public ResultData deleteQA(@RequestParam("id") Integer id, @RequestParam("typeId") Integer typeId) {
        ResultData<Integer> resultData = Tools.getThreadResultData();
        if (id != null) {
            resultData = helpCenterApi.deleteQAAndType(id);
        } else {
            resultData = helpCenterApi.deleteType(typeId);
        }
        return resultData;
    }

    @Log("添加问答")
    @RequiresPermissions("mall:center:add")
    @GetMapping(Router.ADMIN_HELP_CENTER_ADD)
    public String addQA(Model model) {
        //查询所有分类信息
        Long venderId = this.getVenderId();
        if (venderId == null) {
            return null;
        }
        ResultData<List<HelpCenterInfoVo>> resultData = helpCenterApi.listAllType(venderId);
        List<HelpCenterInfoVo> beanList = new ArrayList<>();
        if (resultData != null) {
            beanList = resultData.getData();
        }
        model.addAttribute("typeList", beanList);
        return "tcmalladmin/helpcentermanager/add";
    }

    @Log("添加问答分类")
    @RequiresPermissions("mall:center:add")
    @GetMapping(Router.ADMIN_HELP_CENTER_ADD_TYPE)
    public String addType() {
        return "tcmalladmin/helpcentermanager/addType";
    }

    @Log("添加问答")
    @RequiresPermissions("mall:center:add")
    @PostMapping(Router.ADMIN_HELP_CENTER_ADD)
    @ResponseBody
    public ResultData addQA(@RequestParam Map<String, Object> params) {
        ResultData<Integer> resultData = helpCenterApi.addQA(params);
        return resultData;
    }

    @Log("添加问答分类")
    @RequiresPermissions("mall:center:add")
    @PostMapping(Router.ADMIN_HELP_CENTER_ADD_TYPE)
    @ResponseBody
    public ResultData addType(@RequestParam Map<String, Object> params) {
        Long venderId = this.getVenderId();
        if (venderId == null) {
            return null;
        }
        params.put("venderid", venderId);
        ResultData<Integer> resultData = helpCenterApi.addType(params);
        return resultData;
    }
}
