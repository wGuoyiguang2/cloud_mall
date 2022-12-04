package com.cibnvideo.thirdpart.message;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface MessageSink {

    String JD_SITE_PRICE_SINK = "jdSitePriceSink";

    @Input(JD_SITE_PRICE_SINK)
    SubscribableChannel inputJob();
}
