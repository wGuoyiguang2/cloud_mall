package com.cibnvideo.tcmalladmin.controller;

import com.cibnvideo.common.annotation.Log;
import com.cibnvideo.common.controller.BaseController;
import com.cibnvideo.common.utils.Result;
import com.cibnvideo.tcmalladmin.model.bean.ContactUsBean;
import com.cibnvideo.tcmalladmin.omsapi.ContactUsApi;
import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.ResultData;
import com.hqtc.common.utils.Tools;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/7/10 18:19
 */
@Controller
public class ContactUsController extends BaseController {
    @Autowired
    private ContactUsApi contactUsApi;

    /**
     * 给大客户添加一条默认数据
     *
     * @return
     */
    private ContactUsBean getDefaultContactUsBean() {
        ContactUsBean contactUsBean = new ContactUsBean();
        Long venderId = this.getVenderId();
        contactUsBean.setVenderid(venderId);
        contactUsBean.setId(0);
        contactUsBean.setHotline("0000-0000");
        contactUsBean.setUrl("www.xxxx.com");
        return contactUsBean;
    }

    @Log("请求访问联系我们列表页面")
    @RequiresPermissions("mall:contactus:html")
    @GetMapping("/v1/tcmalladmin/contactUsHtml")
    public String contactUsHtml() {
        return "tcmalladmin/contactusmanager/contactus";
    }

    @RequiresPermissions("mall:contactus:html")
    @GetMapping("/v1/tcmalladmin/contactUsList")
    @ResponseBody
    public Result listContactUs() {
        Result<ContactUsBean> result = new Result<>();
        Long venderId = this.getVenderId();
        if (venderId == null) {
            return null;
        }
        ResultData<List<ContactUsBean>> resultData = contactUsApi.getContactWithUsByVenderId(venderId);
        if (resultData.getError()==ErrorCode.SUCCESS&&resultData != null && resultData.getData() != null && resultData.getData().size() > 0) {
            List<ContactUsBean> contactUsBeanList = resultData.getData();
            result.setTotal(contactUsBeanList.size());
            result.setRows(contactUsBeanList);
        } else {
            //添加默认数据
            List<ContactUsBean> list = new ArrayList<>();
            ContactUsBean bean = this.getDefaultContactUsBean();
            list.add(bean);
            result.setRows(list);
            result.setTotal(1);
        }
        return result;
    }

    @Log("编辑联系我们")
    @RequiresPermissions("mall:contactus:edit")
    @GetMapping("/v1/tcmalladmin/contactus/edit")
    public String getContactUsById(@RequestParam("id") Integer id, Model model) {
        ResultData<ContactUsBean> resultData = contactUsApi.getContactUsById(id);
        ContactUsBean contactUsBean = new ContactUsBean();
        contactUsBean = resultData.getData();
        if (contactUsBean == null) {
            contactUsBean = this.getDefaultContactUsBean();
        }
        model.addAttribute("bean", contactUsBean);
        return "tcmalladmin/contactusmanager/edit";
    }

    @Log("更新联系我们")
    @RequiresPermissions("mall:contactus:edit")
    @PostMapping("/v1/tcmalladmin/contactus/edit")
    @ResponseBody
    public ResultData getContactUsById(ContactUsBean contactUsBean) {
        ResultData result = Tools.getThreadResultData();
        if (contactUsBean.getId() == 0) {
            result = contactUsApi.addContactUs(contactUsBean);
        } else {
            result = contactUsApi.updateContactUs(contactUsBean);
        }
        return result;
    }
}
