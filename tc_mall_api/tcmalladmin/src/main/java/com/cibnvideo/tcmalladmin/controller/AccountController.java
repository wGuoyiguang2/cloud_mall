package com.cibnvideo.tcmalladmin.controller;

import com.cibnvideo.common.annotation.Log;
import com.cibnvideo.common.controller.BaseController;
import com.cibnvideo.common.dao.VenderDao;
import com.cibnvideo.common.entity.Vender;
import com.cibnvideo.common.entity.VenderSettlement;
import com.cibnvideo.common.utils.Result;
import com.cibnvideo.tcmalladmin.bmsapi.OrderProductApi;
import com.cibnvideo.tcmalladmin.model.bean.VenderAccountVo;
import com.cibnvideo.tcmalladmin.omsapi.AccountApi;
import com.cibnvideo.tcmalladmin.model.bean.AccountInfoVo;
import com.cibnvideo.tcmalladmin.model.bean.AccountManagerVo;
import com.cibnvideo.tcmalladmin.omsapi.VenderSettlementApi;
import com.hqtc.common.config.ErrorCode;
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

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/7/21 15:45
 */
@Controller
public class AccountController extends BaseController {

    @Autowired
    private VenderDao venderDao;
    @Autowired
    private OrderProductApi orderProductApi;
    @Autowired
    private AccountApi accountApi;
    @Autowired
    private VenderSettlementApi venderSettlementApi;

    @Log("请求访问账期结算列表页面")
    @RequiresPermissions("mall:account:list")
    @GetMapping("/v1/tcmalladmin/accountHtml")
    public String accountHtml(Model model) {
        //查询客户结算方式
        Long venderId = this.getVenderId();
        if (venderId == null) {
            return "redirect:/login";
        }
        ResultData<VenderSettlement> resultData = venderSettlementApi.getVenderSettlement(venderId);
        VenderSettlement venderSettlement = resultData.getData();
        Integer settlementType = null;
        if (venderSettlement != null) {
            settlementType = venderSettlement.getSettlementType();
        }
        AccountInfoVo accountInfoVo = accountApi.getAccountInfo(venderId);
        if (settlementType==1) {
            model.addAttribute("needPayMoney", (accountInfoVo==null) ? 0.00 : accountInfoVo.getNeedPayMoney());
            model.addAttribute("hasPayMoney", (accountInfoVo==null) ? 0.00 : accountInfoVo.getHasPayMoney());
            model.addAttribute("sumPayMoney", (accountInfoVo==null) ? 0.00 : accountInfoVo.getSumPayMoney());
            return "tcmalladmin/accountmanager/perMonthAccount";
        } else {
            model.addAttribute("balance", (venderSettlement==null||venderSettlement.getBalance() == null) ? 0.00 : venderSettlement.getBalance());
            model.addAttribute("sumPayMoney", (accountInfoVo==null) ? 0.00 : accountInfoVo.getSumPayMoney());
            return "tcmalladmin/accountmanager/preAccount";
        }
    }

    @RequiresPermissions("mall:account:list")
    @GetMapping("/v1/tcmalladmin/accountList")
    @ResponseBody
    public Result accountList(@RequestParam Map<String, Object> params) {
        Long venderId = this.getVenderId();
        if (venderId == null) {
            return null;
        }
        params.put("venderId", venderId);
        Result<AccountManagerVo> result = new Result<>();
        if (StringUtils.isEmpty((String) params.get("productName")) && StringUtils.isEmpty((String) params.get("productId"))) {
            result = accountApi.accountList(params);
        } else {
            Result<String> orderResultData = orderProductApi.getOrderNo(params);
            List<String> orderList = orderResultData.getRows();
            int total = orderResultData.getTotal();
            if (orderList != null && orderList.size() > 0) {
                ResultData<List<AccountManagerVo>> resultData = accountApi.getOrderAccount(orderList);
                List<AccountManagerVo> accountManagerVoList = resultData.getData();
                result.setTotal(total);
                result.setRows(accountManagerVoList);
            }
        }
        return result;
    }


    @Log("请求访问大客户管理账期结算列表页面")
    @RequiresPermissions("admin:account:list")
    @GetMapping("/v1/tcmalladmin/adminAccountList")
    public String adminVenderAccountList(Model model) {
        return "tcmalladmin/accountmanager/perMonthAccountAdmin0";
    }

    @RequiresPermissions("admin:account:list")
    @GetMapping("/v1/tcmalladmin/adminAccountList1")
    @ResponseBody
    public Result adminAccountList(@RequestParam Map<String,Object> map) {
        Result result=new Result();
        ResultData<DataList<List<VenderAccountVo>>> resultData=accountApi.getVenderAccountInfo(map);
        if(resultData.getError()!=ErrorCode.SUCCESS){
            return result;
        }
        DataList<List<VenderAccountVo>> dataList = Tools.getThreadDataList();
        if(resultData.getData()!=null){
            dataList =resultData.getData();
            if(dataList !=null){
                List<VenderAccountVo> rows= dataList.getData();
                if(rows!=null){
                    for (VenderAccountVo vo:rows) {
                        Vender vender = venderDao.get(vo.getVenderId());
                        if(vender!=null){
                            vo.setVenderName(vender.getName());
                        }
                    }
                }
                result.setTotal(dataList.getTotalRows());
                result.setRows(rows);
            }
        }
        return result;
    }


    @Log("请求访问大客户管理账期结算列表页面")
    @RequiresPermissions("admin:account:list")
    @GetMapping("/v1/tcmalladmin/accountAdminHtml")
    public String adminAccountList(Model model,@RequestParam("venderId") Long venderId) {
        model.addAttribute("venderId",venderId);
        return "tcmalladmin/accountmanager/perMonthAccountAdmin";
    }

    @RequiresPermissions("admin:account:list")
    @GetMapping("/v1/tcmalladmin/adminAccountList2")
    @ResponseBody
    public Result adminAccountList(Model model,@RequestParam Map<String, Object> params,@RequestParam(value="venderId",required = false) Long venderId) {
        if(venderId!=null){
            params.put("venderId",venderId);
        }
        Result<AccountManagerVo> result = new Result<>();
        if (StringUtils.isEmpty((String) params.get("productName")) && StringUtils.isEmpty((String) params.get("productId"))) {
            result = accountApi.accountList(params);
        } else {
            Result<String> orderResultData = orderProductApi.getOrderNo(params);
            List<String> orderList = orderResultData.getRows();
            int total = orderResultData.getTotal();
            if (orderList != null && orderList.size() > 0) {
                ResultData<List<AccountManagerVo>> resultData = accountApi.getOrderAccount(orderList);
                List<AccountManagerVo> accountManagerVoList = resultData.getData();
                result.setTotal(total);
                result.setRows(accountManagerVoList);
            }
        }
        return result;
    }
    @RequiresPermissions("admin:account:settle")
    @PostMapping("/v1/tcmalladmin/settleAdminAccount")
    @ResponseBody
    public ResultData settleAccount(@RequestParam("startTime") String startTime,@RequestParam("endTime") String endTime,@RequestParam("venderId") Long venderId) {
        ResultData accountResult=accountApi.settleAccount(startTime,endTime,venderId);
        return accountResult;
    }
}
