package com.hqtc.bms.model.base;

import com.hqtc.bms.model.params.MessageInfo;
import com.hqtc.bms.service.MessageSink;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@EnableBinding(MessageSink.class)
@Component
public class SendMessageDao {
    private Logger logger  = LoggerFactory.getLogger(getClass());

    @Autowired
    private MessageSink source;

    public boolean sendAftersaleMessage(MessageInfo messageInfo) {
        try {
            logger.info("售后开始发送消息............");
            Message<MessageInfo> message = MessageBuilder.withPayload(messageInfo).build();
            boolean result = source.outputBmsAftersale().send(message);
            logger.info("售后发送消息:"+result);
        } catch (Exception e) {
            logger.error("售后发送消息失败: " + e.getMessage());
            return false;
        }
        return true;
    }
}