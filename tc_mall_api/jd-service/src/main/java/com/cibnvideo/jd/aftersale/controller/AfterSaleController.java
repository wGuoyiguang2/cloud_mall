package com.cibnvideo.jd.aftersale.controller;

import com.cibnvideo.jd.aftersale.params.*;
import com.cibnvideo.jd.aftersale.params.apply.ApplyAfterSaleRequestParams;
import com.cibnvideo.jd.aftersale.params.apply.ApplyAfterSaleResponseParams;
import com.cibnvideo.jd.aftersale.params.cancel.CancelServiceResponseParams;
import com.cibnvideo.jd.aftersale.params.detail.ServiceDetailInfoResponseParams;
import com.cibnvideo.jd.aftersale.params.iscan.IsCanAfterSaleResponseParams;
import com.cibnvideo.jd.aftersale.params.servicelist.ServiceListPageResponseParams;
import com.cibnvideo.jd.aftersale.params.servicetype.JdCustomerTypeResponseParams;
import com.cibnvideo.jd.aftersale.params.warereturntype.JdMethodResponseParams;
import com.cibnvideo.jd.aftersale.params.waresendinfo.SendSkuRequestParams;
import com.cibnvideo.jd.aftersale.params.waresendinfo.SendSkuResponseParams;
import com.cibnvideo.jd.aftersale.service.AfterSaleService;
import com.cibnvideo.jd.common.constants.PathConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * description:售后
 * Created by laiqingchuang on 18-7-10 .
 */
@RestController
public class AfterSaleController {

    @Autowired
    AfterSaleService afterSaleService;

    /**
     * 校验某订单中某商品是否可以提交售后服务
     * @param requestParams
     * @return
     */
    @PostMapping(path= PathConstants.ISCAN)
    public IsCanAfterSaleResponseParams isCanAfterSale(JdOrderRequestParams requestParams){
        return afterSaleService.isCanAfterSale(requestParams);
    }

    /**
     * 根据订单号、商品编号查询支持的商品返回京东方式
     * @param requestParams
     * @return
     */
    @PostMapping(path= PathConstants.GETJDMETHOD)
    public JdMethodResponseParams getJdMethod(JdOrderRequestParams requestParams){
        return afterSaleService.getJdMethod(requestParams);
    }

    /**
     * 根据订单号、商品编号查询支持的服务类型
     * @param requestParams
     * @return
     */
    @PostMapping(path= PathConstants.GETJDCUSTOMERTYPE)
    public JdCustomerTypeResponseParams getJdCustomerType(JdOrderRequestParams requestParams){
        return afterSaleService.getJdCustomerType(requestParams);
    }

    /**
     * 根据客户账号和订单号分页查询服务单概要信息
     * @param requestParams
     * @return
     */
    @PostMapping(path= PathConstants.GETSERVICELISTPAGE)
    public ServiceListPageResponseParams getServiceListPage(JdOrderRequestParams requestParams){
        return afterSaleService.getServiceListPage(requestParams);
    }

    /**
     * 根据服务单号查询服务单明细信息
     * @param requestParams
     * @return
     */
    @PostMapping(path= PathConstants.GETSERVICEDETAIL)
    public ServiceDetailInfoResponseParams getServiceDetail(@RequestBody ServiceRequestParams requestParams){
        return afterSaleService.getServiceDetail(requestParams);
    }

    /**
     * 取消服务单/客户放弃
     * @param requestParams
     * @return
     */
    @PostMapping(path= PathConstants.CANCELSERVICE)
    public CancelServiceResponseParams cancelService(@RequestBody ServiceRequestParams requestParams){
        return afterSaleService.cancelService(requestParams);
    }

    /**
     * 填写客户发运信息
     * @param requestParams
     * @return
     */
    @PostMapping(path=PathConstants.ADDSENDSKU)
    public SendSkuResponseParams addSendSku(SendSkuRequestParams requestParams){
        return afterSaleService.SendSku(requestParams);
    }

    /**
     * 申请售后服务（退货、换货、维修）
     * @param requestParams
     * @return
     */
    @PostMapping(path=PathConstants.APPLYAFTERSALE)
    public ApplyAfterSaleResponseParams applyAfterSale(@RequestBody ApplyAfterSaleRequestParams requestParams){
        return afterSaleService.applyAfterSale(requestParams);
    }
}
