package com.hqtc.ums.controller;

import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.ResultData;
import com.hqtc.common.utils.Tools;
import com.hqtc.ums.config.Router;
import com.hqtc.ums.model.WechatSubScriptionConfig;
import com.hqtc.ums.model.response.LoginResponseBean;
import com.hqtc.ums.service.WechatQrCodeService;
import com.hqtc.ums.service.WechatService;
import com.hqtc.ums.service.impl.YouXiangWechatSubScriptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.ManagedBean;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wanghaoyang on 18-6-30.
 */
@RestController
public class WechatController extends CommonController{

    private String defaultSource = "tc_mall_tv_%s";

    @Autowired
    @Resource(name = "RPCWechatServiceImpl")
    private WechatService wechatService;

    @Autowired
    @Resource(name = "YouXiangWecahtSubScriptionConfigBean")
    private WechatSubScriptionConfig wechatSubScriptionConfig;

    @Autowired
    private YouXiangWechatSubScriptionHandler youXiangWechatSubScriptionHandler;

    @Autowired
    @Resource(name = "JRYXWechatQrCodeServiceImpl")
    private WechatQrCodeService wechatQrCodeService;

    @RequestMapping(value = Router.ROUTER_QRCODE_GET, method = {RequestMethod.POST})
    public ResultData createQrCode(@RequestParam("mac")String mac,
                                   @RequestParam(value = "venderId", defaultValue = "0")int venderId,
                                   HttpServletResponse response){
        ResultData resultData = Tools.getThreadResultData();
//        resultData = wechatService.createQrCode(mac);
        resultData = wechatQrCodeService.createLoginQrCode(mac, String.format(defaultSource, venderId));
        return resultData;
    }

    @RequestMapping(value = Router.ROUTER_QRCODE_CHECK, method = {RequestMethod.POST})
    public ResultData scanQrCode(@RequestParam("scanId")int scanId,
                                 @RequestParam("ticket")String ticket,
                                 HttpServletResponse response){
//        resultData = wechatService.scanQrCode(scanId, ticket);
        ResultData resultData = wechatQrCodeService.checkQrCode(scanId, ticket);
        if(resultData.getError() == ErrorCode.SUCCESS){
            LoginResponseBean loginResponse  = (LoginResponseBean)resultData.getData();
            Map<String, String> map = new HashMap<>(10);
            map.put("coinnumber", "0");
            map.put("endTime","");
            map.put("member", "false");
            map.put("memberType", "0");
            map.put("newCoin", "0");
            map.put("nickName", loginResponse.getNickName());
            map.put("userHeader", loginResponse.getUserHeader());
            map.put("userId", String.valueOf(loginResponse.getUserId()));
            map.put("userName", loginResponse.getNickName());
            map.put("userToken","");
            resultData.setData(map);
        }
        resolveResult(resultData, response);
        return resultData;
    }

    @RequestMapping(value = Router.WECHAT_SUBSCRIPTION_YOUXIANG, method = {RequestMethod.GET})
    public String wangbinChecSign(@RequestParam("signature")String signature,
                                  @RequestParam("timestamp")String timestamp,
                                  @RequestParam("nonce")String nonce,
                                  @RequestParam("echostr")String echostr){
        if(youXiangWechatSubScriptionHandler.checkSignature(wechatSubScriptionConfig, timestamp, nonce, signature)){
            return echostr;
        }else {
            return "fail";
        }
    }

    @RequestMapping(value = Router.WECHAT_SUBSCRIPTION_YOUXIANG, method = {RequestMethod.POST})
    public void wangbinMessage(@RequestParam("signature")String signature,
                               @RequestParam("timestamp")String timestamp,
                               @RequestParam("nonce")String nonce,
                               @RequestParam("openid")String openid,
                               HttpServletRequest request,
                               HttpServletResponse response){
        youXiangWechatSubScriptionHandler.mssageHandler(request, response);
    }
}
