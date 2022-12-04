package com.cibnvideo.oms.tcmallcustomer.controller;


import com.cibnvideo.oms.config.Router;
import com.cibnvideo.oms.tcmallcustomer.bean.PriceRate;
import com.cibnvideo.oms.tcmallcustomer.service.RatePricePolicyService;
import com.cibnvideo.oms.tcmallcustomer.service.SendMessageService;
import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.DataList;
import com.hqtc.common.response.ResultData;
import com.hqtc.common.utils.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class RatePricePolicyController {

    @Autowired
    RatePricePolicyService ratePricePolicyService;

    @Autowired
    SendMessageService sendMessageService;

    @RequestMapping(value = Router.V1_OMS_PRICE_POLICY_RATE_LIST, method = RequestMethod.GET)
    ResultData list(@RequestParam Map<String, Object> params){
        ResultData resultData = Tools.getThreadResultData();
        List<PriceRate> priceRateList = ratePricePolicyService.list(params);
        int count = ratePricePolicyService.count(params);
        DataList<List<PriceRate>> result = Tools.getThreadDataList();
        result.setData(priceRateList);
        result.setTotalRows(count);
        resultData.setData(result);
        return resultData;
    }

    @RequestMapping(value = Router.V1_OMS_PRICE_POLICY_RATE_COUNT, method = RequestMethod.GET)
    ResultData count(@RequestParam Map<String, Object> params){
        ResultData<Integer> resultData = Tools.getThreadResultData();
        int count = ratePricePolicyService.count(params);
        resultData.setData(count);
        return resultData;
    }

    @RequestMapping(value = Router.V1_OMS_PRICE_POLICY_RATE_GET, method = RequestMethod.GET)
    ResultData get(@RequestParam Integer id){
        ResultData<PriceRate> resultData = Tools.getThreadResultData();
        resultData.setData(ratePricePolicyService.get(id));
        return resultData;
    }

    @Transactional
    @RequestMapping(value = Router.V1_OMS_PRICE_POLICY_RATE_ADD, method = RequestMethod.POST)
    ResultData add(@RequestBody PriceRate priceRate){
        ResultData<Integer> resultData = Tools.getThreadResultData();
        Integer venderId = priceRate.getVenderid();
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("venderid", venderId);
        List<PriceRate> priceRateList = ratePricePolicyService.list(params);
        if(priceRateList != null && priceRateList.size() > 0) {
            for(PriceRate rate:priceRateList) {
                if(rate.isOverFlow(priceRate)) {
                    resultData.setError(ErrorCode.PARAM_ERROR);
                    resultData.setMsg("区间重叠");
                    return resultData;
                }
            }
        }

        int result = ratePricePolicyService.save(priceRate);
        resultData.setData(result);
        if(result > 0){
            sendMessageService.priceProductAllChange(priceRate.getVenderid());
        }
        return resultData;
    }

    @RequestMapping(value = Router.V1_OMS_PRICE_POLICY_RATE_REMOVE, method = RequestMethod.GET)
    ResultData remove(@RequestParam Integer id){
        ResultData<Integer> resultData = Tools.getThreadResultData();
        PriceRate priceRate = ratePricePolicyService.get(id);
        if(priceRate != null){
            int result = ratePricePolicyService.remove(id);
            resultData.setData(result);
            if(result>0){
                sendMessageService.priceProductAllChange(priceRate.getVenderid());
            }
        }else {
            resultData.setError(ErrorCode.SERVER_EXCEPTION);
            resultData.setMsg("price collection not exist, id = " + id);
        }
        return resultData;
    }

    @RequestMapping(value = Router.V1_OMS_PRICE_POLICY_RATE_BATCHREMOVE, method = RequestMethod.POST)
    ResultData batchRemove(@RequestBody Map<String, Object> params){
        ResultData<Integer> resultData = Tools.getThreadResultData();
        int result = ratePricePolicyService.batchRemove(params);
        resultData.setData(result);
        if(result > 0){
            if(params.containsKey("venderid") && params.containsKey("policyid") && params.containsKey("ids")){
                int venderId = (int)params.get("venderid");
                sendMessageService.priceProductAllChange(venderId);
            }
        }
        return resultData;
    }
}
