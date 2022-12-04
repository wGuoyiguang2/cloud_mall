package com.hqtc.bms.service;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * description:消息配置接口
 * Created by laiqingchuang on 18-9-5 .
 */
public interface MessageSink {

    String INPUT_BMS_AFTERSALE="input_bms_aftersale";

    String OUTPUT_BMS_AFTERSALE="output_bms_aftersale";

    @Input(INPUT_BMS_AFTERSALE)
    SubscribableChannel inputBmsAftersale();

    @Output(OUTPUT_BMS_AFTERSALE)
    MessageChannel outputBmsAftersale();
}
