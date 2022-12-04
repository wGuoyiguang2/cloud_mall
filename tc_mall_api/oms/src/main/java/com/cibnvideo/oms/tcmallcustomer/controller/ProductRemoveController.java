package com.cibnvideo.oms.tcmallcustomer.controller;


import com.cibnvideo.oms.config.Router;
import com.cibnvideo.oms.tcmallcustomer.bean.ProductRemove;
import com.cibnvideo.oms.tcmallcustomer.service.ProductRemoveService;
import com.cibnvideo.oms.tcmallcustomer.service.SendMessageService;
import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.DataList;
import com.hqtc.common.response.ResultData;
import com.hqtc.common.utils.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class ProductRemoveController {

    @Autowired
    ProductRemoveService productRemoveService;

    @Autowired
    SendMessageService sendMessageService;

    @RequestMapping(value = Router.V1_OMS_PRODUCT_REMOVE_LIST, method = RequestMethod.GET)
    ResultData list(@RequestParam Map<String, Object> params){
        ResultData resultData = new ResultData();
        List<ProductRemove> productRemoveList = productRemoveService.list(params);
        int count = productRemoveService.count(params);
        DataList<List<ProductRemove>> result = new DataList<>();
        result.setData(productRemoveList);
        result.setTotalRows(count);
        resultData.setData(result);
        return resultData;
    }

    @RequestMapping(value = Router.V1_OMS_PRODUCT_REMOVE_COUNT, method = RequestMethod.GET)
    ResultData count(@RequestParam Map<String, Object> params){
        ResultData<Integer> resultData = Tools.getThreadResultData();
        int count = productRemoveService.count(params);
        resultData.setData(count);
        return resultData;
    }

    @RequestMapping(value = Router.V1_OMS_PRODUCT_REMOVE_GET, method = RequestMethod.GET)
    ResultData get(@RequestParam Integer id){
        ResultData<ProductRemove> resultData = Tools.getThreadResultData();
        resultData.setData(productRemoveService.get(id));
        return resultData;
    }

    @RequestMapping(value = Router.V1_OMS_PRODUCT_REMOVE_SKUS_BY_VENDERID, method = RequestMethod.GET)
    ResultData skusByVenderId(@PathVariable("venderId") Integer venderId){
        ResultData<List<Long>> resultData = new ResultData<>();
        resultData.setData(productRemoveService.skusByVenderId(venderId));
        return resultData;
    }

    /**
     * 商品下架
     * @param productRemove
     * @return
     */
    @RequestMapping(value = Router.V1_OMS_PRODUCT_REMOVE_ADD, method = RequestMethod.POST)
    ResultData add(@RequestBody ProductRemove productRemove){
        ResultData<Integer> resultData = Tools.getThreadResultData();
        int result = productRemoveService.save(productRemove);
        resultData.setData(result);
        if(result > 0){
            sendMessageService.productRemove(productRemove.getVenderId(), productRemove.getSku());
        }
        return resultData;
    }

    /**
     * 批量商品下架
     * @param productRemoves
     * @return
     */
    @RequestMapping(value = Router.V1_OMS_PRODUCT_REMOVE_BATCH_ADD, method = RequestMethod.POST)
    ResultData batchAdd(@RequestBody List<ProductRemove> productRemoves){
        ResultData<Integer> resultData = Tools.getThreadResultData();
        int result = productRemoveService.batchSave(productRemoves);
        resultData.setData(result);
        if(result > 0){
            for(ProductRemove productRemove:productRemoves) {
                sendMessageService.productRemove(productRemove.getVenderId(), productRemove.getSku());
            }
        }
        return resultData;
    }

    /**
     * 商品恢复上架
     * @param id
     * @return
     */
    @RequestMapping(value = Router.V1_OMS_PRODUCT_REMOVE_REMOVE, method = RequestMethod.GET)
    ResultData remove(@RequestParam Integer id){
        ResultData<Integer> resultData = Tools.getThreadResultData();
        ProductRemove productRemove = productRemoveService.get(id);
        if(productRemove != null){
            int result = productRemoveService.remove(id);
            resultData.setData(result);
            if(result > 0){
                sendMessageService.productRemoveRevert(productRemove.getVenderId(), productRemove.getSku());
            }
        }else {
            resultData.setError(ErrorCode.SERVER_EXCEPTION);
            resultData.setMsg("product remove revert, id not exist");
        }

        return resultData;
    }

    /**
     * 根据venderId及sku恢复商品上架
     * @param venderId
     * @param sku
     * @return
     */
    @RequestMapping(value = Router.V1_OMS_PRODUCT_REMOVE_REMOVE_BY_SKU, method = RequestMethod.GET)
    ResultData removeBySku(@PathVariable("venderId") Integer venderId, @RequestParam("sku") Long sku){
        ResultData<Integer> resultData = Tools.getThreadResultData();
        resultData.setData(productRemoveService.removeBySku(venderId, sku));
        return resultData;
    }


    /**
     * 根据id批量恢复上架商品
     * @param params
     * @return
     */
    @RequestMapping(value = Router.V1_OMS_PRODUCT_REMOVE_BATCHREMOVE, method = RequestMethod.POST)
    ResultData batchRemove(@RequestBody Map<String, Object> params){
        ResultData<Integer> resultData = Tools.getThreadResultData();
        if(params.containsKey("venderId") && params.containsKey("ids")){
            int venderId = (int)params.get("venderId");
            List<Integer> ids = (List<Integer>)params.get("ids");
            List<Long> skus = new ArrayList<Long>();
            for(Integer id:ids){
                ProductRemove productRemove = productRemoveService.get(id);
                skus.add(productRemove.getSku());
            }
            int result = productRemoveService.batchRemove(params);
            resultData.setData(result);
            if(result > 0){
                for(Long sku:skus){
                    sendMessageService.productRemoveRevert(venderId, sku);
                }
            }
        }else {
            resultData.setError(ErrorCode.PARAM_ERROR);
            resultData.setMsg("product remove batch revert, venderId or skus not exist");
        }

        return resultData;
    }

    /**
     * 根据venderId及sku批量恢复商品上架
     * @param productRemoves
     * @return
     */
    @RequestMapping(value = Router.V1_OMS_PRODUCT_REMOVE_BATCH_REMOVE_BY_SKUS, method = RequestMethod.POST)
    ResultData batchRemoveBySku(@RequestBody List<ProductRemove> productRemoves){
        ResultData<Integer> resultData = Tools.getThreadResultData();
        int result = productRemoveService.batchRemoveBySku(productRemoves);
        resultData.setData(result);
        if(result > 0){
            for(ProductRemove productRemove:productRemoves) {
                sendMessageService.productRemoveRevert(productRemove.getVenderId(), productRemove.getSku());
            }
        }
        return resultData;
    }
}
