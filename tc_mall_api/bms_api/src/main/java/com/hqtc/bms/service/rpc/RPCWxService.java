package com.hqtc.bms.service.rpc;

import com.hqtc.bms.model.rpc.WxCode2SessionBean;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * Created by wanghaoyang on 18-9-27.
 */
@FeignClient(name = "wx-server" ,url="https://api.weixin.qq.com", fallback = RPCWxServiceFallback.class)
public interface RPCWxService {

    @RequestMapping(value = "/sns/jscode2session", method = RequestMethod.GET)
    String getWxUserInfoByUserCode(@RequestParam("appid") String appid,
                                               @RequestParam("secret") String secret,
                                               @RequestParam("js_code") String js_code,
                                               @RequestParam("grant_type") String grant_type);
}
