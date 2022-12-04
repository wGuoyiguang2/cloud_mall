package com.hqtc.bms.controller;

import com.hqtc.bms.config.Router;
import com.hqtc.bms.config.VenderRouter;
import com.hqtc.bms.model.params.*;
import com.hqtc.bms.model.response.AfterSaleVo;
import com.hqtc.bms.model.response.Result;
import com.hqtc.bms.model.rpc.AfterSaleReasonBean;
import com.hqtc.bms.service.*;
import com.hqtc.bms.service.rpc.RPCOmsService;
import com.hqtc.common.response.ResultData;
import com.hqtc.common.utils.Tools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.*;
import static com.hqtc.common.utils.Tools.getThreadResultData;

/**
 * description:售后
 * Created by laiqingchuang on 18-7-11 .
 */
@RestController
public class AfterSaleController {
    private Logger logger  = LoggerFactory.getLogger(getClass());

    @Autowired
    SendMessageService sendMessageService;
    @Autowired
    AfterSaleJdService afterSaleJdService;
    @Autowired
    BmsOrderSercice bmsOrderSercice;
    @Autowired
    ImsAddressService imsAddressService;
    @Autowired
    AfterSaleTaskService afterSaleTaskService;
    @Autowired
    RPCOmsService rpcomsService;
    @Autowired
    private AfterSaleService afterSaleService;
    @Value("${service.config.pageSize}")
    private int pageSize;
    @Value("${service.config.serviceSize}")
    private int serviceSize;

    /**
     * 校验某订单中某商品是否可以提交售后服务
     */
    @RequestMapping(value={Router.AFTERSALE_ISCAN,VenderRouter.AFTER_SALE_ISCAN}, method = RequestMethod.POST)
    public ResultData isCanAfterSale(@RequestParam("jdOrderId") Long jdOrderId,
                                     @RequestParam("skuId") Long skuId){
        ResultData result = getThreadResultData();
        Map<String,IsCanAfterSaleResponseParams> map= afterSaleJdService.isCanAfterSale(jdOrderId,skuId);
        result.setData(map.get("biz_afterSale_availableNumberComp_query_response"));
        return result;
    }

    /**
     * 售后服务主页面信息展示
     */
    @RequestMapping(value=Router.AFTERSALE_VIEW, method = RequestMethod.POST)
    public ResultData view(@RequestParam("jdOrderId") Long jdOrderId,
                           @RequestParam("skuId") Long skuId){
        ResultData result = getThreadResultData();
        Map<String,Object> map=new HashMap<String,Object>();

        //服务类型  退货(10)、换货(20)、维修(30)
        Map<String,JdCustomerTypeResponseParams> serviceType= afterSaleJdService.getJdCustomerType(jdOrderId,skuId);
        map.put("serviceType",serviceType.get("biz_afterSale_customerExpectComp_query_response").getResult());

        //商品退回方式  上门取件(4)、客户发货(40)、客户送货(7)
        Map<String,JdMethodResponseParams> productReturnType=afterSaleJdService.getJdMethod(jdOrderId,skuId);
        map.put("productReturnType",productReturnType.get("biz_afterSale_wareReturnJdComp_query_response").getResult());

        //订单信息
        OrderInfoResponseParams orderInfo=afterSaleTaskService.getOrderInfo(jdOrderId);
        map.put("orderInfo",orderInfo);

        result.setData(map);
        return result;
    }

    /**
     * 申请售后服务（退货、换货、维修）
     */
//    @CrossOrigin("*")
    @RequestMapping(value={Router.AFTERSALE_APPLYAFTERSALE,VenderRouter.AFTER_SALE_APPLYAFTERSALE}, method = RequestMethod.POST)
    public ResultData applyAfterSale(@RequestBody ApplyAfterSaleRequestParams requestParams){
        ResultData result = getThreadResultData();
        if(requestParams.getCustomerExpect()==10){
            AfterSalePickwareDto p = requestParams.getAsPickwareDto();
            AfterSaleReturnwareDto r= new AfterSaleReturnwareDto();
            r.setReturnwareType(10);
            r.setReturnwareProvince(p.getPickwareProvince());
            r.setReturnwareCity(p.getPickwareCity());
            r.setReturnwareCounty(p.getPickwareCounty());
            r.setReturnwareVillage(p.getPickwareVillage());
            r.setReturnwareAddress(p.getPickwareAddress());
            requestParams.setAsReturnwareDto(r);
        }

        Map<String,ServiceListPageResponseParams> serviceMap=afterSaleJdService.getServiceListPage(requestParams.getJdOrderId(),1,serviceSize);
        int maxSeq=0;
        int skuNum=requestParams.getAsDetailDto().getSkuNum();
        if(serviceMap !=null){
            ServiceListPageResponseParams serviceParam=serviceMap.get("biz_afterSale_serviceListPage_query_response");
            if(serviceParam !=null && serviceParam.getResult() !=null && serviceParam.getResult().getServiceInfoList() !=null && serviceParam.getResult().getServiceInfoList().size()>0){
                maxSeq=serviceParam.getResult().getServiceInfoList().size();
            }
        }
        Map<String,ApplyAfterSaleResponseParams> map=afterSaleJdService.applyAfterSale(requestParams);
        logger.info("申请售后1:"+map.toString());
        ApplyAfterSaleResponseParams param=map.get("biz_afterSale_afsApply_create_response");
        if(param ==null){
            result.setData(false);
            result.setMsg("申请售后失败");
            return result;
        }
        if(param.getSuccess()==true){
            logger.info("申请售后2:"+"true");
            //获取售后流水所需要的参数
            OrderAftersaleBean bean=getAfterSaleBean(requestParams);
            logger.info("申请售后3:"+bean.getBackAddr());
            result = afterSaleTaskService.addOrderAftersale(bean, maxSeq, skuNum);
            logger.info("申请售后4:"+result.getMsg());
            return result;
        }
        result.setData(param.getSuccess());
        if(param.getSuccess()==false){
            result.setMsg(param.getResultMessage());
        }
        return result;
    }

    /**
     * 获取售后流水所需要的参数
     */
    private OrderAftersaleBean getAfterSaleBean(ApplyAfterSaleRequestParams requestParams) {
        OrderAftersaleBean bean = new OrderAftersaleBean();
        bean.setChildTradeNo(String.valueOf(requestParams.getJdOrderId()));       //京东订单号
        bean.setName(requestParams.getAsCustomerDto().getCustomerContactName());  //联系人
        bean.setPhone(requestParams.getAsCustomerDto().getCustomerTel());         //电话
        bean.setProductId(requestParams.getAsDetailDto().getSkuId());
        bean.setServiceType(requestParams.getCustomerExpect());                   //服务类型
        bean.setReason(requestParams.getQuestionDesc());                          //退货原因
        bean.setBackType(requestParams.getAsPickwareDto().getPickwareType());     //退回方式 4:上门取件|40:客户发货|7:客户送货
        //取货地址
        OrderInfoResponseParams backAddr=imsAddressService.getAddressInfo(requestParams.getAsPickwareDto().getPickwareCounty(),requestParams.getAsPickwareDto().getPickwareVillage()).getData();
        bean.setBackAddr(backAddr.getProvinceName()+backAddr.getCityName()+backAddr.getCountyName()+backAddr.getTownName()+requestParams.getAsPickwareDto().getPickwareAddress());
        if(requestParams.getCustomerExpect()!=null && !requestParams.getCustomerExpect().equals(10)){
            bean.setReturnType(10);                                                //返件方式 10:自营配送|20:第三方配送
            //收货地址
            OrderInfoResponseParams returnAddr=imsAddressService.getAddressInfo(requestParams.getAsReturnwareDto().getReturnwareCounty(),requestParams.getAsReturnwareDto().getReturnwareVillage()).getData();
            bean.setReturnAddr(returnAddr.getProvinceName()+returnAddr.getCityName()+returnAddr.getCountyName()+returnAddr.getTownName()+requestParams.getAsReturnwareDto().getReturnwareAddress());
        }
        bean.setStatus(10);                                                        //售后状态
        bean.setRemarks("您的服务单已申请成功，待售后审核中");                          //描述
        return bean;
    }

    /**
     * 取消服务单/客户放弃
     */
    @RequestMapping(value={Router.AFTERSALE_CANCELSERVICE,VenderRouter.AFTER_SALE_CANCELSERVICE}, method = RequestMethod.POST)
    public ResultData applyAfterSale(@RequestBody ServiceRequestParams requestParams){
        ResultData result = getThreadResultData();
        if(requestParams.getApproveNotes()==null || requestParams.getApproveNotes().equals("")){
            requestParams.setApproveNotes("取消售后");
        }
        Map<String,CancelServiceResponseParams> map=afterSaleJdService.cancelService(requestParams);
        CancelServiceResponseParams param=map.get("biz_afterSale_auditCancel_query_response");
        if(param.getSuccess()==true){
            int num=afterSaleTaskService.updateCancelService(requestParams);
        }
        if(param.getSuccess()==false){
            result.setMsg(param.getResultMessage());
        }
        result.setData(param.getSuccess());
        return result;
    }

    /**
     * 填写客户发运信息
     */
    @RequestMapping(value=Router.AFTERSALE_ADDSENDSKU, method = RequestMethod.POST)
    public ResultData applyAfterSale(@RequestParam("afsServiceId") Integer afsServiceId,
                                     @RequestParam("freightMoney") BigDecimal freightMoney,
                                     @RequestParam("expressCompany") String expressCompany,
                                     @RequestParam("deliverDate") String deliverDate,
                                     @RequestParam("expressCode") String expressCode){
        ResultData result = getThreadResultData();
        Map<String,SendSkuResponseParams> map=afterSaleJdService.sendSku(afsServiceId,freightMoney,expressCompany,deliverDate,expressCode);
        result.setData(map.get("biz_afterSale_sendSku_update_response"));
        return result;
    }

    /**
     * 根据客户账号和订单号分页查询服务单概要信息
     */
    @RequestMapping(value=Router.AFTERSALE_GETSERVICELISTPAGE, method = RequestMethod.POST)
    public ResultData getServiceListPage(@RequestParam("jdOrderId") Long jdOrderId,
                                         @RequestParam("pageIndex") Integer pageIndex,
                                         @RequestParam("pageSize") Integer pageSize){
        ResultData result = getThreadResultData();
        Map<String,ServiceListPageResponseParams> map=afterSaleJdService.getServiceListPage(jdOrderId,pageIndex,pageSize);
        result.setData(map.get("biz_afterSale_serviceListPage_query_response"));
        return result;
    }

    /**
     * 根据服务单号查询服务单明细信息
     */
    @RequestMapping(value=Router.AFTERSALE_GETSERVICEDETAIL, method = RequestMethod.POST)
    public ResultData getServiceDetail(@RequestBody ServiceRequestParams requestParams){
        ResultData result = getThreadResultData();
        Map<String,ServiceDetailInfoResponseParams> map =afterSaleJdService.getServiceDetail(requestParams);
        result.setData(map.get("biz_afterSale_serviceDetailInfo_query_response"));
        return result;
    }

//    @CrossOrigin("*")
    @RequestMapping(value = Router.AFTER_SALE_REASON, method = {RequestMethod.GET, RequestMethod.POST})
    public ResultData<List<AfterSaleReasonBean>> getAfterSaleReason(@RequestParam("venderId") Integer venderId){
        ResultData resultData = rpcomsService.getAfterSaleReason(venderId);
        return resultData;
    }

    /**
     * 根据订单号获取售后列表
     */
    @RequestMapping(value = Router.AFTER_SALE_LISTBYORDERSN, method = RequestMethod.GET)
    public ResultData getAftersaleListByOrderSn(@RequestParam("orderSn") String orderSn,
                                                @RequestParam(required=false) Integer type){
        ResultData result = getThreadResultData();
        List<OrderAftersaleBean> list = afterSaleTaskService.getAftersaleListByOrderSn(orderSn,type);
        result.setData(list);
        return result;
    }

    /**
     * 根据用户id获取售后列表
     */
    @RequestMapping(value = Router.AFTER_SALE_LISTBYUSERID, method = RequestMethod.GET)
    public ResultData getAftersaleListByUserId(@RequestParam("userId") Integer userId,
                                               @RequestParam(value = "pageSize", defaultValue = "")Integer limit,
                                               @RequestParam(value = "pageNum", defaultValue = "") Integer offset){
        limit = limit == null ? pageSize: limit;
        offset = offset == null ? 0 : offset;
        offset = offset > 0 ? offset - 1 : 0;
        Map<String, Object> map = new HashMap<>();
        ResultData result = getThreadResultData();
        int totalRows=afterSaleTaskService.getAftersaleCountByUserId(userId);
        List<AftersaleBean> dataList = afterSaleTaskService.getAftersaleListByUserId(userId,offset,limit);
        map.put("totalRows",totalRows);
        map.put("dataList",dataList);
        result.setData(map);
        return result;
    }

    @PostMapping(Router.V1_BMS_AFTER_SALES_MANAGER_LIST)
    public ResultData<Result<AfterSaleVo>> afterSaleManagerList(@RequestParam Map<String,Object> params){
        ResultData<Result<AfterSaleVo>> resultData= Tools.getThreadResultData();
        Result<AfterSaleVo> result=new Result<>();
        int total=afterSaleService.countAfterSaleManager(params);
        if(total==0){
            return resultData;
        }
        List<AfterSaleVo> list=afterSaleService.afterSaleManagerList(params);
        result.setTotal(total);
        result.setRows(list);
        resultData.setData(result);
        return resultData;
    }
}

