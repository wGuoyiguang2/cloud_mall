package com.cibnvideo.fallback.omsapi;

import com.cibnvideo.common.utils.Result;
import com.cibnvideo.tcmalladmin.model.bean.VenderAccountVo;
import com.cibnvideo.tcmalladmin.omsapi.AccountApi;
import com.cibnvideo.tcmalladmin.model.bean.AccountInfoVo;
import com.cibnvideo.tcmalladmin.model.bean.AccountManagerVo;
import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.DataList;
import com.hqtc.common.response.ResultData;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import static com.hqtc.common.utils.Tools.getThreadResultData;


@Component
public class AccountFallbackFactory implements FallbackFactory<AccountApi> {
    private Logger logger  = LoggerFactory.getLogger(getClass());
    @Override
    public AccountApi create(Throwable throwable) {
        return new AccountApi(){

            @Override
            public Result<AccountManagerVo> accountList(Map<String, Object> params) {
                errorResponse("oms accountList failed");
                return null;
            }

            @Override
            public AccountInfoVo getAccountInfo(Long venderId) {
                errorResponse("oms getAccountInfo failed");
                return null;
            }

            @Override
            public ResultData<List<AccountManagerVo>> getOrderAccount(List<String> orderList) {
                return errorResponse("oms getOrderAccount failed");
            }

            @Override
            public ResultData settleAccount(String startTime, String endTime, Long venderId) {
                return errorResponse("oms settleAccount failed");
            }

            @Override
            public ResultData<DataList<List<VenderAccountVo>>> getVenderAccountInfo(Map<String, Object> map) {
                return errorResponse("oms getVenderAccountInfo failed");
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
