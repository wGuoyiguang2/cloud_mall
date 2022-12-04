package com.hqtc.bms.controller;

import com.hqtc.bms.config.Router;
import com.hqtc.bms.config.VenderRouter;
import com.hqtc.bms.model.database.*;
import com.hqtc.bms.model.params.*;
import com.hqtc.bms.model.response.OrderResponse;
import com.hqtc.bms.model.rpc.*;
import com.hqtc.bms.service.CardService;
import com.hqtc.bms.service.OrderService;
import com.hqtc.bms.service.ProductService;
import com.hqtc.bms.service.QrCodeService;
import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.ResultData;
import com.hqtc.common.utils.Tools;
import io.swagger.models.auth.In;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

/**
 * Created by wanghaoyang on 18-7-4.
 */
@RestController
public class PayController {

    private Logger logger = LoggerFactory.getLogger("PayController");

    @Autowired
    @Resource(name = "OrderServiceImpl")
    private OrderService orderService;

    @Autowired
    @Resource(name = "ProductServiceImpl")
    private ProductService productService;

    @Autowired
    @Resource(name = "QrCodeServiceImpl")
    private QrCodeService qrCodeService;

    @Value("${order.notifyEmail}")
    private String notifyEmail;

    @Value("${order.product.maxSize}")
    private int maxProductType;

    @Autowired
    @Resource(name = "CardServiceImpl")
    private CardService cardService;

    @RequestMapping(value = {Router.ROUTER_ORDER}, method = RequestMethod.POST)
    public ResultData order(@RequestAttribute(value = "userId") Integer userId,
                            @RequestBody @Valid OrderParams orderParams,
                            BindingResult bindingResult){
        ResultData resultData = Tools.getThreadResultData();
        if(bindingResult.hasErrors()){
            resultData.setError(ErrorCode.PARAM_ERROR);
            resultData.setMsg(bindingResult.getAllErrors().get(0).getDefaultMessage());
            return resultData;
        }
        orderParams.setUserId(userId);
        if(null == orderParams.getProductIds() && null == orderParams.getProducts()){
            resultData.setError(ErrorCode.PARAM_ERROR);
            resultData.setMsg("参数错误:请传入正确的products");
            return resultData;
        }
        if(orderParams.getProducts().keySet().size()>maxProductType || orderParams.getProducts().size()<1){
            resultData.setError(ErrorCode.PARAM_ERROR);
            resultData.setMsg("抱歉，无法再添加，最多50种商品");
            return resultData;
        }
        //获取商品价格
        Map<Long, ProductPriceBean> map = productService.getProductPrice(orderParams.getVenderId() , new ArrayList<>(orderParams.getProducts().keySet()));
        if(null == map){
            resultData.setError(ErrorCode.NO_PRODUCT);
            resultData.setMsg("没有查找到此商品");
            return resultData;
        }
        List<TOrderProductBean> tOrderProductBeans = new ArrayList<>(orderParams.getProducts().size());
        String orderSn = orderService.createOrderSn();
        BigDecimal totalAgreePrice = new BigDecimal(0);
        BigDecimal totalPrice = new BigDecimal(0);
        for (Map.Entry<String, Integer> entry: orderParams.getProducts().entrySet()){
            String productId = entry.getKey();
            int productSize = entry.getValue();
            TOrderProductBean tOrderProductBean = new TOrderProductBean();
            ProductPriceBean productPriceBean = map.get(Long.parseLong(productId));
            if(null==productPriceBean.getAgree_price() || null==productPriceBean.getPrice() || null==productPriceBean.getName()){
                resultData.setError(ErrorCode.STOCK_NOT_ENOUGH);
                resultData.setMsg(productPriceBean.getName()+" 此商品已下架");
                return resultData;
            }
            tOrderProductBean.setChild_trade_no("");
            tOrderProductBean.setOrder_sn(orderSn);
            tOrderProductBean.setProduct_id(Long.parseLong(productId));
            tOrderProductBean.setName(productPriceBean.getName());
            tOrderProductBean.setAgree_price(productPriceBean.getAgree_price());
            tOrderProductBean.setPrice(productPriceBean.getPrice());
            tOrderProductBean.setPay_price(new BigDecimal(0));
            tOrderProductBean.setCount(productSize);
            tOrderProductBean.setTaxrate(0);
            tOrderProductBean.setOrder_state(1);
            tOrderProductBean.setCtime(this.getCurrentTime());
            tOrderProductBean.setUtime(this.getCurrentTime());
            tOrderProductBeans.add(tOrderProductBean);
            totalPrice = totalPrice.add(tOrderProductBean.getPrice().multiply(new BigDecimal(productSize)));
            totalAgreePrice = totalAgreePrice.add(tOrderProductBean.getAgree_price().multiply(new BigDecimal(productSize)));
        }
        TOrderBean tOrderBean = new TOrderBean();
        tOrderBean.setAgree_price(totalAgreePrice);
        tOrderBean.setUser_id(orderParams.getUserId());
        tOrderBean.setVenderid(orderParams.getVenderId());
        tOrderBean.setOrder_sn(orderSn);
        tOrderBean.setCtime(new Date());
        tOrderBean.setPrice(totalPrice);
        tOrderBean.setInvoice(orderParams.getInvoice());
        TOrderAddressBean tOrderAddressBean = new TOrderAddressBean();
        tOrderAddressBean.setOrder_id(tOrderBean.getId());
        tOrderAddressBean.setProvince_id(orderParams.getProvinceId());
        tOrderAddressBean.setCity_id(orderParams.getCityId());
        tOrderAddressBean.setCounty_id(orderParams.getCountyId());
        tOrderAddressBean.setTown_id(orderParams.getTownId());
        tOrderAddressBean.setName(orderParams.getName());
        tOrderAddressBean.setPhone(orderParams.getPhone());
        tOrderAddressBean.setDetail(orderParams.getDetail());
        tOrderAddressBean.setCtime(this.getCurrentTime());
        //查询商家余额
        if(!this.checkVenderBalance(orderParams.getVenderId(), totalAgreePrice)){
            resultData.setError(ErrorCode.VENDER_BANANCE_NOT_ENOUGH);
            resultData.setMsg("商户余额不足");
            return resultData;
        }
        //查询库存和上下架信息
        Map<Long, Integer> productCount = new HashMap<>(tOrderProductBeans.size());
        for (Map.Entry<String, Integer> entry: orderParams.getProducts().entrySet()){
            productCount.put(Long.parseLong(entry.getKey()), entry.getValue());
        }
        String address = new StringBuffer().append(orderParams.getProvinceId()).append("_")
                .append(orderParams.getCityId()).append("_")
                .append(orderParams.getCountyId()).append("_")
                .append(orderParams.getTownId()).toString();
        List<Long> stockSkus= orderService.checkStockAndOnline(address, orderParams.getVenderId(), productCount);
        if(stockSkus == null){
            resultData.setError(ErrorCode.SERVER_EXCEPTION);
            resultData.setMsg("查询库存信息失败");
            return resultData;
        }else if(!stockSkus.isEmpty()){
            List<String> errorMsg = new ArrayList<>(stockSkus.size());
            for(TOrderProductBean tOrderProductBean :tOrderProductBeans){
                if(stockSkus.contains(tOrderProductBean.getProduct_id())) {
                    errorMsg.add(tOrderProductBean.getName());
                }
            }
            resultData.setError(ErrorCode.STOCK_NOT_ENOUGH);
            resultData.setMsg("商品库存不足:"+String.join(";", errorMsg));
            return resultData;
        }
        //向京东下单
        List<SubmitOrderRequestParams.SubmitOrderSkuVo> submitOrderSkuVos = this.createJdOrderSkus(tOrderProductBeans);
        SubmitOrderRequestParams submitOrderRequestParams = this.createJdOrderParams( tOrderBean, tOrderAddressBean, submitOrderSkuVos);
        ResultData<OrderRepVo2> resultData2 = orderService.orderToJdService(submitOrderRequestParams);
        if(0 != resultData2.getError()){
            return resultData2;
        }
        OrderRepVo2 orderRepVo2 = resultData2.getData();
        List<SkuVo> skuVos = orderRepVo2.getSku();
        Map<Long, Integer> skuTax = new HashMap<>(skuVos.size());
        for(SkuVo skuVo:skuVos){
            skuTax.put(Long.parseLong(skuVo.getSkuId()), Integer.parseInt(skuVo.getTax()));
        }
        for (TOrderProductBean tOrderProductBean: tOrderProductBeans){
            tOrderProductBean.setTaxrate(skuTax.get(tOrderProductBean.getProduct_id()));
        }
        //获取运费
        GetFreightRequestParams getFreightRequestParams = new GetFreightRequestParams();
        getFreightRequestParams.setProvinceId(orderParams.getProvinceId());
        getFreightRequestParams.setCityId(orderParams.getCityId());
        getFreightRequestParams.setCountyId(orderParams.getCountyId());
        getFreightRequestParams.setTownId(orderParams.getTownId());
        getFreightRequestParams.setProductIds(orderParams.getProducts());
        ResultData<FreightDetailBean> venderFreight =  orderService.getVenderFreight(orderParams.getVenderId(), getFreightRequestParams);
        if(null == venderFreight || ErrorCode.SUCCESS != venderFreight.getError() || null == venderFreight.getData()){
            resultData.setError(ErrorCode.SERVER_EXCEPTION);
            resultData.setMsg("获取运费失败");
            return resultData;
        }
        tOrderBean.setFreight(venderFreight.getData().getFreight());
        tOrderBean.setJd_freight(orderRepVo2.getFreight());
        tOrderBean.setTrade_no(orderRepVo2.getJdOrderId());
        tOrderBean.setOrder_state(1);
        tOrderBean.setPay_price(new BigDecimal(0));
        //订单信息写入数据库
        if(orderService.createOrder(tOrderBean)<1){
            resultData.setError(ErrorCode.SERVER_EXCEPTION);
            resultData.setMsg("写入数据库失败1");
            return resultData;
        }
        tOrderAddressBean.setOrder_id(tOrderBean.getId());
        if(orderService.addOrderAddress(tOrderAddressBean)<1){
            resultData.setError(ErrorCode.SERVER_EXCEPTION);
            resultData.setMsg("写入数据库失败2");
            return resultData;
        }
        if(orderService.addOrderProduct2(tOrderProductBeans)<1){
            resultData.setError(ErrorCode.SERVER_EXCEPTION);
            resultData.setMsg("写入数据库失败3");
            return resultData;
        }

        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setOrderSn(orderSn);
        orderResponse.setTotalFee(totalPrice.add(tOrderBean.getFreight()));
        orderResponse.setTotalProduct(tOrderProductBeans.size());
        orderResponse.setUserId(orderParams.getUserId());
        orderResponse.setVenderId(orderParams.getVenderId());
        orderResponse.setJdTradeNo(tOrderBean.getTrade_no());
        orderResponse.setFeight(tOrderBean.getFreight());
        orderResponse.setOrderPrice(tOrderBean.getPrice());
        resultData.setData(orderResponse);
        return resultData;
    }


    @RequestMapping(value = Router.ROUTER_CREATE_PAY_QR_CODE, method = RequestMethod.POST)
    public ResultData createQrCode(@Valid QrCodeParams qrCodeParams, BindingResult bindingResult) {
        ResultData<Map<String, String>> resultData = Tools.getThreadResultData();
        if (bindingResult.hasErrors()) {
            resultData.setError(ErrorCode.PARAM_ERROR);
            resultData.setMsg(bindingResult.getAllErrors().get(0).getDefaultMessage());
            return resultData;
        }

        //查询订单信息
        TOrderBean orderBean = orderService.getOrderInfo(qrCodeParams.getOrderSn());
        resultData = qrCodeService.checkOrder(orderBean, qrCodeParams.getUserId(), qrCodeParams.getVenderId());
        if(0 != resultData.getError()){
            return resultData;
        }
        //查询商户信息
        List<VenderPayment> vps = qrCodeService.getVenderAllPayment(qrCodeParams.getVenderId());
        if(null == vps || vps.isEmpty()){
            resultData.setError(10011);
            resultData.setMsg("商户不提供此种支付方式");
            return resultData;
        }
        Map<String, String> qrCodeUrl= qrCodeService.createPayQrCode(orderBean, vps);
        if(null ==qrCodeUrl || qrCodeUrl.isEmpty()){
            resultData.setError(10012);
            resultData.setMsg("生成二维码失败");
            return resultData;
        }
        //生成二维码
        resultData.setData(qrCodeUrl);
        return resultData;
    }

    @RequestMapping(value = Router.ROUTER_CREATE_PAY_MUTIPLE_QRCODE, method = RequestMethod.POST)
    public ResultData mutiplePay(@Valid MutiplePayParams payParams, BindingResult bindingResult) {
        ResultData<Map<String, String>> resultData = Tools.getThreadResultData();
        if (bindingResult.hasErrors()) {
            resultData.setError(ErrorCode.PARAM_ERROR);
            resultData.setMsg(bindingResult.getAllErrors().get(0).getDefaultMessage());
            return resultData;
        }
        //查询订单信息
        TOrderBean orderBean = orderService.getOrderInfo(payParams.getOrderSn());
        resultData = qrCodeService.checkOrder(orderBean, payParams.getUserId(), payParams.getVenderId());
        if(0 != resultData.getError()){
            return resultData;
        }
        if(null == payParams.getCardNo() || payParams.getCardNo().isEmpty()){//纯二维码支付
            List<VenderPayment> vps = qrCodeService.getVenderAllPayment(payParams.getVenderId());
            if(null == vps || vps.isEmpty()){
                resultData.setError(10011);
                resultData.setMsg("商户不提供此种支付方式");
                return resultData;
            }
            Map<String, String> qrCodeUrl= qrCodeService.createPayQrCode(orderBean, vps);
            if(null ==qrCodeUrl || qrCodeUrl.isEmpty()){
                resultData.setError(10012);
                resultData.setMsg("生成二维码失败");
                return resultData;
            }
            resultData.setData(qrCodeUrl);
        }else {// 混合支付(充值卡和二维码)
            resultData = cardService.useCard(payParams.getCardNo(), orderBean);
        }
        return resultData;
    }

    /**
     * 判断用户扫码支付的价格是否等于需要支付的价格
     * 如果扫码支付的价格等于需要支付的价格,则完成
     * 如果扫码支付的价格不等于需要支付的价格,则查看是否有购物卡参数支付,
     *   如果无,则支付失败退款,
     *   如果是,则扣购物卡的钱
     *     如果扣款成功,则支付成功
     *     如果扣款失败,则支付失败退款
     * */
    @RequestMapping(value = {Router.ROUTER_NOTIFY_NEW}, method = RequestMethod.POST)
    public ResultData payNotifyNew(PayNotifyUrlParams payNotifyUrlParams) {
        return orderService.paySuccessNotify(payNotifyUrlParams);
    }

    @RequestMapping(value = {VenderRouter.ROUTER_VENDER_NOTIFY}, method = RequestMethod.POST)
    public ResultData venderNotify(@Valid VenderOrderNotifyParams venderOrderNotifyParams,
                                   BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            ResultData resultData  = Tools.getThreadResultData();
            resultData.setError(ErrorCode.PARAM_ERROR);
            resultData.setMsg(bindingResult.getAllErrors().get(0).getDefaultMessage());
            return resultData;
        }
        return orderService.venderOrderNotify(venderOrderNotifyParams);
    }

    @RequestMapping(value = Router.ROUTER_SCAN, method = RequestMethod.GET)
    public ResultData scanQrCode(@RequestParam("orderSn")String orderSn){
        ResultData resultData = Tools.getThreadResultData();
        TOrderBean tOrderBean = orderService.getOrderInfo(orderSn);
        if(null == tOrderBean){
            resultData.setError(ErrorCode.NO_ORDER);
            resultData.setMsg("没有查找到对应订单");
            return resultData;
        }
        Map<String, Integer> scan = new HashMap<>(1);
        scan.put("result", tOrderBean.getPay_status());
        resultData.setData(scan);
        return resultData;
    }

    @RequestMapping(value = VenderRouter.ROUTER_VENDER_BALANCE, method = RequestMethod.GET)
    public ResultData getVenderBalance(@RequestParam("venderId")int venderId){
        ResultData resultData = Tools.getThreadResultData();
        int type  = orderService.getVenderPayType(venderId);
        if(2 == type){
            BigDecimal venderBalance = orderService.getVenderBalance(venderId);
            Map<String, Object> map = new HashedMap(2);
            map.put("venderId", venderId);
            map.put("balance", venderBalance.floatValue());
            resultData.setData(map);
        }else if(-1 == type){
            resultData.setError(ErrorCode.SERVER_EXCEPTION);
            resultData.setMsg("查询失败，请重试");
        }else {
            resultData.setError(ErrorCode.COOPERATION_MODE_ERROR);
            resultData.setMsg("当前合作模式不支持查询商户预存款");
        }
        return resultData;
    }

    private String getCurrentTime(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return  simpleDateFormat.format(new Date());
    }

    private SubmitOrderRequestParams createJdOrderParams(TOrderBean tOrderBean, TOrderAddressBean tOrderAddressBean, List<SubmitOrderRequestParams.SubmitOrderSkuVo> submitOrderSkuVos){
        SubmitOrderRequestParams submitOrderRequestParams = new SubmitOrderRequestParams();
        submitOrderRequestParams.setThirdOrder(tOrderBean.getOrder_sn());
        submitOrderRequestParams.setSku(submitOrderSkuVos);
        submitOrderRequestParams.setName(tOrderAddressBean.getName());
        submitOrderRequestParams.setProvince(String.valueOf(tOrderAddressBean.getProvince_id()));
        submitOrderRequestParams.setCity(String.valueOf(tOrderAddressBean.getCity_id()));
        submitOrderRequestParams.setCounty(String.valueOf(tOrderAddressBean.getCounty_id()));
        submitOrderRequestParams.setTown(String.valueOf(tOrderAddressBean.getTown_id()));
        submitOrderRequestParams.setAddress(tOrderAddressBean.getDetail());
        submitOrderRequestParams.setZip("");
        submitOrderRequestParams.setPhone(tOrderAddressBean.getPhone());
        submitOrderRequestParams.setMobile(tOrderAddressBean.getPhone());
        submitOrderRequestParams.setEmail(notifyEmail);
        submitOrderRequestParams.setUnpl("");
        submitOrderRequestParams.setRemark("");
        submitOrderRequestParams.setInvoiceState("2");
        submitOrderRequestParams.setInvoiceType("2");
        submitOrderRequestParams.setInvoiceName("");
        submitOrderRequestParams.setInvoicePhone("");
        submitOrderRequestParams.setInvoiceProvice("");
        submitOrderRequestParams.setInvoiceCity("");
        submitOrderRequestParams.setInvoiceCounty("");
        submitOrderRequestParams.setInvoiceTown("");
        submitOrderRequestParams.setInvoiceAddress("");
        submitOrderRequestParams.setRegCompanyName("");
        submitOrderRequestParams.setRegCode("");
        submitOrderRequestParams.setRegPhone("");
        submitOrderRequestParams.setRegAddr("");
        submitOrderRequestParams.setRegBank("");
        submitOrderRequestParams.setRegBankAccount("");
        submitOrderRequestParams.setSelectedInvoiceTitle("");
        submitOrderRequestParams.setCompanyName("");
        submitOrderRequestParams.setInvoiceContent("");
        submitOrderRequestParams.setPaymentType("4");
        submitOrderRequestParams.setIsUseBalance("1");
        submitOrderRequestParams.setSubmitState("0");
        submitOrderRequestParams.setDoOrderPriceMode("");
        submitOrderRequestParams.setOrderPriceSnap("");
        return submitOrderRequestParams;
    }

    private List<SubmitOrderRequestParams.SubmitOrderSkuVo> createJdOrderSkus(List<TOrderProductBean> orderProductBeans){
        Map<Long, Integer> productCount = new HashMap<>();
        for(TOrderProductBean tOrderProductBean: orderProductBeans){
            if (productCount.containsKey(tOrderProductBean.getProduct_id())){
                productCount.put(tOrderProductBean.getProduct_id(), productCount.get(tOrderProductBean.getProduct_id())+tOrderProductBean.getCount());
            }else {
                productCount.put(tOrderProductBean.getProduct_id(), tOrderProductBean.getCount());
            }
        }
        SubmitOrderRequestParams submitOrderRequestParams = new SubmitOrderRequestParams();
        List<SubmitOrderRequestParams.SubmitOrderSkuVo> submitOrderSkuVos = new ArrayList<>(productCount.size());
        for (Map.Entry<Long, Integer> entry: productCount.entrySet()){
            SubmitOrderRequestParams.SubmitOrderSkuVo sku = submitOrderRequestParams.new SubmitOrderSkuVo();
            sku.setNum(String.valueOf(entry.getValue()));
            sku.setSkuId(String.valueOf(entry.getKey()));
            submitOrderSkuVos.add(sku);
        }
        return submitOrderSkuVos;
    }

    private Date stringToDate(String strDate){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        try {
            date = formatter.parse(strDate);
        } catch (ParseException e) {
                logger.error(e.getMessage());
        }
        return date;
    }

    //判断商户余额是否支持此单
    private boolean checkVenderBalance(int venderId, BigDecimal totalPrice){
        //结算方式 0实时 1按月 2预付款
        int type  = orderService.getVenderPayType(venderId);
        if(2 == type){
            BigDecimal venderBalance = orderService.getVenderBalance(venderId);
            if(totalPrice.compareTo(venderBalance) > 0){
                return false;
            }
        }
        return true;
    }

    @PostMapping(Router.V1_BMS_FLASHSALE_ORDER_CREATE)
    public ResultData flashCreateOrder(@RequestBody @Valid FlashSaleOrderParams orderParams, BindingResult bindingResult){
        ResultData<OrderResponse> resultData = Tools.getThreadResultData();
        if(bindingResult.hasErrors()){
            resultData.setError(ErrorCode.PARAM_ERROR);
            resultData.setMsg(bindingResult.getAllErrors().get(0).getDefaultMessage());
            return resultData;
        }
        if(null == orderParams.getProductIds() && null == orderParams.getProducts()){
            resultData.setError(ErrorCode.PARAM_ERROR);
            resultData.setMsg("参数错误");
            return resultData;
        }
        //获取商品价格
        Map<Long, ProductPriceBean> map = productService.getProductPrice(orderParams.getVenderId() , new ArrayList<>(orderParams.getProducts().keySet()));
        if(null == map){
            resultData.setError(ErrorCode.NO_PRODUCT);
            resultData.setMsg("没有查找到此商品");
            return resultData;
        }
        List<TOrderProductBean> tOrderProductBeans = new ArrayList<>(orderParams.getProducts().size());
        String orderSn = orderService.createOrderSn();
        BigDecimal totalAgreePrice = new BigDecimal(0);
        BigDecimal totalPrice = new BigDecimal(0);
        for (Map.Entry<String, Integer> entry: orderParams.getProducts().entrySet()){
            String productId = entry.getKey();
            int productSize = entry.getValue();
            TOrderProductBean tOrderProductBean = new TOrderProductBean();
            ProductPriceBean productPriceBean = map.get(Long.parseLong(productId));
            if(null==productPriceBean.getAgree_price() || null==productPriceBean.getPrice() || null==productPriceBean.getName()){
                resultData.setError(ErrorCode.STOCK_NOT_ENOUGH);
                resultData.setMsg(productPriceBean.getName()+" 此商品已下架");
                return resultData;
            }
            tOrderProductBean.setOrder_sn(orderSn);
            tOrderProductBean.setChild_trade_no("");
            tOrderProductBean.setProduct_id(Long.parseLong(productId));
            tOrderProductBean.setName(productPriceBean.getName());
            tOrderProductBean.setAgree_price(productPriceBean.getAgree_price());
            tOrderProductBean.setPrice(orderParams.getPrice());
            tOrderProductBean.setPay_price(new BigDecimal(0));
            tOrderProductBean.setCount(productSize);
            tOrderProductBean.setTaxrate(0);
            tOrderProductBean.setOrder_state(1);
            tOrderProductBean.setCtime(this.getCurrentTime());
            tOrderProductBean.setUtime(this.getCurrentTime());
            tOrderProductBeans.add(tOrderProductBean);
            totalPrice = totalPrice.add(tOrderProductBean.getPrice().multiply(new BigDecimal(productSize)));
            totalAgreePrice = totalAgreePrice.add(tOrderProductBean.getAgree_price().multiply(new BigDecimal(productSize)));
        }
        TOrderBean tOrderBean = new TOrderBean();
        tOrderBean.setAgree_price(totalAgreePrice);
        tOrderBean.setUser_id(orderParams.getUserId());
        tOrderBean.setVenderid(orderParams.getVenderId());
        tOrderBean.setOrder_sn(orderSn);
        tOrderBean.setCtime(new Date());
        tOrderBean.setPrice(totalPrice);
        tOrderBean.setInvoice(orderParams.getInvoice());
        TOrderAddressBean tOrderAddressBean = new TOrderAddressBean();
        tOrderAddressBean.setOrder_id(tOrderBean.getId());
        tOrderAddressBean.setProvince_id(orderParams.getProvinceId());
        tOrderAddressBean.setCity_id(orderParams.getCityId());
        tOrderAddressBean.setCounty_id(orderParams.getCountyId());
        tOrderAddressBean.setTown_id(orderParams.getTownId());
        tOrderAddressBean.setName(orderParams.getName());
        tOrderAddressBean.setPhone(orderParams.getPhone());
        tOrderAddressBean.setDetail(orderParams.getDetail());
        tOrderAddressBean.setCtime(this.getCurrentTime());
        //查询商家余额
        if(!this.checkVenderBalance(orderParams.getVenderId(), totalAgreePrice)){
            resultData.setError(ErrorCode.VENDER_BANANCE_NOT_ENOUGH);
            resultData.setMsg("商户余额不足");
            return resultData;
        }
        //查询库存信息
        Map<Long, Integer> productCount = new HashMap<>(tOrderProductBeans.size());
        for (Map.Entry<String, Integer> entry: orderParams.getProducts().entrySet()){
            productCount.put(Long.parseLong(entry.getKey()), entry.getValue());
        }
        String address = new StringBuffer().append(orderParams.getProvinceId()).append("_")
                .append(orderParams.getCityId()).append("_")
                .append(orderParams.getCountyId()).append("_")
                .append(orderParams.getTownId()).toString();
        int stockState = orderService.checkProductStock(address, productCount);
        if(-1 == stockState){
            resultData.setError(ErrorCode.SERVER_EXCEPTION);
            resultData.setMsg("jdservice服务器异常");
            return resultData;
        }else if(stockState>1){
            resultData.setError(ErrorCode.STOCK_NOT_ENOUGH);
            resultData.setMsg("商品库存不足");
            return resultData;
        }
        //向京东下单
        List<SubmitOrderRequestParams.SubmitOrderSkuVo> submitOrderSkuVos = this.createJdOrderSkus(tOrderProductBeans);
        SubmitOrderRequestParams submitOrderRequestParams = this.createJdOrderParams( tOrderBean, tOrderAddressBean, submitOrderSkuVos);
        ResultData<OrderRepVo2> resultData2 = orderService.orderToJdService(submitOrderRequestParams);
        if(0 != resultData2.getError()){
            return resultData2;
        }
        OrderRepVo2 orderRepVo2 = resultData2.getData();
        List<SkuVo> skuVos = orderRepVo2.getSku();
        Map<Integer, Integer> skuTax = new HashMap<>(skuVos.size());
        for(SkuVo skuVo:skuVos){
            skuTax.put(Integer.parseInt(skuVo.getSkuId()), Integer.parseInt(skuVo.getTax()));
        }
        for (TOrderProductBean tOrderProductBean: tOrderProductBeans){
            tOrderProductBean.setTaxrate(skuTax.get(tOrderProductBean.getProduct_id()));
        }
        tOrderBean.setFreight(orderRepVo2.getFreight());
        tOrderBean.setTrade_no(orderRepVo2.getJdOrderId());
        tOrderBean.setOrder_state(1);
        tOrderBean.setPay_price(new BigDecimal(0));
        //订单信息写入数据库
        if(orderService.createOrder(tOrderBean)<1){
            resultData.setError(ErrorCode.SERVER_EXCEPTION);
            resultData.setMsg("写入数据库失败1");
            return resultData;
        }
        tOrderAddressBean.setOrder_id(tOrderBean.getId());
        if(orderService.addOrderAddress(tOrderAddressBean)<1){
            resultData.setError(ErrorCode.SERVER_EXCEPTION);
            resultData.setMsg("写入数据库失败2");
            return resultData;
        }
        if(orderService.addOrderProduct(tOrderProductBeans)<1){
            resultData.setError(ErrorCode.SERVER_EXCEPTION);
            resultData.setMsg("写入数据库失败3");
            return resultData;
        }

        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setOrderSn(orderSn);
        orderResponse.setTotalFee(totalPrice.add(tOrderBean.getFreight()));
        orderResponse.setTotalProduct(tOrderProductBeans.size());
        orderResponse.setUserId(orderParams.getUserId());
        orderResponse.setVenderId(orderParams.getVenderId());
        orderResponse.setJdTradeNo(tOrderBean.getTrade_no());
        orderResponse.setFeight(tOrderBean.getFreight());
        orderResponse.setOrderPrice(tOrderBean.getPrice());
        resultData.setData(orderResponse);
        return resultData;
    }

    @RequestMapping(value = Router.ROUTER_CREATE_PAY_MUTIPLE_AUTH, method = RequestMethod.POST)
    public ResultData mutiplePayAuth(@Valid MutiplePayParams payParams, BindingResult bindingResult) {
        ResultData<Map<String, String>> resultData = Tools.getThreadResultData();
        if (bindingResult.hasErrors()) {
            resultData.setError(ErrorCode.PARAM_ERROR);
            resultData.setMsg(bindingResult.getAllErrors().get(0).getDefaultMessage());
            return resultData;
        }
        //查询订单信息
        TOrderBean orderBean = orderService.getOrderInfo(payParams.getOrderSn());
        resultData = qrCodeService.checkOrder(orderBean, payParams.getUserId(), payParams.getVenderId());
        if(0 != resultData.getError()){
            return resultData;
        }
        if(3 == payParams.getPayType()) {//小程序
            if (null == payParams.getUserCode() || payParams.getUserCode().isEmpty()) {
                resultData.setError(ErrorCode.PARAM_ERROR);
                resultData.setMsg("小程序支付请传入用户code");
                return resultData;
            }
            WxCode2SessionBean res = qrCodeService.getWxUserInfoByUserCode(payParams.getUserCode(), payParams.getVenderId());
            if (null == res || res.getErrcode() != 0) {
                resultData.setError(ErrorCode.USER_NOT_EXIST);
                resultData.setMsg("小程序code无法换取用户");
                return resultData;
            } else {
                payParams.setUserCode(res.getOpenid());
            }
        }
        if(null == payParams.getCardNo() || payParams.getCardNo().isEmpty()){//纯二维码支付
            VenderPayment vps = null;
            if(3 == payParams.getPayType()){//小程序
                vps = qrCodeService.getWechatAppletPayment(payParams.getVenderId());
            }else {
                vps = qrCodeService.getVenderPayment(payParams.getVenderId(), payParams.getPayType());
            }
            if(null == vps){
                resultData.setError(10011);
                resultData.setMsg("商户不提供此种支付方式");
                return resultData;
            }
            Map<String, String> info= qrCodeService.createPayAuth(orderBean, vps, payParams);
            if(null ==info || info.isEmpty()){
                resultData.setError(10012);
                resultData.setMsg("向PMS下单失败");
                return resultData;
            }
            resultData.setData(info);
        }else {// 混合支付(充值卡和二维码)
            resultData = cardService.useCard(payParams.getCardNo(), orderBean, payParams.getPayType(), payParams);
        }
        return resultData;
    }


    @RequestMapping(value = {VenderRouter.ROUTER_VENDER_ORDER}, method = RequestMethod.POST)
    public ResultData venderOrder(@RequestAttribute(value = "userId") Integer userId,
                            @RequestParam("venderId") int venderId,
                            @RequestBody @Valid OrderParams orderParams,
                            BindingResult bindingResult){
        ResultData resultData = Tools.getThreadResultData();
        if(bindingResult.hasErrors()){
            resultData.setError(ErrorCode.PARAM_ERROR);
            resultData.setMsg(bindingResult.getAllErrors().get(0).getDefaultMessage());
            return resultData;
        }
        orderParams.setUserId(userId);
        orderParams.setVenderId(venderId);
        if(null == orderParams.getProductIds() && null == orderParams.getProducts()){
            resultData.setError(ErrorCode.PARAM_ERROR);
            resultData.setMsg("参数错误:请传入正确的products");
            return resultData;
        }
        if(orderParams.getProducts().keySet().size()>maxProductType || orderParams.getProducts().size()<1){
            resultData.setError(ErrorCode.PARAM_ERROR);
            resultData.setMsg("抱歉，无法再添加，最多50种商品");
            return resultData;
        }
        //获取商品价格
        Map<Long, ProductPriceBean> map = productService.getProductPrice(orderParams.getVenderId() , new ArrayList<>(orderParams.getProducts().keySet()));
        if(null == map){
            resultData.setError(ErrorCode.NO_PRODUCT);
            resultData.setMsg("没有查找到此商品");
            return resultData;
        }
        List<TOrderProductBean> tOrderProductBeans = new ArrayList<>(orderParams.getProducts().size());
        String orderSn = orderService.createOrderSn();
        BigDecimal totalAgreePrice = new BigDecimal(0);
        BigDecimal totalPrice = new BigDecimal(0);
        for (Map.Entry<String, Integer> entry: orderParams.getProducts().entrySet()){
            String productId = entry.getKey();
            int productSize = entry.getValue();
            TOrderProductBean tOrderProductBean = new TOrderProductBean();
            ProductPriceBean productPriceBean = map.get(Long.parseLong(productId));
            if(null==productPriceBean.getAgree_price() || null==productPriceBean.getPrice() || null==productPriceBean.getName()){
                resultData.setError(ErrorCode.STOCK_NOT_ENOUGH);
                resultData.setMsg(productPriceBean.getName()+" 此商品已下架");
                return resultData;
            }
            tOrderProductBean.setChild_trade_no("");
            tOrderProductBean.setOrder_sn(orderSn);
            tOrderProductBean.setProduct_id(Long.parseLong(productId));
            tOrderProductBean.setName(productPriceBean.getName());
            tOrderProductBean.setAgree_price(productPriceBean.getAgree_price());
            tOrderProductBean.setPrice(productPriceBean.getPrice());
            tOrderProductBean.setPay_price(new BigDecimal(0));
            tOrderProductBean.setCount(productSize);
            tOrderProductBean.setTaxrate(0);
            tOrderProductBean.setOrder_state(1);
            tOrderProductBean.setCtime(this.getCurrentTime());
            tOrderProductBean.setUtime(this.getCurrentTime());
            tOrderProductBeans.add(tOrderProductBean);
            totalPrice = totalPrice.add(tOrderProductBean.getPrice().multiply(new BigDecimal(productSize)));
            totalAgreePrice = totalAgreePrice.add(tOrderProductBean.getAgree_price().multiply(new BigDecimal(productSize)));
        }
        TOrderBean tOrderBean = new TOrderBean();
        tOrderBean.setAgree_price(totalAgreePrice);
        tOrderBean.setUser_id(orderParams.getUserId());
        tOrderBean.setVenderid(orderParams.getVenderId());
        tOrderBean.setOrder_sn(orderSn);
        tOrderBean.setCtime(new Date());
        tOrderBean.setPrice(totalPrice);
        tOrderBean.setInvoice(orderParams.getInvoice());
        TOrderAddressBean tOrderAddressBean = new TOrderAddressBean();
        tOrderAddressBean.setOrder_id(tOrderBean.getId());
        tOrderAddressBean.setProvince_id(orderParams.getProvinceId());
        tOrderAddressBean.setCity_id(orderParams.getCityId());
        tOrderAddressBean.setCounty_id(orderParams.getCountyId());
        tOrderAddressBean.setTown_id(orderParams.getTownId());
        tOrderAddressBean.setName(orderParams.getName());
        tOrderAddressBean.setPhone(orderParams.getPhone());
        tOrderAddressBean.setDetail(orderParams.getDetail());
        tOrderAddressBean.setCtime(this.getCurrentTime());
        //查询商家余额
        if(!this.checkVenderBalance(orderParams.getVenderId(), totalAgreePrice)){
            resultData.setError(ErrorCode.VENDER_BANANCE_NOT_ENOUGH);
            resultData.setMsg("商户余额不足");
            return resultData;
        }
        //查询库存和上下架信息
        Map<Long, Integer> productCount = new HashMap<>(tOrderProductBeans.size());
        for (Map.Entry<String, Integer> entry: orderParams.getProducts().entrySet()){
            productCount.put(Long.parseLong(entry.getKey()), entry.getValue());
        }
        String address = new StringBuffer().append(orderParams.getProvinceId()).append("_")
                .append(orderParams.getCityId()).append("_")
                .append(orderParams.getCountyId()).append("_")
                .append(orderParams.getTownId()).toString();
        List<Long> stockSkus= orderService.checkStockAndOnline(address, orderParams.getVenderId(), productCount);
        if(stockSkus == null){
            resultData.setError(ErrorCode.SERVER_EXCEPTION);
            resultData.setMsg("查询库存信息失败");
            return resultData;
        }else if(!stockSkus.isEmpty()){
            List<String> errorMsg = new ArrayList<>(stockSkus.size());
            for(TOrderProductBean tOrderProductBean :tOrderProductBeans){
                if(stockSkus.contains(tOrderProductBean.getProduct_id())) {
                    errorMsg.add(tOrderProductBean.getName());
                }
            }
            resultData.setError(ErrorCode.STOCK_NOT_ENOUGH);
            resultData.setMsg("商品库存不足:"+String.join(";", errorMsg));
            return resultData;
        }
        //向京东下单
        List<SubmitOrderRequestParams.SubmitOrderSkuVo> submitOrderSkuVos = this.createJdOrderSkus(tOrderProductBeans);
        SubmitOrderRequestParams submitOrderRequestParams = this.createJdOrderParams( tOrderBean, tOrderAddressBean, submitOrderSkuVos);
        ResultData<OrderRepVo2> resultData2 = orderService.orderToJdService(submitOrderRequestParams);
        if(0 != resultData2.getError()){
            return resultData2;
        }
        OrderRepVo2 orderRepVo2 = resultData2.getData();
        List<SkuVo> skuVos = orderRepVo2.getSku();
        Map<Long, Integer> skuTax = new HashMap<>(skuVos.size());
        for(SkuVo skuVo:skuVos){
            skuTax.put(Long.parseLong(skuVo.getSkuId()), Integer.parseInt(skuVo.getTax()));
        }
        for (TOrderProductBean tOrderProductBean: tOrderProductBeans){
            tOrderProductBean.setTaxrate(skuTax.get(tOrderProductBean.getProduct_id()));
        }
        //获取运费
        GetFreightRequestParams getFreightRequestParams = new GetFreightRequestParams();
        getFreightRequestParams.setProvinceId(orderParams.getProvinceId());
        getFreightRequestParams.setCityId(orderParams.getCityId());
        getFreightRequestParams.setCountyId(orderParams.getCountyId());
        getFreightRequestParams.setTownId(orderParams.getTownId());
        getFreightRequestParams.setProductIds(orderParams.getProducts());
        ResultData<FreightDetailBean> venderFreight =  orderService.getVenderFreight(orderParams.getVenderId(), getFreightRequestParams);
        if(null == venderFreight || ErrorCode.SUCCESS != venderFreight.getError() || null == venderFreight.getData()){
            resultData.setError(ErrorCode.SERVER_EXCEPTION);
            resultData.setMsg("获取运费失败");
            return resultData;
        }
        tOrderBean.setJd_freight(orderRepVo2.getFreight());
        tOrderBean.setFreight(venderFreight.getData().getFreight());
        tOrderBean.setTrade_no(orderRepVo2.getJdOrderId());
        tOrderBean.setOrder_state(1);
        tOrderBean.setPay_price(new BigDecimal(0));
        //订单信息写入数据库
        if(orderService.createOrder(tOrderBean)<1){
            resultData.setError(ErrorCode.SERVER_EXCEPTION);
            resultData.setMsg("写入数据库失败1");
            return resultData;
        }
        tOrderAddressBean.setOrder_id(tOrderBean.getId());
        if(orderService.addOrderAddress(tOrderAddressBean)<1){
            resultData.setError(ErrorCode.SERVER_EXCEPTION);
            resultData.setMsg("写入数据库失败2");
            return resultData;
        }
        if(orderService.addOrderProduct2(tOrderProductBeans)<1){
            resultData.setError(ErrorCode.SERVER_EXCEPTION);
            resultData.setMsg("写入数据库失败3");
            return resultData;
        }

        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setOrderSn(orderSn);
        orderResponse.setTotalFee(totalPrice.add(tOrderBean.getFreight()));
        orderResponse.setTotalProduct(tOrderProductBeans.size());
        orderResponse.setUserId(orderParams.getUserId());
        orderResponse.setVenderId(orderParams.getVenderId());
        orderResponse.setJdTradeNo(tOrderBean.getTrade_no());
        orderResponse.setFeight(tOrderBean.getFreight());
        orderResponse.setOrderPrice(tOrderBean.getPrice());
        resultData.setData(orderResponse);
        return resultData;
    }

}
