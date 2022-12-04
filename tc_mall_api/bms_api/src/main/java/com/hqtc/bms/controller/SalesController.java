package com.hqtc.bms.controller;

import com.hqtc.bms.config.Router;
import com.hqtc.bms.model.params.SalesAmountVo;
import com.hqtc.bms.model.params.SalesManagerVo;
import com.hqtc.bms.model.response.Result;
import com.hqtc.bms.service.ProductService;
import com.hqtc.common.response.ResultData;
import com.hqtc.common.utils.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/8/9 10:59
 */
@RestController
public class SalesController {

    @Autowired
    private ProductService productService;
    @GetMapping(Router.V1_BMS_PRODUCT_SALES_AMOUNT)
    public ResultData<SalesAmountVo> getSalesAmount(@RequestParam("venderId") Long venderId){
        ResultData resultData= Tools.getThreadResultData();
        SalesAmountVo vo=productService.getSalesAmount(venderId);
        resultData.setData(vo);
        return resultData;
    }
    @PostMapping(Router.V1_BMS_PRODUCT_SALES_LIST)
    public ResultData<Result<SalesManagerVo>> salesManagerList(@RequestParam Map<String,Object> params){
        ResultData resultData=Tools.getThreadResultData();
        Result<SalesManagerVo> result=new Result<>();
        List<SalesManagerVo> list=productService.salesManagerList(params);
        int total=productService.countSalesManager(params);
        result.setRows(list);
        result.setTotal(total);
        resultData.setData(result);
        return resultData;
    }
    @GetMapping(Router.V1_BMS_PRODUCT_SALES_GET)
    public ResultData<SalesManagerVo> getSalesManagerVo(@RequestParam("sku") Long sku){
        ResultData resultData=Tools.getThreadResultData();
        SalesManagerVo vo=productService.getSalesManagerVo(sku);
        resultData.setData(vo);
        return resultData;
    }
}
