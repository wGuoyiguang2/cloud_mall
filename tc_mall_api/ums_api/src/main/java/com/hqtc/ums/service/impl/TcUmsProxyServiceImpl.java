package com.hqtc.ums.service.impl;

import com.hqtc.common.response.ResultData;
import com.hqtc.common.utils.Tools;
import com.hqtc.ums.config.RPCRouter;
import com.hqtc.ums.model.params.SmsSendParams;
import com.hqtc.ums.model.response.TcUmsUserInfoResponse;
import com.hqtc.ums.rpc.RPCTcUmsService;
import com.hqtc.ums.service.TcUmsProxyService;
import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by wanghaoyang on 18-11-17.
 */
@Service("TcUmsProxyServiceImpl")
public class TcUmsProxyServiceImpl implements TcUmsProxyService{

    private Logger logger = LoggerFactory.getLogger("TcUmsProxyServiceImpl");

    private String secureToken = "3E8A4281-3568-467C-96C5-7496DAD6A0D8";

    @Autowired
    private RPCTcUmsService rpcTcUmsService;

    @Override
    public ResultData sendCode(String mobile){
        Map<String, String> params = new HashMap<>(10);
        params.put("mobile", mobile);
        params.put("header","yx");
        params.put("msgType", "1");
        this.formatParams(params, RPCRouter.SMS_MESSAGE_SEND);
        return rpcTcUmsService.sendCode(mobile, params.get("header"), params.get("msgType"), params.get("timestamp"),
                params.get("rand"), params.get("token"), params.get("opaque"));
    }

    @Override
    public ResultData verifyCode(String mobile, String code){
        Map<String, String> params = new HashMap<>(10);
        params.put("mobile", mobile);
        params.put("code", code);
        this.formatParams(params, RPCRouter.SMS_MESSAGE_VERIFY);
        return rpcTcUmsService.verifyCode(mobile, code, params.get("timestamp"), params.get("rand"),
                params.get("token"), params.get("opaque"));
    }

    @Override
    public ResultData phonePassWordRegister(String mobile, String passWord, String code, String source){
        Map<String, String> params = new HashMap<>(10);
        params.put("mobile", mobile);
        params.put("code", code);
        params.put("passWord", passWord);
        params.put("source", source);
        this.formatParams(params, RPCRouter.USER_REGISTER_MOBILE);
        return rpcTcUmsService.phonePassWordRegister(mobile, passWord, code, source, params.get("timestamp")
                , params.get("rand"), params.get("token"), params.get("opaque"));
    }

    @Override
    public ResultData mobileLogin(String mobile, String code, String source){
        Map<String, String> params = new HashMap<>(10);
        params.put("mobile", mobile);
        params.put("code", code);
        params.put("source", source);
        this.formatParams(params, RPCRouter.USER_LOGIN_MOBILE);
        return rpcTcUmsService.mobileLogin(mobile, code, source, params.get("timestamp")
                , params.get("rand"), params.get("token"), params.get("opaque"));
    }

    @Override
    public ResultData<TcUmsUserInfoResponse> passWordLogin(String mobile, String passWord){
        Map<String, String> params = new HashMap<>(10);
        params.put("mobile", mobile);
        params.put("passWord", passWord);
        this.formatParams(params, RPCRouter.USER_LGOIN_PASSWORD);
        return rpcTcUmsService.passWordLogin(mobile, passWord, params.get("timestamp")
                , params.get("rand"), params.get("token"), params.get("opaque"));
    }

    @Override
    public ResultData<TcUmsUserInfoResponse> passWordReset(String mobile, String passWord, String code){
        Map<String, String> params = new HashMap<>(10);
        params.put("mobile", mobile);
        params.put("passWord", passWord);
        params.put("code", code);
        this.formatParams(params, RPCRouter.USER_RESET_PASSWORD);
        return rpcTcUmsService.passWordReset(mobile, passWord, code, params.get("timestamp")
                , params.get("rand"), params.get("token"), params.get("opaque"));
    }

    @Override
    public ResultData appletLogin(String userCode, String appName, String source){
        Map<String, String> params = new HashMap<>(10);
        params.put("userCode", userCode);
        params.put("appName", appName);
        params.put("source", source);
        this.formatParams(params, RPCRouter.USER_LOGIN_APPLET);
        return rpcTcUmsService.appletLogin(userCode, appName, source, params.get("timestamp")
                , params.get("rand"), params.get("token"), params.get("opaque"));
    }

    @Override
    public ResultData createQrCode(String mac, String source){
        Map<String, String> params = new HashMap<>(10);
        params.put("mac", mac);
        params.put("source", source);
        this.formatParams(params, RPCRouter.WECHAT_QRCODE_CREATE);
        return rpcTcUmsService.createQrCode(mac, source, params.get("timestamp")
                , params.get("rand"), params.get("token"), params.get("opaque"));
    }

    @Override
    public ResultData scanQrCode(String sceneId, String ticket){
        Map<String, String> params = new HashMap<>(10);
        params.put("sceneId", sceneId);
        params.put("tocket", ticket);
        this.formatParams(params, RPCRouter.WECHAT_QRCODE_SCAN);
        return rpcTcUmsService.scanQrCode(sceneId, ticket, params.get("timestamp")
                , params.get("rand"), params.get("token"), params.get("opaque"));
    }

    private Map<String, String> formatParams(Map<String, String> map, String uri){
        map.put("timestamp", String.valueOf((int)(System.currentTimeMillis()/1000)));
        map.put("rand", String.valueOf((int)((Math.random()*9+1)*1000)));
        map.put("token","hqtc");
        List<Map.Entry<String, String>> params = new ArrayList<Map.Entry<String, String>>(map.entrySet());
        Collections.sort(params, new Comparator<Map.Entry<String, String>>() {
            public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
                return o1.getKey().compareTo(o2.getKey());
            }
        });
        String requestParams = "";
        for (int i = 0; i < params.size(); i++) {
            Map.Entry<String, String> entry = params.get(i);
            requestParams += (entry.getKey().toString() + "=" + entry.getValue() + "&");
        }
        String opaqueUri = uri + "?" + requestParams + "key=" + secureToken;
        logger.error(opaqueUri);
        map.put("opaque", Tools.md5(opaqueUri));
        return map;
    }

}
