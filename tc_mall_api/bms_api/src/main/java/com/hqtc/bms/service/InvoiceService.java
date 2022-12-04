package com.hqtc.bms.service;

import com.hqtc.bms.model.params.InvoiceInfoVo;
import com.hqtc.bms.model.params.InvoiceManagerVo;
import com.hqtc.bms.model.params.InvoiceresultDto;
import com.hqtc.bms.model.params.OrderInvoiceBean;
import com.hqtc.bms.model.rpc.InvoiceDetailRequestBean;
import com.hqtc.bms.model.rpc.InvoiceOrdernoRequestBean;
import com.hqtc.bms.model.rpc.InvoiceRequestBean;
import com.hqtc.bms.model.rpc.InvoiceResultRequestBean;

import java.util.List;
import java.util.Map;

/**
 * description:发票service
 * Created by laiqingchuang on 18-7-3 .
 */
public interface InvoiceService {

    /**
     * 开发票
     */
    String addInvoice(InvoiceRequestBean requestbean);

    /**
     * 开票结果查询
     */
    String getInvoiceResult(InvoiceResultRequestBean requestbean);

    /**
     * 根据订单号查询发票请求流水号接口
     */
    String getFpqqlshByOrdeno(InvoiceOrdernoRequestBean requestbean);

    /**
     * 获取开发票所需要的参数
     */
    InvoiceRequestBean getInvoiceParam(String orderSn,String invoiceHead,String kptype);

    /**
     * 同步发票数据到数据库
     * @return
     */
    int saveInvoiceInfo(OrderInvoiceBean invoiceMain,List<InvoiceDetailRequestBean> invoiceDetail);

    List<InvoiceManagerVo> invoiceManagerList(Map<String, Object> params);

    int countInvoiceManagerList(Map<String, Object> params);

    /**
     * 获取流水号集合
     */
    List<String> getSerialNoList();

    /**
     * 更新发票表数据
     */
    int updateOrderInvoice(List<InvoiceresultDto> list);
    InvoiceInfoVo getInvoiceInfo(Long venderId);

    OrderInvoiceBean getById(String id);

    /**
     * 校验是否已开发票
     */
    OrderInvoiceBean isHaveInvoice(String orderSn);

    /**
     * 检验订单是否已完成
     */
    int getCount(String orderSn);
}
