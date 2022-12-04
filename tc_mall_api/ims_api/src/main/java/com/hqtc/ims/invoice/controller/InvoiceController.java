package com.hqtc.ims.invoice.controller;

import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.ResultData;
import com.hqtc.common.utils.CookieOperate;
import com.hqtc.common.utils.Tools;
import com.hqtc.ims.common.constant.PathConstants;
import com.hqtc.ims.invoice.model.bean.InvoiceBean;
import com.hqtc.ims.invoice.model.bean.InvoiceVo;
import com.hqtc.ims.invoice.service.InvoiceService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description: 发票controller
 * @Author: WangBin
 * @Date: 2018/7/20 11:25
 */
@RestController
public class InvoiceController {
    @Value("${service.config.invoiceQrcode}")
    private String invoiceQrcodeUrl;
    @Autowired
    private InvoiceService invoiceService;

    /**
     * 添加/更新发票
     * @param name
     * @param phone
     * @param email
     * @param invoiceHead
     * @param taxnum
     * @param userId
     * @param invoiceType
     * @return
     */
    @PostMapping(PathConstants.ROUTE_MODIFY_INVOICE)
    public ResultData addOrUpdateInvoice(@RequestParam(value="name",required = true) String name,
                                         @RequestParam(value="phone",required = true) String phone,
                                         @RequestParam(value="email",required = false) String email,
                                         @RequestParam(value="invoiceHead",required = true) String invoiceHead,
                                         @RequestParam(value="taxnum",required = false) String taxnum,
                                         @RequestAttribute("userId") Integer userId,
                                         @RequestParam(value="invoiceType",required = true) Integer invoiceType){
        ResultData resultData= Tools.getThreadResultData();
        if(2==invoiceType){
            if(StringUtils.isEmpty(taxnum)){
                resultData.setMsg("参数异常，请输入税号！");
                resultData.setError(ErrorCode.PARAM_ERROR);
                return  resultData;
            }
        }
        InvoiceBean invoiceBean =new InvoiceBean();
        invoiceBean.setName(name);
        invoiceBean.setEmail(email);
        invoiceBean.setInvoiceHead(invoiceHead);
        invoiceBean.setPhone(phone);
        invoiceBean.setTaxnum(taxnum);
        invoiceBean.setUserId(userId);
        invoiceBean.setInvoiceType(invoiceType);
        Integer row = invoiceService.addOrUpdateInvoice(invoiceBean);
        if(row == null || row <= 0){
            resultData.setError(ErrorCode.WRITE_DATA_ERROR);
            resultData.setMsg("更新或添加发票失败！");
        }
        return resultData;
    }

    /**
     * 获取发票二维码地址
     * @param
     * @return
     */
    @GetMapping(PathConstants.ROUTE_GET_INVOICE_QRCODE)
    public ResultData getQrCodeHtml(@RequestAttribute("userId") Integer userId,
                                    @RequestParam("venderId") Integer venderId,
                                    @RequestParam("mac1") String mac){
        ResultData resultData= Tools.getThreadResultData();
        String url=invoiceQrcodeUrl + "?vender=" + venderId + "&mac=" + mac;
        InvoiceVo invoiceVo=invoiceService.getByUserId(userId);
        if(invoiceVo==null){
            invoiceVo=new InvoiceVo();
        }
        invoiceVo.setUrl(url);
        resultData.setData(invoiceVo);
        return resultData;
    }


    @GetMapping(PathConstants.ROUTE_GET_INVOICE_INFO)
    public ResultData<InvoiceVo> getInvoiceInfo(@RequestAttribute("userId") Integer userId){
        ResultData<InvoiceVo> resultData= Tools.getThreadResultData();
        InvoiceVo invoiceVo=invoiceService.getByUserId(userId);
        resultData.setData(invoiceVo);
        return resultData;
    }

    @GetMapping(PathConstants.ROUTE_GET_INVOICE_NOLOGININFO)
    public ResultData<InvoiceVo> getInvoiceNologinInfo(@RequestParam("userId") Integer userId){
        ResultData<InvoiceVo> resultData= Tools.getThreadResultData();
        InvoiceVo invoiceVo=invoiceService.getByUserId(userId);
        resultData.setData(invoiceVo);
        return resultData;
    }
}
