package com.hqtc.bms.service.impl;

import com.google.gson.Gson;
import com.hqtc.bms.model.database.*;
import com.hqtc.bms.model.mapper.TOrderProductMapper;
import com.hqtc.bms.model.mapper.TOrderRefundMapper;
import com.hqtc.bms.model.mapper.TOrderVenderRefundMapper;
import com.hqtc.bms.model.params.*;
import com.hqtc.bms.model.response.PmsResultData;
import com.hqtc.bms.model.response.Result;
import com.hqtc.bms.model.rpc.AlipayConfig;
import com.hqtc.bms.model.rpc.VenderPayment;
import com.hqtc.bms.model.rpc.WXPayConfig;
import com.hqtc.bms.service.*;
import com.hqtc.bms.service.rpc.RPCPmsService;
import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.ResultData;
import com.hqtc.common.utils.Tools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by wanghaoyang on 18-7-27.
 */
@Service("RefundServiceImpl")
public class RefundServiceImpl implements RefundService{

    private Logger logger = LoggerFactory.getLogger("RefundServiceImpl");

    @Autowired
    private OrderService orderService;

    @Autowired
    private TOrderRefundMapper orderRefundMapper;

    @Autowired
    private RPCPmsService rpcPmsService;

    @Autowired
    private QrCodeService qrCodeService;

    @Value("${pay.refundNotifyUrl}")
    private String refundNotifyUrl;

    @Value("${pay.refundCertPath}")
    private String refundCertPath;

    @Autowired
    private TOrderProductMapper tOrderProductMapper;

    @Autowired
    private MessageService messageService;

    @Autowired
    private TOrderVenderRefundMapper tOrderVenderRefundMapper;

    @Autowired
    private CardService cardService;

    @Autowired
    private ProxyService proxyService;

    @Override
    public ResultData userRefund(String orderSn, Map<String, Integer> products, String refundReason, String eventId){
        ResultData resultData = new ResultData();
        TOrderBean orderBean = orderService.getOrderInfo(orderSn);
        if(null == orderBean){
            resultData.setError(ErrorCode.NO_ORDER);
            resultData.setMsg("???????????????");
            return resultData;
        }
        if(0 == orderBean.getPay_status()){
            resultData.setError(ErrorCode.UN_PAYED);
            resultData.setMsg("???????????????");
            return resultData;
        }
        List<TOrderRefundBean> refundBeans = this.multipleFormatRefundBeans(orderSn, products, eventId);
        if(null == refundBeans || refundBeans.isEmpty()){
            resultData.setError(ErrorCode.PARAM_ERROR);
            resultData.setMsg("??????????????????????????????");
            return resultData;
        }

        if(this.addUserRefund(refundBeans)<1){
            resultData.setError(ErrorCode.SERVER_EXCEPTION);
            resultData.setMsg("?????????????????????????????????");
            return resultData;
        }
        BigDecimal refundPriceB = new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP);
        float cardRefundPrice = 0;
        for(TOrderRefundBean refundBean1: refundBeans){
            refundPriceB = refundPriceB.add(new BigDecimal(refundBean1.getRefund_price()));
            cardRefundPrice += refundBean1.getCard_refund_price()*100;
        }
        float refundPrice = refundPriceB.setScale(2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).floatValue();
        if(cardRefundPrice <= 0 && refundPrice <=  0){
            resultData.setError(ErrorCode.REFUND_AMOUNT_ERROR);
            resultData.setMsg("??????????????????");
            return resultData;
        }
        if(cardRefundPrice > 0 && refundPrice<0.009){//?????????
            proxyService.cardRefundNotify(refundBeans.get(0).getRefund_no(), this.createCardRefundTradeNo());
//            this.userRefundNotify(refundBeans.get(0).getRefund_no(), this.createCardRefundTradeNo());
            return  resultData;
        }else {
            VenderPayment venderPayment = qrCodeService.getVenderPayment(orderBean.getVenderid(), orderBean.getPay_type());
            if(null == venderPayment){
                resultData.setError(ErrorCode.SERVER_EXCEPTION);
                resultData.setMsg("Oms???????????????,?????????????????????????????????");
                return resultData;
            }
            RefundParams refundParams = new RefundParams();
            refundParams.setNotifyUrl(refundNotifyUrl);
            refundParams.setOutTradeNo(orderBean.getPay_order_sn());
            refundParams.setTotalFee(orderBean.getPay_price().multiply(new BigDecimal(100)).intValue());
            refundParams.setRefundFee((int) refundPrice);
            refundParams.setRefundReason(refundReason);
            refundParams.setPayType(orderBean.getPay_type());
            refundParams.setGoodInfos(null);
            refundParams.setOutRefundNo(refundBeans.get(0).getRefund_no());
            return this.userRefund(refundParams, venderPayment);
        }
    }

    @Override
    public ResultData payFailUserRefund(TOrderBean orderBean){
        logger.warn("????????????????????????orderSn:"+orderBean);
        ResultData resultData = new ResultData();
        VenderPayment venderPayment = qrCodeService.getVenderPayment(orderBean.getVenderid(), orderBean.getPay_type());
        if(null == venderPayment){
            resultData.setError(ErrorCode.SERVER_EXCEPTION);
            resultData.setMsg("Oms???????????????,?????????????????????????????????");
            return resultData;
        }
        RefundParams refundParams = new RefundParams();
        refundParams.setNotifyUrl(refundNotifyUrl);
        refundParams.setOutTradeNo(orderBean.getPay_order_sn());
        refundParams.setTotalFee(orderBean.getPay_price().multiply(new BigDecimal(100)).intValue());
        refundParams.setRefundFee(orderBean.getPay_price().multiply(new BigDecimal(100)).intValue());
        refundParams.setRefundReason("??????????????????");
        refundParams.setPayType(orderBean.getPay_type());
        refundParams.setGoodInfos(null);
        refundParams.setOutRefundNo("PF"+orderBean.getOrder_sn());
        return this.userRefund(refundParams, venderPayment);
    }

    @Override
    public List<TOrderRefundBean> findRefundByOrderSnAndRefundNo(String orderSn, String refundNo){
        return orderRefundMapper.getOrderRefundByOrderSnAndRefundNo(orderSn, refundNo);
    }

    @Override
    public int addUserRefund(TOrderRefundBean orderRefundBean){
        return orderRefundMapper.addRefundOrder(orderRefundBean);
    }

    @Override
    public int addUserRefund(List<TOrderRefundBean> orderRefundBeans){
        return orderRefundMapper.batchAddRefundOrder(orderRefundBeans);
    }

    @Override
    public int addVenderRefund(TOrderVenderRefundBean tOrderVenderRefundBean){
        return tOrderVenderRefundMapper.addVenderRefund(tOrderVenderRefundBean);
    }

    @Override
    public TOrderVenderRefundBean getVenderRefund(String orderSn, String eventId){
        return tOrderVenderRefundMapper.getVenderRefund(orderSn, eventId);
    }

    @Override
    public String userRefundNotify(String refundNo, String refundTradeNo){
        List<TOrderRefundBean> tOrderRefundBeans1 = orderRefundMapper.getOrderRefundByRefundNo(refundNo);
        if(null == tOrderRefundBeans1 || tOrderRefundBeans1.isEmpty()){
            logger.warn("??????????????????,????????????????????????:"+refundNo+ " --"+refundTradeNo);
            return "FAIL";
        }
        ResultData resultData = this.cardRefund(tOrderRefundBeans1.get(0).getOrder_sn(), refundNo);
        if(null == refundNo || resultData.getError()!=ErrorCode.SUCCESS){
            logger.warn("?????????????????????:"+refundNo+ " --"+refundTradeNo);
            logger.warn(String.valueOf(resultData.getMsg()));
            return "FAIL";
        }
        if(orderRefundMapper.updateRefundState(refundTradeNo, refundNo)>0){
            List<TOrderRefundBean> tOrderRefundBeans = orderRefundMapper.getRefundInfo(refundTradeNo, refundNo);
            this.successRefundHandler(tOrderRefundBeans);
            return "SUCCESS";
        }
        logger.warn("????????????????????????:"+refundNo+ " --"+refundTradeNo);
        return "FAIL";
    }

    @Override
    public List<TOrderRefundBean> getSuccessRefundByOrderSnAndProduct(String orderSn, long product){
        return orderRefundMapper.findreFundByOrderSnAndProductId(orderSn, product);
    }

    @Override
    public List<TOrderRefundBean> getEventRefundBean(String orderSn, String eventId){
        return orderRefundMapper.getEventRefundInfo(orderSn, eventId);
    }

    @Override
    public List<Map<String, Object>> getRefundProductVolumn(String productId, int venderId){
        return orderRefundMapper.getRefundProductSalesVolumn(productId, venderId);
    }

    @Override
    public int getRefundedProductCount(String orderSn, long product){
        Integer s = orderRefundMapper.getRefundedProductSize(orderSn, product);
        if(null == s){
            return 0;
        }else {
            return s;
        }
    }

    @Override
    public ResultData venderRefund(String orderSn, Map<String, Integer> products, String refundReason, String eventId){
        ResultData resultData = this.getVenderRefundPrice(orderSn, products);
        if(0 != resultData.getError()){//????????????
            return resultData;
        }
        List<TOrderRefundBean> refundBeans = this.multipleFormatRefundBeans(orderSn, products, eventId);
        if(null == refundBeans || refundBeans.isEmpty()){
            resultData.setError(ErrorCode.PARAM_ERROR);
            resultData.setMsg("??????????????????????????????");
            return resultData;
        }
        float refundPrice = 0;
        float cardRefundPrice = 0;
        for(TOrderRefundBean refundBean1: refundBeans){
            refundPrice += refundBean1.getRefund_price()*100;
            cardRefundPrice += refundBean1.getCard_refund_price()*100;
        }
        if(cardRefundPrice <= 0 && refundPrice <=  0){
            resultData.setError(ErrorCode.REFUND_AMOUNT_ERROR);
            resultData.setMsg("??????????????????");
            return resultData;
        }
        TOrderBean orderBean = orderService.getOrderInfo(orderSn);
        BigDecimal venderRefundPrice = (BigDecimal)resultData.getData();
        TOrderVenderRefundBean tOrderVenderRefundBean = new TOrderVenderRefundBean();
        tOrderVenderRefundBean.setVenderid(orderBean.getVenderid());
        tOrderVenderRefundBean.setOrder_sn(orderSn);
        tOrderVenderRefundBean.setRefund_price(venderRefundPrice);
        tOrderVenderRefundBean.setUnq_id(eventId);
        tOrderVenderRefundBean.setCtime(new Date());
        if(!orderService.refundToVender(orderSn, venderRefundPrice,
                BigDecimal.valueOf(refundPrice/100).setScale(2, BigDecimal.ROUND_HALF_UP), BigDecimal.valueOf(cardRefundPrice/100).setScale(2, BigDecimal.ROUND_HALF_UP))){//????????????,????????????
            resultData.setError(ErrorCode.SERVER_EXCEPTION);
            tOrderVenderRefundBean.setRefund_status(0);
            resultData.setMsg("????????????????????????");
        }else {
            tOrderVenderRefundBean.setRefund_status(1);
            resultData.setError(ErrorCode.SUCCESS);
            resultData.setMsg("OK");
        }
        if(tOrderVenderRefundMapper.addVenderRefund(tOrderVenderRefundBean)<1){
            logger.error("????????????????????????:"+tOrderVenderRefundBean.toString());
        }
        return resultData;
    }

    @Override
    public ResultData denyReceiveRefund(String jdOrderId, String jdMessageId){
        ResultData resultData = new ResultData();
        List<TOrderProductBean> tOrderProductBeans = tOrderProductMapper.getOrderProductByJdOrderId(jdOrderId);
        if(null == tOrderProductBeans || tOrderProductBeans.isEmpty()){
            resultData.setError(ErrorCode.NO_ORDER);
            resultData.setMsg("????????????????????????????????????");
            return resultData;
        }
        String orderSn = tOrderProductBeans.get(0).getOrder_sn();
        String refundReason = "????????????";
        Map<String, Integer> products = new HashMap<>(tOrderProductBeans.size());
        for(TOrderProductBean tOrderProductBean: tOrderProductBeans){
            if(products.containsKey(String.valueOf(tOrderProductBean.getProduct_id()))){
                products.put(String.valueOf(tOrderProductBean.getProduct_id()),
                        products.get(String.valueOf(tOrderProductBean.getProduct_id()))+ tOrderProductBean.getCount());
            }else {
                products.put(String.valueOf(tOrderProductBean.getProduct_id()), tOrderProductBean.getCount());
            }
        }
        //???????????????????????????????????????????????????
        MultipleRefundParams multipleRefundParams = new MultipleRefundParams();
        multipleRefundParams.setEventId(jdMessageId);
        multipleRefundParams.setOrderSn(orderSn);
        multipleRefundParams.setProducts(products);
        multipleRefundParams.setRefundReason(refundReason);
        return this.multipleRefund(multipleRefundParams);
    }

    @Override
    public ResultData refund(String jdOrderId){
        ResultData resultData = new ResultData();
        List<TOrderProductBean> tOrderProductBeans = tOrderProductMapper.getOrderProductByJdOrderId(jdOrderId);
        if(null == tOrderProductBeans || tOrderProductBeans.isEmpty()){
            resultData.setError(ErrorCode.NO_ORDER);
            resultData.setMsg("????????????????????????????????????");
            return resultData;
        }
        String orderSn = tOrderProductBeans.get(0).getOrder_sn();
        String refundReason = "????????????";
        Map<String, Integer> products = new HashMap<>(tOrderProductBeans.size());
        for(TOrderProductBean tOrderProductBean: tOrderProductBeans){
            if(products.containsKey(tOrderProductBean.getProduct_id())){
                products.put(String.valueOf(tOrderProductBean.getProduct_id()),
                        products.get(tOrderProductBean.getProduct_id())+ tOrderProductBean.getCount());
            }else {
                products.put(String.valueOf(tOrderProductBean.getProduct_id()), tOrderProductBean.getCount());
            }
        }
        //???????????????????????????????????????????????????
        MultipleRefundParams multipleRefundParams = new MultipleRefundParams();
        multipleRefundParams.setEventId(this.getDeliveryEventId(jdOrderId));
        multipleRefundParams.setOrderSn(orderSn);
        multipleRefundParams.setProducts(products);
        multipleRefundParams.setRefundReason(refundReason);
        return this.multipleRefund(multipleRefundParams);
    }

    @Override
    public ResultData successRefundHandler(List<TOrderRefundBean> tOrderRefundBeans){
        ResultData resultData = new ResultData();
        if(null == tOrderRefundBeans || tOrderRefundBeans.isEmpty()){
            resultData.setError(ErrorCode.PARAM_ERROR);
            resultData.setMsg("????????????!");
        }else {
            for (TOrderRefundBean tOrderRefundBean: tOrderRefundBeans) {
                long productId = tOrderRefundBean.getProduct_id();
                String orderSn = tOrderRefundBean.getOrder_sn();
                List<TOrderProductBean> tOrderProductBeans = orderService.getOrderProduct(orderSn, productId);
                if (null == tOrderProductBeans || tOrderProductBeans.isEmpty()) {
                    resultData.setError(ErrorCode.NO_ORDER);
                    resultData.setMsg("?????????????????????????????????");
                } else {
                    String jdOrderSn = tOrderProductBeans.get(0).getChild_trade_no();
                    messageService.successRefundFinally(jdOrderSn);
                }
            }
        }
        return resultData;
    }


//    @Override
//    public ResultData multipleRefund(MultipleRefundParams multipleRefundParams){
//        ResultData resultData = new ResultData();
//        //???????????????????????????????????????
//        TOrderVenderRefundBean venderRefundBean = this.getVenderRefund(multipleRefundParams.getOrderSn()
//                , multipleRefundParams.getEventId());
//        if(null != venderRefundBean){
//            int venderRefundState = venderRefundBean.getRefund_status();
//        }
//        List<TOrderRefundBean> orderRefundBeans = this.getEventRefundBean(multipleRefundParams.getOrderSn(),
//                multipleRefundParams.getEventId());
//        if(null != orderRefundBeans && !orderRefundBeans.isEmpty()){
//            int userRefundState = orderRefundBeans.get(0).getRefund_status();
//        }
//        //????????????????????????????????????????????????????????????
//        if(null == venderRefundBean && (null != orderRefundBeans && orderRefundBeans.isEmpty())){
//            this.venderRefund(multipleRefundParams.getOrderSn(), multipleRefundParams.getProducts()
//                    , multipleRefundParams.getRefundReason(), multipleRefundParams.getEventId());
//            ResultData resultData1 = this.userRefund(multipleRefundParams.getOrderSn(), multipleRefundParams.getProducts()
//                    , multipleRefundParams.getRefundReason(), multipleRefundParams.getEventId());
//            logger.info(resultData1.getMsg());
//        }
//        resultData.setError(ErrorCode.SUCCESS);
//        resultData.setMsg("??????????????????,???????????????????????????");
//        return resultData;
//    }

    @Override
    public ResultData multipleRefund(MultipleRefundParams multipleRefundParams){
        ResultData resultData = new ResultData();
        //???????????????????????????????????????
        TOrderVenderRefundBean venderRefundBean = this.getVenderRefund(multipleRefundParams.getOrderSn()
                , multipleRefundParams.getEventId());
        if(null == venderRefundBean){
            ResultData resultData1 =this.venderRefund(multipleRefundParams.getOrderSn(), multipleRefundParams.getProducts()
                    , multipleRefundParams.getRefundReason(), multipleRefundParams.getEventId());
            logger.info(resultData1.getMsg());
        }
        List<TOrderRefundBean> orderRefundBeans = this.getEventRefundBean(multipleRefundParams.getOrderSn(),
                multipleRefundParams.getEventId());
        if(null == orderRefundBeans || orderRefundBeans.isEmpty()){
            ResultData resultData2 = this.userRefund(multipleRefundParams.getOrderSn(), multipleRefundParams.getProducts()
                    , multipleRefundParams.getRefundReason(), multipleRefundParams.getEventId());
            logger.info(resultData2.getMsg());
        }
        resultData.setError(ErrorCode.SUCCESS);
        resultData.setMsg("??????????????????,???????????????????????????");
        return resultData;
    }


    @Override
    public ResultData cardRefund(String orderSn, String refundNo){
        ResultData resultData = new ResultData();
        List<TOrderRefundBean> tOrderRefundBeans = orderRefundMapper.getOrderRefundByOrderSnAndRefundNo(orderSn, refundNo);
        if(null == tOrderRefundBeans || tOrderRefundBeans.isEmpty()){
            resultData.setError(ErrorCode.PARAM_ERROR);
            resultData.setMsg("??????????????????????????????");
            return resultData;
        }
        BigDecimal cardRefundFee = new BigDecimal(0);
        for(TOrderRefundBean tOrderRefundBean: tOrderRefundBeans){
            cardRefundFee = cardRefundFee.add(new BigDecimal(tOrderRefundBean.getCard_refund_price()));
        }
        return this.cardRefund(cardRefundFee, orderSn, refundNo, tOrderRefundBeans.get(0).getUser_id());
    }

    @Override
    public ResultData refundStatusSearch(String orderSn, String eventId){
        ResultData resultData = new ResultData();
        TOrderBean orderBean = orderService.getOrderInfo(orderSn);
        if(null == orderBean){
            resultData.setError(ErrorCode.NO_ORDER);
            resultData.setMsg("???????????????");
            return resultData;
        }
        List<TOrderRefundBean> refundBeans = this.getEventRefundBean(orderSn, eventId);
        if(null == refundBeans || refundBeans.isEmpty()){
            resultData.setError(ErrorCode.NO_RESPONSE);
            resultData.setMsg("??????????????????");
            return resultData;
        }
        if(1 == refundBeans.get(0).getRefund_status()){
            resultData.setError(ErrorCode.SUCCESS);
            resultData.setMsg("??????????????????");
            return resultData;
        }
        VenderPayment venderPayment = qrCodeService.getVenderPayment(orderBean.getVenderid(), orderBean.getPay_type());
        if(null == venderPayment) {
            resultData.setError(ErrorCode.SERVER_EXCEPTION);
            resultData.setMsg("Oms???????????????,?????????????????????????????????");
            return resultData;
        }
        ResultData<TcmallRefundQueryBaseInfo> refundResult = this.searchPmsRefundInfo(venderPayment, refundBeans.get(0).getRefund_no());
        if(ErrorCode.SUCCESS == refundResult.getError()){
            TcmallRefundQueryBaseInfo baseInfo = refundResult.getData();
            if(null != baseInfo && "SUCCESS".equals(this.userRefundNotify(refundBeans.get(0).getRefund_no(), baseInfo.getRefundOrderSn()))){
                resultData.setError(ErrorCode.SUCCESS);
                resultData.setMsg("??????????????????");
            }
        }else {
            resultData.setError(refundResult.getError());
            resultData.setMsg(refundResult.getMsg());
        }
        return resultData;
    }

    private ResultData<TcmallRefundQueryBaseInfo> searchPmsRefundInfo(VenderPayment venderPayment, String refundNo){
        if(1 == venderPayment.getPayType()){//??????
            WechatRefundQueryParams params = new WechatRefundQueryParams();
            params.setOutRefundNo(refundNo);
            params.setAppId(venderPayment.getAppId());
            params.setMchId(venderPayment.getMchId());
            params.setSkey(venderPayment.getPrivateKey());
            return this.pmsWechatRefundQuery(params);
        }else if(2 == venderPayment.getPayType()){//?????????
            AlipayRefundQueryParams params = new AlipayRefundQueryParams();
            params.setOutRefundNo(refundNo);
            params.setAppId(venderPayment.getAppId());
            params.setPrivateKey(venderPayment.getPrivateKey());
            params.setAlipayPublicKey(venderPayment.getPublicKey());
            return this.pmsAlipayRefundQuery(params);
        }else {
            ResultData<TcmallRefundQueryBaseInfo> refundResult = new ResultData<>();
            refundResult.setError(ErrorCode.FALI);
            refundResult.setMsg("????????????????????????");
            return refundResult;
        }
    }

    private ResultData cardRefund(BigDecimal refundFee, String orderSn, String refundNo, int userId){
        ResultData resultData = new ResultData();
        List<TOrderCardBean> orderCardBeans = cardService.getOrderCards(orderSn);
        if(null == orderCardBeans || orderCardBeans.isEmpty()){
            resultData.setError(ErrorCode.SUCCESS);
            resultData.setMsg("?????????????????????????????????");
            return resultData;
        }
        BigDecimal usedFee = new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP);
        refundFee = refundFee.setScale(2, BigDecimal.ROUND_HALF_UP);
        for(TOrderCardBean tOrderCardBean: orderCardBeans){
            usedFee = usedFee.add(tOrderCardBean.getUse_fee());
        }
        if(refundFee.compareTo(usedFee)>0){
            resultData.setError(ErrorCode.PAY_MONEY_ERROR);
            resultData.setMsg("???????????????:????????????????????????:"+refundFee.floatValue());
            return resultData;
        }
        //??????????????????
        List<TCardBean> tCardBeans = new ArrayList<>(orderCardBeans.size());
        List<TCardUserRecordBean> tCardUserRecordBeans = new ArrayList<>(orderCardBeans.size());
        for(TOrderCardBean tOrderCardBean: orderCardBeans){
            if(refundFee.compareTo(BigDecimal.valueOf(0.009)) <= 0){
                break;
            }
            BigDecimal cardRefundTotalInThisOrder = cardService.getCardRefundFee(orderSn, tOrderCardBean.getCard_no());//????????????????????????????????????????????????
            BigDecimal cardRefundMaxInThisOrder = tOrderCardBean.getUse_fee().subtract(cardRefundTotalInThisOrder);//???????????????????????????????????????????????????
            if(cardRefundMaxInThisOrder.compareTo(new BigDecimal(0))<=0){
                continue;
            }
            BigDecimal thisRefundPrice;
            if(refundFee.compareTo(cardRefundMaxInThisOrder)>0){
                thisRefundPrice = cardRefundMaxInThisOrder;
                refundFee = refundFee.subtract(cardRefundMaxInThisOrder);
            }else {
                thisRefundPrice = refundFee;
                refundFee = new BigDecimal(0);
            }
            TCardBean tCardBean = new TCardBean();
            tCardBean.setBalance(thisRefundPrice);
            tCardBean.setCard_no(tOrderCardBean.getCard_no());
            tCardBeans.add(tCardBean);

            TCardUserRecordBean tCardUserRecordBean = new TCardUserRecordBean();
            tCardUserRecordBean.setCard_no(tOrderCardBean.getCard_no());
            tCardUserRecordBean.setOperate_type(3);
            tCardUserRecordBean.setCtime(new Date());
            tCardUserRecordBean.setRefund_no(refundNo);
            tCardUserRecordBean.setUser_id(userId);
            tCardUserRecordBean.setUse_fee(thisRefundPrice);
            tCardUserRecordBean.setOrder_sn(orderSn);
            tCardUserRecordBeans.add(tCardUserRecordBean);
        }
        if(cardService.batchUpdateCardBalance(tCardBeans)<1){
            resultData.setError(ErrorCode.WRITE_DATA_ERROR);
            resultData.setMsg("?????????????????????1");
            return resultData;
        }
        if(cardService.batchAddUserCardRecord(tCardUserRecordBeans) < 1){
            resultData.setError(ErrorCode.WRITE_DATA_ERROR);
            resultData.setMsg("?????????????????????2");
            return resultData;
        }
        return resultData;
    }

    private ResultData userRefund(RefundParams refundParams, VenderPayment venderPayment){
        ResultData resultData = new ResultData();
        //??????????????????????????????(??????????????????,????????????,???????????????)
        //???????????????????????????
        //????????????
        Map<String, Object> pmsRefundParams = new HashMap<>();
        pmsRefundParams.put("refundParams", refundParams);
        if(1 == refundParams.getPayType()){//??????
            WXPayConfig wxPayConfig = new WXPayConfig();
            wxPayConfig.setUseSandbox(true);
            wxPayConfig.setSignType("MD5");
            wxPayConfig.setAppID(venderPayment.getAppId());
            wxPayConfig.setMchID(venderPayment.getMchId());
            wxPayConfig.setKey(venderPayment.getPrivateKey());
            wxPayConfig.setCertStream(new StringBuffer(refundCertPath).append(venderPayment.getVenderId()).append(".p12").toString());
            pmsRefundParams.put("wxPayInfo", wxPayConfig);
        }else if(2 == refundParams.getPayType()){//?????????
            AlipayConfig alipayConfig = new AlipayConfig();
            alipayConfig.setAppId(venderPayment.getAppId());
            alipayConfig.setPrivateKey(venderPayment.getPrivateKey());
            alipayConfig.setAlipayPublicKey(venderPayment.getPublicKey());
            alipayConfig.setOpenApiDomain("https://openapi.alipay.com/gateway.do");
            alipayConfig.setSignType("RSA");
            pmsRefundParams.put("aliPayInfo", alipayConfig);
        }
        return this.pmsUserRefund(pmsRefundParams);
    }

//    private Map<String, String> createQrcode(HashMap<String, Object> pmsParams){
//        String req = new Gson().toJson(pmsParams);
//        Map<String, Object> res = rpcPmsService.createQrCode(req);
//        if(null == res || res.isEmpty() || !"0".equals(res.getOrDefault("status", "-1").toString())){
//            logger.info("??????PMS?????????????????????!");
//            return null;
//        }
//        return (Map<String, String>) res.getOrDefault("data", "");
//    }

    private ResultData pmsUserRefund(Map<String, Object> pmsParams){
        ResultData resultData = new ResultData();
        String req = new Gson().toJson(pmsParams);
        Map<String, Object> res = rpcPmsService.userRefund(req);
        if(null == res){
            resultData.setError(ErrorCode.SERVER_EXCEPTION);
            resultData.setMsg("pms????????????");
            return resultData;
        }
        if(res.isEmpty() || !"0".equals(res.getOrDefault("status", "-1").toString())){
            resultData.setError(ErrorCode.REFUND_FAIL);
            resultData.setMsg("????????????");
            return resultData;
        }
        resultData.setData((Map<String, String>) res.getOrDefault("data", ""));
        return resultData;
    }

    private ResultData<TcmallRefundQueryBaseInfo> pmsWechatRefundQuery(WechatRefundQueryParams params){
        ResultData<TcmallRefundQueryBaseInfo> resultData = new ResultData<>();
        PmsResultData<TcmallRefundQueryBaseInfo> res = rpcPmsService.wechatRefundSearch(params);
        if(null == res){
            resultData.setError(ErrorCode.SERVER_EXCEPTION);
            resultData.setMsg("pms????????????");
            logger.warn("??????????????????:pms????????????");
            return resultData;
        }
        if(200 != res.getStatus()){
            resultData.setError(ErrorCode.REFUND_FAIL);
            resultData.setMsg("????????????");
            logger.warn("????????????:{}", res.getMessage());
            return resultData;
        }
        resultData.setData(res.getData());
        return resultData;
    }

    private ResultData<TcmallRefundQueryBaseInfo> pmsAlipayRefundQuery(AlipayRefundQueryParams params){
        ResultData<TcmallRefundQueryBaseInfo> resultData = new ResultData<>();
        PmsResultData<TcmallRefundQueryBaseInfo> res = rpcPmsService.aliPayRefundSearch(params);
        if(null == res){
            resultData.setError(ErrorCode.SERVER_EXCEPTION);
            resultData.setMsg("pms????????????");
            return resultData;
        }
        if(200 != res.getStatus()){
            logger.warn("????????????:{}", res.getMessage());
            resultData.setError(ErrorCode.REFUND_FAIL);
            resultData.setMsg("????????????");
            return resultData;
        }
        resultData.setData(res.getData());
        return resultData;
    }

//    //????????????????????????
//    private BigDecimal getRefundFee(TOrderBean orderBean, Map<String, Integer> products){
//        return orderBean.getPay_price();
//    }
//
//    //????????????(????????????)??????????????? TODO ??????
//    private List<TOrderRefundBean> formatRefundBeans(String orderSn, Map<String, Integer> products, String eventId){
//        TOrderBean orderBean = orderService.getOrderInfo(orderSn);
//        List<Map<String, Object>> orderProductBeans = orderService.getProductCount(orderSn);
//        List<String> orderAllProductIds = new ArrayList<>(orderProductBeans.size());
//        for(Map<String, Object> map: orderProductBeans){
//            String sku = map.get("product_id").toString();
//            orderAllProductIds.add(sku);
//        }
//        if(!orderAllProductIds.containsAll(products.keySet())){
//            logger.error("????????????????????????????????????????????????");
//            return null;
//        }
//        Map<String, Map<String, Object>> productIdInfo = new HashMap<>(orderProductBeans.size());
//        for(Map<String, Object> map: orderProductBeans){
//            productIdInfo.put(map.get("product_id").toString(), map);
//        }
//        List<TOrderRefundBean> refundBeans = new ArrayList<>(products.size());
//        String refundNo = orderService.createOrderSn();
//        for (Map.Entry<String, Integer> entry: products.entrySet()){
//            String sku =  entry.getKey();
//            Map<String, Object> skuInfo = productIdInfo.get(sku);
//            int total = Integer.parseInt(skuInfo.get("count").toString());//??????????????????????????????
//            int refundedTotal = this.getRefundedProductCount(orderSn, Long.parseLong(sku));//?????????????????????
//            int currentTotal = products.get(sku);//????????????????????????
//            if(currentTotal + refundedTotal > total){
//                logger.error("?????????????????????????????????????????????");
//                return null;
//            }
//            //?????????????????????
//            float singlePrice = Float.parseFloat(skuInfo.get("price").toString());
//            float totalPrice = orderBean.getPrice().floatValue();
//            float payPrice = (orderBean.getPay_price().subtract(orderBean.getFreight())).floatValue();
//            int size = products.get(sku);
//            float refundPrice = payPrice*singlePrice*size/totalPrice;
//            //-----------------------------------------------------
//            TOrderRefundBean tOrderRefundBean = new TOrderRefundBean();
//            tOrderRefundBean.setVenderid(orderBean.getVenderid());
//            tOrderRefundBean.setUser_id(orderBean.getUser_id());
//            tOrderRefundBean.setOrder_sn(orderSn);
//            tOrderRefundBean.setProduct_id(Long.parseLong(sku));
//            tOrderRefundBean.setName("");
//            tOrderRefundBean.setCount(products.get(sku));
//            tOrderRefundBean.setRefund_price(refundPrice);
//            tOrderRefundBean.setPay_type(orderBean.getPay_type());
//            tOrderRefundBean.setPay_order_sn(orderBean.getPay_order_sn());
//            tOrderRefundBean.setRefund_no(refundNo);
//            tOrderRefundBean.setRefund_status(0);
//            tOrderRefundBean.setUnq_id(eventId);
//            refundBeans.add(tOrderRefundBean);
//        }
//        return refundBeans;
//    }

    //????????????(????????????)???????????????
    private List<TOrderRefundBean> multipleFormatRefundBeans(String orderSn, Map<String, Integer> products, String eventId){
        TOrderBean orderBean = orderService.getOrderInfo(orderSn);
        List<Map<String, Object>> orderProductBeans = orderService.getProductCount(orderSn);
        List<String> orderAllProductIds = new ArrayList<>(orderProductBeans.size());
        for(Map<String, Object> map: orderProductBeans){
            String sku = map.get("product_id").toString();
            orderAllProductIds.add(sku);
        }
        if(!orderAllProductIds.containsAll(products.keySet())){
            logger.error("????????????????????????????????????????????????");
            return null;
        }
        Map<String, Map<String, Object>> productIdInfo = new HashMap<>(orderProductBeans.size());
        for(Map<String, Object> map: orderProductBeans){
            productIdInfo.put(map.get("product_id").toString(), map);
        }
        List<TOrderRefundBean> refundBeans = new ArrayList<>(products.size());
        String refundNo = orderService.createOrderSn();
        BigDecimal cardPrice = orderBean.getCard_price().subtract(cardService.getOrderCardRefundFee(orderSn));
        for (Map.Entry<String, Integer> entry: products.entrySet()){
            String sku =  entry.getKey();
            Map<String, Object> skuInfo = productIdInfo.get(sku);
            int total = Integer.parseInt(skuInfo.get("count").toString());//??????????????????????????????
            int refundedTotal = this.getRefundedProductCount(orderSn, Long.parseLong(sku));//?????????????????????
            int currentTotal = products.get(sku);//????????????????????????
            if(currentTotal + refundedTotal > total){
                logger.error("?????????????????????????????????????????????");
                return null;
            }
            //?????????????????????
            BigDecimal singlePrice = BigDecimal.valueOf(Float.parseFloat(skuInfo.get("price").toString()));
            int size = products.get(sku);
            //-----------------------------------------------------
            BigDecimal refundPrice = singlePrice.multiply(new BigDecimal(size)).setScale(2, BigDecimal.ROUND_HALF_UP);
            //-----------------------------------------------------
            TOrderRefundBean tOrderRefundBean = new TOrderRefundBean();
            if(cardPrice.compareTo(refundPrice)>=0){
                tOrderRefundBean.setCard_refund_price(refundPrice.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue());
                tOrderRefundBean.setRefund_price(0);
                cardPrice = cardPrice.subtract(refundPrice);
            }else {
                tOrderRefundBean.setCard_refund_price(cardPrice.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue());
                cardPrice = refundPrice.subtract(cardPrice);
                tOrderRefundBean.setRefund_price(cardPrice.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue());
                cardPrice = new BigDecimal(0);
            }
            tOrderRefundBean.setVenderid(orderBean.getVenderid());
            tOrderRefundBean.setUser_id(orderBean.getUser_id());
            tOrderRefundBean.setOrder_sn(orderSn);
            tOrderRefundBean.setProduct_id(Long.parseLong(sku));
            tOrderRefundBean.setName("");
            tOrderRefundBean.setCount(products.get(sku));
            tOrderRefundBean.setPay_type(orderBean.getPay_type());
            tOrderRefundBean.setPay_order_sn(orderBean.getPay_order_sn());
            tOrderRefundBean.setRefund_no(refundNo);
            tOrderRefundBean.setRefund_status(0);
            tOrderRefundBean.setUnq_id(eventId);
            refundBeans.add(tOrderRefundBean);
        }
        return refundBeans;
    }

    private ResultData getVenderRefundPrice(String orderSn, Map<String, Integer> products){
        ResultData resultData = new ResultData();
        List<Map<String, Object>> orderProductBeans = orderService.getProductCount(orderSn);
        List<String> orderAllProductIds = new ArrayList<>(orderProductBeans.size());
        for(Map<String, Object> map: orderProductBeans){
            String sku = map.get("product_id").toString();
            orderAllProductIds.add(sku);
        }
        if(!orderAllProductIds.containsAll(products.keySet())){
            logger.error("????????????????????????????????????????????????");//????????????
            resultData.setError(ErrorCode.PARAM_ERROR);
            resultData.setMsg("????????????????????????????????????????????????");
            return resultData;
        }
        Map<String, Map<String, Object>> productIdInfo = new HashMap<>(orderProductBeans.size());
        for(Map<String, Object> map: orderProductBeans){
            productIdInfo.put(map.get("product_id").toString(), map);
        }
        BigDecimal totalPrice = new BigDecimal(0);
        for (Map.Entry<String, Integer> entry: products.entrySet()){
            String sku =  entry.getKey();
            Map<String, Object> skuInfo = productIdInfo.get(sku);
            int total = Integer.parseInt(skuInfo.get("count").toString());//??????????????????????????????
            int refundedTotal = this.getRefundedProductCount(orderSn, Long.parseLong(sku));//?????????????????????
            int currentTotal = products.get(sku);//????????????????????????
            if(currentTotal + refundedTotal > total){
                logger.error("?????????????????????????????????????????????");//????????????
                resultData.setError(ErrorCode.PARAM_ERROR);
                resultData.setMsg("?????????????????????????????????????????????");
                return resultData;
            }
            int size = products.get(sku);
            //????????????????????????
            BigDecimal venderSinglePrice = new BigDecimal(skuInfo.get("agree_price").toString());
            BigDecimal venderRefundPrice = venderSinglePrice.multiply(new BigDecimal(size));
            totalPrice = totalPrice.add(venderRefundPrice);
        }
        resultData.setData(totalPrice);
        return resultData;
    }

    private String getCurrentStrTime2(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return  simpleDateFormat.format(new Date());
    }

    private String getDeliveryEventId(String keys){
        String s = keys + "juqian";
        return Tools.md5(s).toLowerCase();
    }

    private String createCardRefundTradeNo(){
        int max = 9999;
        int min = 1000;
        return "CARD"+this.getCurrentStrTime2() + new Random().nextInt(max - min)+min;
    }

    @Override
    public ResultData userRefundRetry(String orderSn, String eventId){
        ResultData resultData = new ResultData();
        TOrderBean orderBean = orderService.getOrderInfo(orderSn);
        if(null == orderBean){
            resultData.setError(ErrorCode.NO_ORDER);
            resultData.setMsg("???????????????");
            return resultData;
        }
        List<TOrderRefundBean> orderRefundBeans = this.getEventRefundBean(orderSn, eventId);
        if(orderRefundBeans != null && !orderRefundBeans.isEmpty()){//????????????????????????
            //1:?????????????????????????????????
            if(1 == orderRefundBeans.get(0).getRefund_status()){//??????????????????
                resultData.setError(ErrorCode.SUCCESS);
                resultData.setMsg("????????????");
                return resultData;
            }
            //2:??????pms??????????????????
            ResultData pmsRefundInfo =  this.refundStatusSearch(orderSn, orderRefundBeans.get(0).getUnq_id());
            if(ErrorCode.SUCCESS == pmsRefundInfo.getError()){
                resultData.setError(ErrorCode.SUCCESS);
                resultData.setMsg("????????????");
                return resultData;
            }
            //3:????????????
            float refundPrice = 0;
            for (TOrderRefundBean orderRefundBean :orderRefundBeans){
                refundPrice+=orderRefundBean.getRefund_price();
            }
            return this.refundReTry(orderBean, refundPrice*100, orderRefundBeans.get(0).getRefund_no());
        }else {
            resultData.setError(ErrorCode.FALI);
            resultData.setMsg("??????????????????");
        }
        return resultData;
    }

    @Override
    public ResultData venderRefundRetry(String orderSn, String eventId){
        ResultData resultData = new ResultData();
        TOrderBean orderBean = orderService.getOrderInfo(orderSn);
        if(null == orderBean){
            resultData.setError(ErrorCode.NO_ORDER);
            resultData.setMsg("???????????????");
            return resultData;
        }
        TOrderVenderRefundBean venderRefundBean = tOrderVenderRefundMapper.getVenderRefund(orderSn, eventId);
        if(null == venderRefundBean){
            resultData.setError(ErrorCode.FALI);
            resultData.setMsg("??????????????????");
        }else {
            if(venderRefundBean.getRefund_status() == 1){
                resultData.setError(ErrorCode.SUCCESS);
                resultData.setMsg("????????????");
                return resultData;
            }
            if(!orderService.refundToVender(orderSn, venderRefundBean.getRefund_price(), BigDecimal.valueOf(0) , BigDecimal.valueOf(0))){
                resultData.setError(ErrorCode.SERVER_EXCEPTION);
                resultData.setMsg("????????????????????????");
            }else {
                resultData.setError(ErrorCode.SUCCESS);
                resultData.setMsg("OK");
                if(tOrderVenderRefundMapper.updateVenderRefundStatus(orderSn, eventId)<1){
                    resultData.setError(ErrorCode.WRITE_DATA_ERROR);
                    resultData.setMsg("?????????????????????");
                }
            }
        }
        return resultData;
    }

    private ResultData refundReTry(TOrderBean orderBean, float refundPrice, String refundNo){
        ResultData resultData = new ResultData();
        VenderPayment venderPayment = qrCodeService.getVenderPayment(orderBean.getVenderid(), orderBean.getPay_type());
        if(null == venderPayment){
            resultData.setError(ErrorCode.SERVER_EXCEPTION);
            resultData.setMsg("Oms???????????????,?????????????????????????????????");
            return resultData;
        }
        RefundParams refundParams = new RefundParams();
        refundParams.setNotifyUrl(refundNotifyUrl);
        refundParams.setOutTradeNo(orderBean.getPay_order_sn());
        refundParams.setTotalFee(orderBean.getPay_price().multiply(new BigDecimal(100)).intValue());
        refundParams.setRefundFee((int) refundPrice);
        refundParams.setRefundReason("????????????");
        refundParams.setPayType(orderBean.getPay_type());
        refundParams.setGoodInfos(null);
        refundParams.setOutRefundNo(refundNo);
        return this.userRefund(refundParams, venderPayment);
    }
}
