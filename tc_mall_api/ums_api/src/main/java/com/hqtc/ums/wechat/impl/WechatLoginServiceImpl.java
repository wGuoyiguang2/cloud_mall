package com.hqtc.ums.wechat.impl;

import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.ResultData;
import com.hqtc.ums.model.WxCode2SessionBean;
import com.hqtc.ums.model.databasebean.TLoginWxBean;
import com.hqtc.ums.model.databasebean.TUserBean;
import com.hqtc.ums.model.mapper.TLoginWxMapper;
import com.hqtc.ums.model.mapper.TUserMapper;
import com.hqtc.ums.model.response.AppletLoginResponseBean;
import com.hqtc.ums.wechat.WechatAppletService;
import com.hqtc.ums.wechat.WechatLoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 微信，小程序登录注册相关方法
 * Created by wanghaoyang on 18-10-11.
 */
@Service("WechatLoginServiceImpl")
public class WechatLoginServiceImpl implements WechatLoginService {

    private Logger logger = LoggerFactory.getLogger("WechatLoginServiceImpl");

    @Autowired
    private WechatAppletService wechatAppletService;

    @Autowired
    private TLoginWxMapper tLoginWxMapper;

    @Autowired
    private TUserMapper tUserMapper;

    @Override
    public TUserBean getWechatUserInfo(String openId, String appId){
        TLoginWxBean tLoginWxBean = tLoginWxMapper.getByAppIdAndOpenId(appId, openId);
        if(null == tLoginWxBean){
            return null;
        }
        return tUserMapper.getUserById(tLoginWxBean.getUserid());
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, timeout = 3)
    @Override
    public TUserBean createWecahtUser(String appId, String openId, String unionId) {
        TUserBean tUserBean = new TUserBean();
        tUserBean.setNickname(openId);
        tUserBean.setHeader("");
        tUserBean.setSource("");
        int state = tUserMapper.addUser(tUserBean);
        if(state < 1){
            logger.error("创建用户插入数据库失败1:"+appId+" "+openId);
            throw new RuntimeException("创建用户插入数据库失败1");
        }
        TLoginWxBean tLoginWxBean = new TLoginWxBean();
        tLoginWxBean.setAppid(appId);
        tLoginWxBean.setOpenid(openId);
        tLoginWxBean.setUnionid(unionId);
        tLoginWxBean.setUserid(tUserBean.getId());
        if(tLoginWxMapper.addWxInfo(tLoginWxBean) < 1){
            logger.error("创建用户插入数据库失败2:"+appId+" "+openId);
            throw new RuntimeException("创建用户插入数据库失败2");
        }
        return tUserBean;
    }

    @Override
    public AppletLoginResponseBean formatUserInfo(TUserBean tUserBean){
        AppletLoginResponseBean responseBean = new AppletLoginResponseBean();
        responseBean.setUserId(tUserBean.getId());
        responseBean.setNickName(tUserBean.getNickname());
        responseBean.setUserHeader(tUserBean.getHeader());
        return responseBean;
    }

    @Override
    public int updateWxUnionId(int id, String unionId){
        return tLoginWxMapper.updateUnionIdById(unionId, id);
    }

    @Async
    @Override
    public void updateUserInfo(String nickName, String header, int userId){
        tUserMapper.updateUserInfo(this.formatHeader(header), this.formatNickName(nickName), userId);
    }

    private String formatHeader(String header){
        if(header.length() > 512){
            return header.substring(0, 511);
        }else {
            return header;
        }
    }

    private String formatNickName(String nickName){
        if(nickName.length() > 32){
            return nickName.substring(0, 31);
        }else {
            return nickName;
        }
    }
}
