package com.hqtc.bms.service.rpc;

import com.hqtc.bms.config.RPCRouter;
import com.hqtc.bms.model.rpc.*;
import com.hqtc.common.config.FeignClientService;
import com.hqtc.common.response.ResultData;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by wanghaoyang on 18-7-5.
 */
@FeignClient(name = FeignClientService.OMSAPI, fallback = RPCOmsServiceFallback.class)
public interface RPCOmsService {

    //获取商品的商户价格
    @RequestMapping(value = RPCRouter.ROUTER_OMS_PRODUCT_BATCH_PRICE, method = RequestMethod.POST)
    ResultData<List<ProductVenderPriceBean>> getBatchPrice(@PathVariable("venderId") Long venderId, @RequestBody List<String> skus);

    //获取厂商的支付凭证
    @RequestMapping(value = RPCRouter.V1_OMS_VENDER_PAYMENT_GET, method = RequestMethod.GET)
    ResultData<List<VenderPayment>> getVenderPayMent(@RequestBody Long venderId);

    //获取厂商余额
    @GetMapping(RPCRouter.V1_OMS_VENDER_SETTLEMENT_BALANCE_GET)
    ResultData<BigDecimal> balanceAdd(@RequestParam("venderId") Integer venderId);

    //增加余额
    @PostMapping(RPCRouter.V1_OMS_VENDER_SETTLEMENT_BALANCE_ADD)
    ResultData balanceAdd(@PathVariable("venderId") Integer venderId, @RequestBody BigDecimal value);

    //减少余额
    @PostMapping(RPCRouter.V1_OMS_VENDER_SETTLEMENT_BALANCE_REDUCE)
    ResultData balanceReduce(@PathVariable("venderId") Integer venderId, @RequestBody BigDecimal value);

    //获取商家支付方式
    @PostMapping(RPCRouter.V1_OMS_VENDER_SETTLEMENT_GET)
    ResultData<VenderSettlement> getVenderSettleMent(@RequestBody Long venderId);

    @GetMapping(RPCRouter.V1_OMS_AFTERSALE_CONFIG)
    ResultData<List<AfterSaleReasonBean>> getAfterSaleReason(@RequestParam("venderId") Integer venderId);

    @PostMapping(RPCRouter.V1_OMS_VENDER_SETTLEMENT_ACCOUNT)
    ResultData accountAdd(@RequestBody VenderSettlementAccount venderSettlementAccount);

    //获取商家包邮价格
    @PostMapping(RPCRouter.V1_OMS_FREEFREIGHT_GETBY_VENDERID)
    ResultData<Double> getFreeFreighPrice(@RequestParam("venderId") Integer venderId);
    //获取商家实际支付给京东包邮价格的总和
    @PostMapping(RPCRouter.V1_OMS_FREEFREIGHT_SUMBY_VENDERID)
    ResultData<BigDecimal> sumFreeFreighPrice(@RequestParam("venderId") Integer venderId);
}
