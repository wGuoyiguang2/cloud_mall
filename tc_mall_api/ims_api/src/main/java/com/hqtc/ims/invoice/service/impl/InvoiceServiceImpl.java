package com.hqtc.ims.invoice.service.impl;

import com.hqtc.ims.invoice.model.bean.InvoiceBean;
import com.hqtc.ims.invoice.model.bean.InvoiceVo;
import com.hqtc.ims.invoice.model.mapper.InvoiceMapper;
import com.hqtc.ims.invoice.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/7/20 11:26
 */
@Service
public class InvoiceServiceImpl implements InvoiceService{
    @Autowired
    private InvoiceMapper invoiceMapper;
    @Override
    public Integer addOrUpdateInvoice(InvoiceBean invoiceBean) {
        return invoiceMapper.addOrUpdateInvoice(invoiceBean);
    }

    @Override
    public InvoiceVo getByUserId(Integer userId) {
        return invoiceMapper.getByUserId(userId);
    }
}
