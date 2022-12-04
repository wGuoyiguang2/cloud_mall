package com.cibnvideo.jdsynctask.service.impl;

import com.cibnvideo.jdsynctask.dao.SendMessageDao;
import com.cibnvideo.jdsynctask.model.MessageInfo;
import com.cibnvideo.jdsynctask.service.SendMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SendMessageServiceImpl implements SendMessageService {

    @Autowired
    SendMessageDao service;
    @Override
    public boolean productDetailChange(long sku) {
        MessageInfo messageInfo = new MessageInfo();
        messageInfo.setType(0);
        messageInfo.setSku(sku);
        return service.jdSyncChange(messageInfo);
    }
}
