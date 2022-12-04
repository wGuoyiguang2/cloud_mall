package com.cibnvideo.tcmalladmin.controller;

import com.cibnvideo.common.annotation.Log;
import com.cibnvideo.common.controller.BaseController;
import com.cibnvideo.common.dao.VenderDao;
import com.cibnvideo.common.entity.Vender;
import com.cibnvideo.common.entity.VenderSettlement;
import com.cibnvideo.common.service.VenderService;
import com.cibnvideo.common.utils.Result;
import com.cibnvideo.config.Router;
import com.cibnvideo.tcmalladmin.model.bean.OrderRefundVo;
import com.cibnvideo.tcmalladmin.omsapi.AccountApi;
import com.cibnvideo.tcmalladmin.bmsapi.OrderApi;
import com.cibnvideo.tcmalladmin.model.bean.AccountInfoVo;
import com.cibnvideo.tcmalladmin.model.bean.OrderManagerVo;
import com.cibnvideo.tcmalladmin.model.bean.VenderOrderManagerVo;
import com.cibnvideo.tcmalladmin.omsapi.VenderSettlementApi;
import com.hqtc.common.config.ErrorCode;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class OrderManagerController extends BaseController {
    @Autowired
    private OrderApi orderApi;
    @Autowired
    private VenderService venderService;
    @Autowired
    private VenderSettlementApi venderSettlementApi;
    @Autowired
    private VenderDao venderDao;
    @Autowired
    private AccountApi accountApi;
    //运营管理
    @Log("请求访问订单列表页面")
    @RequiresPermissions("mall:order:list")
    @GetMapping("/v1/tcmalladmin/orderHtml")
    public String orderHtml(Model model) {
        return "tcmalladmin/ordermanager/order";
    }

    @RequiresPermissions("mall:order:list")
    @GetMapping("/v1/tcmalladmin/orderList")
    @ResponseBody
    public Map<String,Object> orderList(@RequestParam Map<String, Object> params) {
        Map<String,Object> map=new HashMap<>();
        Long venderId = this.getVenderId();
        int total=0;
        int startIndex=0;
        int endIndex=0;
        if (venderId == null) {
            return null;
        }
        params.put("venderid", venderId);
        Result<OrderManagerVo> resultData = orderApi.orderList(params);
        List<OrderManagerVo> list=new ArrayList<>();
        if(resultData!=null){
            list=resultData.getRows();
            total=resultData.getTotal();
        }
        if(StringUtils.isNotEmpty((String)params.get("offset"))&&StringUtils.isNotEmpty((String)params.get("limit"))){
            startIndex=Integer.valueOf((String)params.get("offset"));
            endIndex=startIndex+Integer.valueOf((String)params.get("limit"));
        }
        map.put("total",total);
        map.put("rows",list);
        map.put("startIndex",startIndex+1);
        map.put("endIndex",endIndex);
        return map;
    }

    //大客户管理
    @Log("管理员请求访问订单列表页面")
    @RequiresPermissions("admin:order:list")
    @GetMapping("/v1/tcmalladmin/admin/orderHtml")
    public String adminOrderHtml0(Model model) {
        return "tcmalladmin/ordermanager/adminOrder0";
    }

    @RequiresPermissions("admin:order:list")
    @GetMapping("/v1/tcmalladmin/admin/venderOrderList")
    @ResponseBody
    public Result venderOrderList(@RequestParam Map<String, Object> params) {
        Result<VenderOrderManagerVo> resultData = new Result<>();
        if (StringUtils.isNotEmpty((String) params.get("pricePercent"))) {
            Result<VenderOrderManagerVo> result = venderSettlementApi.listVenderSettlementByParams(params);
            List<VenderOrderManagerVo> list0 =new ArrayList<>();
            if(result!=null){
                List<VenderOrderManagerVo> list =result.getRows();
                int total=result.getTotal();
                if(list!=null){
                    for (VenderOrderManagerVo vo : list) {
                        //从admin获取名称，从bms获取订单信息
                        Vender vender = venderDao.get(vo.getVenderId());
                        if(vender==null){
                            list0.add(vo);
                            continue;
                        }
                        vo.setVenderName(vender.getName());
                        //从oms获取账户信息
                        AccountInfoVo accountInfoVo = accountApi.getAccountInfo(vo.getVenderId());
                        //获取所有订单数量 bms
                        int count=orderApi.countOrderByVenderId(vo.getVenderId());
                        if (accountInfoVo != null) {
                            vo.setPaidMoney(accountInfoVo.getHasPayMoney());
                            vo.setUnPaidMoney(accountInfoVo.getNeedPayMoney());
                            vo.setSoldMoney(accountInfoVo.getSumPayMoney());
                            vo.setOrderCount(count);
                        }
                    }
                    list.removeAll(list0);
                }
                resultData.setTotal(total);
                resultData.setRows(list);
            }
        } else {
            //先从admin分页查询客户名称
            List<VenderOrderManagerVo> list = venderDao.listVenderOrderList(params);
            int total = venderDao.countVenderOrderList(params);
            if (list != null) {
                for (VenderOrderManagerVo vo : list) {
                    //从oms获取结算方式和余额
                    ResultData<VenderSettlement> result = venderSettlementApi.getVenderSettlement(vo.getVenderId());
                    if (result != null && result.getData() != null) {
                        vo.setAccountBalance(result.getData().getBalance());
                        vo.setPayType(result.getData().getSettlementType());
                        vo.setPricePercent(result.getData().getPricePercent());
                    }
                    //从oms获取账户信息
                    AccountInfoVo accountInfoVo = accountApi.getAccountInfo(vo.getVenderId());
                    int count=orderApi.countOrderByVenderId(vo.getVenderId());
                    if (accountInfoVo != null) {
                        vo.setPaidMoney(accountInfoVo.getHasPayMoney());
                        vo.setUnPaidMoney(accountInfoVo.getNeedPayMoney());
                        vo.setSoldMoney(accountInfoVo.getSumPayMoney());
                        vo.setOrderCount(count);
                    }
                }
            }
            resultData.setTotal(total);
            resultData.setRows(list);
        }
        return resultData;
    }

    @Log("管理员请求访问客户订单详情列表")
    @RequiresPermissions("admin:order:list")
    @GetMapping("/v1/tcmalladmin/admin/orderHtml2")
    public String adminOrderHtml(Model model, @RequestParam("venderId") Long venderId) {
        model.addAttribute("venderid", venderId);
        return "tcmalladmin/ordermanager/adminOrder";
    }

    @RequiresPermissions("admin:order:list")
    @GetMapping("/v1/tcmalladmin/admin/orderList")
    @ResponseBody
    public Map<String,Object> adminOrderList(@RequestParam Map<String, Object> params) {
        Map<String,Object> map=new HashMap<>();
        int total=0;
        int startIndex=0;
        int endIndex=0;
        Result<OrderManagerVo> resultData = orderApi.orderList(params);
        List<OrderManagerVo> list=new ArrayList<>();
        if(resultData!=null){
            list=resultData.getRows();
            total=resultData.getTotal();
        }
        if(StringUtils.isNotEmpty((String)params.get("offset"))&&StringUtils.isNotEmpty((String)params.get("limit"))){
            startIndex=Integer.valueOf((String)params.get("offset"));
            endIndex=startIndex+Integer.valueOf((String)params.get("limit"));
        }
        map.put("total",total);
        map.put("rows",list);
        map.put("startIndex",startIndex+1);
        map.put("endIndex",endIndex);
        return map;
    }

    @RequiresPermissions("mall:orderRefund:list")
    @GetMapping("/v1/tcmalladmin/orderRefundHtml")
    public String orderRefundList() {
        return "tcmalladmin/ordermanager/orderRefund";
    }

    @GetMapping("/v1/tcmalladmin/orderRefundList0")
    @RequiresPermissions("mall:orderRefund:list")
    @ResponseBody
    public Map<String,Object> listOrderRefund(@RequestParam Map<String,Object> params) {
        Long venderId=this.getVenderId();
        Map<String,Object> map=new HashMap<>();
        int total=0;
        int startIndex=0;
        int endIndex=0;
        params.put("venderId",venderId);
        ResultData<Result<OrderRefundVo>> resultResultData=orderApi.listOrderRefund(params);
        if(StringUtils.isNotEmpty((String)params.get("offset"))&&StringUtils.isNotEmpty((String)params.get("limit"))){
            startIndex=Integer.valueOf((String)params.get("offset"));
            endIndex=startIndex+Integer.valueOf((String)params.get("limit"));
        }
        Result result=resultResultData.getData();
        List<OrderRefundVo> list=new ArrayList<>();
        if(result!=null){
            total=result.getTotal();
            list=result.getRows();
        }
        map.put("total",total);
        map.put("rows",list);
        map.put("startIndex",startIndex+1);
        map.put("endIndex",endIndex);
        return map;
    }

    @RequiresPermissions("mall:refundUser:edit")
    @PostMapping(value = Router.ROUTER_REFUND_RETRY_USER)
    @ResponseBody
    ResultData refundUserRetry(@RequestParam("orderSn")String orderSn,@RequestParam("eventId")String eventId){
        ResultData resultData= Tools.getThreadResultData();
        resultData=orderApi.refundUserRetry(orderSn,eventId);
        return resultData;
    }
    @RequiresPermissions("mall:refundVender:edit")
    @PostMapping(value = Router.ROUTER_REFUND_RETRY_VENDER)
    @ResponseBody
    ResultData refundVenderRetry(@RequestParam("orderSn")String orderSn,@RequestParam("eventId")String eventId){
        ResultData resultData= Tools.getThreadResultData();
        resultData=orderApi.refundVenderRetry(orderSn,eventId);
        return resultData;
    }
}
