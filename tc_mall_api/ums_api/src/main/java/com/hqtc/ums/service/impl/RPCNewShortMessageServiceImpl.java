package com.hqtc.ums.service.impl;

import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.ResultData;
import com.hqtc.ums.model.mapper.TUserMapper;
import com.hqtc.ums.model.response.OldVersionLoginResponse;
import com.hqtc.ums.model.response.TcUmsUserInfoResponse;
import com.hqtc.ums.model.response.TcUmsVerifyCodeResponse;
import com.hqtc.ums.service.TcUmsProxyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wanghaoyang on 18-11-17.
 */
@Service("RPCNewShortMessageServiceImpl")
public class RPCNewShortMessageServiceImpl extends RPCShortMessageServiceImpl {

    private Logger logger = LoggerFactory.getLogger("RPCNewShortMessageServiceImpl");

    @Autowired
    private TcUmsProxyService tcUmsProxyService;

    @Override
    public ResultData sendShortMessage(String mobile, int msgType, String mac) {
        ResultData resultData = tcUmsProxyService.sendCode(mobile);
        if (null == resultData) {
            resultData = new ResultData();
            resultData.setError(ErrorCode.SERVER_EXCEPTION);
            resultData.setMsg("tc_ums服务器异常");
            return resultData;
        }
        if(resultData.getError() != ErrorCode.SUCCESS){
            resultData.setError(ErrorCode.SERVER_EXCEPTION);
            resultData.setMsg(resultData.getMsg());
            return resultData;
        }
        return resultData;
    }

    @Override
    public boolean verifyCode(String mobile, String code) {
        ResultData resultData = tcUmsProxyService.verifyCode(mobile, code);
        if (null == resultData) {
            logger.error("tc_ums服务异常");
            return false;
        }
        if(resultData.getError() != ErrorCode.SUCCESS){
            logger.warn(resultData.getMsg());
            return false;
        }
        TcUmsVerifyCodeResponse verifyCodeResponse = (TcUmsVerifyCodeResponse) resultData.getData();
        return verifyCodeResponse.isResult();
    }

    @Override
    public ResultData phoneRegister(String mobile, String passWord, String mac, String code) {
        return this.rpcResponseFormat(tcUmsProxyService.phonePassWordRegister(mobile, passWord, code, "hqtc_mall_"));
    }

    @Override
    public ResultData phoneLogin(String mobile, String passWord, String mac){
        return  this.rpcResponseFormat(tcUmsProxyService.passWordLogin(mobile, passWord));
    }

    @Override
    public ResultData passWordReset(String mobile, String passWord, String code, String mac){
        return this.rpcResponseFormat(tcUmsProxyService.passWordReset(mobile, passWord, code));
    }


    //TODO
    @Override
    public ResultData getUserInfoByUserId(int userId, String mac){
        return null;
    }

    private ResultData rpcResponseFormat(ResultData resultData){
        if (null == resultData) {
            logger.error("tc_ums服务异常");
            resultData = new ResultData();
            resultData.setError(ErrorCode.SERVER_EXCEPTION);
            resultData.setMsg("tc_ums服务器异常");
            return resultData;
        }
        if(resultData.getError() != ErrorCode.SUCCESS){
            logger.warn(resultData.getMsg());
            resultData.setError(ErrorCode.SERVER_EXCEPTION);
            resultData.setMsg(resultData.getMsg());
            return resultData;
        }
        TcUmsUserInfoResponse tcUmsUserInfoResponse = (TcUmsUserInfoResponse) resultData.getData();
        OldVersionLoginResponse response = new OldVersionLoginResponse();
        response.setUserId(tcUmsUserInfoResponse.getUserId());
        response.setUserHeader(tcUmsUserInfoResponse.getHeader());
        response.setUserName(tcUmsUserInfoResponse.getNickName());
        response.setNickName(tcUmsUserInfoResponse.getNickName());
        resultData.setData(response);
        return resultData;
    }
}
