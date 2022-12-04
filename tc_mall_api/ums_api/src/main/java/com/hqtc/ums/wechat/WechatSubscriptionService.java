package com.hqtc.ums.wechat;

import com.hqtc.ums.model.WechatSubScriptionConfig;
import com.hqtc.ums.model.WechatUserInfoBean;

/**
 * Created by wanghaoyang on 18-10-11.
 */
public interface WechatSubscriptionService {

    /**
     * 通过openid获取微信用户的详细信息
     * add by wanghaoyang at 20181012
     * @param openId 用户标识
     * @param token 微信token
     * */
    WechatUserInfoBean getWechatUserInfoByOpenId(String openId, String token);

    /**
     * 根据公众号名称获取公众号信息
     * */

    /**
     * 判断公众号是否存在(于配置文件中)
     * add by wanghaoyang at 20181011
     * @param applicationName 应用名称
     * @return 是否配置文件中是否存在此公众号的信息(true存在|false不存在)
     * */
    boolean checkWechatSubscriptionExist(String applicationName);

    /**
     * 获取微信token
     * @param config 微信公众号的配置
     * @return 公众号的token
     * */
    String getWechatToken(WechatSubScriptionConfig config);

    /**
     * 生成微信token
     * @param config 微信公众号的配置
     * @return 公众号的token
     * */
    String createWeixinToken(WechatSubScriptionConfig config);

    /**
     * 生成临时二维码
     * add by wanghaoyang at 20181102
     * @param accessToken 微信accessToken
     * @param sceneId 场景id
     * */
    String createTempQrCode(String accessToken, int sceneId);


}
