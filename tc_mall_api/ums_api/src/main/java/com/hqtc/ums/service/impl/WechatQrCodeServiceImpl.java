package com.hqtc.ums.service.impl;

import com.google.gson.Gson;
import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.ResultData;
import com.hqtc.common.utils.Tools;
import com.hqtc.ums.model.WechatSubScriptionConfig;
import com.hqtc.ums.model.WechatUserInfoBean;
import com.hqtc.ums.model.databasebean.TLoginWxBean;
import com.hqtc.ums.model.databasebean.TUserBean;
import com.hqtc.ums.model.databasebean.TWxScanBean;
import com.hqtc.ums.model.mapper.TLoginWxMapper;
import com.hqtc.ums.model.mapper.TUserMapper;
import com.hqtc.ums.model.mapper.TWxScanMapper;
import com.hqtc.ums.model.response.LoginQrCodeResponse;
import com.hqtc.ums.model.response.LoginResponseBean;
import com.hqtc.ums.model.response.QrCodeResponse;
import com.hqtc.ums.service.WechatQrCodeService;
import com.hqtc.ums.wechat.WechatLoginService;
import com.hqtc.ums.wechat.WechatSubscriptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionTimedOutException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wanghaoyang on 18-11-1.
 */
@Service("WechatQrCodeServiceImpl")
public class WechatQrCodeServiceImpl implements WechatQrCodeService {

    private Logger logger = LoggerFactory.getLogger("WechatQrCodeServiceImpl");

    public WechatSubScriptionConfig wechatSubScriptionConfig;

    @Autowired
    private WechatSubscriptionService wechatSubScriptionService;

    @Autowired
    private TWxScanMapper tWxScanMapper;

    @Autowired
    private TLoginWxMapper tLoginWxMapper;

    @Autowired
    private TUserMapper userMapper;

    @Autowired
    private WechatLoginService wechatLoginService;

    @Override
    public ResultData createLoginQrCode(String mac, String source){
        ResultData resultData = Tools.getThreadResultData();
        Map<String, String> info  = new HashMap<>(1);
        info.put("mac", mac);
        info.put("appid", this.wechatSubScriptionConfig.getWechatAppId());
        String value = this.createValue(info);
        String md5Value = Tools.md5(value);
        TWxScanBean wxScanBean = tWxScanMapper.findScanInfo(md5Value, this.wechatSubScriptionConfig.getWechatAppId(), 1);
        QrCodeResponse qrCodeResponse = new QrCodeResponse();
        int today = (int)(System.currentTimeMillis()/1000);
        if(null != wxScanBean && (today - ((int)(wxScanBean.getCtime().getTime()/1000))< 2500000) && !"".equals(wxScanBean.getTicket())){
            if(this.clearScanInfo(wxScanBean.getId()) < 1){
                resultData.setError(ErrorCode.SERVER_EXCEPTION);
                resultData.setMsg("生成二维码失败");
                return resultData;
            }
            qrCodeResponse.setScanId(String.valueOf(wxScanBean.getId()));
            qrCodeResponse.setQrTicket(wxScanBean.getTicket());
        }else {
            LoginQrCodeResponse loginQrCodeResponse = this.getTicket(this.wechatSubScriptionConfig.getWechatAppId(), value, md5Value, source);
            if(null == loginQrCodeResponse){
                resultData.setError(ErrorCode.FALI);
                resultData.setMsg("生成二维码失败");
                return resultData;
            }
            qrCodeResponse.setScanId(String.valueOf(loginQrCodeResponse.getScanId()));
            qrCodeResponse.setQrTicket(loginQrCodeResponse.getTicket());
        }
        resultData.setData(qrCodeResponse);
        return resultData;
    }


    @Override
    public ResultData<TUserBean> scanQrCode(int sceneId, String openId){
        ResultData resultData = new ResultData();
        TWxScanBean wxScanBean = tWxScanMapper.findScanById(sceneId);
        if(null == wxScanBean){
            resultData.setError(ErrorCode.PARAM_ERROR);
            resultData.setMsg("此事件不存在");
            return resultData;
        }
        if(tWxScanMapper.updateScanInfo(openId, new Date(), sceneId) < 1){
            logger.warn("更新扫码记录失败");
        }
        TLoginWxBean tLoginWxBean = tLoginWxMapper.findByOpenId(openId);
        TUserBean userBean = null;
        if(null == tLoginWxBean){//注册
            try {
                userBean = wechatLoginService.createWecahtUser(wxScanBean.getAppid(), openId, "");
            } catch (TransactionTimedOutException e) {
                logger.error("创建微信用户事务超时:"+openId);
            } catch (RuntimeException e) {
                logger.error("创建微信用户事务超时:"+openId);
            }
            if(null == userBean){
                resultData.setError(ErrorCode.SERVER_EXCEPTION);
                resultData.setMsg("创建用户出错");
                return resultData;
            }
            WechatUserInfoBean wechatUserInfoBean = this.getWechatUserInfo(openId);
            if(null != wechatUserInfoBean){
                userBean.setHeader(wechatUserInfoBean.getHeadimgurl());
                userBean.setNickname(wechatUserInfoBean.getNickname());
                wechatLoginService.updateUserInfo(wechatUserInfoBean.getNickname(), wechatUserInfoBean.getHeadimgurl(), userBean.getId());
            }
        }else {//登录
            userBean = userMapper.getUserById(tLoginWxBean.getUserid());
            if(null == userBean){
                resultData.setError(ErrorCode.SERVER_EXCEPTION);
                resultData.setMsg("用户不存在");
                return resultData;
            }
            userBean = this.updateUserInfo(userBean, openId);
        }
        resultData.setData(userBean);
        return resultData;
    }

    private LoginQrCodeResponse getTicket(String appid, String value, String md5Value, String source){
        TWxScanBean wxScanBean = new TWxScanBean();
        wxScanBean.setAppid(appid);
        wxScanBean.setOpenid("");
        wxScanBean.setValue(value);
        wxScanBean.setTicket("");
        wxScanBean.setValuemd5num(md5Value);
        wxScanBean.setType(1);
        wxScanBean.setSource(source);
        wxScanBean.setCtime(new Date());
        if(tWxScanMapper.addWxScan(wxScanBean) < 1){
            logger.warn("插入数据库失败:生成二维码");
            return null;
        }
        int sceneId = wxScanBean.getId();
        String ticket = this.createTicket(sceneId);
        if(null == ticket || "".equals(ticket)){
            logger.warn("生成二维码失败");
            return null;
        }
        if(tWxScanMapper.updateScanTicket(ticket, sceneId) < 1){
            logger.warn("更新二维码数据库失败");
            return null;
        }
        LoginQrCodeResponse response = new LoginQrCodeResponse();
        response.setTicket(ticket);
        response.setScanId(sceneId);
        return response;
    }

    @Override
    public ResultData checkQrCode(int sceneId, String ticket){
        ResultData resultData = Tools.getThreadResultData();
        TWxScanBean wxScanBean = tWxScanMapper.findScanById(sceneId);
        if(null != wxScanBean && ticket.equals(wxScanBean.getTicket()) && !"".equals(wxScanBean.getOpenid())){
            TLoginWxBean tLoginWxBean = tLoginWxMapper.findByOpenId(wxScanBean.getOpenid());
            if(null == tLoginWxBean){
                resultData.setError(ErrorCode.WX_NOT_SCAN);
                resultData.setMsg("微信未扫描");
                return resultData;
            }
            TUserBean user = userMapper.getUserById(tLoginWxBean.getUserid());
            if(null == user){
                resultData.setError(ErrorCode.WX_NOT_SCAN);
                resultData.setMsg("微信未扫描");
                return resultData;
            }
            LoginResponseBean loginResponse = new LoginResponseBean();
            loginResponse.setNickName(user.getNickname());
            loginResponse.setUserHeader(user.getHeader());
            loginResponse.setUserId(user.getId());
            resultData.setData(loginResponse);
            return resultData;
        }else {
            resultData.setError(ErrorCode.WX_NOT_SCAN);
            resultData.setMsg("微信未扫描");
            return resultData;
        }
    }

    private int clearScanInfo(int scanId){
        return tWxScanMapper.updateClearScan(scanId);
    }

    private String createValue(Map<String, String> info){
        Gson gson = new Gson();
        return gson.toJson(info);
    }

    private String createTicket(int sceneId){
        return  wechatSubScriptionService.createTempQrCode(
                wechatSubScriptionService.getWechatToken(this.wechatSubScriptionConfig),
                sceneId);
    }

    private WechatUserInfoBean getWechatUserInfo(String openId){
        return wechatSubScriptionService.getWechatUserInfoByOpenId(openId, wechatSubScriptionService.getWechatToken(wechatSubScriptionConfig));
    }

    private TUserBean updateUserInfo(TUserBean userBean, String openId){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String d1 = simpleDateFormat.format(userBean.getUtime());
        String d2 = simpleDateFormat.format(new Date());
        if(d2.equals(d1)){
            return userBean;
        }else {
            WechatUserInfoBean wechatUserInfoBean = this.getWechatUserInfo(openId);
            if(null != wechatUserInfoBean){
                if(userBean.getHeader().equals(wechatUserInfoBean.getHeadimgurl()) && userBean.getNickname().equals(wechatUserInfoBean.getNickname())){
                    return userBean;
                }else {
                    userBean.setNickname(wechatUserInfoBean.getNickname());
                    userBean.setHeader(wechatUserInfoBean.getHeadimgurl());
                    wechatLoginService.updateUserInfo(wechatUserInfoBean.getNickname(), wechatUserInfoBean.getHeadimgurl(), userBean.getId());
                }
            }
            return userBean;
        }
    }
}
