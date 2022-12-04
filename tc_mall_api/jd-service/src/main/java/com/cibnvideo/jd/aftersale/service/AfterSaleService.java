package com.cibnvideo.jd.aftersale.service;

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
import com.cibnvideo.jd.common.service.BaseService;

/**
 * description:售后
 * Created by laiqingchuang on 18-7-10 .
 */
public interface AfterSaleService extends BaseService{

    /**
     * 校验某订单中某商品是否可以提交售后服务
     * @param requestParams
     * @return
     */
    IsCanAfterSaleResponseParams isCanAfterSale(JdOrderRequestParams requestParams);

    /**
     * 根据订单号、商品编号查询支持的商品返回京东方式
     * @param requestParams
     * @return
     */
    JdMethodResponseParams getJdMethod(JdOrderRequestParams requestParams);

    /**
     * 根据订单号、商品编号查询支持的服务类型
     * @param requestParams
     * @return
     */
    JdCustomerTypeResponseParams getJdCustomerType(JdOrderRequestParams requestParams);

    /**
     * 根据客户账号和订单号分页查询服务单概要信息
     * @param requestParams
     * @return
     */
    ServiceListPageResponseParams getServiceListPage(JdOrderRequestParams requestParams);

    /**
     * 根据服务单号查询服务单明细信息
     * @param requestParams
     * @return
     */
    ServiceDetailInfoResponseParams getServiceDetail(ServiceRequestParams requestParams);

    /**
     * 取消服务单/客户放弃
     * @param requestParams
     * @return
     */
    CancelServiceResponseParams cancelService(ServiceRequestParams requestParams);

    /**
     * 填写客户发运信息
     * @param requestParams
     * @return
     */
    SendSkuResponseParams SendSku(SendSkuRequestParams requestParams);

    /**
     * 申请售后服务（退货、换货、维修）
     * @param requestParams
     * @return
     */
    ApplyAfterSaleResponseParams applyAfterSale(ApplyAfterSaleRequestParams requestParams);

}
