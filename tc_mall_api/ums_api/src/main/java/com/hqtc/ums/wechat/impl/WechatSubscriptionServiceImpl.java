package com.hqtc.ums.wechat.impl;

import com.hqtc.ums.model.WechatApplicationConfigBean;
import com.hqtc.ums.model.WechatSubScriptionConfig;
import com.hqtc.ums.model.WechatUserInfoBean;
import com.hqtc.ums.rpc.RPCWechatService;
import com.hqtc.ums.wechat.WechatSubscriptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;


/**
 * Created by wanghaoyang on 18-10-12.
 */
@Service("WechatSubscriptionServiceImpl")
public class WechatSubscriptionServiceImpl implements WechatSubscriptionService {

    private Logger logger = LoggerFactory.getLogger("WechatSubscriptionServiceImpl");

    @Autowired
    private WechatApplicationConfigBean applicationConfigBean;

    @Autowired
    private RPCWechatService rpcWechatService;


    @Override
    public boolean checkWechatSubscriptionExist(String applicationName){
        if(applicationConfigBean.getSubscription().keySet().contains(applicationName)){
            return true;
        }
        return false;
    }

    @Override
    public WechatUserInfoBean getWechatUserInfoByOpenId(String openId, String token){
        return rpcWechatService.getWxUserInfoByOpenId(token, openId, "zh_CN");
    }

    @Cacheable(value = "weixinCacheManager",key = "'tcmall.ums.weixin.token.'+#config.getWechatAppId()", cacheManager = "weixinCacheManager")
    @Override
    public String getWechatToken(WechatSubScriptionConfig config){
        String token = this.createWeixinToken(config);
        if(null == token || "".equals(token)){
            logger.error("获取微信token失败:"+config.getWechatAppId());
            token = null;
        }
        return token;
    }

    @Override
    public String createWeixinToken(WechatSubScriptionConfig config){
        WechatAccessTokenBean accessTokenBean = rpcWechatService.getWxToken("client_credential",
                config.getWechatAppId(), config.getWechatSecret());
        if(null == accessTokenBean){
            return null;
        }else {
            return accessTokenBean.getAccess_token();
        }
    }

    @Override
    public String createTempQrCode(String accessToken, int sceneId){
        WechatTempQrCodeParams params = new WechatTempQrCodeParams();
        params.setAction_info(params.new ActionInfo(params.new SceneInfo(sceneId)));
        WechatTempQrCodeResponse response = rpcWechatService.createTempQrCode(accessToken, params);
        if(null == response){
            return null;
        }else {
            return response.getTicket();
        }
    }
}
