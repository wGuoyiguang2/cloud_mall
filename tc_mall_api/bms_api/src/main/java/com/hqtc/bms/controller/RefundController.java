package com.hqtc.bms.controller;

import com.hqtc.bms.config.Router;
import com.hqtc.bms.model.database.TOrderBean;
import com.hqtc.bms.model.params.MultipleRefundParams;
import com.hqtc.bms.model.params.RefundUserParams;
import com.hqtc.bms.service.OrderService;
import com.hqtc.bms.service.RefundService;
import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.ResultData;
import com.hqtc.common.utils.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * Created by wanghaoyang on 18-7-23.
 */
@RestController
public class RefundController {


    @Autowired
    @Resource(name = "OrderServiceImpl")
    private OrderService orderService;

    @Autowired
    @Resource(name = "RefundServiceImpl")
    private RefundService refundService;

    @RequestMapping(value = Router.ROUTER_USER_REFUND, method = RequestMethod.POST)
    public ResultData userRefund(@RequestBody RefundUserParams refundUserParams){
        ResultData resultData = Tools.getThreadResultData();
        TOrderBean orderBean = orderService.getOrderInfo(refundUserParams.getOrderSn());
        if(null == orderBean){
            resultData.setError(ErrorCode.NO_ORDER);
            resultData.setMsg("查无此订单");
            return resultData;
        }
        resultData =  refundService.userRefund(refundUserParams.getOrderSn(), refundUserParams.getProducts(), refundUserParams.getRefundReason(), refundUserParams.getEventId());
        return resultData;
    }

    @RequestMapping(value = Router.ROUTER_REFUND_NOTIFY, method = RequestMethod.POST)
    public String refundNotify(@RequestParam("refundNo")String refundNo,
                               @RequestParam("refundOrderSn")String refundOrderSn){
        return refundService.userRefundNotify(refundNo, refundOrderSn);
    }

    @RequestMapping(value = Router.ROUTER_VENDER_REFUND, method = RequestMethod.POST)
    public ResultData venderRefund(@RequestBody RefundUserParams refundUserParams){
        ResultData resultData = Tools.getThreadResultData();
        TOrderBean orderBean = orderService.getOrderInfo(refundUserParams.getOrderSn());
        if(null == orderBean){
            resultData.setError(ErrorCode.NO_ORDER);
            resultData.setMsg("查无此订单");
            return resultData;
        }
        resultData =  refundService.venderRefund(refundUserParams.getOrderSn(), refundUserParams.getProducts(), refundUserParams.getRefundReason(), refundUserParams.getEventId());
        return resultData;
    }

    /**
     * 退款(既退给用户,又退给大客户)
     * 该方法为幂等性方法,既多次调用,不会对结果产生任何影响
     * add by wanghaoyang at 20180827
     * */
    @RequestMapping(value = Router.ROUTER_REFUND, method = RequestMethod.POST)
    public ResultData multipleRefund(@RequestBody @Valid MultipleRefundParams multipleRefundParams, BindingResult bindingResult){
        ResultData resultData = Tools.getThreadResultData();
        if(bindingResult.hasErrors()){
            resultData.setError(ErrorCode.PARAM_ERROR);
            resultData.setMsg(bindingResult.getAllErrors().get(0).getDefaultMessage());
            return resultData;
        }
        TOrderBean orderBean = orderService.getOrderInfo(multipleRefundParams.getOrderSn());
        if(null == orderBean){
            resultData.setError(ErrorCode.NO_ORDER);
            resultData.setMsg("查无此订单");
            return resultData;
        }
        resultData =  refundService.multipleRefund(multipleRefundParams);
        return resultData;
    }


    @RequestMapping(value = Router.ROUTER_REFUND_STATE, method = RequestMethod.GET)
    public ResultData refundState(@RequestParam("orderSn")String orderSn,
                                  @RequestParam("eventId")String eventId){
        return refundService.refundStatusSearch(orderSn, eventId);
    }

    @RequestMapping(value = Router.ROUTER_REFUND_RETRY_USER, method = RequestMethod.POST)
    public ResultData refundUserRetry(@RequestParam("orderSn")String orderSn,
                                  @RequestParam("eventId")String eventId){
        return refundService.userRefundRetry(orderSn, eventId);
    }

    @RequestMapping(value = Router.ROUTER_REFUND_RETRY_VENDER, method = RequestMethod.POST)
    public ResultData refundVenderRetry(@RequestParam("orderSn")String orderSn,
                                  @RequestParam("eventId")String eventId){
        return refundService.venderRefundRetry(orderSn, eventId);
    }
}
