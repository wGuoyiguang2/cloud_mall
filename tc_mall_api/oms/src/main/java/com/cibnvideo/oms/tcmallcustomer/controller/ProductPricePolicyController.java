package com.cibnvideo.oms.tcmallcustomer.controller;


import com.cibnvideo.oms.config.Router;
import com.cibnvideo.oms.tcmallcustomer.bean.PriceProduct;
import com.cibnvideo.oms.tcmallcustomer.service.ProductPricePolicyService;
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
public class ProductPricePolicyController {

    @Autowired
    ProductPricePolicyService productPricePolicyService;

    @Autowired
    SendMessageService sendMessageService;

    @RequestMapping(value = Router.V1_OMS_PRICE_POLICY_PRODUCT_LIST, method = RequestMethod.GET)
    ResultData list(@RequestParam Map<String, Object> params){
        ResultData resultData = Tools.getThreadResultData();
        List<PriceProduct> priceProductList = productPricePolicyService.list(params);
        int count = productPricePolicyService.count(params);
        DataList<List<PriceProduct>> result = Tools.getThreadDataList();
        result.setData(priceProductList);
        result.setTotalRows(count);
        resultData.setData(result);
        return resultData;
    }

    @RequestMapping(value = Router.V1_OMS_PRICE_POLICY_PRODUCT_COUNT, method = RequestMethod.GET)
    ResultData count(@RequestParam Map<String, Object> params){
        ResultData<Integer> resultData = Tools.getThreadResultData();
        int count = productPricePolicyService.count(params);
        resultData.setData(count);
        return resultData;
    }

    @RequestMapping(value = Router.V1_OMS_PRICE_POLICY_PRODUCT_GET, method = RequestMethod.GET)
    ResultData get(@RequestParam Integer id){
        ResultData<PriceProduct> resultData = Tools.getThreadResultData();
        resultData.setData(productPricePolicyService.get(id));
        return resultData;
    }

    @RequestMapping(value = Router.V1_OMS_PRICE_POLICY_PRODUCT_ADD, method = RequestMethod.POST)
    ResultData add(@RequestBody PriceProduct priceProduct){
        ResultData<Integer> resultData = Tools.getThreadResultData();
        int result = productPricePolicyService.save(priceProduct);
        resultData.setData(result);
        if(result > 0){
            sendMessageService.priceProductChange(priceProduct.getVenderId(), priceProduct.getSku());
        }
        return resultData;
    }

    @RequestMapping(value = Router.V1_OMS_PRICE_POLICY_PRODUCT_UPDATE, method = RequestMethod.POST)
    ResultData update(@RequestBody PriceProduct priceProduct){
        ResultData<Integer> resultData = Tools.getThreadResultData();
        int result = productPricePolicyService.update(priceProduct);
        resultData.setData(result);
        if(result>0){
            sendMessageService.priceProductChange(priceProduct.getVenderId(), priceProduct.getSku());
        }
        return resultData;
    }

    @RequestMapping(value = Router.V1_OMS_PRICE_POLICY_PRODUCT_REMOVE, method = RequestMethod.GET)
    ResultData remove(@RequestParam Integer id){
        ResultData<Integer> resultData = Tools.getThreadResultData();
        PriceProduct priceProduct = productPricePolicyService.get(id);
        if(priceProduct != null){
            int result = productPricePolicyService.remove(id);
            resultData.setData(result);
            if(result>0){
                sendMessageService.priceProductChange(priceProduct.getVenderId(), priceProduct.getSku());
            }
        }else {
            resultData.setError(ErrorCode.SERVER_EXCEPTION);
            resultData.setMsg("product price policy not exist");
        }

        return resultData;
    }

    @RequestMapping(value = Router.V1_OMS_PRICE_POLICY_PRODUCT_BATCHREMOVE, method = RequestMethod.POST)
    ResultData batchRemove(@RequestBody Map<String, Object> params){
        ResultData<Integer> resultData = Tools.getThreadResultData();
        if(params.containsKey("venderId") && params.containsKey("skus")){
            int result = productPricePolicyService.batchRemove(params);
            resultData.setData(result);
            int venderId = (int)params.get("venderId");
            List<Long> skus = (List<Long>)params.get("skus");
            for(Object sku:skus){
                if(sku instanceof Integer) {
                    sendMessageService.priceProductChange(venderId, ((Integer) sku).longValue());
                } else if(sku instanceof Long) {
                    sendMessageService.priceProductChange(venderId, (Long)sku);
                } else {
                    resultData.setError(ErrorCode.SERVER_EXCEPTION);
                    resultData.setMsg("sku type is unknow");
                }
            }
        }else {
            resultData.setError(ErrorCode.SERVER_EXCEPTION);
            resultData.setMsg("venderId or skus not is null");
        }

        return resultData;
    }
}
