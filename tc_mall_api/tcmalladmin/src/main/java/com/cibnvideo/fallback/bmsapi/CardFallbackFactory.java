package com.cibnvideo.fallback.bmsapi;

import com.cibnvideo.common.utils.Result;
import com.cibnvideo.tcmalladmin.bmsapi.CardApi;
import com.cibnvideo.tcmalladmin.model.bean.*;
import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.ResultData;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static com.hqtc.common.utils.Tools.getThreadResultData;


@Component
public class CardFallbackFactory implements FallbackFactory<CardApi> {
    private Logger logger  = LoggerFactory.getLogger(getClass());
    @Override
    public CardApi create(Throwable throwable) {
        return new CardApi(){
            @Override
            public ResultData<Result<CardVo>> cardList(Map<String, Object> params) {
                return errorResponse("bms cardList failed");
            }

            @Override
            public ResultData<Result<CardBatchVo>> cardBatchList(Map<String, Object> params) {
                return errorResponse("bms cardBatchList failed");
            }

            @Override
            public ResultData addCard(Map<String, Object> params) {
                return errorResponse("bms addCard failed");
            }

            @Override
            public ResultData<Result<CardAdminRecord>> cardBatchRecordList(Map<String, Object> params) {
                return errorResponse("bms cardBatchRecordList failed");
            }

            @Override
            public ResultData cardBatchOperate(Integer type, String batchNo, String intro, Integer userId) {
                return errorResponse("bms cardBatchOperate failed");
            }

            @Override
            public ResultData<CardBatchVo> getByBatchId(Integer id) {
                return errorResponse("bms getByBatchId failed");
            }

            @Override
            public ResultData cardOperate(Integer type, String cardNos, String intro, Integer userId, Integer bindUser) {
                return errorResponse("bms cardOperate failed");
            }

            @Override
            public ResultData<CardBatchAdminRecordDetailVo> getCardBatchAdminRecordDetail(Integer id) {
                return errorResponse("bms getCardBatchAdminRecordDetail failed");
            }

            @Override
            public ResultData<BigDecimal> getBalanceByCardNos(String cardNos) {
                return errorResponse("bms getBalanceByCardNos failed");
            }

            @Override
            public ResultData<CardBatchVo> getCardBatchDetail(String batchNo) {
                return errorResponse("bms getCardBatchDetail failed");
            }

            @Override
            public ResultData<List<CardVo>> listCard(Map<String, Object> map) {
                return errorResponse("bms listCard failed");
            }

            @Override
            public ResultData addCardAdminOperateRecord(CardAdminRecord cardAdminRecord) {
                return errorResponse("bms addCardAdminOperateRecord failed");
            }

            @Override
            public ResultData<List<CardVo>> listCardByCardNos(List<String> cardNos) {
                return errorResponse("bms listCardByCardNos failed");
            }

            @Override
            public ResultData<Result<CardUserRecordManagerVo>> listCardUserRecord(Map<String, Object> map) {
                return errorResponse("bms listCardUserRecord failed");
            }

            @Override
            public ResultData<CardVo> getCardDetail(String cardNo) {
                return errorResponse("bms getCardDetail failed");
            }

            @Override
            public ResultData<Result<CardDetailVo>> cardDetailList(Map<String,Object> map) {
                return errorResponse("bms cardDetailList failed");
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
