package com.hqtc.bms.service.rpc;

import com.hqtc.bms.model.rpc.WxCode2SessionBean;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * Created by wanghaoyang on 18-9-27.
 */
@Component
public class RPCWxServiceFallback implements RPCWxService {

    @Override
    public String getWxUserInfoByUserCode(@RequestParam("appid") String appid,
                                                      @RequestParam("secret") String secret,
                                                      @RequestParam("js_code") String js_code,
                                                      @RequestParam("grant_type") String grant_type){
        return null;
    }
}
