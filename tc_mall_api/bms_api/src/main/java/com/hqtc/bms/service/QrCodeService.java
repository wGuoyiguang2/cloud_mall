package com.hqtc.bms.service;

import com.hqtc.bms.model.database.TOrderBean;
import com.hqtc.bms.model.database.TOrderRefundBean;
import com.hqtc.bms.model.params.MutiplePayParams;
import com.hqtc.bms.model.rpc.VenderPayment;
import com.hqtc.bms.model.rpc.WxCode2SessionBean;
import com.hqtc.common.response.ResultData;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by wanghaoyang on 18-7-3.
 */
public interface QrCodeService {

    /**
     * 生成微信支付二维码
     * add by wanghaoyang at 20180703
     * @param orderBean 订单信息
     * @param venderPayment 客户支付凭证
     *
     * */
    String createWechatQrCode(TOrderBean orderBean, VenderPayment venderPayment);


    /**
     * 生成支付宝支付二维码
     * add by wanghaoyang at 20180703
     * @param orderBean 订单信息
     * @param venderPayment 客户支付凭证
     * @param payType 支付类型
     *
     * */
    Map<String, String> createPayQrCode(TOrderBean orderBean, VenderPayment venderPayment, int payType);

    /**
     * 生成订单二维码
     * add by wanghaoyang at 20180926
     * @param orderBean 订单信息
     * @param venderPayments 客户支付凭证
     * */
    Map<String, String> createPayQrCode(TOrderBean orderBean,  List<VenderPayment> venderPayments);

    /**
     * 生成定额额二维码
     * add by wanghaoyang at 20180926
     * @param orderBean 订单信息
     * @param venderPayments 大客户的支付凭证
     * @param quotaPrice 定额的数值(单位:元)
     * */
    Map<String, String> createQuotaQrCode(TOrderBean orderBean, List<VenderPayment> venderPayments, BigDecimal quotaPrice);

    /**
     * 获取商家支付凭证
     * @param venderId 商户id
     * @param type 支付类型1:支付宝0:微信
     * */
    VenderPayment getVenderPayment(int venderId, int type);

    /**
     * 校验订单信息
     * */
    ResultData checkOrder(TOrderBean tOrderBean, int userId, int venderId);

    /**
     * 获取商家支付凭证
     * @param venderId 商户id
     * */
    List<VenderPayment> getVenderAllPayment(int venderId);

    /**
     * 生成支付跳转凭证
     * add by wanghaoyang at 20180926
     * @param orderBean 订单信息
     * @param venderPayment 大客户支付能力
     * @param payParams 支付参数
     * */
    Map<String, String> createPayAuth(TOrderBean orderBean, VenderPayment venderPayment, MutiplePayParams payParams);

    /**
     * 生成定额的跳转凭证
     * add by wanghaoyang at 20180927
     * @param orderBean 订单信息
     * @param venderPayment 支付能力
     * @param quotaPrice 支付金额(单位元)
     * */
    Map<String, String> createQuotaPayAuth(TOrderBean orderBean, VenderPayment venderPayment, BigDecimal quotaPrice, MutiplePayParams payParams);

    /**
     * 获取微信小程序的支付能力
     * add by wanghaoyang at 20180927
     * */
//    VenderPayment getWechatAppletPayment();
    VenderPayment getWechatAppletPayment(int venderId);

    /**
     * 通过code获取用户的微信信息
     * */
//    WxCode2SessionBean getWxUserInfoByUserCode(String userCode);
    WxCode2SessionBean getWxUserInfoByUserCode(String userCode, int venderId);
}
