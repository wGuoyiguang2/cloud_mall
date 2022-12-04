package com.cibnvideo.oms.tcmallcustomer.controller;


import com.cibnvideo.oms.config.Router;
import com.cibnvideo.oms.tcmallcustomer.bean.PriceCollection;
import com.cibnvideo.oms.tcmallcustomer.service.CollectionPricePolicyService;
import com.cibnvideo.oms.tcmallcustomer.service.SendMessageService;
import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.DataList;
import com.hqtc.common.response.ResultData;
import com.hqtc.common.utils.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class CollectionPricePolicyController {

    @Autowired
    CollectionPricePolicyService collectionPricePolicyService;

    @Autowired
    SendMessageService sendMessageService;

    @RequestMapping(value = Router.V1_OMS_PRICE_POLICY_COLLECTION_LIST, method = RequestMethod.GET)
    ResultData list(@RequestParam Map<String, Object> params){
        ResultData resultData = Tools.getThreadResultData();
        List<PriceCollection> priceCollectionList = collectionPricePolicyService.list(params);
        int count = collectionPricePolicyService.count(params);
        DataList<List<PriceCollection>> result = Tools.getThreadDataList();
        result.setData(priceCollectionList);
        result.setTotalRows(count);
        resultData.setData(result);
        return resultData;
    }

    @RequestMapping(value = Router.V1_OMS_PRICE_POLICY_COLLECTION_COUNT, method = RequestMethod.GET)
    ResultData count(@RequestParam Map<String, Object> params){
        ResultData<Integer> resultData = Tools.getThreadResultData();
        int count = collectionPricePolicyService.count(params);
        resultData.setData(count);
        return resultData;
    }

    @RequestMapping(value = Router.V1_OMS_PRICE_POLICY_COLLECTION_GET, method = RequestMethod.GET)
    ResultData get(@RequestParam Integer id){
        ResultData<PriceCollection> resultData = Tools.getThreadResultData();
        resultData.setData(collectionPricePolicyService.get(id));
        return resultData;
    }

    @RequestMapping(value = Router.V1_OMS_PRICE_POLICY_COLLECTION_ADD, method = RequestMethod.POST)
    ResultData add(@RequestBody PriceCollection priceCollection){
        ResultData<Integer> resultData = Tools.getThreadResultData();
        int result = collectionPricePolicyService.save(priceCollection);
        resultData.setData(result);
        if(result > 0){
            sendMessageService.priceProductCollectionChange(priceCollection.getVenderid(), priceCollection.getCollectionid());
        }
        return resultData;
    }

    @RequestMapping(value = Router.V1_OMS_PRICE_POLICY_COLLECTION_REMOVE, method = RequestMethod.GET)
    ResultData remove(@RequestParam Integer id){
        ResultData<Integer> resultData = Tools.getThreadResultData();
        PriceCollection priceCollection = collectionPricePolicyService.get(id);
        if(priceCollection != null){
            int result = collectionPricePolicyService.remove(id);
            resultData.setData(result);
            if(result>0){
                sendMessageService.priceProductCollectionChange(priceCollection.getVenderid(), priceCollection.getCollectionid());
            }
        }else {
            resultData.setError(ErrorCode.SERVER_EXCEPTION);
            resultData.setMsg("price collection not exist, id = " + id);
        }
        return resultData;
    }

    @RequestMapping(value = Router.V1_OMS_PRICE_POLICY_COLLECTION_BATCHREMOVE, method = RequestMethod.POST)
    ResultData batchRemove(@RequestBody Map<String, Object> params){
        ResultData<Integer> resultData = Tools.getThreadResultData();
        int result = collectionPricePolicyService.batchRemove(params);
        resultData.setData(result);
        if(result > 0){
            if(params.containsKey("venderid") && params.containsKey("policyid") && params.containsKey("ids")){
                int venderId = (int)params.get("venderid");
                List<Integer> ids = (List<Integer>)params.get("ids");
                for(Integer id:ids){
                    PriceCollection priceCollection = collectionPricePolicyService.get(id);
                    if(priceCollection!=null){
                        sendMessageService.priceProductCollectionChange(venderId, priceCollection.getCollectionid());
                    }
                }
            }
        }
        return resultData;
    }
}
