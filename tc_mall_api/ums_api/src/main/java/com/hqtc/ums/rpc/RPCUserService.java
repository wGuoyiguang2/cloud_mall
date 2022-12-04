package com.hqtc.ums.rpc;

import com.hqtc.ums.config.RPCRouter;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * Created by wanghaoyang on 18-6-28.
 */
@Component("RPCUserService")
@FeignClient(name = "tv-ums", url = "${RPC.umsHost}", fallback = RPCUserServiceFeignClientFallback.class)
public interface RPCUserService {

    @RequestMapping(method = RequestMethod.GET, value = RPCRouter.RPC_ROUTER_START_VERIFY_CODE)
    String verifyCode(@RequestParam("mobile") String mobile, @RequestParam("code")String code);

    @RequestMapping(method = RequestMethod.GET, value = RPCRouter.RPC_ROUTER_START_SEND_CODE_MESSAGE)
    Map<String, String> sendCode(@RequestParam("mobile") String mobile,
                                 @RequestParam("msgType")String msgType,
                                 @RequestParam("versionId")String versionId,
                                 @RequestParam("mac")String mac,
                                 @RequestParam("osVersionId")String osVersionId,
                                 @RequestParam("terminalChannel")String terminalChannel
                                 );


    @RequestMapping(value = RPCRouter.RPC_ROUTER_START_USER_REGISTER, method = {RequestMethod.POST})
    String userRegister(@RequestParam(value = "mobile")String mobile,
                               @RequestParam(value = "password")String passWord,
                               @RequestParam(value = "verificationCode")String code,
                               @RequestParam(value = "mac")String mac,
                               @RequestParam(value = "terminalChannel")String terminalChannel,
                                     @RequestParam("versionId")String versionId,
                                     @RequestParam("osVersionId")String osVersionId);

    @RequestMapping(value = RPCRouter.RPC_ROUTER_START_USER_LOGIN, method = {RequestMethod.POST})
    String userLogin(@RequestParam(value = "mobile")String mobile,
                     @RequestParam(value = "password")String passWord,
                     @RequestParam(value = "mac")String mac,
                     @RequestParam(value = "versionId")String versionId,
                     @RequestParam(value = "osVersionId")String osVersionId);

    @RequestMapping(value = RPCRouter.RPC_ROUTER_START_CREATE_QR_CODE, method = {RequestMethod.POST})
    String createQrCode(@RequestParam(value = "deviceId")String deviceId,
                               @RequestParam(value = "clientId")String clientId,
                               @RequestParam(value = "groupId")String groupId,
                               @RequestParam(value = "cid")String cid,
                               @RequestParam(value = "channelNumber")String channelNumber,
                               @RequestParam(value = "versionId")String versionId,
                               @RequestParam(value = "mac")String mac);

    @RequestMapping(value = RPCRouter.RPC_ROUTER_START_EQ_CHECK_SCAN, method = RequestMethod.GET)
    String scanQrCode(@RequestParam(value = "scanid", defaultValue = "0")int scanId,
                                 @RequestParam(value = "ticket", defaultValue = "")String ticket);

    @RequestMapping(value = RPCRouter.RPC_ROUTER_START_RESET_PASSWORD, method = RequestMethod.POST)
    String resetPassWord(@RequestParam(value = "mobile")String mobile,
                         @RequestParam(value = "password")String password,
                         @RequestParam(value = "mac")String mac,
                         @RequestParam(value = "verificationCode")String verificationCode,
                         @RequestParam(value = "versionId")String versionId,
                         @RequestParam(value = "osVersionId")String osVersionId);

    @RequestMapping(value = RPCRouter.RPC_ROUTER_AUTO_LOGIN, method = RequestMethod.GET)
    String autoLogin(@RequestParam(value = "userId")int userId,
                         @RequestParam(value = "deviceId")String deviceId,
                         @RequestParam(value = "versionId")String versionId,
                         @RequestParam(value = "mac")String mac);
}
