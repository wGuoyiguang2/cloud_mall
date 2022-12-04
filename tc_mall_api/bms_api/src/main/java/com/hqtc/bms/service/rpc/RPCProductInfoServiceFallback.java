package com.hqtc.bms.service.rpc;

import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.ResultData;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by wanghaoyang on 18-7-4.
 */
@Component
public class RPCProductInfoServiceFallback implements RPCProductInfoService{

    @Override
    public ResultData getHomeRecommend(String sku){
        ResultData resultData = new ResultData();
        resultData.setError(ErrorCode.SERVER_EXCEPTION);
        resultData.setMsg("服务异常");
        resultData.setData(null);
        return resultData;
    }
}
