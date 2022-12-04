package com.hqtc.ums.service;

import com.hqtc.common.response.ResultData;

/**
 * Created by wanghaoyang on 18-6-28.
 */
public interface ShortMessageService {

    /**
     * 发送短信接口
     * add by wanghaoyang at 2018-06-30
     * @param mobile 手机号
     * @param msgType 短信类型 1:注册2:找回密码
     * @param mac 终端mac
     * @return 成功|失败
     * */
    ResultData sendShortMessage(String mobile, int msgType, String mac);

    /**
     * 校验短信验证码
     * add by wanghaoyang at 2019-06-30
     * @param mobile 手机号
     * @param code   短信验证码
     * @return true校验成功|false校验失败
     * */
    boolean verifyCode(String mobile, String code);

    /**
     * 手机号注册
     * add by wanghaoyang 2018-06-30
     * @param mobile 手机号
     * @param passWord 密码
     * @param mac 终端mac
     * @param code 短信校验码
     * */
    ResultData phoneRegister(String mobile, String passWord, String mac, String code);

    /**
     * 手机号登录
     * add by wanghaoyang 2018-06-30
     * @param mobile 手机号
     * @param passWord 密码
     * @param mac 终端mac
     * */
    ResultData phoneLogin(String mobile, String passWord, String mac);

    /**
     * 密码重置
     * add by wanghaoyang 20180726
     *
     * */
    ResultData passWordReset(String mobile, String passWord, String code, String mac);

    /**
     * 通过userId获取用户信息
     * add by wanghaoyang at 20180909
     * @param userId 用户id
     * @param mac 登录设备的mac
     * */
    ResultData getUserInfoByUserId(int userId, String mac);
}
