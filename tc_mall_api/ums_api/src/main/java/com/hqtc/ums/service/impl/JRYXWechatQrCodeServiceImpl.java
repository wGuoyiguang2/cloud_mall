package com.hqtc.ums.service.impl;

import com.hqtc.ums.model.YouXiangWecahtSubScriptionConfigBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by wanghaoyang on 18-11-22.
 */
@Service("JRYXWechatQrCodeServiceImpl")
public class JRYXWechatQrCodeServiceImpl extends WechatQrCodeServiceImpl {

    private YouXiangWecahtSubScriptionConfigBean jryxWechatAppletConfigBean;

    @Autowired
    public JRYXWechatQrCodeServiceImpl(YouXiangWecahtSubScriptionConfigBean jryxWechatAppletConfigBean){
        this.wechatSubScriptionConfig = jryxWechatAppletConfigBean;
    }

}
