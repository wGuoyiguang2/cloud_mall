package com.cibnvideo.jd.mail.impl;

import com.cibnvideo.jd.common.config.BaseConfig;
import com.cibnvideo.jd.common.utils.HttpClientUtil;
import com.cibnvideo.jd.mail.MailService;
import com.cibnvideo.jd.mail.model.MailRequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2019/1/11 14:02
 */
@Service
public class MailServiceImpl implements MailService{
    @Autowired
    private BaseConfig baseConfig;
    @Override
    public void send(String title,String content) {
        MailRequestParam mailRequestParam=new MailRequestParam();
        mailRequestParam.setTitle(title);
        mailRequestParam.setContent(content);
        mailRequestParam.setKey(baseConfig.getMailKey());
        mailRequestParam.setMail(baseConfig.getMailUser());
        HttpClientUtil.post(baseConfig.getMailUrl(),mailRequestParam,"utf-8",false);
    }
}
