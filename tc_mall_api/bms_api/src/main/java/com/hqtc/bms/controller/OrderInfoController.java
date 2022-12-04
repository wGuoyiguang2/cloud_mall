package com.hqtc.bms.controller;

import com.google.gson.Gson;
import com.hqtc.bms.config.Router;
import com.hqtc.bms.config.VenderRouter;
import com.hqtc.bms.model.database.TOrderAddressBean;
import com.hqtc.bms.model.database.TOrderBean;
import com.hqtc.bms.model.database.TOrderProductBean;
import com.hqtc.bms.model.params.GetFreightRequestParams;
import com.hqtc.bms.model.params.OrderDetailParams;
import com.hqtc.bms.model.params.OrderManagerVo;
import com.hqtc.bms.model.params.StockDetailParams;
import com.hqtc.bms.model.response.GetOrderDetailResponse;
import com.hqtc.bms.model.response.OrderDetailResponse;
import com.hqtc.bms.model.response.OrderRefundVo;
import com.hqtc.bms.model.response.Result;
import com.hqtc.bms.model.rpc.StockVo;
import com.hqtc.bms.service.OrderService;
import com.hqtc.bms.service.ProductService;
import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.ResultData;
import com.hqtc.common.utils.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wanghaoyang on 18-7-12.
 */
@RestController
public class OrderInfoController {

    @Autowired
    @Resource(name = "OrderServiceImpl")
    private OrderService orderService;

    @Value("${spring.config.afterSaleQrcode}")
    private String afterSalePageUrl;

    @Value("${order.product.maxSize}")
    private int maxProductType;

    @Autowired
    @Resource(name = "ProductServiceImpl")
    private ProductService productService;

    @RequestMapping(value = Router.ROUTER_PRODUCT_ORDER_DETAIL, method = RequestMethod.GET)
    public ResultData orderUpper(@RequestParam(value = "tradeNo") String tradeNo,
                                 @RequestParam(value = "sku")long sku) {
        ResultData resultData = Tools.getThreadResultData();
        TOrderBean orderBean = orderService.getOrderInfoByTradeNo(tradeNo);
        if(null == orderBean){
            resultData.setError(ErrorCode.NO_ORDER);
            resultData.setMsg("未查找到词订单");
            return resultData;
        }
        List<TOrderProductBean> orderProductBeans = orderService.getOrderProduct(orderBean.getOrder_sn(), sku);
        if(null == orderProductBeans || orderProductBeans.isEmpty()){
            resultData.setError(ErrorCode.NO_SUCH_PRODUCT);
            resultData.setMsg("订单中不包含此中商品");
            return resultData;
        }
        TOrderAddressBean tOrderAddressBean = orderService.findOrderAddress(orderBean.getId());
        if(null == tOrderAddressBean){
            resultData.setError(ErrorCode.NO_ADDRESS);
            resultData.setMsg("没有查找到收货地址");
            return resultData;
        }
        OrderDetailResponse orderDetailResponse = new OrderDetailResponse();
        orderDetailResponse.setOrderSn(orderBean.getOrder_sn());
        orderDetailResponse.setTradeNo(orderBean.getTrade_no());
        orderDetailResponse.setProvinceId(tOrderAddressBean.getProvince_id());
        orderDetailResponse.setCityId(tOrderAddressBean.getCity_id());
        orderDetailResponse.setCountryId(tOrderAddressBean.getCounty_id());
        orderDetailResponse.setTownId(tOrderAddressBean.getTown_id());
        orderDetailResponse.setDetail(tOrderAddressBean.getDetail());
        orderDetailResponse.setReceiver(tOrderAddressBean.getName());
        orderDetailResponse.setPhone(tOrderAddressBean.getPhone());
        TOrderProductBean tOrderProductBean = orderProductBeans.get(0);
        int size = 0;
        for(TOrderProductBean tOrderProductBean1: orderProductBeans){
            size += tOrderProductBean1.getCount();
        }
        orderDetailResponse.setProductName(tOrderProductBean.getName());
        orderDetailResponse.setProductPrice(tOrderProductBean.getPrice());
        orderDetailResponse.setProductId(tOrderProductBean.getProduct_id());
        orderDetailResponse.setProductSize(size);
        orderDetailResponse.setTotalPrice(orderBean.getPay_price());
        resultData.setData(orderDetailResponse);
        return resultData;
    }


    @RequestMapping(value ={Router.ROUTER_ORDER_DETAIL, VenderRouter.ROUTER_ORDER_DETAIL}, method = RequestMethod.GET)
    public ResultData orderDetail(@RequestAttribute(value = "userId") Integer userId,
                                  @Valid OrderDetailParams orderDetailParams,
                                  BindingResult bindingResult) {
        ResultData<GetOrderDetailResponse> resultData = Tools.getThreadResultData();
        if (bindingResult.hasErrors()) {
            resultData.setError(ErrorCode.PARAM_ERROR);
            resultData.setMsg(bindingResult.getAllErrors().get(0).getDefaultMessage());
            return resultData;
        }
        //判断用户是否有这笔订单
        if(null == orderService.getUserOrderDetail(orderDetailParams.getOrderSn(), userId)){
            resultData.setError(ErrorCode.NO_ORDER);
            resultData.setMsg("订单不存在");
            return resultData;
        }
        //查询订单信息
        TOrderBean orderBean = orderService.getOrderInfo(orderDetailParams.getOrderSn());
        if(null == orderBean){
            resultData.setError(ErrorCode.NO_ORDER);
            resultData.setMsg("没有找到对应的订单");
            return resultData;
        }
        //查询收货地址信息
        TOrderAddressBean tOrderAddressBean = orderService.findOrderAddress(orderBean.getId());
        if(null == tOrderAddressBean){
            resultData.setError(ErrorCode.NO_ADDRESS);
            resultData.setMsg("没有找到对应的收货地址");
            return resultData;
        }
        GetOrderDetailResponse getOrderDetailResponse = new GetOrderDetailResponse();
        getOrderDetailResponse.setUserId(orderBean.getUser_id());
        getOrderDetailResponse.setVenderId(orderBean.getVenderid());
        getOrderDetailResponse.setOrderSn(orderBean.getOrder_sn());
        getOrderDetailResponse.setPayStatus(orderBean.getPay_status());
        getOrderDetailResponse.setAddTime(orderBean.getCtime());
        getOrderDetailResponse.setPayTime(orderBean.getUtime());
        getOrderDetailResponse.setPrice(orderBean.getPrice());
        getOrderDetailResponse.setPayPrice(orderBean.getPay_price().add(orderBean.getCard_price()));
        getOrderDetailResponse.setName(tOrderAddressBean.getName());
        getOrderDetailResponse.setPhone(tOrderAddressBean.getPhone());
        getOrderDetailResponse.setProvinceId(tOrderAddressBean.getProvince_id());
        getOrderDetailResponse.setCityId(tOrderAddressBean.getCity_id());
        getOrderDetailResponse.setCountyId(tOrderAddressBean.getCounty_id());
        getOrderDetailResponse.setTownId(tOrderAddressBean.getTown_id());
        getOrderDetailResponse.setAddress(orderService.getOrderAddress(tOrderAddressBean));
        getOrderDetailResponse.setPayType(orderBean.getPay_type());
        getOrderDetailResponse.setFreight(orderBean.getFreight());
        getOrderDetailResponse.setOrderState(orderBean.getOrder_state());
        getOrderDetailResponse.setJdTradeNo(orderBean.getTrade_no());
        getOrderDetailResponse.setQrCode(this.createAfterSaleUrl(orderBean.getVenderid(), orderDetailParams.getMac1()));
        List<Map<String, Object>> tOrderProductBeans = orderService.getProductCount(orderDetailParams.getOrderSn());
        List<String> productIds = new ArrayList<>();
        if(null != tOrderProductBeans){
            for(Map<String, Object> map1: tOrderProductBeans){
                productIds.add(map1.get("product_id").toString());
            }
        }
        Map<Long, String> productImage1 = orderService.getProductImage(productIds);
        if(null != productImage1) {
            for(Map<String, Object> map2: tOrderProductBeans){
                map2.put("imagePath", productImage1.getOrDefault(Long.parseLong(map2.get("product_id").toString()), ""));
            }
        }
        getOrderDetailResponse.setProduct(tOrderProductBeans);
        resultData.setData(getOrderDetailResponse);
        return resultData;
    }

    @RequestMapping(value = Router.ROUTER_JD_ORDER_DETAIL, method = RequestMethod.GET)
    public ResultData orderDetail(@RequestParam(value = "orderSn", defaultValue = "")String orderSn,
                                  @RequestParam(value = "tradeNo", defaultValue = "")String tradeNo) {
        ResultData resultData = Tools.getThreadResultData();
        if(!"".equals(orderSn)) {
            TOrderBean orderBean = orderService.getOrderInfo(orderSn);
            if (null == orderBean) {
                resultData.setError(ErrorCode.NO_ORDER);
                resultData.setMsg("订单不存在");
                return resultData;
            }
            tradeNo = orderBean.getTrade_no();
        }
        String orderResponseParams = orderService.getJdOrderInfo(tradeNo);
        resultData.setData(new Gson().fromJson(orderResponseParams, Map.class));
        return resultData;
    }

    @RequestMapping(value = Router.ROUTER_ORDER_LIST, method = RequestMethod.GET)
    public ResultData orderList(@RequestParam("userId")int userId,
                                @RequestParam(value = "page", defaultValue = "1")int page,
                                @RequestParam(value = "size", defaultValue = "10")int size,
                                @RequestParam(value = "orderState", defaultValue = "0")int orderState) {
        ResultData resultData = Tools.getThreadResultData();
        page = page >=1?page:1;
        size = (size>=100 || size<1)?100:size;
        int pageStart = (page-1)*size;
        Map<String, Object> res = new HashMap<>(2);
        res.put("currentState", orderState);
        res.put("info", orderService.getUserOrder(userId, pageStart, size, orderState));
        resultData.setData(res);
        return resultData;
    }
    /**
     * 查询订单管理列表
     * @param params
     * @return
     */
    @GetMapping(Router.BMS_ORDER_MANAGER_LIST)
    public Result<OrderManagerVo> orderManagerList(@RequestParam Map<String,Object> params) {
        Result<OrderManagerVo> resultData = new Result<>();
        List<OrderManagerVo> list = orderService.orderManagerList(params);
        int count = orderService.countManagerOrderList(params);
        resultData.setRows(list);
        resultData.setTotal(count);
        return resultData;
    }

    @RequestMapping(value = Router.ROUTER_JD_SEARCH_STOCK, method = RequestMethod.POST)
    public ResultData stockDetail(@RequestBody @Valid StockDetailParams stockDetailParams,
                                  @RequestParam("venderId")int venderId,
                                  BindingResult bindingResult){
        ResultData<List<StockVo>> resultData = Tools.getThreadResultData();
        if(bindingResult.hasErrors()){
            resultData.setError(ErrorCode.PARAM_ERROR);
            resultData.setMsg(bindingResult.getAllErrors().get(0).getDefaultMessage());
            return resultData;
        }
        if(stockDetailParams.getProductIds().keySet().size()>maxProductType || stockDetailParams.getProductIds().size()<1){
            resultData.setError(ErrorCode.PARAM_ERROR);
            resultData.setMsg("抱歉，无法再添加，最多50种商品。");
            return resultData;
        }
        String address = new StringBuffer().append(stockDetailParams.getProvinceId()).append("_")
                .append(stockDetailParams.getCityId()).append("_")
                .append(stockDetailParams.getCountyId()).append("_")
                .append(stockDetailParams.getTownId()).toString();
        resultData = orderService.getProductState(address, venderId, stockDetailParams.getProductIds());
        return resultData;
    }

    @RequestMapping(value = {Router.ROUTER_ORDER_CANCEL, VenderRouter.ROUTER_ORDER_CANCEL}, method = RequestMethod.POST)
    public ResultData cancelOrder(@RequestAttribute(value = "userId") Integer userId,
                                  @RequestParam(value = "orderSn")String orderSn) {
        ResultData resultData =  Tools.getThreadResultData();
        TOrderBean orderBean = orderService.getOrderInfo(orderSn);
        if(null == orderBean){
            resultData.setError(ErrorCode.NO_ORDER);
            resultData.setMsg("没有查找到此订单");
            return resultData;
        }
        if(orderBean.getUser_id() != userId){
            resultData.setError(ErrorCode.NO_ORDER);
            resultData.setMsg("非此用户订单");
            return resultData;
        }
        Map<String, Boolean> result = new HashMap<>();
        result.put("result", orderService.cancelOrder(orderSn));
        resultData.setData(result);
        return resultData;
    }

    @RequestMapping(value = Router.ROUTER_ORDER_FREIGHT, method = RequestMethod.POST)
    public ResultData getFreight(@RequestParam(value = "venderId", defaultValue = "0") int venderId
            , @RequestBody @Valid GetFreightRequestParams params
            , BindingResult bindingResult) {
        ResultData resultData =  Tools.getThreadResultData();
        if(bindingResult.hasErrors()){
            resultData.setError(ErrorCode.PARAM_ERROR);
            resultData.setMsg(bindingResult.getAllErrors().get(0).getDefaultMessage());
            return resultData;
        }
        if(params.getProductIds().keySet().size()>maxProductType || params.getProductIds().size()<1){
            resultData.setError(ErrorCode.PARAM_ERROR);
            resultData.setMsg("抱歉，无法再添加，最多50种商品。");
            return resultData;
        }
        if(1 > venderId){
            resultData.setError(ErrorCode.PARAM_ERROR);
            resultData.setMsg("venderId错误:"+venderId);
            return resultData;
        }
        return orderService.getVenderFreight(venderId, params);
    }

    @RequestMapping(value = Router.ROUTER_ORDER_FREIGHT_V2, method = RequestMethod.POST)
    public ResultData getFreight(@RequestBody @Valid GetFreightRequestParams params, BindingResult bindingResult) {
        ResultData resultData =  Tools.getThreadResultData();
        if(bindingResult.hasErrors()){
            resultData.setError(ErrorCode.PARAM_ERROR);
            resultData.setMsg(bindingResult.getAllErrors().get(0).getDefaultMessage());
            return resultData;
        }
        if(params.getProductIds().keySet().size()>maxProductType || params.getProductIds().size()<1){
            resultData.setError(ErrorCode.PARAM_ERROR);
            resultData.setMsg("抱歉，无法再添加，最多50种商品。");
            return resultData;
        }
        return orderService.getJdFreight(params);
    }

    private String createAfterSaleUrl(String hqtcCookie, String jdTradeNo, String orderSn) {
        return new StringBuffer(afterSalePageUrl).toString();
    }

    private String createAfterSaleUrl(Integer venderId, String mac) {
        return new StringBuffer(afterSalePageUrl).append("?vender=").append(venderId).append("&mac=").append(mac).toString();
    }

    @GetMapping(Router.BMS_ORDER_SUM_BY_VENDER)
    public int countOrderByVenderId(@RequestParam("venderId") Long venderId){
        int count=orderService.countOrderByVenderId(venderId);
        return count;
    }
    @GetMapping(Router.BMS_PRODUCT_GET_ORDERNO)
    public Result<String> getProductOrderNo(@RequestParam Map<String,Object> params){
        Result<String> result=new Result<>();
        List<String> orderList=orderService.getProductOrderNo(params);
        int total=orderService.countProductOrderNos(params);
        result.setTotal(total);
        result.setRows(orderList);
        return  result;
    }

    //获取商品销量
    @RequestMapping(value = Router.ROUTER_PRODUCT_SALED_COUNT, method = RequestMethod.POST)
    public ResultData getProductSalesVolume(@RequestParam("products")String products,
                                            @RequestParam(value = "venderId", defaultValue = "0")int venderId,
                                            @RequestParam(value = "resultType", defaultValue = "0")int resultType){
        ResultData resultData = Tools.getThreadResultData();
        if(!orderService.isNumericByComma(products)){
            resultData.setError(ErrorCode.PARAM_ERROR);
            resultData.setMsg("参数错误,格式应为被逗号分割的数字");
            return resultData;
        }
        if(0 == resultType){//返回数据为[{"productId":11231, "total":250}]格式
//            List<Map<String, Integer>> skus = orderService.getProductSalesVolumeWrapper(products, venderId);
            List<Map<String, Long>> skus = orderService.getProductSalesVolume(products, venderId);
            resultData.setData(skus);
        }else {//返回数据为[{"11231":250}]
//            Map<String, Integer> skus = orderService.getProductSalesVolume2Wrapper(products, venderId);
            Map<String, Long> skus = orderService.getProductSalesVolume2(products, venderId);
            resultData.setData(skus);
        }
        return resultData;
    }

    @RequestMapping(value = Router.ROUTER_ORDER_DELETE, method = RequestMethod.POST)
    public ResultData orderDetail(@RequestAttribute(value = "userId") Integer userId,
                                  @RequestParam(value = "orderSn")String orderSn) {
        ResultData<GetOrderDetailResponse> resultData = Tools.getThreadResultData();
        int res = orderService.deleteOrder(orderSn, userId);
        if(0 >= res) {
            resultData.setError(ErrorCode.FALI);
            resultData.setMsg("删除订单失败，请重试");
        }
        return resultData;
    }


    @PostMapping(Router.V1_BMS_ORDER_REFUND_LIST)
    ResultData<Result<OrderRefundVo>> listOrderRefund(@RequestParam Map<String, Object> params){
        ResultData<Result<OrderRefundVo>> resultResultData=Tools.getThreadResultData();
        Result<OrderRefundVo> result=new Result<>();
        int total=orderService.countOrderRefund(params);
        if(total==0){
            return resultResultData;
        }
        List<OrderRefundVo> list=orderService.listOrderRefund(params);
        result.setRows(list);
        result.setTotal(total);
        resultResultData.setData(result);
        return resultResultData;
    }
}
