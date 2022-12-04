package com.hqtc.ums.controller;

import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.ResultData;
import com.hqtc.common.utils.CookieOperate;
import com.hqtc.common.utils.ThreadObjectHolder;
import com.hqtc.common.utils.Tools;
import com.hqtc.ums.config.Router;
import com.hqtc.ums.service.ShortMessageService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.digest.Md5Crypt;
import org.bouncycastle.jcajce.provider.asymmetric.rsa.DigestSignatureSpi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sun.security.provider.MD5;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wanghaoyang on 18-6-28.
 */
@RestController
public class ShortMessageController extends CommonController{

    @Autowired
    @Resource(name = "RPCShortMessageServiceImpl")
    private ShortMessageService shortMessageService;

    @RequestMapping(value = Router.ROUTER_VERIFY_CODE, method = {RequestMethod.POST})
    public ResultData verifyCode(@RequestParam("mobile")String mobile,
                                 @RequestParam("code")String code){
        ResultData resultData = (ResultData) ThreadObjectHolder.getSetClass("resultData", ResultData.class);
        boolean res = shortMessageService.verifyCode(mobile, code);
        resultData.setError(ErrorCode.SUCCESS);
        resultData.setMsg("OK");
        Map<String, Integer> data = new HashMap<>(1);
        data.put("result", res?1:0);
        resultData.setData(data);
        return resultData;
    }

    @RequestMapping(value = Router.ROUTER_SEND_CODE, method = {RequestMethod.POST})
    public ResultData sendCode(@RequestParam("mobile")String mobile
                            , @RequestParam("mac")String mac
                            , @RequestParam("msgType")int msgType){
        ResultData resultData = Tools.getThreadResultData();
        resultData = shortMessageService.sendShortMessage(mobile, msgType, mac);
        return resultData;
    }

    @RequestMapping(value = Router.ROUTER_PHONE_REGISTER, method = {RequestMethod.POST})
    public ResultData register(@RequestParam("mobile")String mobile
            , @RequestParam("passWord")String passWord
            , @RequestParam("code") String code
            , @RequestParam("mac")String mac,
                               HttpServletResponse response){
        ResultData resultData = Tools.getThreadResultData();
        resultData = shortMessageService.phoneRegister(mobile, passWord, mac, code);
        resolveResult(resultData, response);
        return resultData;
    }

    @RequestMapping(value = Router.ROUTER_PHONE_LOGIN, method = {RequestMethod.POST})
    public ResultData phoneLogin(@RequestParam("mobile")String mobile
            , @RequestParam("passWord")String passWord
            , @RequestParam("mac")String mac,
                                 HttpServletResponse response){
        ResultData resultData = Tools.getThreadResultData();
        resultData = shortMessageService.phoneLogin(mobile, passWord, mac);
        resolveResult(resultData, response);
        return resultData;
    }

    @RequestMapping(value = Router.ROUTER_LOGOUT, method = {RequestMethod.POST})
    public ResultData mallUserLogout(HttpServletRequest request, HttpServletResponse response){
        ResultData resultData = Tools.getThreadResultData();
        userLogout(request, response);
        return resultData;
    }

    @RequestMapping(value = Router.ROUTER_PWD_RESET, method = {RequestMethod.POST})
    public ResultData resetPassword(@RequestParam("mobile")String mobile,
                                    @RequestParam("password")String password,
                                    @RequestParam("code")String code,
                                    @RequestParam("mac")String mac,
                                    HttpServletResponse response){
        ResultData resultData = Tools.getThreadResultData();
        resultData  = shortMessageService.passWordReset(mobile, password, code, mac);
        resolveResult(resultData, response);
        return resultData;
    }

    @RequestMapping(value = Router.ROUTER_USER_INFO, method = {RequestMethod.GET})
    public ResultData resetPassword(@RequestParam("userId")int userId,
                                    @RequestParam(name = "mac", defaultValue = "hqtcmall")String mac){
        ResultData resultData  = shortMessageService.getUserInfoByUserId(userId, mac);
        return resultData;
    }

    private String encryptPassWord(String passWord){
        return DigestUtils.md5Hex(passWord+"hqtc").toLowerCase();
    }
}
