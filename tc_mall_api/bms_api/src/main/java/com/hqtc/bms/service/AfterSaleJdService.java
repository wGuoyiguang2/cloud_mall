package com.hqtc.bms.service;

import com.hqtc.bms.config.Router;
import com.hqtc.bms.model.params.*;
import com.hqtc.bms.service.rpc.AfterSaleJdServiceFallback;
import com.hqtc.common.config.FeignClientService;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import java.math.BigDecimal;
import java.util.Map;

/**
 * description:京东接口
 * Created by laiqingchuang on 18-7-11 .
 */
@FeignClient(name=FeignClientService.JDSERVICE,fallbackFactory = AfterSaleJdServiceFallback.class)
public interface AfterSaleJdService {
    /**
     * 校验某订单中某商品是否可以提交售后服务
     */
    @RequestMapping(method = RequestMethod.POST, value = Router.JDAFTERSALE_ISCAN)
    Map<String,IsCanAfterSaleResponseParams> isCanAfterSale(@RequestParam("jdOrderId") Long jdOrderId,
                                                            @RequestParam("skuId") Long skuId);

    /**
     * 根据订单号、商品编号查询支持的商品返回京东方式
     */
    @RequestMapping(method = RequestMethod.POST, value = Router.JDAFTERSALE_GETJDMETHOD)
    Map<String,JdMethodResponseParams> getJdMethod(@RequestParam("jdOrderId") Long jdOrderId,
                                                   @RequestParam("skuId") Long skuId);

    /**
     * 根据订单号、商品编号查询支持的服务类型
     */
    @RequestMapping(method = RequestMethod.POST, value = Router.JDAFTERSALE_GETJDCUSTOMERTYPE)
    Map<String,JdCustomerTypeResponseParams> getJdCustomerType(@RequestParam("jdOrderId") Long jdOrderId,
                                                               @RequestParam("skuId") Long skuId);

    /**
     * 根据客户账号和订单号分页查询服务单概要信息
     */
    @RequestMapping(method = RequestMethod.POST, value = Router.JDAFTERSALE_GETSERVICELISTPAGE)
    Map<String,ServiceListPageResponseParams> getServiceListPage(@RequestParam("jdOrderId") Long jdOrderId,
                                                                 @RequestParam("pageIndex") Integer pageIndex,
                                                                 @RequestParam("pageSize") Integer pageSize);

    /**
     * 根据服务单号查询服务单明细信息
     */
    @RequestMapping(method = RequestMethod.POST, value = Router.JDAFTERSALE_GETSERVICEDETAIL)
    Map<String,ServiceDetailInfoResponseParams> getServiceDetail(@RequestBody ServiceRequestParams requestParams);

    /**
     * 取消服务单/客户放弃
     */
    @RequestMapping(method = RequestMethod.POST, value = Router.JDAFTERSALE_CANCELSERVICE)
    Map<String,CancelServiceResponseParams> cancelService(@RequestBody ServiceRequestParams requestParams);

    /**
     * 填写客户发运信息
     */
    @RequestMapping(method = RequestMethod.POST, value = Router.JDAFTERSALE_ADDSENDSKU)
    Map<String,SendSkuResponseParams> sendSku(@RequestParam("afsServiceId") Integer afsServiceId,
                                              @RequestParam("freightMoney") BigDecimal freightMoney,
                                              @RequestParam("expressCompany") String expressCompany,
                                              @RequestParam("deliverDate") String deliverDate,
                                              @RequestParam("expressCode") String expressCode);

    /**
     * 申请售后服务（退货、换货、维修）
     */
    @RequestMapping(method = RequestMethod.POST, value = Router.JDAFTERSALE_APPLYAFTERSALE)
    Map<String,ApplyAfterSaleResponseParams> applyAfterSale(@RequestBody ApplyAfterSaleRequestParams requestParams);

    /**
     * 查询配送信息
     */
    @RequestMapping(method = RequestMethod.POST, value = Router.JDORDERTRACK_INFO)
    Map<String,OrderTrackResponseParams> getOrderTrack(@RequestBody OrderTrackRequestParams param);

}
