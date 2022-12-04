package com.hqtc.ums.service;

import com.hqtc.common.response.ResultData;

/**
 * Created by wanghaoyang on 18-6-30.
 */
public interface WechatService {

    /**
     * 生成微信二维码
     * @param mac 终端二维码
     * */
    ResultData createQrCode(String mac);

    /**
     * 轮循二维码是否被扫描
     * @param scanId 二维码id
     * @param ticket 微信二维码ticket
     * */
    ResultData scanQrCode(int scanId, String ticket);
}
