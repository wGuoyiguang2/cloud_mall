package com.cibnvideo.tcmalladmin.omsapi;

import com.cibnvideo.common.entity.VenderSettlement;
import com.cibnvideo.common.utils.Result;
import com.cibnvideo.config.Router;
import com.cibnvideo.fallback.omsapi.VenderSettlementFallbackFactory;
import com.cibnvideo.tcmalladmin.model.bean.SellPriceResult;
import com.cibnvideo.tcmalladmin.model.bean.VenderInvoiceManagerVo;
import com.cibnvideo.tcmalladmin.model.bean.VenderOrderManagerVo;
import com.hqtc.common.config.FeignClientService;
import com.hqtc.common.response.ResultData;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@FeignClient(value = FeignClientService.OMSAPI, fallbackFactory = VenderSettlementFallbackFactory.class)
public interface VenderSettlementApi {

    @RequestMapping(value = Router.V1_OMS_VENDER_SETTLEMENT_ADD, method = RequestMethod.POST)
    ResultData<Integer> addVenderSettlement(VenderSettlement venderSettlement);

    @RequestMapping(value = Router.V1_OMS_VENDER_SETTLEMENT_GET, method = RequestMethod.POST)
    ResultData<VenderSettlement> getVenderSettlement(Long venderId);

    @RequestMapping(value = Router.V1_OMS_VENDER_SETTLEMENT_UPDATE, method = RequestMethod.POST)
    ResultData<Integer> updateVenderSettlement(VenderSettlement venderSettlement);

    @RequestMapping(value = Router.V1_OMS_VENDER_SETTLEMENT_REMOVE, method = RequestMethod.POST)
    ResultData<Integer> removeVenderSettlement(Long venderId);

    @RequestMapping(value = Router.V1_OMS_PRODUCT_BATCH_PRICE, method = RequestMethod.POST)
    ResultData<List<SellPriceResult>> getBatchPrice(@PathVariable("venderId") Integer venderId, List<Long> skus);

    @RequestMapping(value = Router.V1_OMS_PRODUCT_PRICE, method = RequestMethod.POST)
    ResultData<SellPriceResult> getPrice(@PathVariable("venderId") Integer venderId, Long sku);

    @RequestMapping(value = Router.V1_OMS_VENDER_SETTLEMENT_BALANCE_GET, method = RequestMethod.GET)
    ResultData<BigDecimal> balanceGet(@RequestParam("venderId") Integer venderId);

    @RequestMapping(value = Router.V1_OMS_VENDER_SETTLEMENT_BALANCE_ADD, method = RequestMethod.POST)
    ResultData<Integer> balanceAdd(@PathVariable("venderId") Integer venderId, @RequestBody BigDecimal value);

    @RequestMapping(value = Router.V1_OMS_VENDER_SETTLEMENT_BALANCE_REDUCE, method = RequestMethod.POST)
    ResultData<Integer> balanceReduce(@PathVariable("venderId") Integer venderId, @RequestBody BigDecimal value);

    @PostMapping(Router.V1_OMS_VENDER_ORDER_MANAGER_LIST)
    Result<VenderOrderManagerVo> listVenderOrderManager(@RequestParam Map<String, Object> params);

    @PostMapping(Router.V1_OMS_VENDER_SETTLEMENT_GET_BY_PARAMS)
    Result<VenderOrderManagerVo> listVenderSettlementByParams(@RequestParam Map<String, Object> params);

    @PostMapping(Router.V1_OMS_VENDER_INVOICE_GET_BY_PARAMS)
    Result<VenderInvoiceManagerVo> listVenderInvoiceManager(@RequestParam Map<String, Object> params);
}
