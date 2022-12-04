package com.cibnvideo.oms.tcmallcustomer.controller;


import com.cibnvideo.oms.config.Router;
import com.cibnvideo.oms.tcmallcustomer.bean.ProductCollection;
import com.cibnvideo.oms.tcmallcustomer.service.ProductCollectionService;
import com.hqtc.common.response.DataList;
import com.hqtc.common.response.ResultData;
import com.hqtc.common.utils.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class ProductCollectionController {

    @Autowired
    ProductCollectionService productCollectionService;

    @RequestMapping(value = Router.V1_OMS_PRODUCTCOLLECTION_LIST, method = RequestMethod.GET)
    ResultData list(@RequestParam Map<String, Object> params){
        ResultData resultData = Tools.getThreadResultData();
        List<ProductCollection> productCollectionList = productCollectionService.list(params);
        int count = productCollectionService.count(params);
        DataList<List<ProductCollection>> result = Tools.getThreadDataList();
        result.setData(productCollectionList);
        result.setTotalRows(count);
        resultData.setData(result);
        return resultData;
    }

    @RequestMapping(value = Router.V1_OMS_PRODUCTCOLLECTION_GET, method = RequestMethod.GET)
    ResultData get(@RequestParam Integer id){
        ResultData<ProductCollection> resultData = Tools.getThreadResultData();
        resultData.setData(productCollectionService.get(id));
        return resultData;
    }

    @RequestMapping(value = Router.V1_OMS_PRODUCTCOLLECTION_ADD, method = RequestMethod.POST)
    ResultData add(@RequestBody ProductCollection productCollection){
        ResultData<Integer> resultData = Tools.getThreadResultData();
        resultData.setData(productCollectionService.save(productCollection));
        return resultData;
    }

    @RequestMapping(value = Router.V1_OMS_PRODUCTCOLLECTION_REMOVE, method = RequestMethod.GET)
    ResultData remove(@RequestParam Integer id){
        ResultData<Integer> resultData = Tools.getThreadResultData();
        resultData.setData(productCollectionService.remove(id));
        return resultData;
    }

    @RequestMapping(value = Router.V1_OMS_PRODUCTCOLLECTION_UPDATE, method = RequestMethod.POST)
    ResultData update(@RequestBody ProductCollection productCollection){
        ResultData<Integer> resultData = Tools.getThreadResultData();
        resultData.setData(productCollectionService.update(productCollection));
        return resultData;
    }
}
