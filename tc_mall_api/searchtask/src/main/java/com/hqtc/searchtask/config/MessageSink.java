package com.hqtc.searchtask.config;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface MessageSink {
    String INPUT_JDSYNC = "input-jdsync";

    String INPUT_OMS = "input-oms";

    @Input(INPUT_JDSYNC)
    SubscribableChannel inputJdsync();

    @Input(INPUT_OMS)
    SubscribableChannel inputOMS();
}
