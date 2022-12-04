package com.hqtc.ums.service;

import com.hqtc.common.response.ResultData;
import com.hqtc.ums.model.databasebean.TUserBean;

/**
 * Created by wanghaoyang on 18-11-1.
 */
public interface WechatQrCodeService {

    /**
     * 生成登录二维码
     * */
    ResultData createLoginQrCode(String mac, String source);

    /**
     * 二维码扫描
     * */
    ResultData<TUserBean> scanQrCode(int sceneId, String openId);

    /**
     * 轮询二维码
     * */
    ResultData checkQrCode(int sceneId, String ticket);
}
