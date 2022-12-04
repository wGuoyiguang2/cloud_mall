package com.hqtc.bms.service.rpc;

import com.hqtc.bms.model.rpc.VenderSettlementAccount;
import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.ResultData;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by wanghaoyang on 18-7-5.
 */
@Component
public class RPCOmsServiceFallback implements RPCOmsService {
    @Override
    public ResultData getBatchPrice(Long venderId, List<String> skus){
        return this.getDefaultResult();
    }

    @Override
    public ResultData getVenderPayMent(Long venderId){
        return this.getDefaultResult();
    }

    @Override
    public ResultData balanceAdd(@RequestParam("venderId") Integer venderId){
        return null;
    }

    @Override
    public ResultData balanceAdd(@PathVariable("venderId") Integer venderId, @RequestBody BigDecimal value){
        return null;
    }

    @Override
    public ResultData balanceReduce(@PathVariable("venderId") Integer venderId, @RequestBody BigDecimal value){
        return null;
    }

    @Override
    public ResultData getAfterSaleReason(@RequestParam("venderId") Integer venderId){
        return getDefaultResult();
    }

    @Override
    public ResultData getVenderSettleMent(@RequestBody Long venderId){
        return null;
    }

    @Override
    public ResultData accountAdd(@RequestBody VenderSettlementAccount venderSettlementAccount){
        return null;
    }

    @Override
    public ResultData<Double> getFreeFreighPrice(@RequestParam("venderId") Integer venderId){
        return getDefaultResult();
    }

    @Override
    public ResultData<BigDecimal> sumFreeFreighPrice(Integer venderId) {
        return getDefaultResult();
    }

    private ResultData getDefaultResult(){
        ResultData resultData = new ResultData();
        resultData.setError(ErrorCode.SERVER_EXCEPTION);
        resultData.setMsg("Feign Client Request OMS Service Failed");
        return resultData;
    }
}
