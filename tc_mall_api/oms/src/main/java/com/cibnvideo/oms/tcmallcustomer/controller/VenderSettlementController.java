package com.cibnvideo.oms.tcmallcustomer.controller;

import com.cibnvideo.oms.bean.Result;
import com.cibnvideo.oms.bean.SellPriceResult;
import com.cibnvideo.oms.config.Router;
import com.cibnvideo.oms.tcmallcustomer.bean.VenderInvoiceManagerVo;
import com.cibnvideo.oms.tcmallcustomer.bean.VenderOrderManagerVo;
import com.cibnvideo.oms.tcmallcustomer.bean.VenderSettlement;
import com.cibnvideo.oms.tcmallcustomer.bean.VenderSettlementAccount;
import com.cibnvideo.oms.tcmallcustomer.service.PricePolicyService;
import com.cibnvideo.oms.tcmallcustomer.service.SendMessageService;
import com.cibnvideo.oms.tcmallcustomer.service.VenderAccountSettlementService;
import com.cibnvideo.oms.tcmallcustomer.service.VenderSettlementService;
import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.ResultData;
import com.hqtc.common.utils.Tools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class VenderSettlementController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    VenderSettlementService venderSettlementService;

    @Autowired
    VenderAccountSettlementService venderAccountSettlementService;

    @Autowired
    PricePolicyService pricePolicyService;

    @Autowired
    SendMessageService sendMessageService;

    @PostMapping(Router.V1_OMS_VENDER_SETTLEMENT_ADD)
    public ResultData addVenderSettleMent(@RequestBody VenderSettlement venderSettlement) {
        VenderSettlement v = venderSettlementService.get(venderSettlement.getVenderId());
        int result = 0;
        if (v != null) {
            result = venderSettlementService.update(venderSettlement);
        } else {
            result = venderSettlementService.save(venderSettlement);
        }
        if (result > 0) {
            sendMessageService.venderCreate(venderSettlement.getVenderId().intValue());
        }
        ResultData resultData = Tools.getThreadResultData();
        resultData.setData(result);
        return resultData;
    }

    @PostMapping(Router.V1_OMS_VENDER_SETTLEMENT_REMOVE)
    public ResultData removeVenderSettleMent(@RequestBody Long venderId) {
        ResultData resultData = Tools.getThreadResultData();
        int result = 0;
        result = venderSettlementService.remove(venderId);
        resultData.setData(result);
        if (result > 0) {
            sendMessageService.venderRemove(venderId.intValue());
        }
        return resultData;
    }

    @PostMapping(Router.V1_OMS_VENDER_SETTLEMENT_GET)
    public ResultData getVenderSettleMent(@RequestBody Long venderId) {
        ResultData resultData = Tools.getThreadResultData();
        resultData.setData(venderSettlementService.get(venderId));
        return resultData;
    }

    @PostMapping(Router.V1_OMS_VENDER_SETTLEMENT_UPDATE)
    public ResultData updateVenderSettleMent(@RequestBody VenderSettlement venderSettlement) {
        ResultData resultData = Tools.getThreadResultData();
        int result = venderSettlementService.update(venderSettlement);
        resultData.setData(result);
        if (result > 0) {
            sendMessageService.venderSettlementModify(venderSettlement.getVenderId().intValue());
        }
        return resultData;
    }

    @GetMapping(Router.V1_OMS_VENDER_SETTLEMENT_BALANCE_GET)
    public ResultData balanceAdd(@RequestParam("venderId") Integer venderId) {
        ResultData<BigDecimal> resultData = Tools.getThreadResultData();
        resultData.setData(venderSettlementService.balanceGet(venderId));
        return resultData;
    }

    @Transactional
    @PostMapping(Router.V1_OMS_VENDER_SETTLEMENT_BALANCE_ADD)
    public ResultData balanceAdd(@PathVariable("venderId") Integer venderId, @RequestBody BigDecimal value) {
        ResultData<Integer> resultData = Tools.getThreadResultData();
        resultData.setData(venderSettlementService.balanceAdd(venderId, value));
        return resultData;
    }

    @Transactional
    @PostMapping(Router.V1_OMS_VENDER_SETTLEMENT_BALANCE_REDUCE)
    public ResultData balanceReduce(@PathVariable("venderId") Integer venderId, @RequestBody BigDecimal value) {
        ResultData<Integer> resultData = Tools.getThreadResultData();
        int result = venderSettlementService.balanceReduce(venderId, value);
        if (result > 0) {
            BigDecimal r = venderSettlementService.balanceGet(venderId);
            if (r.compareTo(BigDecimal.ZERO) == -1) {
                throw new RuntimeException("扣款失败，预付款余额不足");
            }
        }
        resultData.setData(result);
        return resultData;
    }

    @Transactional
    @PostMapping(Router.V1_OMS_VENDER_SETTLEMENT_ACCOUNT)
    public ResultData accountAdd(@RequestBody VenderSettlementAccount venderSettlementAccount) {
        ResultData<Integer> resultData = Tools.getThreadResultData();
        if (venderSettlementAccount.getAgreePrice() == null || venderSettlementAccount.getFreight() == null
                || venderSettlementAccount.getOrderSn() == null || venderSettlementAccount.getPayPrice() == null
                || venderSettlementAccount.getPrice() == null || venderSettlementAccount.getVenderId() == null
                || venderSettlementAccount.getTradeNo() == null || venderSettlementAccount.getPayType() == null
                || venderSettlementAccount.getType() == null || venderSettlementAccount.getCardPrice() == null) {
            resultData.setError(ErrorCode.PARAM_ERROR);
            resultData.setMsg("参数有误");
            return resultData;
        }
        Integer venderId = venderSettlementAccount.getVenderId();
        Integer type = venderSettlementAccount.getType();
        VenderSettlement venderSettlement = venderSettlementService.get(venderId.longValue());
        if (venderSettlement != null) {
            Integer settlementType = venderSettlement.getSettlementType();
            if (settlementType != null) {
                venderSettlementAccount.setCtime(new Date());
                venderSettlementAccount.setUtime(new Date());
                //预付款
                if (settlementType == 2) {
                    BigDecimal agreePrice = venderSettlementAccount.getAgreePrice();
                    BigDecimal cardPrice = venderSettlementAccount.getCardPrice();
                    BigDecimal freight = venderSettlementAccount.getFreight();
                    //扣款
                    if (type == 0) {
                        BigDecimal price = agreePrice.add(freight).subtract(cardPrice);
                        int result = venderSettlementService.balanceReduce(venderId, price);
                        if (result > 0) {
                            BigDecimal r = venderSettlementService.balanceGet(venderId);
                            if (r.compareTo(BigDecimal.ZERO) == -1) {
                                throw new RuntimeException("扣款失败，预付款余额不足");
                            }
                            venderSettlementAccount.setIsSettle(1);
                            int resultCode = venderAccountSettlementService.save(venderSettlementAccount);
                            if (resultCode == 0) {
                                throw new RuntimeException("扣款账单保存失败");
                            }
                            resultData.setData(resultCode);
                            return resultData;
                        }
                        resultData.setData(result);
                        return resultData;
                        //退款
                    } else if (type == 1) {
                        BigDecimal price = agreePrice.subtract(cardPrice);
                        int result = venderSettlementService.balanceAdd(venderId, price);
                        if (result > 0) {
                            venderSettlementAccount.setIsSettle(1);
                            int resultCode = venderAccountSettlementService.save(venderSettlementAccount);
                            if (resultCode == 0) {
                                throw new RuntimeException("退款账单保存失败");
                            }
                            resultData.setData(resultCode);
                            return resultData;
                        } else {
                            throw new RuntimeException("退款失败");
                        }
                    } else {
                        throw new RuntimeException("账单类型未设置");
                    }
                    //按月结算
                } else if (settlementType == 1) {
                    venderSettlementAccount.setIsSettle(0);
                    int resultCode = venderAccountSettlementService.save(venderSettlementAccount);
                    if (resultCode == 0) {
                        throw new RuntimeException("按月结算扣款账单保存失败");
                    }
                    resultData.setData(resultCode);
                    return resultData;
                    //实时结算
                } else if (settlementType == 0) {
                    venderSettlementAccount.setIsSettle(0);
                    int resultCode = venderAccountSettlementService.save(venderSettlementAccount);
                    if (resultCode == 0) {
                        throw new RuntimeException("实时结算扣款账单保存失败");
                    }
                    resultData.setData(resultCode);
                    return resultData;
                } else {
                    resultData.setError(ErrorCode.PARAM_ERROR);
                    resultData.setMsg("厂商结算方式未知");
                    return resultData;
                }
            } else {
                resultData.setError(ErrorCode.PARAM_ERROR);
                resultData.setMsg("厂商结算方式未设置");
                return resultData;
            }
        } else {
            resultData.setError(ErrorCode.PARAM_ERROR);
            resultData.setMsg("厂商ID不存在");
            return resultData;
        }
    }

    @PostMapping(Router.V1_OMS_PRODUCT_BATCH_PRICE)
    public ResultData getBatchPrice(@PathVariable("venderId") Long venderId, @RequestBody List<Long> skus) {
        logger.info("getBatchPrice venderId = " + venderId);
        VenderSettlement venderSettlement = venderSettlementService.get(venderId);
        if (venderSettlement == null) {
            logger.error("未查询到对应厂商价格系数");
            ResultData<List<SellPriceResult>> resultData = Tools.getThreadResultData();
            resultData.setError(ErrorCode.NO_RESPONSE);
            resultData.setMsg("未查询到对应厂商价格系数");
            return resultData;
        } else {
            BigDecimal venderPricePercent = venderSettlement.getPricePercent();
            ResultData<List<SellPriceResult>> resultDataSellPrice = Tools.getThreadResultData();
            if (skus.size() == 0) {
                logger.error("未检测到sku列表");
                resultDataSellPrice.setError(ErrorCode.PARAM_ERROR);
                resultDataSellPrice.setMsg("未检测到sku列表");
                return resultDataSellPrice;
            }
            List<SellPriceResult> sellPriceResultList = skus.stream().map(
                    sku -> pricePolicyService.getSellPriceBySku(venderPricePercent, venderId.intValue(), sku)
            ).collect(Collectors.toList());
            logger.info("getBatchPrice list size = " + sellPriceResultList.size());
            resultDataSellPrice.setData(sellPriceResultList);
            return resultDataSellPrice;
        }

    }

    @PostMapping(Router.V1_OMS_PRODUCT_PRICE)
    public ResultData getPrice(@PathVariable("venderId") Long venderId, @RequestBody Long sku) {
        logger.info("getPrice venderId = " + venderId + " sku = " + sku);
        VenderSettlement venderSettlement = venderSettlementService.get(venderId);
        if (venderSettlement == null) {
            ResultData<SellPriceResult> resultData = Tools.getThreadResultData();
            resultData.setError(ErrorCode.NO_RESPONSE);
            resultData.setMsg("未查询到对应厂商价格系数");
            return resultData;
        } else {
            ResultData<SellPriceResult> resultDataSellPrice = Tools.getThreadResultData();
            BigDecimal venderPricePercent = venderSettlement.getPricePercent();
            SellPriceResult sellPriceResult = pricePolicyService.getSellPriceBySku(venderPricePercent, venderId.intValue(), sku);
            resultDataSellPrice.setData(sellPriceResult);
            return resultDataSellPrice;
        }

    }

    @PostMapping(Router.V1_OMS_VENDER_SETTLEMENT_GET_BY_PARAMS)
    public Result<VenderOrderManagerVo> getVenderSettleMentByParams(@RequestParam Map<String, Object> params) {
        Result<VenderOrderManagerVo> result = new Result<>();
        List<VenderOrderManagerVo> list = venderSettlementService.listVenderOrderManagerVo(params);
        int total = venderSettlementService.countVenderOrderManagerVo(params);
        result.setTotal(total);
        result.setRows(list);
        return result;
    }

    @PostMapping(Router.V1_OMS_VENDER_INVOICE_GET_BY_PARAMS)
    public Result<VenderInvoiceManagerVo> listVenderInvoiceManager(@RequestParam Map<String, Object> params) {
        Result<VenderInvoiceManagerVo> result = new Result<>();
        List<VenderInvoiceManagerVo> list = venderSettlementService.listVenderInvoiceManager(params);
        int total = venderSettlementService.countVenderInvoiceManager(params);
        result.setTotal(total);
        result.setRows(list);
        return result;
    }

    @GetMapping(Router.V1_OMS_LIST_VENDERID)
    public ResultData listVenderId() {
        ResultData<List<Integer>> resultData = Tools.getThreadResultData();
        resultData.setData(venderSettlementService.listVenderId());
        return resultData;
    }
}
