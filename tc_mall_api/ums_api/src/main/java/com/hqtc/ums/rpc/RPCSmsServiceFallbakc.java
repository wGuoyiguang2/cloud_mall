package com.hqtc.ums.rpc;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by wanghaoyang on 18-10-16.
 */
@Component
public class RPCSmsServiceFallbakc implements RPCSmsService {

    @Override
    public String sendMessage(@RequestParam("userId") String userId,
                       @RequestParam("pwd") String pwd,
                       @RequestParam("timespan") String timespan,
                       @RequestParam("mobile") String mobile,
                       @RequestParam("content")String content,
                       @RequestParam("msgid")String msgId,
                       @RequestParam("msgfmt")String msgfmt){
        return null;
    }
}
