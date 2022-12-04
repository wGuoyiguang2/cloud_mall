package com.hqtc.ums.rpc;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**TODO
 * Created by wanghaoyang on 18-10-16.
 */
@FeignClient(name = "wx-server" ,url="http://114.118.2.242:8090/sms/send.do", fallback = RPCWechatServiceFallback.class)
public interface RPCSmsService {

    @RequestMapping(value = "/sns/jscode2session", method = RequestMethod.GET)
    String sendMessage(@RequestParam("userId") String userId,
                                   @RequestParam("pwd") String pwd,
                                   @RequestParam("timespan") String timespan,
                                   @RequestParam("mobile") String mobile,
                                   @RequestParam("content")String content,
                                   @RequestParam("msgid")String msgId,
                                   @RequestParam("msgfmt")String msgfmt);
}
