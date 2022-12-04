package com.cibnvideo.tcmalladmin.controller;

import com.cibnvideo.common.controller.BaseController;
import com.cibnvideo.common.utils.Result;
import com.cibnvideo.tcmalladmin.bmsapi.AfterSaleApi;
import com.cibnvideo.tcmalladmin.model.bean.AfterSaleVo;
import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.ResultData;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2019/2/17 13:19
 */
@Controller
public class AfterSaleManagerController extends BaseController{

    @Autowired
    private AfterSaleApi afterSaleApi;
    @RequiresPermissions("mall:afterSale:list")
    @GetMapping("/v1/tcmalladmin/afterSaleList")
    public String afterSaleList() {
        return "tcmalladmin/aftersalemanager/aftersale";
    }

    @RequiresPermissions("mall:afterSale:list")
    @GetMapping("/v1/tcmalladmin/afterSaleList0")
    @ResponseBody
    public Result<AfterSaleVo> listAfterSale(@RequestParam Map<String,Object> params) {
        Long venderId = this.getVenderId();
        params.put("venderid",venderId);
        ResultData<Result<AfterSaleVo>> resultResultData=afterSaleApi.listManagerAfterSale(params);
        return resultResultData.getData();
    }
}
