package com.hqtc.ums.rpc;

import com.hqtc.ums.model.WechatUserInfoBean;
import com.hqtc.ums.wechat.impl.WechatAccessTokenBean;
import com.hqtc.ums.wechat.impl.WechatTempQrCodeParams;
import com.hqtc.ums.wechat.impl.WechatTempQrCodeResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * Created by wanghaoyang on 18-10-11.
 */
@Component
public class RPCWechatServiceFallback implements RPCWechatService{

    @Override
    public String getWxUserInfoByUserCode(@RequestParam("appid") String appid,
                                          @RequestParam("secret") String secret,
                                          @RequestParam("js_code") String js_code,
                                          @RequestParam("grant_type") String grant_type){
        return null;
    }

    @Override
    public WechatUserInfoBean getWxUserInfoByOpenId(@RequestParam("access_token")String access_token,
                                                    @RequestParam("openid")String openid,
                                                    @RequestParam("lang")String lang){
        return null;
    }

    @Override
    public WechatAccessTokenBean getWxToken(@RequestParam("grant_type")String grant_type,
                                            @RequestParam("appid")String appid,
                                            @RequestParam("secret")String secret){
        return null;
    }

    @Override
    public WechatTempQrCodeResponse createTempQrCode(@RequestParam("access_token")String token, @RequestBody WechatTempQrCodeParams body){
        return null;
    }
}
