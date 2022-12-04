package com.hqtc.ums.service.impl;

import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.ResultData;
import com.hqtc.ums.service.WechatService;
import com.hqtc.ums.rpc.RPCUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.HashMap;
import java.util.TreeMap;

/**
 * Created by wanghaoyang on 18-6-30.
 */
@Service("RPCWechatServiceImpl")
public class RPCWechatServiceImpl extends CommonServiceImpl implements WechatService {

    private Logger logger = LoggerFactory.getLogger("RPCWechatServiceImpl");

    @Autowired
    private RPCUserService rpcUserService;

    public ResultData createQrCode(String mac){
        ResultData resultData = new ResultData();
        String deviceId = "1";
        String clientId = "2";
        String groupId = "3";
        String cid = "4";
        String channelNumber  = channel;
        String versionId = "v5.8.8";
        String res = rpcUserService.createQrCode(deviceId, clientId, groupId, cid, channelNumber, versionId, mac);
        if (null == res || res.isEmpty()) {
            resultData.setError(ErrorCode.PARAM_ERROR);
            resultData.setMsg("服务器异常");
            return resultData;
        }
        Map map =  gson.fromJson(res, typeToken);
        int status = Integer.parseInt(String.valueOf(map.get("status")));
        if(0 == status){
            resultData.setError(ErrorCode.SUCCESS);
            resultData.setMsg("OK");
            Map<String, Object> data = new HashMap<>();
            data.put("qrTicket", map.get("qrTicket"));
            data.put("scanId", map.get("scanid"));
            resultData.setData(data);
        }else {
            resultData.setError(ErrorCode.FALI);
            resultData.setMsg("失败");
        }
        return resultData;
    }

    @Override
    public ResultData scanQrCode(int scanId, String ticket){
        ResultData resultData = new ResultData();
        String res = rpcUserService.scanQrCode(scanId, ticket);
        if (null == res || res.isEmpty()) {
            resultData.setError(ErrorCode.SERVER_EXCEPTION);
            resultData.setMsg("服务器异常");
            return resultData;
        }
        TreeMap<String, Object> map =  gson.fromJson(res, typeToken);
        resultData = this.umsErrorCodeFormat(Integer.parseInt(String.valueOf(map.get("status"))));
        resultData.setData( "null".equals(map.get("data"))?null:gson.fromJson(map.get("data").toString(), typeToken));
        return resultData;
    }
}
