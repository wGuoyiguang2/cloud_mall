package com.cibnvideo.jdsynctask.config;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface MessageSource {

    String OUTPUT_JDSYNC_CHANGE = "output-jdsync-change";

    @Output(OUTPUT_JDSYNC_CHANGE)
    MessageChannel outputJdSyncChange();
}
