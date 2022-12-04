package com.hqtc.bms.service.rpc;

import com.hqtc.bms.model.response.TLoginAccountBean;
import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.ResultData;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;
import java.util.Map;

import static com.hqtc.common.utils.Tools.getThreadResultData;

@Component
public class UmsServiceFallback implements FallbackFactory<UmsService> {
    @Override
    public UmsService create(Throwable throwable) {
        return new UmsService(){

            @Override
            public ResultData<Map<String, Object>> getUserInfo(int userId, String mac) {
                return errorResponse("ums getUserInfo failed");
            }

            @Override
            public ResultData<TLoginAccountBean> getVenderUserInfo(String userName, String passWord) {
                return errorResponse("ums getVenderUser failed");
            }

            private ResultData errorResponse(String msg){
                ResultData result = getThreadResultData();
                result.setMsg(msg);
                result.setError(ErrorCode.SERVER_EXCEPTION);
                return result;
            }
        };
    }
}
