package com.hqtc.bms.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.hqtc.bms.model.database.TOrderAddressBean;
import com.hqtc.bms.model.database.TOrderBean;
import com.hqtc.bms.model.database.TOrderProductBean;
import com.hqtc.bms.model.mapper.TOrderAddressMapper;
import com.hqtc.bms.model.mapper.TOrderMapper;
import com.hqtc.bms.model.mapper.TOrderProductMapper;
import com.hqtc.bms.model.params.*;
import com.hqtc.bms.model.response.OrderListResponse;
import com.hqtc.bms.model.response.OrderRefundVo;
import com.hqtc.bms.model.rpc.*;
import com.hqtc.bms.service.AsyncTaskService;
import com.hqtc.bms.service.CardService;
import com.hqtc.bms.service.ProductService;
import com.hqtc.bms.service.RefundService;
import com.hqtc.bms.service.rpc.RPCImsService;
import com.hqtc.bms.service.rpc.RPCJDService;
import com.hqtc.bms.service.rpc.RPCOmsService;
import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.ResultData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by wanghaoyang on 18-7-4.
 */
@Service("OrderServiceImpl")
public class OrderServiceImpl extends AbstractOrderServiceImpl {
    private Logger logger = LoggerFactory.getLogger("OrderServiceImpl");

    @Autowired
    private TOrderMapper tOrderMapper;

    @Autowired
    private TOrderProductMapper tOrderProductMapper;

    @Autowired
    private RPCImsService imsService;

    @Autowired
    private RPCJDService rpcjdService;

    @Autowired
    private TOrderAddressMapper tOrderAddressMapper;

    @Autowired
    private AsyncTaskService asyncTaskService;

    @Autowired
    private ProductService productService;

    @Autowired
    private RPCOmsService rpcOmsService;

    @Autowired
    private RefundService refundService;

    @Value("${order.vender.defaultFreight}")
    private BigDecimal defaultFreight;

    @Autowired
    private CardService cardService;

    @Autowired
    private RPCImsService rpcImsService;

    @Value("${order.toJD}")
    private boolean isOrderToJD;

    @Autowired
    private TerminalOrderErrorCodeNote terminalOrderErrorCodeNote;

    @Override
    public int createOrder(TOrderBean tOrderBean){
        return tOrderMapper.addOrder(tOrderBean);
    }

    @Override
    public int updateFreight(BigDecimal freight, String orderSn){
        return tOrderMapper.updateFreight(freight, orderSn);
    }

    @Override
    public List<OrderManagerVo> orderManagerList(Map<String, Object> params) {
        return tOrderMapper.orderManagerList(params);
    }

    @Override
    public int countManagerOrderList(Map<String, Object> params) {
        return tOrderMapper.countManagerOrderList(params);
    }

    @Override
    public int addOrderProduct(List<TOrderProductBean> tOrderProductBeans){
        for (TOrderProductBean t :tOrderProductBeans){
            tOrderProductMapper.add(t);
        }
        return 1;
    }

    @Override
    public int addOrderProduct2(List<TOrderProductBean> tOrderProductBeans){
        return tOrderProductMapper.batchAddProduct(tOrderProductBeans);
    }

    @Override
    public List<TOrderProductBean> getOrderProduct(List<String> orderSn){
        return tOrderProductMapper.getOrderDetails(String.join(",", orderSn));
    }

    public List<TOrderProductBean> getOrderProduct(String orderSn){
        return tOrderProductMapper.getOrderDetailByOrderSn(orderSn);
    }

    @Override
    public List<TOrderBean> getOrderInfo(List<String> orderSn){
        return tOrderMapper.getOrderInfo(String.join(",", orderSn));
    }

    @Override
    public TOrderBean getOrderInfo(String orderSn){
        return tOrderMapper.getOrderInfoByOrderSn(orderSn);
    }

    @Override
    public TOrderBean getOrderInfoByTradeNo(String tradeNo){
        return tOrderMapper.getOrderInfoByTradeNo(tradeNo);
    }

    @Override
    public int updateOrderStatus(TOrderBean tOrderBean){
        return tOrderMapper.updateOrderStatus(tOrderBean);
    }

    @Override
    public int updateJdOrderStatus(TOrderBean tOrderBean){
        return tOrderMapper.updateJdOrderStatus(tOrderBean);
    }

    @Override
    public AddressBean getReceiverInfo(int id){
        ResultData<AddressBean> resultData = imsService.getAddressById(id);
        if(null == resultData){
            return null;
        }
        return resultData.getData();
    }

    @Override
    public List<TOrderProductBean> getOrderProduct(String orderSn, long sku){
        return tOrderProductMapper.getOrderProductDetails(orderSn, sku);
    }

    @Override
    public int addOrderAddress(TOrderAddressBean tOrderAddressBean){
        return tOrderAddressMapper.insert(tOrderAddressBean);
    }

    @Override
    public TOrderAddressBean findOrderAddress(int orderId){
        return tOrderAddressMapper.findById(orderId);
    }

    @Override
    public ResultData<OrderRepVo2> orderToJdService(SubmitOrderRequestParams submitOrderRequestParams){
        ResultData<OrderRepVo2> resultData = new ResultData();
        SubmitOrderResponseParams submitOrderResponseParams = rpcjdService.submitOrder(submitOrderRequestParams);
        logger.info(submitOrderResponseParams.toString());
        OrderVo orderVo = submitOrderResponseParams.getBiz_order_unite_submit_response();
        if(null == orderVo){
            logger.error("向京东下单失败:"+submitOrderRequestParams.getThirdOrder());
            resultData.setError(ErrorCode.UPPER_ORDER_FAIL);
            resultData.setMsg("jdservice向京东下单失败:"+submitOrderRequestParams.getThirdOrder());
            return resultData;
        }
        if(!"true".equals(orderVo.getSuccess())){
            logger.error("向京东下单失败:"+submitOrderRequestParams.getThirdOrder());
            resultData.setError(ErrorCode.PAY_JD_FAIL);
            resultData.setMsg(this.getJdErrorCodeMessage(orderVo.getResultCode()));
            return resultData;
        }
        resultData.setData(orderVo.getResult());
        return resultData;
    }


    private ResultData checkOrder(TOrderBean orderBean){
        ResultData resultData = new ResultData();
        if(null == orderBean){
            resultData.setError(ErrorCode.NO_ORDER);
            resultData.setMsg("无此订单");
            return resultData;
        }
//        if(0 == orderBean.getPay_status()){
//            resultData.setError(ErrorCode.UN_PAYED);
//            resultData.setMsg("订单未支付");
//            return resultData;
//        }
//        if(1 == orderBean.getOrder_state()){
//            resultData.setError(ErrorCode.ORDER_COMPLATE);
//            resultData.setMsg("此订单已完成");
//            return resultData;
//        }
        return resultData;
    }

    private ResultData checkProduct(List<TOrderProductBean> orderProductBeans){
        ResultData resultData = new ResultData();
        if(null == orderProductBeans || orderProductBeans.isEmpty()){
            resultData.setError(ErrorCode.NO_PRODUCT);
        }
        return resultData;
    }

    @Override
    public List<Map<String, Object>> getProductCount(String orderSn){
        return tOrderProductMapper.getProductCount(orderSn);
    }

    @Override
    public List<Map<String, Object>> getProductsDetail(String orderSn){
        return tOrderProductMapper.getProductCount2(orderSn);
    }

    @Override
    public int updateChildTradeNo(String orderSn, String childTradeNo, List<String> products){
        return tOrderProductMapper.updateChildTradeNo(orderSn, childTradeNo, String.join(",", products));
    }

    @Override
    public String getOrderAddress(TOrderAddressBean orderAddressBean){
        ResultData<CommonAddressBean> resultData = imsService.getCommonAddressById(orderAddressBean.getTown_id(),
                orderAddressBean.getCounty_id(), orderAddressBean.getCity_id(), orderAddressBean.getProvince_id());
        if(null == resultData || 0!=resultData.getError()){
            return "";
        }
        CommonAddressBean commonAddressBean = new CommonAddressBean();
        commonAddressBean = resultData.getData();
        return new StringBuffer().append(commonAddressBean.getProvinceName()).append(commonAddressBean.getCityName())
                .append(commonAddressBean.getCountyName()).append(commonAddressBean.getTownName())
                .append(orderAddressBean.getDetail()).toString();
    }

    @Override
    public Object getJdOrderDetail(String tradeNo){
        //TODO
        return null;
    }

    @Override
    public OrderPayResponseParams orderToJd(String jdTradeNo){
        OrderPayRequestParams requestParams = new OrderPayRequestParams();
        requestParams.setJdOrderId(jdTradeNo);
        return rpcjdService.orderPay(requestParams);
    }

    @Override
    public String getJdOrderInfo(String jdTradeNo){
        OrderRequestParams orderRequestParams = new OrderRequestParams();
        orderRequestParams.setJdOrderId(jdTradeNo);
        orderRequestParams.setQueryExts("jdOrderState");
        return rpcjdService.getOrderInfo(orderRequestParams);
    }

    @Override
    public RPCConfirmOccupyStockResponseParams jdOrderStock(String jdTradeNo){
        ConfirmOccupyStockRequestParams confirmOccupyStockRequestParams = new ConfirmOccupyStockRequestParams();
        confirmOccupyStockRequestParams.setJdOrderId(jdTradeNo);
        return rpcjdService.confirmOccupyStock(confirmOccupyStockRequestParams);
    }

    @Override
    public List<OrderListResponse> getUserOrder(int userId, int start, int size, int orderState){
        List<TOrderBean> tOrderBeans = new ArrayList<>(size);
        if(0 == orderState){
            tOrderBeans = tOrderMapper.findAllOrderByUserId(userId, start , size);
        }else if(1 == orderState || 2 == orderState) {
            List<String> states = new ArrayList<>();
            states.add("1");
            states.add("2");
            tOrderBeans = tOrderMapper.findStateOrderByUserId(userId, String.join(",", states), start , size);
        }else {
            List<String> states = new ArrayList<>();
            states.add(String.valueOf(orderState));
            tOrderBeans = tOrderMapper.findStateOrderByUserId(userId, String.join(",", states), start , size);
        }
        if(null == tOrderBeans || tOrderBeans.isEmpty()){
            return new ArrayList<>(0);
        }
        List<OrderListResponse> orderListResponses = new ArrayList<>();
        List<String> orderUnReceive = new ArrayList<>();
        for(TOrderBean orderBean: tOrderBeans){
            OrderListResponse orderListResponse = new OrderListResponse();
            orderListResponse.setOrder_sn(orderBean.getOrder_sn());
            orderListResponse.setId(orderBean.getId());
            orderListResponse.setVenderid(orderBean.getVenderid());
            orderListResponse.setOrder_sn(orderBean.getOrder_sn());
            orderListResponse.setTrade_no(orderBean.getTrade_no());
            orderListResponse.setPrice(orderBean.getPrice());
            orderListResponse.setPay_price(orderBean.getPay_price());
            orderListResponse.setAgree_price(orderBean.getAgree_price());
            orderListResponse.setPay_status(orderBean.getPay_status());
            orderListResponse.setOrder_state(orderBean.getOrder_state());
            orderListResponse.setCtime(orderBean.getCtime());
            orderListResponse.setUtime(orderBean.getUtime());
            orderListResponse.setFreight(orderBean.getFreight());
            orderListResponse.setPay_order_sn(orderBean.getPay_order_sn());
            orderListResponse.setUser_id(orderBean.getUser_id());
            TOrderAddressBean tOrderAddressBean = this.findOrderAddress(orderBean.getId());
            if(null != tOrderAddressBean){
                orderListResponse.setName(tOrderAddressBean.getName());
            }
            List<Map<String, Object>> tOrderProductBeans = this.getProductCount(orderBean.getOrder_sn());
            List<String> productIds = new ArrayList<>(tOrderProductBeans.size());
            for(Map<String, Object> map: tOrderProductBeans){
                productIds.add(String.valueOf(map.get("product_id")));
            }
            Map<Long, String> productImage1 = this.getProductImage(productIds);
            if(null != productImage1) {
                for(Map<String, Object> map: tOrderProductBeans){
                    map.put("imagePath", productImage1.getOrDefault(Long.parseLong(map.get("product_id").toString()), ""));
                }
            }
            orderListResponse.setProduct(tOrderProductBeans);
            orderListResponses.add(orderListResponse);
            if(3 == orderBean.getOrder_state()){
//                orderUnReceive.add(orderBean.getTrade_no());
                orderUnReceive.add(orderBean.getOrder_sn());
            }
        }
        asyncTaskService.syncOrderState(orderUnReceive);
        return orderListResponses;
    }

    @Override
    public int updateProductTaxRate(List<Map<Integer, Integer>> maps){
        //TODO wanghaoyang
        return 0;
    }

    @Override
    public Map<Long, String> getProductImage(List<String> productIds){
        List<ProductsOriginInfoBean> productInfos = productService.getOriginProductsInfo(productIds);
        if(null != productInfos) {
            Map<Long, String> productImage = new HashMap<>();
            for (ProductsOriginInfoBean productsOriginInfoBean : productInfos){
                productImage.put(productsOriginInfoBean.getSku(), productsOriginInfoBean.getImagePath());
            }
            return productImage;
        }
        return null;
    }

    @Override
    public StockOrderResponseParams getProductStock(Map<Long, Integer> products, String address){
        StockOrderRequestParams stockOrderRequestParams = new StockOrderRequestParams();
        List<StockOrderRequestVo> skuNums = new ArrayList<>(products.size());
        for (Map.Entry<Long, Integer> entry:products.entrySet()){
            StockOrderRequestVo vo = new StockOrderRequestVo();
            vo.setNum(entry.getValue());
            vo.setSkuId(entry.getKey());
            skuNums.add(vo);
        }
        stockOrderRequestParams.setSkuNums(skuNums);
        stockOrderRequestParams.setArea(address);
        return rpcjdService.getOrderStocks(stockOrderRequestParams);
    }

    @Override
    public boolean cancelOrder(String orderSn){
        TOrderBean orderBean = tOrderMapper.getOrderInfoByOrderSn(orderSn);
        if(null == orderBean || orderBean.getTrade_no().isEmpty()){
            return false;
        }
        if(orderBean.getOrder_state() == 2 ){
            return true;
        }
        CancelOrderRequestParams cancelOrderRequestParams = new CancelOrderRequestParams();
        cancelOrderRequestParams.setJdOrderId(orderBean.getTrade_no());
        CancelOrderResponseParams cancelOrderResponseParams = rpcjdService.cancelOrder(cancelOrderRequestParams);
        if(null == cancelOrderResponseParams){
            return false;
        }
        CancelOrderVo cancelOrderVo = cancelOrderResponseParams.getBiz_order_cancelorder_response();
        if(null == cancelOrderVo){
            return false;
        }
        if(("true".equals(cancelOrderVo.getSuccess()) && cancelOrderVo.getResult()) || ("3203").equals(cancelOrderVo.getResultCode())){
            tOrderMapper.updateOrderStateByTradeNo(2, orderBean.getTrade_no());
            return true;
        }
        return false;
    }

    @Override
    public FreightDetailBean getProductFreight(FreightRequestParams params){
        FreightResponseParams freightResponseParams = rpcjdService.getFreight(params);
        if(null == freightResponseParams){
            return null;
        }
        FreightVoBean giftVoBean = freightResponseParams.getBiz_order_freight_get_response();
        if(null == giftVoBean){
            return null;
        }
        return giftVoBean.getResult();
    }

    @Override
    public BigDecimal getVenderBalance(int venderId){
        ResultData<BigDecimal> resultData = rpcOmsService.balanceAdd(venderId);
        if(null == resultData || 0 != resultData.getError()){
            logger.error("oms:获取商户余额失败");
            return new BigDecimal(0);
        }
        return resultData.getData();
    }

    @Override
    public int getVenderPayType(int venderId){
        ResultData<VenderSettlement> resultData = rpcOmsService.getVenderSettleMent((long)venderId);
        if(null == resultData){
            return -1;
        }
        VenderSettlement venderSettlement = resultData.getData();
        if(null == venderSettlement ){
            return -1;
        }
        return venderSettlement.getSettlementType();
    }

    @Override
    public boolean reduceVenderBalance(BigDecimal totalFee, int venderId){
        ResultData resultData = rpcOmsService.balanceReduce(venderId, totalFee);
        if(null == resultData){
            return false;
        }
        if(0 == resultData.getError()){
            return true;
        }
        return false;
    }

    @Override
    public boolean handelVendBalance(int type, BigDecimal totalFee, int venderId){
        int venderPayType = this.getVenderPayType(venderId);
        if(2 != venderPayType){
            return true;
        }
        return this.reduceVenderBalance(totalFee, venderId);

    }

    @Override
    public boolean handelVendBalance(String orderSn){
        TOrderBean orderBean = this.getOrderInfo(orderSn);
        if(null == orderBean){
            return false;
        }
        VenderSettlementAccount venderSettlementAccount = new VenderSettlementAccount();
        venderSettlementAccount.setVenderId(orderBean.getVenderid());
        venderSettlementAccount.setType(0);//0扣款1退款
        venderSettlementAccount.setOrderSn(orderSn);
        venderSettlementAccount.setTradeNo(orderBean.getTrade_no());
        venderSettlementAccount.setAgreePrice(orderBean.getAgree_price());
        venderSettlementAccount.setPrice(orderBean.getPrice());
        venderSettlementAccount.setPayPrice(orderBean.getPay_price());
        venderSettlementAccount.setFreight(orderBean.getFreight());
        venderSettlementAccount.setPayType(orderBean.getPay_type());
        ResultData resultData = rpcOmsService.accountAdd(venderSettlementAccount);
        if(null != resultData && 0 == resultData.getError() && "1".equals(String.valueOf(resultData.getData()))){
            return true;
        }
        return false;
    }

    @Override
    public boolean handelVendBalance(VenderSettlementAccount venderSettlementAccount){
        ResultData resultData = rpcOmsService.accountAdd(venderSettlementAccount);
        if(null != resultData && 0 == resultData.getError() && "1".equals(String.valueOf(resultData.getData()))){
            return true;
        }
        return false;
    }

    @Override
    public boolean refundToVender(String orderSn, BigDecimal refundPrice){
        TOrderBean orderBean = this.getOrderInfo(orderSn);
        if(null == orderBean){
            return false;
        }
        VenderSettlementAccount venderSettlementAccount = new VenderSettlementAccount();
        venderSettlementAccount.setVenderId(orderBean.getVenderid());
        venderSettlementAccount.setType(1);//0扣款1退款
        venderSettlementAccount.setOrderSn(orderSn);
        venderSettlementAccount.setTradeNo(orderBean.getTrade_no());
        venderSettlementAccount.setAgreePrice(refundPrice);
        venderSettlementAccount.setPrice(orderBean.getPrice());
        venderSettlementAccount.setPayPrice(orderBean.getPay_price());//退给用户到微信支付宝里的钱
        venderSettlementAccount.setCardPrice(orderBean.getCard_price());//退给用户到购物卡里的钱
        venderSettlementAccount.setFreight(orderBean.getFreight());
        venderSettlementAccount.setPayType(orderBean.getPay_type());
        ResultData resultData = rpcOmsService.accountAdd(venderSettlementAccount);
        if(null != resultData && 0 == resultData.getError() && "1".equals(String.valueOf(resultData.getData()))){
            return true;
        }
        return false;
    }

    @Override
    public boolean refundToVender(String orderSn, BigDecimal refundPrice, BigDecimal payPrice, BigDecimal cardPrice){
        TOrderBean orderBean = this.getOrderInfo(orderSn);
        if(null == orderBean){
            return false;
        }
        VenderSettlementAccount venderSettlementAccount = new VenderSettlementAccount();
        venderSettlementAccount.setVenderId(orderBean.getVenderid());
        venderSettlementAccount.setType(1);//0扣款1退款
        venderSettlementAccount.setOrderSn(orderSn);
        venderSettlementAccount.setTradeNo(orderBean.getTrade_no());
        venderSettlementAccount.setAgreePrice(refundPrice);
        venderSettlementAccount.setPrice(orderBean.getPrice());
        venderSettlementAccount.setPayPrice(payPrice);//退给用户到微信支付宝里的钱
        venderSettlementAccount.setCardPrice(cardPrice);//退给用户到购物卡里的钱
        venderSettlementAccount.setFreight(orderBean.getFreight());//退款时运费不应该参与计算
        venderSettlementAccount.setPayType(orderBean.getPay_type());
        //获取京东运费
        FreightDetailBean freightDetailBean = this.getJdFreightByOrderSn(orderSn);
        if(null == freightDetailBean){
            logger.error("向大客户扣款时获取京东运费失败,请注意处理orderSn:"+orderSn);
            return false;
        }else {
            venderSettlementAccount.setFreight(freightDetailBean.getFreight());
        }
        ResultData resultData = rpcOmsService.accountAdd(venderSettlementAccount);
        if(null != resultData && 0 == resultData.getError() && "1".equals(String.valueOf(resultData.getData()))){
            return true;
        }
        return false;
    }

    @Override
    public int checkProductStock(String address, Map<Long, Integer> productCount){
        CheckProductStockResult result = new CheckProductStockResult();
        StockOrderResponseParams responseParams = this.getProductStock(productCount , address);
        if (null == responseParams){
            return -1;
        }
        StockOrderResponseVo stockOrderResponseVo = responseParams.getBiz_stock_fororder_batget_response();
        if(null == stockOrderResponseVo || "false".equals(stockOrderResponseVo.getSuccess())){
            return -1;
        }
        for (StockVo stockVo :stockOrderResponseVo.getResult()){
            if(34 == stockVo.getStockStateId()){  //34表示无货
                return new Long(stockVo.getSkuId()).intValue();
            }
        }
        return 0;

    }

    @Override
    public List<Long> checkProductStock2(String address, Map<Long, Integer> productCount){
        List<Long> res = new ArrayList<>(productCount.size());
        StockOrderResponseParams responseParams = this.getProductStock(productCount , address);
        if (null == responseParams){
            return null;
        }
        StockOrderResponseVo stockOrderResponseVo = responseParams.getBiz_stock_fororder_batget_response();
        if(null == stockOrderResponseVo || "false".equals(stockOrderResponseVo.getSuccess())){
            return null;
        }
        for (StockVo stockVo :stockOrderResponseVo.getResult()){
            if(34 == stockVo.getStockStateId()){  //34表示无货
                res.add(stockVo.getSkuId());
            }
        }
        return res;

    }

    @Override
    public int countOrderByVenderId(Long venderId) {
        return tOrderMapper.countOrderByVenderId(venderId);
    }

    @Override
    public List<String> getProductOrderNo(Map<String, Object> params) {
        return tOrderMapper.getProductOrderNo(params);
    }

    @Override
    public int countProductOrderNos(Map<String, Object> params) {
        return tOrderMapper.countProductOrderNos(params);
    }

    @Override
    public Object formatJdOrderInfo(String jdOrderInfo){
        Map map = JSON.parseObject(jdOrderInfo, Map.class);
        if(null == map){
            return null;
        }
        Map response = (Map) map.get("jd_kpl_open_selectjdorder_query_response");
        if(null == response){
            return null;
        }
        if(!"0000".equals(String.valueOf(response.get("resultCode")))){
            return null;
        }
        Map result = (Map) response.get("result");
        if(null == result){
            return null;
        }
        Object myBean = new Object();
        if("2".equals(String.valueOf(result.get("type")))){//子订单
            myBean = new Gson().fromJson(String.valueOf(response.get("result")), JdChildOrderInfoBean.class);
        }else if("1".equals(String.valueOf(result.get("type")))){//父订单
            myBean = new Gson().fromJson(String.valueOf(response.get("result")), JdFatherOrderInfoBean.class);
        }
        return myBean;
    }

    @Override
    public int writeJdOrderInfo(String jdOrderInfo, String orderSn){
        if(null == jdOrderInfo || jdOrderInfo.length()<1){
            return 0;
        }
        Object orderBean  = this.formatJdOrderInfo(jdOrderInfo);
        if(null == orderBean){
            return 0;
        }
        if(orderBean instanceof JdFatherOrderInfoBean){//父订单
            JdFatherOrderInfoBean jdFatherOrderInfoBean = (JdFatherOrderInfoBean) orderBean;
            List<JdChildOrderInfoBean> cOrder = jdFatherOrderInfoBean.getcOrder();
            if(null == cOrder || cOrder.size()<1){
                return 0;
            }
            for(JdChildOrderInfoBean childOrderInfoBean: cOrder){
                String childJdTradeNo = childOrderInfoBean.getJdOrderId().toString();
                List<String> productIds = new ArrayList<>();
                for(JdSkuInfoBean sku: childOrderInfoBean.getSku()){
                    productIds.add(String.valueOf(sku.getSkuId()));
                }
                this.updateChildTradeNo(orderSn, childJdTradeNo, productIds);
            }
            return 1;
        }else if(orderBean instanceof JdChildOrderInfoBean){//子订单
            JdChildOrderInfoBean childOrder = (JdChildOrderInfoBean) orderBean;
            String childJdTradeNo = childOrder.getJdOrderId().toString();
            List<String> productIds = new ArrayList<>();
            for(JdSkuInfoBean sku: childOrder.getSku()){
                productIds.add(String.valueOf(sku.getSkuId()));
            }
            this.updateChildTradeNo(orderSn, childJdTradeNo, productIds);
            return 1;
        }else {
            return 0;
        }
    }


    @Override
    public List<Map<String, Long>> getProductSalesVolume(String skus, int venderId){
        String[] sku = skus.split(",");
        List<Map<String, Object>> mapperRes = new ArrayList<>(sku.length);
        if(0 == venderId){//不区分大客户
            mapperRes = tOrderProductMapper.getProductSalesVolumn(skus);
        }else {//区分大客户
            mapperRes = tOrderProductMapper.getVenderProductSalesVolumn(skus, venderId);
        }
        List<Map<String, Long>> res = new ArrayList<>(sku.length);
        List<String> skuIds = null;
        if(null == mapperRes){
            skuIds = new ArrayList<>(0);
        }else {
            skuIds = new ArrayList<>(mapperRes.size());
            for(Map<String,Object> map: mapperRes){
                skuIds.add(map.get("productId").toString());
                Map<String, Long> currentMap = new HashMap<>(2);
                currentMap.put("productId", Long.parseLong(map.get("productId").toString()));
                currentMap.put("total", Long.parseLong(map.get("total").toString()));
                res.add(currentMap);
            }
        }
        for(String s: sku){
            if(!skuIds.contains(s)){
                Map<String, Long> map = new HashMap<>(2);
                map.put("productId", Long.parseLong(s));
                map.put("total", Long.valueOf(0));
                res.add(map);
            }
        }
        return res;
    }

    @Override
    public List<Map<String, Long>> getProductSalesVolumeWrapper(String skus, int venderId){
        List<Map<String, Long>> res = this.getProductSalesVolume(skus, venderId);
        Map<Long, Integer> refundProduct = this.getRefundProducts(skus, venderId);
        for(Map<String, Long> map: res){
            long sku = map.get("productId");
            long count  = map.get("total");
            int refundCount = refundProduct.getOrDefault(sku, 0);
            count = count-refundCount>0?count-refundCount:0;
            map.put("productId", sku);
            map.put("total", count);
        }
        return res;
    }

    @Override
    public Map<String, Long> getProductSalesVolume2Wrapper(String skus, int venderId){
        Map<String, Long> res = this.getProductSalesVolume2(skus, venderId);
        Map<Long, Integer> refundProduct = this.getRefundProducts(skus, venderId);
        for(Map.Entry<String, Long> entry: res.entrySet()){
            long sku = Long.parseLong(entry.getKey());
            long count  = entry.getValue();
            int refundCount = refundProduct.getOrDefault(sku, 0);
            count = count-refundCount>0?count-refundCount:0;
            entry.setValue(count);
        }
        return res;
    }

    @Override
    public Map<String, Long> getProductSalesVolume2(String skus, int venderId){
        String[] sku = skus.split(",");
        List<Map<String, Object>> mapperRes = new ArrayList<>(sku.length);
        if(0 == venderId){//不区分大客户
            mapperRes = tOrderProductMapper.getProductSalesVolumn(skus);
        }else {//区分大客户
            mapperRes = tOrderProductMapper.getVenderProductSalesVolumn(skus, venderId);
        }
        Map<String, Long> skuIds;
        if(null == mapperRes){
            skuIds = new HashMap<>(0);
        }else {
            skuIds = new HashMap<>(mapperRes.size());
            for(Map<String,Object> map: mapperRes){
                skuIds.put(map.get("productId").toString(), Long.parseLong(map.get("total").toString()));
            }
        }
        for(String s: sku){
            if(!skuIds.keySet().contains(s)){
                Map<String, Integer> map = new HashMap<>(2);
                skuIds.put(s, Long.parseLong("0"));
            }
        }
        return skuIds;
    }

    @Override
    public boolean isNumericByComma(String str){
        Pattern pattern = Pattern.compile("\\d+(,\\d+)*");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }

    @Override
    public ResultData splitOrder(String jdOrderSn){
        ResultData resultData = new ResultData();
        TOrderBean tOrderBean = this.getOrderInfoByTradeNo(jdOrderSn);
        if(null == tOrderBean){
            resultData.setError(ErrorCode.NO_ORDER);
            resultData.setMsg("没有在平台中查找到此订单");
            return resultData;
        }
        String orderResponseParams = this.getJdOrderInfo(tOrderBean.getTrade_no());
        if(null == orderResponseParams || "".equals(orderResponseParams)){
            resultData.setError(ErrorCode.NO_ORDER);
            resultData.setMsg("没有在京东中查找到此订单");
            return resultData;
        }
        int s = this.writeJdOrderInfo(orderResponseParams, tOrderBean.getOrder_sn());
        if(s < 1){
            resultData.setError(ErrorCode.FALI);
            resultData.setMsg("刷新子订单失败");
            return resultData;
        }
        return resultData;
    }


    @Override
    public ResultData<FreightDetailBean> getJdFreight(GetFreightRequestParams params){
        ResultData resultData = new ResultData();
        Map<String, Integer> products = params.getProductIds();
        List<FreightRequestSkuVo> skuVos = new ArrayList<>(products.size());
        for(Map.Entry<String, Integer> entry: products.entrySet()){
            FreightRequestSkuVo skuVo = new FreightRequestSkuVo();
            skuVo.setNum(String.valueOf(entry.getValue()));
            skuVo.setSkuId(entry.getKey());
            skuVos.add(skuVo);
        }
        FreightRequestParams requestParams = new FreightRequestParams();
        requestParams.setSku(skuVos);
        requestParams.setProvince(params.getProvinceId());
        requestParams.setCity(params.getCityId());
        requestParams.setCounty(params.getCountyId());
        requestParams.setTown(params.getTownId());
        requestParams.setPaymentType(4);//支付方式,预付费
        FreightDetailBean freightDetailBean = this.getProductFreight(requestParams);
        if(null == freightDetailBean){
            resultData.setError(ErrorCode.FALI);
            resultData.setMsg("jdservice获取运费失败");
            return resultData;
        }
        resultData.setData(freightDetailBean);
        return resultData;

    }

    @Override
    public ResultData getVenderFreight(int venderId, GetFreightRequestParams params){
        ResultData resultData = new ResultData();
        //获取大客户的包邮价
        ResultData<Double> freeFreightRes = rpcOmsService.getFreeFreighPrice(venderId);
        if(null == freeFreightRes){
            resultData.setData(ErrorCode.SERVER_EXCEPTION);
            resultData.setMsg("OMS服务调用异常");
            return resultData;
        }
        if(ErrorCode.SUCCESS != freeFreightRes.getError()){
            return freeFreightRes;
        }
        BigDecimal freePricePrice = new BigDecimal(freeFreightRes.getData());
        //获取当前大客户下商品的总价
        Map<Long, ProductPriceBean> map = productService.getProductPrice(venderId , new ArrayList<>(params.getProductIds().keySet()));
        if(null == map){
            resultData.setError(ErrorCode.NO_PRODUCT);
            resultData.setMsg("没有查找到此商品");
            return resultData;
        }
        BigDecimal payPrice = new BigDecimal(0);
        for (Map.Entry<Long, ProductPriceBean> entry: map.entrySet()){
            long sku = entry.getKey();
            ProductPriceBean productPriceBean = entry.getValue();
            int count = params.getProductIds().get(String.valueOf(sku));
            payPrice = payPrice.add(productPriceBean.getPrice().
                    multiply(new BigDecimal(count)));
        }
        //满足包邮价格
        FreightDetailBean freightDetailBean = new FreightDetailBean();
        if(payPrice.compareTo(freePricePrice) >= 0){
            freightDetailBean = new FreightDetailBean();
            BigDecimal freight = new BigDecimal(0);
            freightDetailBean.setBaseFreight(freight);
            freightDetailBean.setFreight(freight);
            freightDetailBean.setRemoteRegionFreight(freight);
            freightDetailBean.setRemoteSku("");
        }else {
            //获取京东运费
            FreightRequestParams requestParams = new FreightRequestParams();
            List<FreightRequestSkuVo> skuVos = new ArrayList<>(params.getProductIds().size());
            for (Map.Entry<String, Integer> entry : params.getProductIds().entrySet()) {
                FreightRequestSkuVo skuVo = new FreightRequestSkuVo();
                skuVo.setSkuId(entry.getKey());
                skuVo.setNum(String.valueOf(entry.getValue()));
                skuVos.add(skuVo);
            }
            requestParams.setSku(skuVos);
            requestParams.setProvince(params.getProvinceId());
            requestParams.setCity(params.getCityId());
            requestParams.setCounty(params.getCountyId());
            requestParams.setTown(params.getTownId());
            requestParams.setPaymentType(4);//支付方式,预付费
            freightDetailBean = this.getProductFreight(requestParams);
            if (null == freightDetailBean) {
                resultData.setError(ErrorCode.FALI);
                resultData.setMsg("获取运费失败");
                return resultData;
            }
            //如果京东不包邮，则返回京东运费,否则返回设置的默认运费
            if(freightDetailBean.getFreight().compareTo(new BigDecimal(0)) == 0){
                freightDetailBean.setFreight(defaultFreight);
            }
            freightDetailBean.setPriceDifference(freePricePrice.subtract(payPrice).setScale(2, BigDecimal.ROUND_UP));
        }
        resultData.setData(freightDetailBean);
        return resultData;
    }

    private String getCurrentTime(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return  simpleDateFormat.format(new Date());
    }

    @Override
    public FreightDetailBean getJdFreightByOrderSn(String orderSn){
        List<TOrderProductBean> orderProductBeans = this.getOrderProduct(orderSn);
        if(null == orderProductBeans || orderProductBeans.isEmpty()){
            logger.error("获取京东运费:查询订单失败:{}", orderSn);
            return null;
        }
        TOrderBean orderBean = this.getOrderInfo(orderSn);
        if(null == orderBean){
            logger.error("获取京东运费:查询订单失败:{}", orderSn);
            return null;
        }
        TOrderAddressBean tOrderAddressBean = this.findOrderAddress(orderBean.getId());
        if(null == tOrderAddressBean){
            logger.error("获取京东运费:查询订单地址失败:{}", orderSn);
            return null;
        }
        Map<String, Integer> products = new HashMap<>(orderProductBeans.size());
        for(TOrderProductBean tOrderProductBean: orderProductBeans){
            products.put(String.valueOf(tOrderProductBean.getProduct_id()), tOrderProductBean.getCount());
        }
        GetFreightRequestParams getFreightRequestParams = new GetFreightRequestParams();
        getFreightRequestParams.setProductIds(products);
        getFreightRequestParams.setProvinceId(tOrderAddressBean.getProvince_id());
        getFreightRequestParams.setCityId(tOrderAddressBean.getCity_id());
        getFreightRequestParams.setCountyId(tOrderAddressBean.getCounty_id());
        getFreightRequestParams.setTownId(tOrderAddressBean.getTown_id());
        ResultData<FreightDetailBean> freightDetailBeanResultData = this.getJdFreight(getFreightRequestParams);
        if(null == freightDetailBeanResultData || ErrorCode.SUCCESS !=freightDetailBeanResultData.getError()
                || null == freightDetailBeanResultData.getData()){
            logger.error("获取京东运费:jdservice返回失败,{}", orderSn);
            return null;
        }
        return freightDetailBeanResultData.getData();
    }

    @Override
    public List<String> getUnPayedOrderSnLimit(int limitSize, int expireMinute){
        long expireTime = System.currentTimeMillis() - (expireMinute * 60 * 1000);
        Date date = new Date(expireTime);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return tOrderMapper.getUnPayedOrderSnLimit(  simpleDateFormat.format(date),limitSize);
    }

    @Override
    public int paySuccessUpdateOrderState(BigDecimal payPrice, BigDecimal cardPrice, int payType, String orderSn, String payOrderSn){
        TOrderBean tOrderBean2 = new TOrderBean();
        tOrderBean2.setPay_price(payPrice);
        tOrderBean2.setCard_price(cardPrice);
        tOrderBean2.setPay_status(1);
        tOrderBean2.setUtime(new Date());
        tOrderBean2.setOrder_sn(orderSn);
        tOrderBean2.setPay_order_sn(payOrderSn);
        tOrderBean2.setPay_type(payType);
        tOrderBean2.setOrder_state(3);//支付完成,变成待收货状态
        return this.updateOrderStatus(tOrderBean2);
    }

    @Override
    public boolean paySuccessVenderCharged(int venderId, String orderSn, String jdOrderSn, int payType, BigDecimal argeePrice, BigDecimal price, BigDecimal payPrice, BigDecimal cardPrice){
        VenderSettlementAccount venderSettlementAccount = new VenderSettlementAccount();
        venderSettlementAccount.setVenderId(venderId);
        venderSettlementAccount.setType(0);//0扣款1退款
        venderSettlementAccount.setOrderSn(orderSn);
        venderSettlementAccount.setTradeNo(jdOrderSn);
        venderSettlementAccount.setAgreePrice(argeePrice);
        venderSettlementAccount.setPrice(price);
        venderSettlementAccount.setPayPrice(payPrice);
        venderSettlementAccount.setPayType(payType);
        venderSettlementAccount.setCardPrice(cardPrice);
        //获取京东运费
        FreightDetailBean freightDetailBean = this.getJdFreightByOrderSn(orderSn);
        if(null == freightDetailBean){
            logger.error("向大客户扣款时获取京东运费失败,请注意处理orderSn:"+orderSn);
            return false;
        }else {
            venderSettlementAccount.setFreight(freightDetailBean.getFreight());
        }
        return this.handelVendBalance(venderSettlementAccount);
    }

    @Override
    public boolean refundToUser(TOrderBean orderBean, BigDecimal refundPrice, int payType, String outTradeNo){
        orderBean.setPay_price(refundPrice);
        orderBean.setPay_type(payType);
        orderBean.setPay_order_sn(outTradeNo);
        ResultData resultData = refundService.payFailUserRefund(orderBean);
        return true;
    }

    /**
     * 获取某个渠道下退货商品的数量
     * add by wanghaoyang at 20180903
     * */
    private Map<Long, Integer> getRefundProducts(String skus, int venderId){
        List<Map<String, Object>> refundProducts = refundService.getRefundProductVolumn(skus, venderId);
        Map<Long, Integer> productMaps;
        if(null == refundProducts){
            productMaps = new HashMap<>(0);
        }else {
            productMaps = new HashMap<>(refundProducts.size());
            for(Map<String, Object> map: refundProducts) {
                long sku = Long.parseLong(map.get("productId").toString());
                int total = Integer.parseInt(map.get("total").toString());
                productMaps.put(sku, total);
            }
        }
        return productMaps;
    }

    @Override
    public ResultData paySuccessNotify(PayNotifyUrlParams payNotifyUrlParams){
        ResultData resultData = new ResultData();
        TOrderBean tOrderBean = this.getOrderInfo(payNotifyUrlParams.getOut_trade_no());
        if(null == tOrderBean){
            resultData.setError(10012);
            resultData.setMsg("此订单不存在");
            return resultData;
        }
        if(1 == tOrderBean.getPay_status()){
            resultData.setError(10013);
            resultData.setMsg("订单已支付");
            return resultData;
        }
        BigDecimal orderPrice = tOrderBean.getPrice().add(tOrderBean.getFreight());//用户应该支付的价格
        BigDecimal payPrice = new BigDecimal(payNotifyUrlParams.getTotal_fee()).divide(new BigDecimal(100));//用户扫码支付的价格
        BigDecimal cardPrice = new BigDecimal(0);
        int payType = Integer.parseInt(payNotifyUrlParams.getPay_type());
        if(orderPrice.compareTo(payPrice) > 0){
            List<String> cardNos = cardService.getOrderCardNos(tOrderBean.getOrder_sn());
            if(null == cardNos || cardNos.isEmpty()){//没有使用购物卡
                resultData.setError(ErrorCode.PAY_MONEY_ERROR);
                resultData.setMsg("支付金额错误orderSn:"+tOrderBean.getOrder_sn());
                logger.warn("支付金额错误orderSn:"+tOrderBean.getOrder_sn());
                this.refundToUser(tOrderBean, payPrice, payType, payNotifyUrlParams.getOrder_sn());
                return resultData;
            }
            cardPrice = orderPrice.subtract(payPrice);
            try {
                BigDecimal res = cardService.deductMoneyFromCards(cardNos, cardPrice, tOrderBean.getOrder_sn(), tOrderBean.getUser_id());
                if(new BigDecimal(0).compareTo(res) != 0) {
                    resultData.setError(ErrorCode.CARD_START_PAY_ERROR);
                    resultData.setMsg("购物卡支付失败,请重试");
                    this.refundToUser(tOrderBean, payPrice, payType, payNotifyUrlParams.getOrder_sn());
                    return resultData;
                }
            }catch (RuntimeException e){
                logger.error("发起支付失败:"+e.getMessage());
                resultData.setError(ErrorCode.CARD_START_PAY_ERROR);
                resultData.setMsg("购物卡支付失败:"+e.getMessage());
                this.refundToUser(tOrderBean, payPrice, payType, payNotifyUrlParams.getOrder_sn());
                return resultData;
            }
        }
        int state = this.paySuccessUpdateOrderState(payPrice, cardPrice
                , payType, tOrderBean.getOrder_sn(), payNotifyUrlParams.getOrder_sn());
        if(1 != state){
            resultData.setError(10014);
            resultData.setMsg("订单通知失败");
            return resultData;
        }
        if(!this.paySuccessVenderCharged(tOrderBean.getVenderid(), tOrderBean.getOrder_sn(),  tOrderBean.getTrade_no(), payType, tOrderBean.getAgree_price(), tOrderBean.getPrice(), payPrice, cardPrice)){ //TODO 扣款失败,需要向用户退款
            logger.error("扣款失败,向用户退款"+tOrderBean.getOrder_sn());
            resultData.setError(ErrorCode.VENDER_BANANCE_NOT_ENOUGH);
            resultData.setMsg("向商户扣款失败");
            return resultData;
        }
        if(isOrderToJD) {//是否向京东确认下单
            logger.info("开始确认向京东下单");
            RPCConfirmOccupyStockResponseParams res = this.jdOrderStock(tOrderBean.getTrade_no());
            if (null == res) {
                resultData.setError(ErrorCode.STOCK_JD_ORDER);
                logger.error("向京东确认下单失败");
                resultData.setMsg("向京东确认下单失败");
                return resultData;
            }
            RPCStockVo rpcStockVo = res.getBiz_order_occupyStock_confirm_response();
            if (null == rpcStockVo || !rpcStockVo.getResult()) {
                logger.error("向京东确认下单失败2");
                resultData.setError(ErrorCode.STOCK_JD_ORDER);
                resultData.setMsg("向京东确认下单失败2");
                return resultData;
            }
        }
        String orderResponseParams = this.getJdOrderInfo(tOrderBean.getTrade_no());
        this.writeJdOrderInfo(orderResponseParams, tOrderBean.getOrder_sn());
        resultData.setMsg("订单通知成功");
        this.clearShopCart(tOrderBean.getUser_id(), tOrderBean.getOrder_sn());
        return resultData;
    }

    @Override
    public ResultData venderOrderNotify(VenderOrderNotifyParams venderOrderNotifyParams){
        ResultData resultData = new ResultData();
        TOrderBean tOrderBean = this.getOrderInfo(venderOrderNotifyParams.getOrderSn());
        if(null == tOrderBean){
            resultData.setError(ErrorCode.NO_ORDER);
            resultData.setMsg("此订单不存在");
            return resultData;
        }
        if(1 == tOrderBean.getPay_status()){
            resultData.setError(ErrorCode.ORDER_PAYED);
            resultData.setMsg("订单已支付");
            return resultData;
        }
        if(venderOrderNotifyParams.getVenderId() != tOrderBean.getVenderid()){
            resultData.setError(ErrorCode.PARAM_ERROR);
            resultData.setMsg("大客户ID不匹配");
            return resultData;
        }
        BigDecimal payPrice = new BigDecimal(venderOrderNotifyParams.getTotalFee()).divide(new BigDecimal(100));//用户扫码支付的价格
        if(payPrice.compareTo(tOrderBean.getPrice().add(tOrderBean.getFreight())) != 0){
            resultData.setError(ErrorCode.PAY_MONEY_ERROR);
            resultData.setMsg("支付金额错误");
            return resultData;
        }
        BigDecimal cardPrice = new BigDecimal(0);
        int payType = venderOrderNotifyParams.getPayType();
        int state = this.paySuccessUpdateOrderState(payPrice, cardPrice
                , payType, tOrderBean.getOrder_sn(), venderOrderNotifyParams.getOutTradeNo());
        if(1 != state){
            resultData.setError(ErrorCode.SERVER_EXCEPTION);
            resultData.setMsg("修改订单失败");
            return resultData;
        }
        if(!this.paySuccessVenderCharged(tOrderBean.getVenderid(), tOrderBean.getOrder_sn(),  tOrderBean.getTrade_no(), payType, tOrderBean.getAgree_price(), tOrderBean.getPrice(), payPrice, cardPrice)){
            logger.error("扣款失败,向用户退款"+tOrderBean.getOrder_sn());
            resultData.setMsg("向商户扣款失败");
            resultData.setError(ErrorCode.VENDER_BANANCE_NOT_ENOUGH);
            return resultData;
        }
        if(isOrderToJD) {//是否向京东确认下单
            logger.info("开始确认向京东下单");
            RPCConfirmOccupyStockResponseParams res = this.jdOrderStock(tOrderBean.getTrade_no());
            if (null == res) {
                resultData.setError(ErrorCode.STOCK_JD_ORDER);
                resultData.setMsg("向京东确认下单失败");
                logger.error("向京东确认下单失败");
                return resultData;
            }
            RPCStockVo rpcStockVo = res.getBiz_order_occupyStock_confirm_response();
            if (null == rpcStockVo || !rpcStockVo.getResult()) {
                logger.error("向京东确认下单失败2");
                resultData.setMsg("向京东确认下单失败2");
                resultData.setError(ErrorCode.STOCK_JD_ORDER);
                return resultData;
            }
        }
        String orderResponseParams = this.getJdOrderInfo(tOrderBean.getTrade_no());
        this.writeJdOrderInfo(orderResponseParams, tOrderBean.getOrder_sn());
        resultData.setMsg("订单通知成功");
        this.clearShopCart(tOrderBean.getUser_id(), tOrderBean.getOrder_sn());
        return resultData;
    }

    /**
     * 清空购物车
     * */
    @Override
    public boolean clearShopCart(int userId ,String orderSn){
        List<String> productIds = tOrderProductMapper.getOrderProduct(orderSn);
        if(null == productIds || productIds.isEmpty()){
            logger.warn("清空购物车失败orderSn:"+orderSn);
            return false;
        }
        ResultData resultData = rpcImsService.deleteCartProduct(userId, String.join(",", productIds));
        if(null == resultData || resultData.getError() != 0){
            logger.warn("清空购物车失败orderSn:"+orderSn);
            return false;
        }
        return true;
    }

    @Override
    public int deleteOrder(String orderSn, int userId){
        return tOrderMapper.deleteOrderByUserIdAndOrderSn(userId, orderSn);
    }

    @Override
    public String getJdErrorCodeMessage(String resultCode){
        return terminalOrderErrorCodeNote.getJdOrderCodeNote().getOrDefault(Integer.parseInt(resultCode), "系统出问题了，请稍后再试!");
    }

    @Override
    public ResultData<List<StockVo>> getProductState(String address,int venderId, Map<Long, Integer> products){
        ResultData<List<StockVo>> resultData = new ResultData();
        StockOrderResponseParams responseParams = this.getProductStock(products , address);
        if (null == responseParams){
            logger.error("jdservice服务器异常");
            resultData.setError(ErrorCode.SERVER_EXCEPTION);
            resultData.setMsg("jdservice服务器异常");
            return resultData;
        }
        StockOrderResponseVo stockOrderResponseVo = responseParams.getBiz_stock_fororder_batget_response();
        if(null == stockOrderResponseVo || "false".equals(stockOrderResponseVo.getSuccess())){
            resultData.setError(ErrorCode.FALI);
            resultData.setMsg("查询失败");
            return resultData;
        }
        //判断商品的上下架状态
        Map<Long, Integer> productState = productService.getProductState(venderId, products.keySet().stream().map((id)->id+"").collect(Collectors.toList()));
        if(null == productState || productState.isEmpty()){
            logger.error("search服务器异常");
            resultData.setError(ErrorCode.SERVER_EXCEPTION);
            resultData.setMsg("search服务器异常");
            return resultData;
        }
        List<Long> jdResultSku = new ArrayList<>();
        for(StockVo stockVo:stockOrderResponseVo.getResult()){
            long skuId = stockVo.getSkuId();
            jdResultSku.add(skuId);
            if(0 == productState.getOrDefault(skuId, 0)){
                stockVo.setStockStateId(34);//34为无货状态
            }
        }
        for(Long skuId: products.keySet()){
            if(!jdResultSku.contains(skuId)){
                StockVo stockVo = new StockVo();
                stockVo.setStockStateId(34);
                stockVo.setSkuId(skuId);
                stockVo.setAreaId(address);
                stockVo.setStockStateDesc("京东已经将商品删除");
                stockVo.setRemainNum(-1);
                stockOrderResponseVo.getResult().add(stockVo);
            }
        }
        resultData.setData(stockOrderResponseVo.getResult());
        return resultData;
    }

    @Override
    public List<Long> checkStockAndOnline(String address,int venderId, Map<Long, Integer> products){
        ResultData<List<StockVo>> resultData = this.getProductState(address, venderId, products);
        if(ErrorCode.SUCCESS != resultData.getError()){
            return null;
        }
        List<Long> skus = new ArrayList<>();
        for(StockVo stockVo:resultData.getData()){
            if(34 == stockVo.getStockStateId()){
                skus.add(stockVo.getSkuId());
            }
        }
        return skus;
    }

    @Override
    public TOrderBean getUserOrderDetail(String orderSn, int userId){
        return tOrderMapper.getUserOrderDetail(orderSn, userId);
    }

    @Override
    public int countOrderRefund(Map<String, Object> params) {
        return tOrderMapper.countOrderRefund(params);
    }

    @Override
    public List<OrderRefundVo> listOrderRefund(Map<String, Object> params) {
        return tOrderMapper.listOrderRefund(params);
    }
}
