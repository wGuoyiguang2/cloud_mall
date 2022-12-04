package com.hqtc.ums.rpc;

import com.hqtc.common.response.ResultData;
import com.hqtc.ums.config.RPCRouter;
import com.hqtc.ums.model.params.SmsSendParams;
import com.hqtc.ums.model.response.TcUmsUserInfoResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by wanghaoyang on 18-11-17.
 */
@FeignClient(name = "tc-ums", url = "http://172.16.200.125:8091", fallback = RPCTcUmsServiceFallback.class)
public interface RPCTcUmsService {

    @RequestMapping(value = RPCRouter.SMS_MESSAGE_SEND, method = RequestMethod.POST)
    ResultData sendCode(@RequestParam(value = "mobile")String mobile,
                        @RequestParam(value = "header", defaultValue = "hqtc")String header,
                        @RequestParam(value = "msgType", defaultValue = "0")String msgType,
                        @RequestParam("timestamp")String timestamp,
                        @RequestParam("rand")String rand,
                        @RequestParam("token")String token,
                        @RequestParam("opaque")String opaque);

    @RequestMapping(value = RPCRouter.SMS_MESSAGE_VERIFY, method = RequestMethod.POST)
    ResultData verifyCode(@RequestParam(value = "mobile")String mobile,
                        @RequestParam(value = "code")String header,
                        @RequestParam("timestamp")String timestamp,
                        @RequestParam("rand")String rand,
                        @RequestParam("token")String token,
                        @RequestParam("opaque")String opaque);

    @RequestMapping(value = RPCRouter.USER_REGISTER_MOBILE, method = RequestMethod.POST)
    ResultData<TcUmsUserInfoResponse> phonePassWordRegister(@RequestParam("mobile")String mobile,
                                     @RequestParam("passWord")String passWord,
                                     @RequestParam("code")String code,
                                     @RequestParam("source")String source,
                                     @RequestParam("timestamp")String timestamp,
                                     @RequestParam("rand")String rand,
                                     @RequestParam("token")String token,
                                     @RequestParam("opaque")String opaque);

    @RequestMapping(value = RPCRouter.USER_LOGIN_MOBILE, method = RequestMethod.POST)
    ResultData<TcUmsUserInfoResponse> mobileLogin(@RequestParam("mobile")String mobile,
                           @RequestParam("code")String code,
                           @RequestParam("source")String source,
                           @RequestParam("timestamp")String timestamp,
                           @RequestParam("rand")String rand,
                           @RequestParam("token")String token,
                           @RequestParam("opaque")String opaque);

    @RequestMapping(value = RPCRouter.USER_LGOIN_PASSWORD, method = RequestMethod.GET)
    ResultData<TcUmsUserInfoResponse> passWordLogin(@RequestParam("mobile")String mobile,
                                                    @RequestParam("passWord")String passWord,
                                                    @RequestParam("timestamp")String timestamp,
                                                    @RequestParam("rand")String rand,
                                                    @RequestParam("token")String token,
                                                    @RequestParam("opaque")String opaque);

    @RequestMapping(value = RPCRouter.USER_RESET_PASSWORD, method = RequestMethod.POST)
    ResultData<TcUmsUserInfoResponse> passWordReset(@RequestParam("mobile")String mobile,
                             @RequestParam("passWord")String passWord,
                             @RequestParam("code")String code,
                             @RequestParam("timestamp")String timestamp,
                             @RequestParam("rand")String rand,
                             @RequestParam("token")String token,
                             @RequestParam("opaque")String opaque);

    //小程序登录
    @RequestMapping(value = RPCRouter.USER_LOGIN_APPLET, method = RequestMethod.POST)
    ResultData appletLogin(@RequestParam("userCode")String userCode,
                           @RequestParam("appName")String appName,
                           @RequestParam("source")String source,
                           @RequestParam("timestamp")String timestamp,
                           @RequestParam("rand")String rand,
                           @RequestParam("token")String token,
                           @RequestParam("opaque")String opaque);


    @RequestMapping(value = RPCRouter.WECHAT_QRCODE_CREATE, method = RequestMethod.GET)
    ResultData createQrCode(@RequestParam("mac")String mac,
                            @RequestParam("source")String source,
                            @RequestParam("timestamp")String timestamp,
                            @RequestParam("rand")String rand,
                            @RequestParam("token")String token,
                            @RequestParam("opaque")String opaque);

    @RequestMapping(value = RPCRouter.WECHAT_QRCODE_SCAN, method = RequestMethod.GET)
    ResultData scanQrCode(@RequestParam("sceneId")String sceneId,
                          @RequestParam("ticket")String ticket,
                          @RequestParam("timestamp")String timestamp,
                          @RequestParam("rand")String rand,
                          @RequestParam("token")String token,
                          @RequestParam("opaque")String opaque);
}
