package com.hqtc.bms.service;

/**
 * Created by wanghaoyang on 18-10-22.
 */
public interface ProxyService {

    /**
     * 用户退款成功回调通知
     * add by wanghaoyang at 20180727
     * @param refundNo 平台退款单号
     * @param refundTradeNo 第三方退款单号
     * */
    void cardRefundNotify(String refundNo, String refundTradeNo);
}
