package com.cibnvideo.tcmalladmin.bmsapi;

import com.cibnvideo.common.utils.Result;
import com.cibnvideo.config.Router;
import com.cibnvideo.fallback.bmsapi.CardFallbackFactory;
import com.cibnvideo.tcmalladmin.model.bean.*;
import com.hqtc.common.config.FeignClientService;
import com.hqtc.common.response.ResultData;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/7/20 20:39
 */
@FeignClient(value = FeignClientService.BMSAPI,fallbackFactory = CardFallbackFactory.class)
public interface CardApi {
    @PostMapping(Router.V1_BMS_CARD_MANAGER_LIST)
    ResultData<Result<CardVo>> cardList(@RequestParam Map<String, Object> params);
    @PostMapping(Router.V1_BMS_CARD_BATCH_MANAGER_LIST)
    ResultData<Result<CardBatchVo>> cardBatchList(@RequestParam Map<String, Object> params);
    @PostMapping(Router.V1_BMS_CARD_BATCH_ADD_CARD)
    ResultData addCard(@RequestParam Map<String, Object> params);
    @PostMapping(Router.V1_BMS_CARD_BATCH_ADMIN_RECORD_LIST)
    ResultData<Result<CardAdminRecord>> cardBatchRecordList(@RequestParam Map<String, Object> params);
    @PostMapping(value=Router.V1_BMS_CARD_BATCH_OPERATE)
    ResultData cardBatchOperate(@RequestParam("type") Integer type, @RequestParam("batchNo") String batchNo, @RequestParam("intro") String intro, @RequestParam("userId") Integer userId);
    @PostMapping(Router.V1_BMS_CARD_BATCH_GETBY_ID)
    ResultData<CardBatchVo> getByBatchId(@RequestParam("id") Integer id);
    @PostMapping(value=Router.V1_BMS_CARD_OPERATE)
    ResultData cardOperate(@RequestParam("type") Integer type,@RequestParam("cardNos") String cardNos,@RequestParam("intro") String intro,@RequestParam("userId") Integer userId,@RequestParam(value = "bindUser",required = false) Integer bindUser);
    @PostMapping(Router.V1_BMS_CARD_BATCH_ADMIN_RECORD_DETAIL)
    ResultData<CardBatchAdminRecordDetailVo> getCardBatchAdminRecordDetail(@RequestParam("id") Integer id);
    @PostMapping(Router.V1_BMS_CARD_GET_BALANCE_BY_CARDNOS)
    ResultData<BigDecimal> getBalanceByCardNos(@RequestParam("cardNos") String cardNos);
    @PostMapping(Router.V1_BMS_CARD_BATCH_DETAIL)
    ResultData<CardBatchVo> getCardBatchDetail(@RequestParam("batchNo") String batchNo);
    @PostMapping(Router.V1_BMS_CARD_LIST)
    ResultData<List<CardVo>> listCard(@RequestParam Map<String, Object> map);
    @PostMapping(Router.V1_BMS_CARD_ADMIN_OPERATE_ADD)
    ResultData addCardAdminOperateRecord(@RequestBody CardAdminRecord cardAdminRecord);
    @PostMapping(Router.V1_BMS_CARD_BY_CARDNOS)
    ResultData<List<CardVo>> listCardByCardNos(@RequestBody List<String> cardNos);
    @PostMapping(Router.V1_BMS_CARD_USER_RECORD_LIST)
    ResultData<Result<CardUserRecordManagerVo>> listCardUserRecord(@RequestParam Map<String, Object> map);
    @PostMapping(Router.V1_BMS_CARD_DETAIL)
    ResultData<CardVo> getCardDetail(@RequestParam("cardNo") String cardNo);
    @PostMapping(Router.V1_BMS_CARD_DETAIL_LIST)
    ResultData<Result<CardDetailVo>> cardDetailList(@RequestParam Map<String,Object> map);
}
