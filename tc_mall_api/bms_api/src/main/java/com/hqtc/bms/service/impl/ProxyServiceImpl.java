package com.hqtc.bms.service.impl;

import com.hqtc.bms.service.ProxyService;
import com.hqtc.bms.service.RefundService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by wanghaoyang on 18-10-22.
 */
@Component("ProxyServiceImpl")
public class ProxyServiceImpl implements ProxyService {
    private Logger logger = LoggerFactory.getLogger("ProxyServiceImpl");

    @Autowired
    @Resource(name = "RefundServiceImpl")
    private RefundService refundService;

    @Async
    @Override
    public void cardRefundNotify(String refundNo, String refundTradeNo){
        try {
            Thread.sleep(3000);
        }catch (InterruptedException e){
            logger.error("购物卡退款回调通知延时失败");
        }
        refundService.userRefundNotify(refundNo, refundTradeNo);
    }
}
