package com.hqtc.ums.wechat.impl;

import com.hqtc.ums.model.WechatSubScriptionConfig;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.util.crypto.SHA1;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 处理微信消息的类
 * 如需要对不同的消息进行处理，请继承此类,并重写对应类型的消息处理
 * Created by wanghaoyang on 18-11-1.
 */
public class AbstractWechatSubScriptionMessageServiceImpl {

    private Logger logger = LoggerFactory.getLogger("AbstractWechatSubScriptionMessageServiceImpl");

    //消息认证
    public boolean checkSignature(WechatSubScriptionConfig config, String timestamp, String nonce, String signature){
        try {
            return SHA1.gen(config.getWechatToken(), timestamp, nonce)
                    .equals(signature);
        } catch (Exception e) {
            logger.error("Checking signature failed, and the reason is :" + e.getMessage());
            return false;
        }
    }

    private WxMpXmlMessage resolveWxMessage(HttpServletRequest request){
        WxMpXmlMessage inMsg;
        try {
            inMsg = WxMpXmlMessage.fromXml(request.getInputStream());
        }catch (Exception e){
            logger.error("解析微信消息失败");
            inMsg = null;
        }
        return inMsg;
    }

    /**
     * 关注的消息处理
     * */
    protected void subScriptMessageHandler(WxMpXmlMessage wxMpXmlMessage, HttpServletResponse response){
        this.defaultMessageHandler(wxMpXmlMessage, response);
    }

    /**
     * 取消关注的消息处理
     * */
    protected void unSubScriptMessageHandler(WxMpXmlMessage wxMpXmlMessage, HttpServletResponse response){
        this.defaultMessageHandler(wxMpXmlMessage, response);
    }

    /**
     * 扫描带参数的二维码事件
     * */
    protected void scanMessageHandler(WxMpXmlMessage wxMpXmlMessage, HttpServletResponse response){
        this.defaultMessageHandler(wxMpXmlMessage, response);
    }

    /**
     * 上报地理位置事件
     * */
    protected void reportLocationMessageHandler(WxMpXmlMessage wxMpXmlMessage, HttpServletResponse response){
        this.defaultMessageHandler(wxMpXmlMessage, response);
    }

    /**
     * 自定义菜单事件
     * */
    protected void clickMessageHandler(WxMpXmlMessage wxMpXmlMessage, HttpServletResponse response){
        this.defaultMessageHandler(wxMpXmlMessage, response);
    }

    /**
     * 点击菜单跳转链接事件
     * */
    protected void viewMessageHandler(WxMpXmlMessage wxMpXmlMessage, HttpServletResponse response){
        this.defaultMessageHandler(wxMpXmlMessage, response);
    }

    /**
     * 文本类类的消息处理
     * */
    protected void textMessageHandler(WxMpXmlMessage wxMpXmlMessage, HttpServletResponse response){
        this.defaultMessageHandler(wxMpXmlMessage, response);
    }

    /**
     * 图片类消息处理
     * */
    protected void imageMessageHandler(WxMpXmlMessage wxMpXmlMessage, HttpServletResponse response){
        this.defaultMessageHandler(wxMpXmlMessage, response);
    }

    /**
     * 语音类消息处理
     * */
    protected void voiceMessageHandler(WxMpXmlMessage wxMpXmlMessage, HttpServletResponse response){
        this.defaultMessageHandler(wxMpXmlMessage, response);
    }

    /**
     * 视频消息处理
     * */
    protected void videoMessageHandler(WxMpXmlMessage wxMpXmlMessage, HttpServletResponse response){
        this.defaultMessageHandler(wxMpXmlMessage, response);
    }

    /**
     * 小视频消息处理
     * */
    protected void shortVideoMessageHandler(WxMpXmlMessage wxMpXmlMessage, HttpServletResponse response){
        this.defaultMessageHandler(wxMpXmlMessage, response);
    }

    /**
     * 地理位置消息处理
     * */
    protected void locationMessageHandler(WxMpXmlMessage wxMpXmlMessage, HttpServletResponse response){
        this.defaultMessageHandler(wxMpXmlMessage, response);
    }

    /**
     * 链接消息处理
     * */
    protected void linkMessageHandler(WxMpXmlMessage wxMpXmlMessage, HttpServletResponse response){
        this.defaultMessageHandler(wxMpXmlMessage, response);
    }

    /**
     * 自定义消息发送
     * */
    protected void sendMessageHandler(WxMpXmlOutMessage wxMpXmlOutMessage, HttpServletResponse response){
        try {
            response.getWriter().write(new String(wxMpXmlOutMessage.toXml().getBytes(), "iso8859-1"));
        }catch (IOException e){
            logger.warn("返回微信消息失败");
        }
    }


    private void defaultMessageHandler(WxMpXmlMessage wxMpXmlMessage, HttpServletResponse response){
        WxMpXmlOutMessage wxMpXmlOutMessage = WxMpXmlOutMessage.TEXT()
                .content("欢迎关注")
                .fromUser(wxMpXmlMessage.getToUser())
                .toUser(wxMpXmlMessage.getFromUser())
                .build();
        try {
            response.getWriter().write(new String(wxMpXmlOutMessage.toXml().getBytes(), "iso8859-1"));
        }catch (IOException e){
            logger.warn("返回微信消息失败");
        }
    }

    public void mssageHandler(HttpServletRequest request, HttpServletResponse response){
        WxMpXmlMessage inMsg = this.resolveWxMessage(request);
        if(null == inMsg){
            logger.warn("解析微信消息失败");
            return;
        }
        switch (inMsg.getMsgType()){
            case WxConsts.XmlMsgType.EVENT:
                this.eventMessageHandler(inMsg, response);
                break;
            case WxConsts.XmlMsgType.TEXT:
                this.textMessageHandler(inMsg, response);
                break;
            case WxConsts.XmlMsgType.IMAGE:
                this.imageMessageHandler(inMsg, response);
                break;
            case WxConsts.XmlMsgType.VOICE:
                this.voiceMessageHandler(inMsg, response);
                break;
            case WxConsts.XmlMsgType.VIDEO:
                this.videoMessageHandler(inMsg, response);
                break;
            case WxConsts.XmlMsgType.SHORTVIDEO:
                this.shortVideoMessageHandler(inMsg, response);
                break;
            case WxConsts.XmlMsgType.LOCATION:
                this.locationMessageHandler(inMsg, response);
                break;
            case WxConsts.XmlMsgType.LINK:
                this.linkMessageHandler(inMsg, response);
                break;
            default:
                this.defaultMessageHandler(inMsg, response);
                break;
        }
    }

    private void eventMessageHandler(WxMpXmlMessage inMsg, HttpServletResponse response){
        switch (inMsg.getEvent()){
            case "subscribe":
                this.subScriptMessageHandler(inMsg, response);
                break;
            case "unsubscribe":
                this.unSubScriptMessageHandler(inMsg, response);
                break;
            case "SCAN":
                this.scanMessageHandler(inMsg, response);
                break;
            case "LOCATION":
                this.reportLocationMessageHandler(inMsg, response);
                break;
            case "CLICK":
                this.clickMessageHandler(inMsg, response);
                break;
            case "VIEW":
                this.viewMessageHandler(inMsg, response);
                break;
            default:
                this.defaultMessageHandler(inMsg, response);
                break;
        }
    }
}
