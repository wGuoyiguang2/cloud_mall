package com.hqtc.ums.rpc;

import com.hqtc.ums.model.WechatUserInfoBean;
import com.hqtc.ums.wechat.impl.WechatAccessTokenBean;
import com.hqtc.ums.wechat.impl.WechatTempQrCodeParams;
import com.hqtc.ums.wechat.impl.WechatTempQrCodeResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by wanghaoyang on 18-10-11.
 */
@FeignClient(name = "wx-server" ,url="https://api.weixin.qq.com", fallback = RPCWechatServiceFallback.class)
public interface RPCWechatService {

        @RequestMapping(value = "/sns/jscode2session", method = RequestMethod.GET)
        String getWxUserInfoByUserCode(@RequestParam("appid") String appid,
                                       @RequestParam("secret") String secret,
                                       @RequestParam("js_code") String js_code,
                                       @RequestParam("grant_type") String grant_type);

        @GetMapping(value = "/cgi-bin/user/info")
        WechatUserInfoBean getWxUserInfoByOpenId(@RequestParam("access_token")String access_token,
                                                 @RequestParam("openid")String openid,
                                                 @RequestParam("lang")String lang);

        //获取微信公众号的token
        @GetMapping(value = "/cgi-bin/token")
        WechatAccessTokenBean getWxToken(@RequestParam("grant_type")String grant_type,
                                         @RequestParam("appid")String appid,
                                         @RequestParam("secret")String secret);

        //生成临时二维码
        @RequestMapping(value = "/cgi-bin/qrcode/create", method = {RequestMethod.POST})
        WechatTempQrCodeResponse createTempQrCode(@RequestParam("access_token")String token, @RequestBody WechatTempQrCodeParams body);


}
