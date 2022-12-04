package com.hqtc.bms.service.rpc;

import com.hqtc.bms.model.params.AlipayRefundQueryParams;
import com.hqtc.bms.model.params.TcmallRefundQueryBaseInfo;
import com.hqtc.bms.model.params.WechatRefundQueryParams;
import com.hqtc.bms.model.response.PmsResultData;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by wanghaoyang on 18-7-6.
 */
@Component
public class RPCPmsServiceFallback implements RPCPmsService {

    @Override
    public Map<String, Object> createQrCode(String body){
        return null;
    }

    @Override
    public Map<String, Object> userRefund(String body){
        return null;
    }

    @Override
    public Map<String, Object> createAuth(String body){
        return null;
    }

    @Override
    public PmsResultData<TcmallRefundQueryBaseInfo> wechatRefundSearch(WechatRefundQueryParams body){
        return null;
    }

    @Override
    public PmsResultData<TcmallRefundQueryBaseInfo> aliPayRefundSearch(AlipayRefundQueryParams body){
        return null;
    }
}
