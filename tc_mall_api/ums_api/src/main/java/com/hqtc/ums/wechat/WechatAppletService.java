package com.hqtc.ums.wechat;


import com.hqtc.common.response.ResultData;
import com.hqtc.ums.model.WxCode2SessionBean;

/**
 * Created by wanghaoyang on 18-10-11.
 */
public interface WechatAppletService {

    /**
     * 判断小程序是否存在(于配置文件中)
     * add by wanghaoyang at 20181011
     * @param applicationName 应用名称
     * @return 是否配置文件中是否存在此小程序的信息(true存在|false不存在)
     * */
    boolean checkWechatAppletExist(String applicationName);

    /**
     * 根据终端获取的code来换取微信用户的信息
     * add by wanghaoyang at 20181011
     * @param userCode 终端生成的小程序的code
     * @param applicationName 小程序的名称
     * @return 微信用户信息
     * */
    ResultData<WxCode2SessionBean> getWxUserInfoByUserCode(String userCode, String applicationName);


}
