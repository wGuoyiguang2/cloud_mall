package com.hqtc.ums.service.impl;

import com.google.gson.*;
import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.ResultData;
import com.hqtc.ums.model.databasebean.TUserBean;
import com.hqtc.ums.model.mapper.TUserMapper;
import com.hqtc.ums.service.ShortMessageService;
import com.hqtc.ums.rpc.RPCUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * 通过调用天成电视平台的ums来处理短信方法
 * Created by wanghaoyang on 18-6-28.
 */
@Service("RPCShortMessageServiceImpl")
public class RPCShortMessageServiceImpl extends CommonServiceImpl implements ShortMessageService {

    private Logger logger = LoggerFactory.getLogger("RPCShortMessageServiceImpl");

    @Autowired
    private RPCUserService rpcUserService;

    @Autowired
    private TUserMapper tUserMapper;

    @Override
    public ResultData sendShortMessage(String mobile, int msgType, String mac) {
        Map<String, String> map = rpcUserService.sendCode(mobile, String.valueOf(msgType), "v5.8.8", mac, "v5.8.8", "90001");
        ResultData resultData = new ResultData();
        if (null == map || map.isEmpty()) {
            resultData.setError(ErrorCode.SERVER_EXCEPTION);
            resultData.setMsg("TV-ums服务器异常");
            return resultData;
        }
        resultData = this.umsErrorCodeFormat(Integer.parseInt(map.get("status")));
        return resultData;
    }

    @Override
    public boolean verifyCode(String mobile, String code) {
        String res = rpcUserService.verifyCode(mobile, code);
        if (null == res || res.isEmpty()) {
            return false;
        }
        Map map = new Gson().fromJson(res, Map.class);
        if ("0.0".equals(String.valueOf(map.getOrDefault("status","1")))) {
            return true;
        }
        return false;
    }

    @Override
    public ResultData phoneRegister(String mobile, String passWord, String mac, String code) {
        ResultData resultData = new ResultData();
        String res = rpcUserService.userRegister(mobile, passWord, code, mac, channel, "v5.8.8", "v5.8.8");
        if (null == res || res.isEmpty()) {
            resultData.setError(ErrorCode.SERVER_EXCEPTION);
            resultData.setMsg("TV-ums服务器异常");
            return resultData;
        }
        TreeMap<String, Object> map =  gson.fromJson(res, typeToken);
        resultData = this.umsErrorCodeFormat(Integer.parseInt(String.valueOf(map.get("status"))));
        resultData.setData( "null".equals(map.get("data"))?null:gson.fromJson(map.get("data").toString(), typeToken));
        return resultData;
    }

    @Override
    public ResultData phoneLogin(String mobile, String passWord, String mac){
        ResultData resultData = new ResultData();
        String res = rpcUserService.userLogin(mobile, passWord, mac, "v5.8.8", "v5.8.8");
        if (null == res || res.isEmpty()) {
            resultData.setError(ErrorCode.SERVER_EXCEPTION);
            resultData.setMsg("TV-ums服务器异常");
            return resultData;
        }
        Map map =  gson.fromJson(res, typeToken);
        resultData = this.umsErrorCodeFormat(Integer.parseInt(String.valueOf(map.get("status"))));
        resultData.setData( "null".equals(map.get("data"))?null:gson.fromJson(map.get("data").toString(), typeToken));
        return resultData;
    }

    @Override
    public ResultData passWordReset(String mobile, String passWord, String code, String mac){
        ResultData resultData = new ResultData();
        String res = rpcUserService.resetPassWord(mobile, passWord, mac, code,"v5.8.8", "v5.8.8");
        if (null == res || res.isEmpty()) {
            resultData.setError(ErrorCode.SERVER_EXCEPTION);
            resultData.setMsg("TV-ums服务器异常");
            return resultData;
        }
        TreeMap<String, Object> map =  gson.fromJson(res, typeToken);
        resultData = this.umsErrorCodeFormat(Integer.parseInt(String.valueOf(map.get("status"))));
        resultData.setData( "null".equals(map.get("data"))?null:gson.fromJson(map.get("data").toString(), typeToken));
        return resultData;
    }

    @Override
    public ResultData getUserInfoByUserId(int userId, String mac){
        ResultData resultData = new ResultData();
        TUserBean tUserBean = tUserMapper.getUserById(userId);
        if(null != tUserBean){
            Map<String, String> map = new HashMap<>(10);
            map.put("coinnumber", "0");
            map.put("endTime","");
            map.put("member", "false");
            map.put("memberType", "0");
            map.put("newCoin", "0");
            map.put("nickName", tUserBean.getNickname());
            map.put("userHeader", tUserBean.getHeader());
            map.put("userId", String.valueOf(userId));
            map.put("userName", tUserBean.getNickname());
            map.put("userToken","");
            resultData.setData(map);
            return resultData;
        }else {
            resultData.setError(ErrorCode.USER_NOT_EXIST);
            resultData.setMsg("用户不存在");
            return resultData;
        }
//        String res = rpcUserService.autoLogin(userId, "hqtcmall","v5.8.8", mac);
//        if (null == res || res.isEmpty()) {
//            resultData.setError(ErrorCode.SERVER_EXCEPTION);
//            resultData.setMsg("TV-ums服务器异常");
//            return resultData;
//        }
//        TreeMap<String, Object> map =  gson.fromJson(res, typeToken);
//        resultData = this.umsErrorCodeFormat(Integer.parseInt(String.valueOf(map.get("status"))));
//        resultData.setData( "null".equals(map.get("data"))?null:gson.fromJson(map.get("data").toString(), typeToken));
    }
}
