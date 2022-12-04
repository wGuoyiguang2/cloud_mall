package com.cibnvideo.oms.tcmallcustomer.controller;

import com.cibnvideo.oms.config.Router;
import com.cibnvideo.oms.tcmallcustomer.bean.PriceCategory;
import com.cibnvideo.oms.tcmallcustomer.bean.PriceCollection;
import com.cibnvideo.oms.tcmallcustomer.bean.PricePolicy;
import com.cibnvideo.oms.tcmallcustomer.service.*;
import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.DataList;
import com.hqtc.common.response.ResultData;
import com.hqtc.common.utils.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class PricePolicyController {

    @Autowired
    PricePolicyService pricePolicyService;

    @Autowired
    CategoryPricePolicyService categoryPricePolicyService;

    @Autowired
    CollectionPricePolicyService collectionPricePolicyService;

    @Autowired
    VenderSettlementService venderSettlementService;

    @Autowired
    SendMessageService sendMessageService;

    @RequestMapping(value = Router.V1_OMS_PRICE_POLICY_LIST, method = RequestMethod.GET)
    ResultData list(@RequestParam Map<String, Object> params){
        ResultData resultData = Tools.getThreadResultData();
        List<PricePolicy> pricePolicyList = pricePolicyService.list(params);
        int count = pricePolicyService.count(params);
        DataList<List<PricePolicy>> result = Tools.getThreadDataList();
        result.setData(pricePolicyList);
        result.setTotalRows(count);
        resultData.setData(result);
        return resultData;
    }

    @RequestMapping(value = Router.V1_OMS_PRICE_POLICY_COUNT, method = RequestMethod.GET)
    ResultData count(@RequestParam Map<String, Object> params){
        ResultData<Integer> resultData = Tools.getThreadResultData();
        int count = pricePolicyService.count(params);
        resultData.setData(count);
        return resultData;
    }

    @RequestMapping(value = Router.V1_OMS_PRICE_POLICY_GET, method = RequestMethod.GET)
    ResultData get(@RequestParam Integer id){
        ResultData<PricePolicy> resultData = Tools.getThreadResultData();
        resultData.setData(pricePolicyService.get(id));
        return resultData;
    }

    @RequestMapping(value = Router.V1_OMS_PRICE_POLICY_ADD, method = RequestMethod.POST)
    ResultData add(@RequestBody PricePolicy pricePolicy){
        ResultData<Integer> resultData = Tools.getThreadResultData();
        int result = pricePolicyService.save(pricePolicy);
        resultData.setData(result);
        if(result > 0 && pricePolicy.getType() == 0){
            sendMessageService.priceProductAllChange(pricePolicy.getVenderId());
        }
        return resultData;
    }

    @RequestMapping(value = Router.V1_OMS_PRICE_POLICY_REMOVE, method = RequestMethod.GET)
    ResultData remove(@RequestParam Integer id){
        ResultData<Integer> resultData = Tools.getThreadResultData();
        PricePolicy pricePolicy = pricePolicyService.get(id);
        if(pricePolicy != null){
            int result = pricePolicyService.remove(id);
            resultData.setData(result);
            if(result > 0 && pricePolicy.getType() == 0){
                sendMessageService.priceProductAllChange(pricePolicy.getVenderId());
            }
        }else {
            resultData.setError(ErrorCode.SERVER_EXCEPTION);
            resultData.setMsg("price policy not exist, id = " + id);
        }
        return resultData;
    }

    @RequestMapping(value = Router.V1_OMS_PRICE_POLICY_UPDATE, method = RequestMethod.POST)
    ResultData update(@RequestBody PricePolicy pricePolicy){
        ResultData<Integer> resultData = Tools.getThreadResultData();
        int result = pricePolicyService.update(pricePolicy);
        resultData.setData(result);
        if(result > 0){
            if(pricePolicy.getType() == 0){//全站
                sendMessageService.priceProductAllChange(pricePolicy.getVenderId());
            }else if(pricePolicy.getType() == 1){//分类
                HashMap<String, Object> params = new HashMap<String, Object>();
                params.put("venderid", pricePolicy.getVenderId());
                params.put("policyid", pricePolicy.getId());
                List<PriceCategory> priceCategoryList = categoryPricePolicyService.list(params);
                for(PriceCategory p:priceCategoryList){
                    int catId=0;
                    int catType=-1;
                    if(p.getCat2() != null && p.getCat2() != 0){
                        catId = p.getCat2();
                        catType = 2;
                    }else if(p.getCat1() != null && p.getCat1() != 0){
                        catId = p.getCat1();
                        catType = 1;
                    }else if(p.getCat0() != null && p.getCat0() != 0){
                        catId = p.getCat0();
                        catType = 0;
                    }
                    sendMessageService.priceProductCategoryChange(pricePolicy.getVenderId(),catId, catType);
                }
            }else if(pricePolicy.getType() == 2){//商品集
                HashMap<String, Object> params = new HashMap<String, Object>();
                params.put("venderid", pricePolicy.getVenderId());
                params.put("policyid", pricePolicy.getId());
                List<PriceCollection> priceCollectionList = collectionPricePolicyService.list(params);
                for(PriceCollection p:priceCollectionList){
                    sendMessageService.priceProductCollectionChange(pricePolicy.getVenderId(), p.getCollectionid());
                }
            }

        }
        return resultData;
    }

    @RequestMapping(value = Router.V1_OMS_PRICE_POLICY_LIST_BY_COLLECTIONID, method = RequestMethod.GET)
    ResultData getPricePolicesByCollectionId(@PathVariable("venderId") Integer venderId, @RequestParam("collectionId") Integer collectionId){
        ResultData<List<PricePolicy>> resultData = Tools.getThreadResultData();
        List<PricePolicy> pricePolicyList = pricePolicyService.getPricePolicyByCollectionId(venderId, collectionId);
        resultData.setData(pricePolicyList);
        return resultData;
    }

    @RequestMapping(value = Router.V1_OMS_PRICE_POLICY_MAX, method = RequestMethod.GET)
    ResultData getMaxPricePolicy(@RequestParam("venderId") Integer venderId){
        ResultData<BigDecimal> resultData = Tools.getThreadResultData();
        BigDecimal percent = pricePolicyService.getMaxPricePolicy(venderId);
        BigDecimal tradePercent = venderSettlementService.pricePercentGet(venderId);
        if(tradePercent == null) {
            resultData.setError(ErrorCode.SERVER_EXCEPTION);
            resultData.setMsg("大客户系数为空");
        }else {
            if(percent == null){
                percent = tradePercent;
            }else {
                percent = percent.multiply(tradePercent);
            }
            resultData.setData(percent==null? BigDecimal.ONE:percent);
        }

        return resultData;
    }
}
