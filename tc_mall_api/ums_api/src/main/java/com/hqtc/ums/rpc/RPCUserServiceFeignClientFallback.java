package com.hqtc.ums.rpc;

import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by wanghaoyang on 18-6-28.
 */
@Component
public class RPCUserServiceFeignClientFallback implements RPCUserService{

    @Override
    public String verifyCode(String mobile, String code){
        return null;
    }

    public Map<String, String> sendCode(String mobile, String msgType, String versionId, String mac, String osVersionId, String terminalChannel){
        return null;
    }

    public String userRegister(String mobile,String passWord,String code,String mac,String terminalChannel,
                                            String versionId, String osVersionId){
        return null;
    }

    public String userLogin(String mobile,String passWord,String mac,String versionId,String osVersionId){
        return null;
    }

    @Override
    public String createQrCode(String deviceId,String clientId,String groupId,String cid,String channelNumber,String versionId,String mac){
        return null;
    }

    @Override
    public String scanQrCode(int scanId,String ticket){
        return null;
    }

    @Override
    public String resetPassWord(String mobile,String password,String mac,String verificationCode,String versionId,String osVersionId){
        return null;
    }

    @Override
    public String autoLogin(int userId,String deviceId,String versionId,String mac){
        return null;
    }
}
