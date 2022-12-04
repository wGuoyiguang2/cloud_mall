package com.hqtc.ums.rpc;

import com.hqtc.common.response.ResultData;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by wanghaoyang on 18-11-17.
 */
@Component("RPCTcUmsServiceFallback")
public class RPCTcUmsServiceFallback implements RPCTcUmsService {

    @Override
    public ResultData sendCode(@RequestParam(value = "mobile")String mobile,
                        @RequestParam(value = "header", defaultValue = "hqtc")String header,
                        @RequestParam(value = "msgType", defaultValue = "0")String msgType,
                        @RequestParam("timestamp")String timestamp,
                        @RequestParam("rand")String rand,
                        @RequestParam("token")String token,
                        @RequestParam("opaque")String opaque){
        return null;
    }

    @Override
    public ResultData verifyCode(@RequestParam(value = "mobile")String mobile,
                          @RequestParam(value = "code")String header,
                          @RequestParam("timestamp")String timestamp,
                          @RequestParam("rand")String rand,
                          @RequestParam("token")String token,
                          @RequestParam("opaque")String opaque){
        return null;
    }

    @Override
    public ResultData phonePassWordRegister(@RequestParam("mobile")String mobile,
                                     @RequestParam("passWord")String passWord,
                                     @RequestParam("code")String code,
                                     @RequestParam("source")String source,
                                     @RequestParam("timestamp")String timestamp,
                                     @RequestParam("rand")String rand,
                                     @RequestParam("token")String token,
                                     @RequestParam("opaque")String opaque){
        return null;
    }

    @Override
    public ResultData mobileLogin(@RequestParam("mobile")String mobile,
                           @RequestParam("code")String code,
                           @RequestParam("source")String source,
                           @RequestParam("timestamp")String timestamp,
                           @RequestParam("rand")String rand,
                           @RequestParam("token")String token,
                           @RequestParam("opaque")String opaque){
        return null;
    }

    @Override
    public ResultData passWordLogin(@RequestParam("mobile")String mobile,
                             @RequestParam("passWord")String passWord,
                             @RequestParam("timestamp")String timestamp,
                             @RequestParam("rand")String rand,
                             @RequestParam("token")String token,
                             @RequestParam("opaque")String opaque){
        return null;
    }

    @Override
    public ResultData passWordReset(@RequestParam("mobile")String mobile,
                             @RequestParam("passWord")String passWord,
                             @RequestParam("code")String code,
                             @RequestParam("timestamp")String timestamp,
                             @RequestParam("rand")String rand,
                             @RequestParam("token")String token,
                             @RequestParam("opaque")String opaque){
        return null;
    }

    @Override
    public ResultData appletLogin(@RequestParam("userCode")String userCode,
                           @RequestParam("appName")String appName,
                           @RequestParam("source")String source,
                           @RequestParam("timestamp")String timestamp,
                           @RequestParam("rand")String rand,
                           @RequestParam("token")String token,
                           @RequestParam("opaque")String opaque){
        return null;
    }

    @Override
    public ResultData createQrCode(@RequestParam("mac")String mac,
                            @RequestParam("source")String source,
                            @RequestParam("timestamp")String timestamp,
                            @RequestParam("rand")String rand,
                            @RequestParam("token")String token,
                            @RequestParam("opaque")String opaque){
        return null;
    }

    @Override
    public ResultData scanQrCode(@RequestParam("sceneId")String sceneId,
                          @RequestParam("ticket")String ticket,
                          @RequestParam("timestamp")String timestamp,
                          @RequestParam("rand")String rand,
                          @RequestParam("token")String token,
                          @RequestParam("opaque")String opaque){
        return null;
    }
}
