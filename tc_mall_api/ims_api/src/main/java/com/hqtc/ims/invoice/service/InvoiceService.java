package com.hqtc.ims.invoice.service;

import com.hqtc.ims.invoice.model.bean.InvoiceBean;
import com.hqtc.ims.invoice.model.bean.InvoiceVo;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/7/20 11:26
 */
public interface InvoiceService {
    Integer addOrUpdateInvoice(InvoiceBean invoiceBean);

    InvoiceVo getByUserId(Integer userId);
}
