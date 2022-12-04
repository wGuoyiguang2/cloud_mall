package com.hqtc.bms.service;

import com.hqtc.bms.model.params.MessageInfo;

/**
 * description:发送消息接口
 * Created by laiqingchuang on 18-9-5 .
 */
public interface SendMessageService {

    boolean sendAftersaleMessage(MessageInfo messageInfo);
}
