package com.hqtc.bms.service.impl;

import com.google.gson.Gson;
import com.hqtc.bms.model.database.TOrderBean;
import com.hqtc.bms.model.database.TOrderProductBean;
import com.hqtc.bms.model.database.TOrderRefundBean;
import com.hqtc.bms.model.mapper.TOrderRefundMapper;
import com.hqtc.bms.model.params.MutiplePayParams;
import com.hqtc.bms.model.params.RefundParams;
import com.hqtc.bms.model.params.WechatAppletPayConfig;
import com.hqtc.bms.model.params.WechatAppletPayConfigBean;
import com.hqtc.bms.model.response.Result;
import com.hqtc.bms.model.rpc.*;
import com.hqtc.bms.service.OrderService;
import com.hqtc.bms.service.QrCodeService;
import com.hqtc.bms.service.rpc.RPCOmsService;
import com.hqtc.bms.service.rpc.RPCPmsService;
import com.hqtc.bms.service.rpc.RPCWxService;
import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.ResultData;
import com.hqtc.common.utils.Tools;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.bouncycastle.jcajce.provider.asymmetric.GOST;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wanghaoyang on 18-7-6.
 */
@Service("QrCodeServiceImpl")
public class QrCodeServiceImpl implements QrCodeService {

    private Logger logger = LoggerFactory.getLogger("QrCodeServiceImpl");

    @Autowired
    private RPCOmsService rpcOmsService;

    @Autowired
    private RPCPmsService rpcPmsService;

    @Autowired
    private RPCWxService rpcWxService;

    @Value("${pay.notifyUrl}")
    private String notifyUrl;

    @Value("${order.pms.partnerId}")
    private String partnerId;

    @Value("${order.pms.appid}")
    private String appid;

    @Autowired
    private WechatAppletPayConfig wechatAppletPayConfig;


    @Override
    public String createWechatQrCode(TOrderBean orderBean, VenderPayment venderPayment){
        return null;
    }

    @Override
    public Map<String, String> createPayQrCode(TOrderBean orderBean, VenderPayment venderPayment, int payType){
        PayParams payParams = new PayParams();
        payParams.setOrderSn(orderBean.getOrder_sn());
//        payParams.setActualPrice(1);
        payParams.setActualPrice(((orderBean.getPrice().add(orderBean.getFreight())).multiply(new BigDecimal(100))).intValue());//
        payParams.setSign("");
        payParams.setGoodsId("");
        payParams.setNotifyUrl(notifyUrl);//
        payParams.setSubject("????????????");
        payParams.setGoodsDetail("");
        payParams.setDeviceInfo("");
        payParams.setBody("");
        payParams.setPartnerId(partnerId);
        payParams.setAppId(appid);
        payParams.setUserId(orderBean.getUser_id());
        HashMap<String, Object> pmsParams = new HashMap<>(3);
        pmsParams.put("orderInfo", payParams);
        pmsParams.put("payType", payType);
        Map<String, String> res = new HashMap<>(2);
        WXPayConfig wxPayConfig = new WXPayConfig();
        wxPayConfig.setUseSandbox(true);
        wxPayConfig.setSignType("MD5");
        wxPayConfig.setAppID(venderPayment.getAppId());
        wxPayConfig.setMchID(venderPayment.getMchId());
        wxPayConfig.setKey(venderPayment.getPrivateKey());
        pmsParams.put("wxPayInfo", wxPayConfig);
        AlipayConfig alipayConfig = new AlipayConfig();
        alipayConfig.setOpenApiDomain("https://openapi.alipay.com/gateway.do");
        alipayConfig.setAppId(venderPayment.getAppId());
        alipayConfig.setPrivateKey(venderPayment.getPrivateKey());
        alipayConfig.setAlipayPublicKey(venderPayment.getPublicKey());
        alipayConfig.setSignType("RSA");
        pmsParams.put("aliPayInfo", alipayConfig);
        Map<String, String> map = this.createQrcode(pmsParams);
        if(null != map){
            map.put("faceValue", orderBean.getPrice().add(orderBean.getFreight()).toString());
        }
        return map;
    }

    @Override
    public Map<String, String> createPayQrCode(TOrderBean orderBean,  List<VenderPayment> venderPayments){
        BigDecimal price = orderBean.getPrice().add(orderBean.getFreight());
        return this.createQuotaQrCode(orderBean, venderPayments, price);
    }

    @Override
    public Map<String, String> createQuotaQrCode(TOrderBean orderBean, List<VenderPayment> venderPayments, BigDecimal quotaPrice){
//        quotaPrice = new BigDecimal(0.01).setScale(2, BigDecimal.ROUND_HALF_UP);//TODO ????????????
        PayParams payParams = new PayParams();
        payParams.setOrderSn(orderBean.getOrder_sn());
        payParams.setActualPrice(quotaPrice.multiply(new BigDecimal(100)).intValue());
        payParams.setSign("");
        payParams.setGoodsId("");
        payParams.setNotifyUrl(notifyUrl);
        payParams.setSubject("????????????");
        payParams.setGoodsDetail("");
        payParams.setDeviceInfo("");
        payParams.setBody("");
        payParams.setPartnerId(partnerId);
        payParams.setAppId(appid);
        payParams.setUserId(orderBean.getUser_id());
        HashMap<String, Object> pmsParams = new HashMap<>(3);
        pmsParams.put("orderInfo", payParams);
        pmsParams.put("payType", 0);
        Map<String, String> res = new HashMap<>(2);
        WXPayConfig wxPayConfig = new WXPayConfig();
        wxPayConfig.setUseSandbox(true);
        wxPayConfig.setSignType("MD5");
        AlipayConfig alipayConfig = new AlipayConfig();
        for (VenderPayment venderPayment: venderPayments){
            if(1 == venderPayment.getPayType()){
                wxPayConfig.setAppID(venderPayment.getAppId());
                wxPayConfig.setMchID(venderPayment.getMchId());
                wxPayConfig.setKey(venderPayment.getPrivateKey());
            }else if(2 == venderPayment.getPayType()){
                alipayConfig.setAppId(venderPayment.getAppId());
                alipayConfig.setPrivateKey(venderPayment.getPrivateKey());
                alipayConfig.setAlipayPublicKey(venderPayment.getPublicKey());
            }
        }
        pmsParams.put("wxPayInfo", wxPayConfig);
        alipayConfig.setOpenApiDomain("https://openapi.alipay.com/gateway.do");
        alipayConfig.setSignType("RSA");
        pmsParams.put("aliPayInfo", alipayConfig);
        return this.createQrcode(pmsParams);
    }

    @Override
    public VenderPayment getVenderPayment(int venderId, int type){
        ResultData<List<VenderPayment>> resultData = rpcOmsService.getVenderPayMent((long)venderId);
        if(null == resultData || 0 != resultData.getError() || resultData.getData().isEmpty()){
            logger.warn(String.format("????????????????????????????????????: %s", venderId));
            return null;
        }
        List<VenderPayment> venderPayments = resultData.getData();
        for(VenderPayment vp: venderPayments){
            if(type == vp.getPayType()){//?????????
                return vp;
            }
        }
        return null;
    }

    @Override
    public List<VenderPayment> getVenderAllPayment(int venderId){
        ResultData<List<VenderPayment>> resultData = rpcOmsService.getVenderPayMent((long)venderId);
        if(null == resultData || 0 != resultData.getError() || resultData.getData().isEmpty()){
            logger.warn(String.format("????????????????????????????????????: %s", venderId));
            return null;
        }
        List<VenderPayment> venderPayments = resultData.getData();
        return venderPayments;
    }

    @Override
    public ResultData checkOrder(TOrderBean tOrderBean, int userId, int venderId){
        ResultData resultData = Tools.getThreadResultData();
        if(null == tOrderBean){
            resultData.setError(10010);
            resultData.setMsg("????????????");
            return resultData;
        }
        if(userId != tOrderBean.getUser_id()){
            resultData.setError(10011);
            resultData.setMsg("???????????????");
            return resultData;
        }
        if(venderId != tOrderBean.getVenderid()){
            resultData.setError(10012);
            resultData.setMsg("???????????????");
            return resultData;
        }
        if(1 == tOrderBean.getPay_status()){
            resultData.setError(ErrorCode.ORDER_PAYED);
            resultData.setMsg("???????????????");
            return resultData;
        }
        return resultData;
    }

    @Override
    public Map<String, String> createPayAuth(TOrderBean orderBean, VenderPayment venderPayment, MutiplePayParams payParams){
        BigDecimal price = orderBean.getPrice().add(orderBean.getFreight());
        Map<String, String> info = this.createQuotaPayAuth(orderBean, venderPayment, price, payParams);
        if(null == info){
            return info;
        }else {
            info.put("faceValue", price.toString());
            return info;
        }
    }

    @Override
    public Map<String, String> createQuotaPayAuth(TOrderBean orderBean, VenderPayment venderPayment, BigDecimal quotaPrice, MutiplePayParams payParams){
        HashMap<String, Object> pmsParams = new HashMap<>(3);
        pmsParams.put("orderInfo", this.getThirdPartPayCommonParams(orderBean, quotaPrice, payParams.getUserCode()));
        pmsParams.put("payType", venderPayment.getPayType());
        if(1 == venderPayment.getPayType() || 3 == venderPayment.getPayType()){
            pmsParams.put("wxPayInfo", this.getWxPayParams(venderPayment));
        }else if(2 == venderPayment.getPayType()){
            pmsParams.put("aliPayInfo", this.getAliPayParams(venderPayment));
        }
        return this.createAuth(pmsParams);
    }

    /**
     * ?????????????????????????????????
     * add by wanghaoyang at 20180927
     * */
    private WXPayConfig getWxPayParams(VenderPayment venderPayment){
        WXPayConfig wxPayConfig = new WXPayConfig();
        wxPayConfig.setUseSandbox(false);
        wxPayConfig.setSignType("MD5");
        wxPayConfig.setAppID(venderPayment.getAppId());
        wxPayConfig.setMchID(venderPayment.getMchId());
        wxPayConfig.setKey(venderPayment.getPrivateKey());
        return wxPayConfig;
    }

    /**
     * ????????????????????????????????????
     * add by wanghaoyang at 20180927
     * */
    private AlipayConfig getAliPayParams(VenderPayment venderPayment){
        AlipayConfig alipayConfig = new AlipayConfig();
        alipayConfig.setAppId(venderPayment.getAppId());
        alipayConfig.setPrivateKey(venderPayment.getPrivateKey());
        alipayConfig.setAlipayPublicKey(venderPayment.getPublicKey());
        alipayConfig.setOpenApiDomain("https://openapi.alipay.com/gateway.do");
        alipayConfig.setSignType("RSA");
        return alipayConfig;
    }

    /**
     * ????????????????????????????????????
     * */
    private PayParams getThirdPartPayCommonParams(TOrderBean orderBean, BigDecimal quotaPrice, String openId){
        PayParams payParams = new PayParams();
        payParams.setOrderSn(orderBean.getOrder_sn());
        payParams.setActualPrice(quotaPrice.multiply(new BigDecimal(100)).intValue());
        payParams.setSign("");
        payParams.setGoodsId("");
        payParams.setNotifyUrl(notifyUrl);
        payParams.setSubject("????????????");
        payParams.setGoodsDetail("");
        payParams.setDeviceInfo("");
        payParams.setBody("");
        payParams.setPartnerId(partnerId);
        payParams.setAppId(appid);
        payParams.setUserId(orderBean.getUser_id());
        payParams.setOpenId(openId);
        return payParams;
    }

//    @Override
//    public ResultData userRefund(String orderSn, Map<String, Integer> products, String refundReason){
//        ResultData resultData = new ResultData();
//        TOrderBean orderBean = orderService.getOrderInfo(orderSn);
//        if(null == orderBean){
//            resultData.setError(ErrorCode.NO_ORDER);
//            resultData.setMsg("???????????????");
//            return resultData;
//        }
//        if(0 == orderBean.getPay_status()){
//            resultData.setError(ErrorCode.UN_PAYED);
//            resultData.setMsg("???????????????");
//            return resultData;
//        }
//        VenderPayment venderPayment = this.getVenderPayment(orderBean.getVenderid(), orderBean.getPay_type());
//        if(null == venderPayment){
//            resultData.setError(ErrorCode.SERVER_EXCEPTION);
//            resultData.setMsg("Oms???????????????,?????????????????????????????????");
//            return resultData;
//        }
//        //??????????????????
//        TOrderRefundBean refundBean = this.formatRefundBean(orderSn, products);
//        if(null == refundBean){
//            resultData.setError(ErrorCode.PARAM_ERROR);
//            resultData.setMsg("??????????????????????????????");
//            return resultData;
//        }
//        if(this.addUserRefund(refundBean)<1){
//            resultData.setError(ErrorCode.SERVER_EXCEPTION);
//            resultData.setMsg("?????????????????????????????????");
//            return resultData;
//        }
//        RefundParams refundParams = new RefundParams();
//        refundParams.setNotifyUrl(refundNotifyUrl);
//        refundParams.setOutTradeNo(orderBean.getPay_order_sn());
//        refundParams.setTotalFee(orderBean.getPay_price().multiply(new BigDecimal(100)).intValue());
//        refundParams.setRefundFee(this.getRefundFee(orderBean, products).multiply(new BigDecimal(100)).intValue());
//        refundParams.setRefundReason(refundReason);
//        refundParams.setPayType(orderBean.getPay_type());
//        refundParams.setGoodInfos(null);
//        refundParams.setOutRefundNo(refundBean.getRefund_no());
//        return this.userRefund(refundParams, venderPayment);
//    }
//
//    @Override
//    public int addUserRefund(TOrderRefundBean orderRefundBean){
//        return orderRefundMapper.addRefundOrder(orderRefundBean);
//    }
//
//    @Override
//    public int addUserRefund(List<TOrderRefundBean> orderRefundBeans){
//        return orderRefundMapper.batchAddRefundOrder(orderRefundBeans);
//    }
//
//    public List<TOrderRefundBean> getSuccessRefundByOrderSnAndProduct(String orderSn, int product){
//        return orderRefundMapper.findreFundByOrderSnAndProductId(orderSn, product);
//    }
//
//    private ResultData userRefund(RefundParams refundParams, VenderPayment venderPayment){
//        ResultData resultData = new ResultData();
//        //??????????????????????????????(??????????????????,????????????,???????????????)
//        //???????????????????????????
//        //????????????
//        Map<String, Object> pmsRefundParams = new HashMap<>();
//        pmsRefundParams.put("refundParams", refundParams);
//        if(1 == refundParams.getPayType()){//??????
//            WXPayConfig wxPayConfig = new WXPayConfig();
//            wxPayConfig.setUseSandbox(true);
//            wxPayConfig.setSignType("MD5");
//            wxPayConfig.setAppID(venderPayment.getAppId());
//            wxPayConfig.setMchID(venderPayment.getMchId());
//            wxPayConfig.setKey(venderPayment.getPrivateKey());
//            pmsRefundParams.put("wxPayInfo", wxPayConfig);
//        }else if(2 == refundParams.getPayType()){//?????????
//            AlipayConfig alipayConfig = new AlipayConfig();
//            alipayConfig.setAppId(venderPayment.getAppId());
//            alipayConfig.setPrivateKey(venderPayment.getPrivateKey());
//            alipayConfig.setAlipayPublicKey(venderPayment.getPublicKey());
//            alipayConfig.setOpenApiDomain("https://openapi.alipay.com/gateway.do");
//            alipayConfig.setSignType("RSA");
//            pmsRefundParams.put("aliPayInfo", alipayConfig);
//        }
//        return this.pmsUserRefund(pmsRefundParams);
//    }
//
    private Map<String, String> createQrcode(HashMap<String, Object> pmsParams){
        String req = new Gson().toJson(pmsParams);
        Map<String, Object> res = rpcPmsService.createQrCode(req);
        if(null == res || res.isEmpty() || !"0".equals(res.getOrDefault("status", "-1").toString())){
            logger.info("??????PMS?????????????????????!");
            return null;
        }
        return (Map<String, String>) res.getOrDefault("data", "");
    }

    private Map<String, String> createAuth(HashMap<String, Object> pmsParams){
        String req = new Gson().toJson(pmsParams);
        Map<String, Object> res = rpcPmsService.createAuth(req);
        logger.info("------------"+String.valueOf(res));
        if(null == res || res.isEmpty() || !"0".equals(res.getOrDefault("status", "-1").toString())){
            logger.info("??????PMS????????????????????????!");
            return null;
        }
        return (Map<String, String>) res.getOrDefault("data", "");
    }

    @Override
    public VenderPayment getWechatAppletPayment(int venderId){
        WechatAppletPayConfigBean wechatAppletPayConfigBean = this.getVenderAppletConfig(venderId);
        if(null == wechatAppletPayConfigBean){
            logger.warn("????????????????????????????????????2:{}", venderId);
            return null;
        }
        VenderPayment venderPayment = new VenderPayment();
        venderPayment.setAppId(wechatAppletPayConfigBean.getAppid());
        venderPayment.setMchId(wechatAppletPayConfigBean.getMechid());
        venderPayment.setPayType(3);
        venderPayment.setPrivateKey(wechatAppletPayConfigBean.getApikey());
        venderPayment.setPublicKey("");
        return venderPayment;
    }

    @Override
    public WxCode2SessionBean getWxUserInfoByUserCode(String userCode, int venderId){
        WechatAppletPayConfigBean wechatAppletPayConfigBean = this.getVenderAppletConfig(venderId);
        if(null == wechatAppletPayConfigBean){
            logger.warn("????????????????????????????????????2:{}", venderId);
            return null;
        }
        String res = rpcWxService.getWxUserInfoByUserCode(wechatAppletPayConfigBean.getAppid(),
                wechatAppletPayConfigBean.getAppSecret(), userCode, "authorization_code");
        if(null == res){
            return null;
        }
        Gson gson = new Gson();
        WxCode2SessionBean wxCode2SessionBean = gson.fromJson(res, WxCode2SessionBean.class);
        if(null == wxCode2SessionBean){
            return null;
        }
        if(0 != wxCode2SessionBean.getErrcode()){
            return null;
        }
        return wxCode2SessionBean;

    }

    private WechatAppletPayConfigBean getVenderAppletConfig(int venderId){
        if(null == wechatAppletPayConfig){
            logger.warn("????????????????????????1:{}", venderId);
            return null;
        }
        Map<Integer, WechatAppletPayConfigBean> wechatAppletPayConfigBeanMap = wechatAppletPayConfig.getLists();
        if(null == wechatAppletPayConfigBeanMap || wechatAppletPayConfigBeanMap.isEmpty()){
            logger.warn("????????????????????????2:{}", venderId);
            return null;
        }
        if(!wechatAppletPayConfigBeanMap.keySet().contains(venderId)){
            logger.warn("????????????????????????????????????1:{}", venderId);
            return null;
        }
        return wechatAppletPayConfigBeanMap.get(venderId);
    }
}
