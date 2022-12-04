package com.hqtc.ums.service.impl;

import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.ResultData;
import com.hqtc.ums.model.databasebean.TUserBean;
import com.hqtc.ums.wechat.impl.AbstractWechatSubScriptionMessageServiceImpl;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by wanghaoyang on 18-11-2.
 */
@Service("YouXiangWechatSubScriptionHandler")
public class YouXiangWechatSubScriptionHandler extends AbstractWechatSubScriptionMessageServiceImpl {

    private Logger logger = LoggerFactory.getLogger("WechatQrCodeServiceImpl");

    private String content = "您已经成功登录。";

    @Autowired
    @Resource(name = "JRYXWechatQrCodeServiceImpl")
    private WechatQrCodeServiceImpl wechatQrCodeService;


    public void subScriptMessageHandler(WxMpXmlMessage wxMpXmlMessage, HttpServletResponse response){
        logger.info("关注事件:"+wxMpXmlMessage.getFromUser());
        ResultData<TUserBean> resultData = this.scanHander(wxMpXmlMessage.getEventKey(), wxMpXmlMessage.getFromUser());
        if(ErrorCode.SUCCESS == resultData.getError()){
            WxMpXmlOutMessage wxMpXmlOutMessage = WxMpXmlOutMessage.TEXT()
                    .content(content)
                    .fromUser(wxMpXmlMessage.getToUser())
                    .toUser(wxMpXmlMessage.getFromUser())
                    .build();
            this.sendMessageHandler(wxMpXmlOutMessage, response);
        }
    }

    public void scanMessageHandler(WxMpXmlMessage wxMpXmlMessage, HttpServletResponse response){
        logger.info("关注事件:"+wxMpXmlMessage.getFromUser());
        ResultData<TUserBean> resultData = this.scanHander(wxMpXmlMessage.getEventKey(), wxMpXmlMessage.getFromUser());
        if(ErrorCode.SUCCESS == resultData.getError()){
            WxMpXmlOutMessage wxMpXmlOutMessage = WxMpXmlOutMessage.TEXT()
                    .content(content)
                    .fromUser(wxMpXmlMessage.getToUser())
                    .toUser(wxMpXmlMessage.getFromUser())
                    .build();
            this.sendMessageHandler(wxMpXmlOutMessage, response);
        }else {
            WxMpXmlOutMessage wxMpXmlOutMessage = WxMpXmlOutMessage.TEXT()
                    .content("登录失败,请再次尝试")
                    .fromUser(wxMpXmlMessage.getToUser())
                    .toUser(wxMpXmlMessage.getFromUser())
                    .build();
            this.sendMessageHandler(wxMpXmlOutMessage, response);
        }
    }

    public void unSubScriptMessageHandler(WxMpXmlMessage wxMpXmlMessage, HttpServletResponse response){
        String eventKey = wxMpXmlMessage.getEventKey();
        logger.info(eventKey);
    }

    public void textMessageHandler(WxMpXmlMessage wxMpXmlMessage, HttpServletResponse response){
        String content = wxMpXmlMessage.getContent();
        logger.info(content);
        logger.info(wxMpXmlMessage.getFromUser());
    }

    private ResultData scanHander(String sceneId, String openId){
        if(sceneId.startsWith("qrscene_")){
            sceneId = sceneId.substring(8, sceneId.length());
        }
        return wechatQrCodeService.scanQrCode(Integer.valueOf(sceneId), openId);
    }

}
