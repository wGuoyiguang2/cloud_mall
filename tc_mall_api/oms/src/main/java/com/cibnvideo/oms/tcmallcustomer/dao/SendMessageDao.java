package com.cibnvideo.oms.tcmallcustomer.dao;

import com.cibnvideo.oms.tcmallcustomer.bean.MessageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@EnableBinding(Source.class)
@Component
public class SendMessageDao {

    @Autowired
    private Source source;

    public boolean sendMessage(MessageInfo msg) {

        try {
            Message<MessageInfo> message = MessageBuilder.withPayload(msg).build();
            return source.output().send(message);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        //return true;
    }
}
