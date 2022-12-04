package com.cibnvideo.jd.aftersale.service.impl;

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
import com.cibnvideo.jd.common.constants.JdMethodConstants;
import com.cibnvideo.jd.common.service.impl.BaseServiceImpl;
import com.google.gson.Gson;
import org.springframework.stereotype.Service;

/**
 * description:售后
 * Created by laiqingchuang on 18-7-10 .
 */
@Service
public class AfterSaleServiceImpl extends BaseServiceImpl implements AfterSaleService {
    Gson gson=new Gson();
    private RequestParams getRequestParams(Object requestParams){
        RequestParams bean=new RequestParams();
        bean.setParam(requestParams);
        return bean;
    }
    /**
     * 校验某订单中某商品是否可以提交售后服务
     * @param requestParams
     * @return
     */
    @Override
    public IsCanAfterSaleResponseParams isCanAfterSale(JdOrderRequestParams requestParams) {
        String json=this.request(JdMethodConstants.getAvailableNumberComp(),this.getRequestParams(requestParams));
        return gson.fromJson(json,IsCanAfterSaleResponseParams.class);
    }

    /**
     * 根据订单号、商品编号查询支持的商品返回京东方式
     * @param requestParams
     * @return
     */
    @Override
    public JdMethodResponseParams getJdMethod(JdOrderRequestParams requestParams) {
        String json=this.request(JdMethodConstants.getWareReturnComp(),this.getRequestParams(requestParams));
        return gson.fromJson(json,JdMethodResponseParams.class);
    }

    /**
     * 根据订单号、商品编号查询支持的服务类型
     * @param requestParams
     * @return
     */
    @Override
    public JdCustomerTypeResponseParams getJdCustomerType(JdOrderRequestParams requestParams) {
        String json=this.request(JdMethodConstants.getCustomerExpectComp(),this.getRequestParams(requestParams));
        return gson.fromJson(json,JdCustomerTypeResponseParams.class);
    }

    /**
     * 根据客户账号和订单号分页查询服务单概要信息
     * @param requestParams
     * @return
     */
    @Override
    public ServiceListPageResponseParams getServiceListPage(JdOrderRequestParams requestParams) {
        String json=this.request(JdMethodConstants.getServiceListPage(),this.getRequestParams(requestParams));
        return gson.fromJson(json,ServiceListPageResponseParams.class);
    }

    /**
     * 根据服务单号查询服务单明细信息
     * @param requestParams
     * @return
     */
    @Override
    public ServiceDetailInfoResponseParams getServiceDetail(ServiceRequestParams requestParams) {
        String json=this.request(JdMethodConstants.getServiceDetailInfo(),this.getRequestParams(requestParams));
        return gson.fromJson(json,ServiceDetailInfoResponseParams.class);
    }

    /**
     * 取消服务单/客户放弃
     * @param requestParams
     * @return
     */
    @Override
    public CancelServiceResponseParams cancelService(ServiceRequestParams requestParams) {
        String json=this.request(JdMethodConstants.getAuditCancel(),this.getRequestParams(requestParams));
        return gson.fromJson(json,CancelServiceResponseParams.class);
    }

    /**
     * 填写客户发运信息
     * @param requestParams
     * @return
     */
    @Override
    public SendSkuResponseParams SendSku(SendSkuRequestParams requestParams) {
        String json=this.request(JdMethodConstants.getSendSku(),this.getRequestParams(requestParams));
        return gson.fromJson(json,SendSkuResponseParams.class);
    }

    /**
     * 申请售后服务（退货、换货、维修）
     * @param requestParams
     * @return
     */
    @Override
    public ApplyAfterSaleResponseParams applyAfterSale(ApplyAfterSaleRequestParams requestParams) {
        String json=this.request(JdMethodConstants.getAfsApply(),this.getRequestParams(requestParams));
        return gson.fromJson(json,ApplyAfterSaleResponseParams.class);
    }
}
