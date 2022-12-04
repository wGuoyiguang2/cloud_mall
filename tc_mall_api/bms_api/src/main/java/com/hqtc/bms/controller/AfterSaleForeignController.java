package com.hqtc.bms.controller;

import com.hqtc.bms.config.Router;
import com.hqtc.bms.config.VenderRouter;
import com.hqtc.bms.model.params.AftersaleForeignBean;
import com.hqtc.bms.model.params.JdCustomerTypeResponseParams;
import com.hqtc.bms.service.AfterSaleJdService;
import com.hqtc.bms.service.AfterSaleTaskService;
import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import static com.hqtc.common.utils.Tools.getThreadResultData;

/**
 * description:售后(对外接口)
 * Created by laiqingchuang on 18-7-11 .
 */
@RestController
public class AfterSaleForeignController {

    @Autowired
    AfterSaleJdService afterSaleJdService;
    @Autowired
    AfterSaleTaskService afterSaleTaskService;

    /**
     * 根据JD订单号,商品编号获取售后服务类型
     */
    @RequestMapping(value= VenderRouter.AFTER_SALE_SERVICETYPE, method = RequestMethod.POST)
    public ResultData view(@RequestAttribute("userId") Integer userId,
                           @RequestParam("jdOrderId") Long jdOrderId,
                           @RequestParam("skuId") Long skuId){
        ResultData result = getThreadResultData();
        if(!afterSaleTaskService.checkJdOrderId(userId,jdOrderId)){
            result.setError(ErrorCode.NO_ORDER);
            result.setMsg("订单不存在");
            return result;
        }
        //服务类型  退货(10)、换货(20)、维修(30)
        Map<String,JdCustomerTypeResponseParams> serviceType= afterSaleJdService.getJdCustomerType(jdOrderId,skuId);
        if(serviceType !=null && serviceType.get("biz_afterSale_customerExpectComp_query_response") !=null && serviceType.get("biz_afterSale_customerExpectComp_query_response").getResult() !=null){
            result.setData(serviceType.get("biz_afterSale_customerExpectComp_query_response").getResult());
        }
        return result;
    }

    /**
     * 根据JD订单号获取售后列表
     */
    @RequestMapping(value = VenderRouter.AFTER_SALE_LIST, method = RequestMethod.POST)
    public ResultData getAftersaleListByJdOrderId(@RequestAttribute("userId") Integer userId,
                                                  @RequestParam("jdOrderId") Long jdOrderId,
                                                  @RequestParam(required=false) Integer type){
        ResultData result = getThreadResultData();
        if(!afterSaleTaskService.checkJdOrderId(userId,jdOrderId)){
            result.setError(ErrorCode.NO_ORDER);
            result.setMsg("订单不存在");
            return result;
        }
        List<AftersaleForeignBean> list = afterSaleTaskService.getAftersaleListByJdOrderId(jdOrderId,type);
        result.setData(list);
        return result;
    }

}