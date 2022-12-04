package com.hqtc.ums.wechat.impl;

import com.google.gson.Gson;
import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.ResultData;
import com.hqtc.ums.model.WechatApplicationConfigBean;
import com.hqtc.ums.model.WxCode2SessionBean;
import com.hqtc.ums.rpc.RPCWechatService;
import com.hqtc.ums.wechat.WechatAppletService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by wanghaoyang on 18-10-11.
 */
@Service("WechatAppletServiceImpl")
public class WechatAppletServiceImpl implements WechatAppletService {

    private Logger logger = LoggerFactory.getLogger("WechatAppletServiceImpl");

    @Autowired
    private WechatApplicationConfigBean wechatApplicationConfigBean;

    @Autowired
    private RPCWechatService rpcWechatService;

    @Override
    public boolean checkWechatAppletExist(String applicationName){
        if(wechatApplicationConfigBean.getApplet().keySet().contains(applicationName)){
            return true;
        }
        return false;
    }

    @Override
    public ResultData<WxCode2SessionBean> getWxUserInfoByUserCode(String userCode, String applicationName){
        ResultData resultData = new ResultData();
        if(!this.checkWechatAppletExist(applicationName)){
            logger.info("小程序不存在:"+applicationName);
            resultData.setError(ErrorCode.PARAM_ERROR);
            resultData.setMsg("小程序不存在:"+applicationName);
            return resultData;
        }
        String res = rpcWechatService.getWxUserInfoByUserCode(wechatApplicationConfigBean.getApplet().get(applicationName).getOrDefault("appid", "")
                , wechatApplicationConfigBean.getApplet().get(applicationName).getOrDefault("appSecret", "")
                , userCode
                , "authorization_code");
        if(null == res){
            resultData.setError(ErrorCode.SERVER_EXCEPTION);
            resultData.setMsg("调用微信/sns/jscode2session接口失败");
            return resultData;
        }
        Gson gson = new Gson();
        WxCode2SessionBean wxCode2SessionBean = gson.fromJson(res, WxCode2SessionBean.class);
        if(null == wxCode2SessionBean){
            resultData.setError(ErrorCode.SERVER_EXCEPTION);
            resultData.setMsg("解析/sns/jscode2session接口返回结果异常");
            return resultData;
        }
        if(0 != wxCode2SessionBean.getErrcode()){
            if(40029 == wxCode2SessionBean.getErrcode()){
                resultData.setError(ErrorCode.PARAM_ERROR);
                resultData.setMsg("userCode无效，请刷新");
            }else if(45011 == wxCode2SessionBean.getErrcode()){
                resultData.setError(ErrorCode.REQUEST_FREQUENTLY);
                resultData.setMsg("请求过于频繁，请稍后重试");
            }else if(-1 == wxCode2SessionBean.getErrcode()){
                resultData.setError(ErrorCode.WECHAT_SERVER_ERROR);
                resultData.setMsg("微信服务器繁忙，请稍后再试");
            }else {
                resultData.setError(ErrorCode.SERVER_EXCEPTION);
                resultData.setMsg("调用微信接口错误:" + wxCode2SessionBean.getErrmsg());
            }
            return resultData;
        }
        wxCode2SessionBean.setAppid(wechatApplicationConfigBean.getApplet().get(applicationName).getOrDefault("appid", ""));
        resultData.setData(wxCode2SessionBean);
        return resultData;
    }
}
