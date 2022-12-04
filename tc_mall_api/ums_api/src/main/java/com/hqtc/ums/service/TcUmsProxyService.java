package com.hqtc.ums.service;

import com.hqtc.common.response.ResultData;
import com.hqtc.ums.model.response.TcUmsUserInfoResponse;

/**
 * Created by wanghaoyang on 18-11-17.
 */
public interface TcUmsProxyService {

    /**
     * 发送短信验证码
     * */
    ResultData sendCode(String mobile);

    /**
     * 校验短信验证码
     * */
    ResultData verifyCode(String mobile, String code);

    /**
     * 手机号注册
     * */
    ResultData<TcUmsUserInfoResponse> phonePassWordRegister(String mobile, String passWord, String code, String source);

    /**
     * 手机验证码登录
     * */
    ResultData<TcUmsUserInfoResponse> mobileLogin(String mobile, String code, String source);

    /**
     * 手机号密码登录
     * */
    ResultData<TcUmsUserInfoResponse> passWordLogin(String mobile, String passWord);

    /**
     * 重置密码
     * */
    ResultData<TcUmsUserInfoResponse> passWordReset(String mobile, String passWord, String code);

    /**
     * 小程序登录
     * */
    ResultData appletLogin(String userCode, String appName, String source);

    /**
     * 生成二维码
     * */
    ResultData createQrCode(String mac, String source);

    /**
     * 判断二维码是否被扫描
     * */
    ResultData scanQrCode(String sceneId, String ticket);
}
