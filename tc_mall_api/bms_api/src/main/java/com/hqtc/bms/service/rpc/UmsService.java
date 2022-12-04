package com.hqtc.bms.service.rpc;

import com.hqtc.bms.config.RPCRouter;
import com.hqtc.bms.model.response.TLoginAccountBean;
import com.hqtc.common.config.FeignClientService;
import com.hqtc.common.response.ResultData;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/7/20 20:39
 */
@FeignClient(value = FeignClientService.UMSAPI,fallbackFactory = UmsServiceFallback.class)
public interface UmsService {
    @RequestMapping(value= RPCRouter.ROUTER_USER_INFO,method = RequestMethod.GET)
    ResultData<Map<String,Object>> getUserInfo(@RequestParam("userId")int userId,
                                               @RequestParam(name = "mac", defaultValue = "hqtcmall")String mac);

    @RequestMapping(value = RPCRouter.ROUTER_GET_VENDER_INFO, method = RequestMethod.GET)
    ResultData<TLoginAccountBean> getVenderUserInfo(@RequestParam("userName")String userName, @RequestParam("passWord")String passWord);
}
