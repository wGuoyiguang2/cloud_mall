package com.cibnvideo.jdsynctask.dao;


import com.cibnvideo.jdsynctask.config.MessageSource;
import com.cibnvideo.jdsynctask.model.MessageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@EnableBinding(MessageSource.class)
@Component
public class SendMessageDao {

    @Autowired
    private MessageSource messageSource;

    public boolean jdSyncChange(MessageInfo msg) {

        try {
            Message<MessageInfo> message = MessageBuilder.withPayload(msg).build();
            return messageSource.outputJdSyncChange().send(message);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
