package com.cibnvideo.tcmalladmin.controller;

import com.cibnvideo.common.annotation.Log;
import com.cibnvideo.common.controller.BaseController;
import com.cibnvideo.common.dao.VenderDao;
import com.cibnvideo.common.entity.Vender;
import com.cibnvideo.common.entity.VenderSettlement;
import com.cibnvideo.common.service.VenderService;
import com.cibnvideo.common.utils.Result;
import com.cibnvideo.tcmalladmin.bmsapi.InvoiceApi;
import com.cibnvideo.tcmalladmin.model.bean.InvoiceInfoVo;
import com.cibnvideo.tcmalladmin.model.bean.InvoiceManagerVo;
import com.cibnvideo.tcmalladmin.model.bean.VenderInvoiceManagerVo;
import com.cibnvideo.tcmalladmin.omsapi.VenderSettlementApi;
import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.ResultData;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 发票管理
 * @Author: WangBin
 * @Date: 2018/7/20 18:03
 */
@Controller
public class InvoiceManagerController extends BaseController{

    @Autowired
    private VenderService venderService;
    @Autowired
    private VenderSettlementApi venderSettlementApi;
    @Autowired
    private InvoiceApi invoiceApi;
    @Autowired
    private VenderDao venderDao;
    @Log("请求发票列表页面")
    @RequiresPermissions("mall:invoice:list")
    @GetMapping("/v1/tcmalladmin/invoiceHtml")
    public String invoiceHtml() {
        return "tcmalladmin/invoicemanager/invoice";
    }

    @RequiresPermissions("mall:invoice:list")
    @GetMapping("/v1/tcmalladmin/invoiceList")
    @ResponseBody
    public Result invoiceList(@RequestParam Map<String, Object> params){
        Long venderId = this.getVenderId();
        if (venderId == null) {
            return null;
        }
        params.put("venderid",venderId);
        Result<InvoiceManagerVo> resultData=invoiceApi.invoiceList(params);
        return resultData;
    }

    //大客户管理
    @Log("请求大客户管理发票列表页面")
    @RequiresPermissions("admin:invoice:list")
    @GetMapping("/v1/tcmalladmin/admin/invoiceHtml")
    public String adminInvoiceHtml0(Model model) {
        return "tcmalladmin/invoicemanager/adminInvoice0";
    }

    @RequiresPermissions("admin:invoice:list")
    @GetMapping("/v1/tcmalladmin/admin/invoiceList")
    @ResponseBody
    public Result invoiceAdminList0(@RequestParam Map<String, Object> params){
        Result<VenderInvoiceManagerVo> resultData=new Result<>();
        if (StringUtils.isNotEmpty((String) params.get("pricePercent"))) {
            Result<VenderInvoiceManagerVo> result= venderSettlementApi.listVenderInvoiceManager(params);
            List<VenderInvoiceManagerVo> list0=new ArrayList<>();
            if(result!=null){
                int total=result.getTotal();
                List<VenderInvoiceManagerVo> list=result.getRows();
                if(list!=null){
                    for (VenderInvoiceManagerVo vo:list) {
                        //从admin查询客户名称
                        Vender vender = venderDao.get(vo.getVenderId());
                        if(vender==null){
                            list0.add(vo);
                            continue;
                        }
                        vo.setVenderName(vender.getName());
                        //从bms查询发票信息
                        InvoiceInfoVo invoiceInfoVo=invoiceApi.getInvoiceInfo(vo.getVenderId());
                        if(invoiceInfoVo!=null){
                            vo.setOpened(invoiceInfoVo.getOpened());
                            vo.setCanceled(invoiceInfoVo.getCanceled());
                            vo.setCount(invoiceInfoVo.getCount());
                        }
                    }
                    list.removeAll(list0);
                    resultData.setTotal(total);
                    resultData.setRows(list);
                }
            }
        }else{
            List<VenderInvoiceManagerVo> list=venderDao.listVenderInvoice(params);
            int total=venderDao.countVenderInvoice(params);
            if(list!=null){
                for (VenderInvoiceManagerVo vo:list) {
                    //从oms获取商品批发系数
                    ResultData<VenderSettlement> result = venderSettlementApi.getVenderSettlement(vo.getVenderId());
                    if(result.getError()== ErrorCode.SUCCESS&&result!=null&&result.getData()!=null){
                        VenderSettlement venderSettlement=result.getData();
                        vo.setPricePercent(venderSettlement.getPricePercent());
                    }
                    //从bms获取发票信息
                    InvoiceInfoVo invoiceInfoVo=invoiceApi.getInvoiceInfo(vo.getVenderId());
                    if(invoiceInfoVo!=null){
                        vo.setOpened(invoiceInfoVo.getOpened());
                        vo.setCanceled(invoiceInfoVo.getCanceled());
                        vo.setCount(invoiceInfoVo.getCount());
                    }
                }
                resultData.setTotal(total);
                resultData.setRows(list);
            }
        }
        return resultData;
    }
    @Log("请求大客户管理发票列表页面")
    @RequiresPermissions("admin:invoice:list")
    @GetMapping("/v1/tcmalladmin/admin/invoiceHtml2")
    public String adminInvoiceHtml(Model model,@RequestParam("venderId") Long venderId) {
        model.addAttribute("venderId",venderId);
        return "tcmalladmin/invoicemanager/adminInvoice";
    }

    @RequiresPermissions("admin:invoice:list")
    @GetMapping("/v1/tcmalladmin/admin/invoiceList2")
    @ResponseBody
    public Result invoiceAdminList(@RequestParam Map<String, Object> params){
        Result<InvoiceManagerVo> resultData=invoiceApi.invoiceList(params);
        return resultData;
    }
    @RequiresPermissions("admin:invoice:remove")
    @PostMapping("/v1/tcmalladmin/admin/removeInvoice")
    @ResponseBody
    public ResultData removeInvoice(@RequestParam("invoiceIds") String invoiceIds){
        ResultData reusltData=invoiceApi.removeInvoices(invoiceIds);
        return reusltData;
    }
}
