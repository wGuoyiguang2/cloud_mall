package com.cibnvideo.oms.tcmallcustomer.controller;


import com.cibnvideo.oms.config.Router;
import com.cibnvideo.oms.jdsyncapi.ProductApi;
import com.cibnvideo.oms.tcmallcustomer.bean.ProductOfCollection;
import com.cibnvideo.oms.tcmallcustomer.service.ProductOfCollectionService;
import com.cibnvideo.oms.tcmallcustomer.service.SendMessageService;
import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.DataList;
import com.hqtc.common.response.ResultData;
import com.hqtc.common.utils.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ProductOfCollectionController {

    @Autowired
    ProductOfCollectionService productOfCollectionService;

    @Autowired
    ProductApi productApi;

    @Autowired
    SendMessageService sendMessageService;

    @RequestMapping(value = Router.V1_OMS_PRODUCT_OF_COLLECTION_LIST, method = RequestMethod.GET)
    ResultData list(@RequestParam Map<String, Object> params){
        ResultData resultData = Tools.getThreadResultData();
        List<ProductOfCollection> productOfCollectionList = productOfCollectionService.list(params);
        int count = productOfCollectionService.count(params);
        DataList<List<ProductOfCollection>> result = Tools.getThreadDataList();
        result.setData(productOfCollectionList);
        result.setTotalRows(count);
        resultData.setData(result);
        return resultData;
    }

    @RequestMapping(value = Router.V1_OMS_PRODUCT_OF_COLLECTION_GET, method = RequestMethod.GET)
    ResultData get(@RequestParam Integer id){
        ResultData<ProductOfCollection> resultData = Tools.getThreadResultData();
        resultData.setData(productOfCollectionService.get(id));
        return resultData;
    }

    @RequestMapping(value = Router.V1_OMS_PRODUCT_OF_COLLECTION_COUNT, method = RequestMethod.GET)
    ResultData count(@RequestParam Map<String, Object> params){
        ResultData<Integer> resultData = Tools.getThreadResultData();
        resultData.setData(productOfCollectionService.count(params));
        return resultData;
    }

    @RequestMapping(value = Router.V1_OMS_PRODUCT_OF_COLLECTION_ADD, method = RequestMethod.POST)
    ResultData add(@RequestBody ProductOfCollection productOfCollection){
        ResultData<Integer> resultData = Tools.getThreadResultData();
        int result = productOfCollectionService.save(productOfCollection);
        resultData.setData(result);
        if(result > 0){
            sendMessageService.priceProductChange(productOfCollection.getVenderId(), productOfCollection.getSku());
        }
        return resultData;
    }

    @RequestMapping(value = Router.V1_OMS_PRODUCT_OF_COLLECTION_BATCH_ADD, method = RequestMethod.POST)
    ResultData batchadd(@RequestBody List<ProductOfCollection> productOfCollections){
        ResultData<Integer> resultData = Tools.getThreadResultData();
        int result = productOfCollectionService.batchSave(productOfCollections);
        resultData.setData(result);
        if(result > 0){
            for(ProductOfCollection p:productOfCollections){
                sendMessageService.priceProductChange(p.getVenderId(), p.getSku());
            }
        }
        return resultData;
    }

    @RequestMapping(value = Router.V1_OMS_PRODUCT_OF_COLLECTION_REMOVE, method = RequestMethod.GET)
    ResultData remove(@RequestParam Integer id){
        ResultData<Integer> resultData = Tools.getThreadResultData();
        ProductOfCollection productOfCollection = productOfCollectionService.get(id);
        if(productOfCollection != null){
            int result = productOfCollectionService.remove(id);
            resultData.setData(result);
            if(result > 0){
                sendMessageService.priceProductChange(productOfCollection.getVenderId(), productOfCollection.getSku());
            }
        }else {
            resultData.setError(ErrorCode.SERVER_EXCEPTION);
            resultData.setMsg("product of collection remove id not exist, " + id);
        }

        return resultData;
    }

    @RequestMapping(value = Router.V1_OMS_PRODUCT_OF_COLLECTION_BATCH_REMOVE, method = RequestMethod.POST)
    ResultData batchRemove(@RequestBody Map<String, Object> map){
        ResultData<Integer> resultData = Tools.getThreadResultData();
        int result = productOfCollectionService.batchRemove(map);
        resultData.setData(result);
        if(result > 0){
            if(map.containsKey("skus") && map.containsKey("venderId")){
                Integer venderId = (Integer)map.get("venderId");
                List<Object> skus = (List<Object>)map.get("skus");
                for(Object skuObj:skus){
                    if(skuObj instanceof Integer) {
                        Long sku = Long.valueOf((Integer)skuObj);
                        sendMessageService.priceProductChange(venderId, sku);
                    }else if(skuObj instanceof Long) {
                        sendMessageService.priceProductChange(venderId, (Long)skuObj);
                    }

                }
            }
        }
        return resultData;
    }

    @RequestMapping(value = Router.V1_OMS_PRODUCTCOLLECTION_BRANDNAMES_BY_ID, method = RequestMethod.GET)
    ResultData<List<String>> getBrandNameBySkus(@RequestParam Integer collectionId,
                                                @RequestParam String removeSkus){
        HashMap<String, Object> params = new HashMap<String, Object>(1);
        params.put("collectionId", collectionId);
        List<Long> skus = new ArrayList<Long>();
        List<ProductOfCollection> productOfCollections = productOfCollectionService.list(params);
        for(ProductOfCollection p:productOfCollections){
            skus.add(p.getSku());
        }

        List<Long> newSkus = new ArrayList<>();
        if(skus.size() != 0 && removeSkus !=null && !"".equals(removeSkus)){
            for(Long sku:skus){
                if(!removeSkus.contains(String.valueOf(sku))){
                    newSkus.add(sku);
                }
            }
        }
        skus=newSkus !=null && newSkus.size() !=0 ? newSkus:skus;
        if(skus.size() == 0){
            ResultData<List<String>> resultData = Tools.getThreadResultData();
            resultData.setData(new ArrayList<>());
            return resultData;
        }else {
            ResultData<List<String>> resultData = productApi.getBrandNamesBySkus(skus);
            return resultData;
        }
    }

    @RequestMapping(value = Router.V1_OMS_PRODUCT_OF_COLLECTION_ID_BY_SKU, method = RequestMethod.GET)
    ResultData<Integer> getCollectionIdBySkus(@RequestParam Integer venderId,
                                                @RequestParam Long sku){
        ResultData resultData = Tools.getThreadResultData();
        resultData.setData(productOfCollectionService.getCollectionIdBySku(venderId, sku));
        return resultData;
    }

}
