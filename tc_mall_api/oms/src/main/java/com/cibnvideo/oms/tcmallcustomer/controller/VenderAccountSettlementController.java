package com.cibnvideo.oms.tcmallcustomer.controller;

import com.cibnvideo.oms.bean.Result;
import com.cibnvideo.oms.config.Router;
import com.cibnvideo.oms.tcmallcustomer.bean.AccountInfoVo;
import com.cibnvideo.oms.tcmallcustomer.bean.AccountManagerVo;
import com.cibnvideo.oms.tcmallcustomer.bean.VenderAccountVo;
import com.cibnvideo.oms.tcmallcustomer.bean.VenderSettlement;
import com.cibnvideo.oms.tcmallcustomer.service.VenderAccountSettlementService;
import com.cibnvideo.oms.tcmallcustomer.service.VenderSettlementService;
import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.DataList;
import com.hqtc.common.response.ResultData;
import com.hqtc.common.utils.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/7/27 20:55
 */
@RestController
public class VenderAccountSettlementController {

    @Autowired
    private VenderAccountSettlementService venderAccountSettlementService;
    @Autowired
    private VenderSettlementService venderSettlementService;
    @PostMapping(Router.OMS_ACCOUNT_INFO_GET)
    public AccountInfoVo getAccountInfo(@RequestParam("venderid") Long venderId){
        AccountInfoVo accountInfoVo=venderAccountSettlementService.getAccountInfo(venderId);
        return accountInfoVo;
    }

    @PostMapping(Router.OMS_ACCOUNT_MANAGER_LIST)
    public Result<AccountManagerVo> accountManagerList(@RequestParam Map<String,Object> params){
        Result<AccountManagerVo> result=new Result<>();
        List<AccountManagerVo> list=venderAccountSettlementService.accountManagerList(params);
        int total=venderAccountSettlementService.countAccountManagerList(params);
        result.setRows(list);
        result.setTotal(total);
        return result;
    }

    @PostMapping(Router.OMS_ACCOUNT_GET_ACCOUNT_LIST)
    public ResultData<List<AccountManagerVo>> getAccountListByOrders(@RequestBody List<String> orderList){
        ResultData resultData= Tools.getThreadResultData();
        List<AccountManagerVo> accountManagerVoList=venderAccountSettlementService.getAccountListByOrders(orderList);
        resultData.setData(accountManagerVoList);
        return resultData;
    }

    /**
     * 客户按月结算，结算操作
     * @param startTime
     * @param endTime
     * @param venderId
     * @return
     */
    @PostMapping(Router.OMS_ACCOUNT_SETTLE)
    public ResultData settleAccount(@RequestParam("startTime") String startTime, @RequestParam("endTime") String endTime, @RequestParam("venderId")Long venderId){
        ResultData resultData=Tools.getThreadResultData();
        VenderSettlement venderSettlement = venderSettlementService.get(venderId);
        if(venderSettlement==null){
            resultData.setMsg("无该大客户！");
            resultData.setError(ErrorCode.FALI);
            return resultData;
        }
        if(venderSettlement.getSettlementType()!=1){
            resultData.setMsg("该大客户结算方式不为按月结算，无需结算！");
            resultData.setError(ErrorCode.FALI);
            return resultData;
        }
        int count=venderAccountSettlementService.settleAccount(startTime,endTime,venderId);
        if(count>0){
            resultData.setMsg("结算成功！账单条数："+count);
            resultData.setData(count);
        }else{
            resultData.setMsg("无待结算账单！");
            resultData.setData(count);
        }
        return resultData;
    }

    @PostMapping(Router.OMS_ACCOUNT_GET_VENDER_ACCOUNT_LIST)
    public ResultData<DataList<List<VenderAccountVo>>> getVenderAccountList(@RequestParam Map<String,Object> map){
        ResultData resultData=Tools.getThreadResultData();
        DataList<List<VenderAccountVo>> result = Tools.getThreadDataList();
        List<VenderAccountVo> list=venderAccountSettlementService.getVenderAccountList(map);
        int total=venderAccountSettlementService.countVenderAccountList(map);
        result.setTotalRows(total);
        result.setData(list);
        resultData.setData(result);
        return resultData;
    }
    @PostMapping(Router.V1_OMS_FREEFREIGHT_SUMBY_VENDERID)
    public ResultData<BigDecimal> sumFreightByVenderId(@RequestParam("venderId") int venderId){
        ResultData<BigDecimal> resultData=Tools.getThreadResultData();
        BigDecimal sumFreight=venderAccountSettlementService.sumFreightByVenderId(venderId);
        resultData.setData(sumFreight);
        return resultData;
    }
}
