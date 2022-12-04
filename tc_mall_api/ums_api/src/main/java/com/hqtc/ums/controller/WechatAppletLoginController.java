package com.hqtc.ums.controller;

import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.ResultData;
import com.hqtc.common.utils.Tools;
import com.hqtc.ums.config.Router;
import com.hqtc.ums.model.WxCode2SessionBean;
import com.hqtc.ums.model.databasebean.TUserBean;
import com.hqtc.ums.wechat.WechatAppletService;
import com.hqtc.ums.wechat.WechatLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionTimedOutException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wanghaoyang on 18-10-11.
 */
@RestController
public class WechatAppletLoginController extends CommonController{

    @Autowired
    private WechatLoginService wechatLoginService;

    @Autowired
    private WechatAppletService wechatAppletService;

    @RequestMapping(value = Router.ROUTER_WECHAT_APPLET_LOGIN, method = RequestMethod.POST)
    public ResultData appletLogin(@RequestParam(value = "userCode")String userCode,
//                                  @RequestParam(value = "appName")String appName,
                                  @RequestParam(value = "venderId")int venderId,
                                  HttpServletResponse response) {
        String appName = String.valueOf(venderId);
        ResultData resultData = new ResultData();
        ResultData<WxCode2SessionBean> re = wechatAppletService.getWxUserInfoByUserCode(userCode, appName);
        if(ErrorCode.SUCCESS != re.getError()){
            return re;
        }
        WxCode2SessionBean wxCode2SessionBean = re.getData();
        TUserBean tUserBean = wechatLoginService.getWechatUserInfo(wxCode2SessionBean.getOpenid(), wxCode2SessionBean.getAppid());
        if (null != tUserBean) {
            resultData.setData(wechatLoginService.formatUserInfo(tUserBean));
            resolveResult2(resultData, response);
            return resultData;
        }else {
            try {
                tUserBean = wechatLoginService.createWecahtUser(wxCode2SessionBean.getAppid(), wxCode2SessionBean.getOpenid()
                        , wxCode2SessionBean.getUnionid());
                resultData.setData(wechatLoginService.formatUserInfo(tUserBean));
                resolveResult2(resultData, response);
                return resultData;
            } catch (TransactionTimedOutException e) {
                resultData.setError(ErrorCode.SERVER_EXCEPTION);
                resultData.setMsg("插入数据时失败1");
                return resultData;
            } catch (RuntimeException e) {
                resultData.setError(ErrorCode.SERVER_EXCEPTION);
                resultData.setMsg("插入数据时失败2");
                return resultData;
            }
        }
    }

    @RequestMapping(value = Router.ROUTER_WECHAT_APPLET_OPENID, method = RequestMethod.GET)
    public ResultData appletLogin(@RequestParam(value = "userCode")String userCode,
//                                  @RequestParam(value = "appName")String appName,
                                  @RequestParam(value = "venderId")int venderId){
        String appName = String.valueOf(venderId);
        ResultData resultData = new ResultData();
        ResultData<WxCode2SessionBean> re = wechatAppletService.getWxUserInfoByUserCode(userCode, appName);
        if(ErrorCode.SUCCESS != re.getError()){
            return re;
        }
        Map<String, String> data = new HashMap<>(1);
        data.put("openId", re.getData().getOpenid());
        resultData.setData(data);
        return resultData;
    }
}
