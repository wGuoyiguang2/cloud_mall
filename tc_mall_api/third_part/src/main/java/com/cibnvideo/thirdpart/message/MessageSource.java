package com.cibnvideo.thirdpart.message;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;

@Service
public interface MessageSource {
    String JD_SITE_PRICE_SOURCE = "jdSitePriceSource";
    @Output(JD_SITE_PRICE_SOURCE)
    MessageChannel outputJDSitePrice();
}
