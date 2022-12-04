package com.hqtc.bms.config;

/**
 * Created by wanghaoyang on 18-7-4.
 */
public class RPCRouter {

    //批量获取商品信息
    public final static String ROUTER_PRODUCT_INFO = "/v1/jdsync/product/detailinfobyskus";
    //获取商家产品价格
    public final static String ROUTER_OMS_PRODUCT_BATCH_PRICE = "/v1/oms/product/batchprice/{venderId}";
    //获取厂商的支付凭证
    public final static String V1_OMS_VENDER_PAYMENT_GET = "/v1/oms/venderpayment/get";
    //向pms下单(二维码)
    public final static String ROUTER_PMS_ORDER = "/api/tcmall/pay/qrCode";
    //获取收获地址ims
    public static final String V1_ADDRESSDETAIL = "/address/detail";
    //向pms下单(小程序)
    public final static String ROUTER_PMS_ORDER_AUTH = "/api/tcmall/pay/wechat/applet";
    //京东统一下单接口
    public static final String JD_ORDER_SUBMIT ="/order/submitOrder";
    //支付完成接口
    public static final String ORDER_PAY ="/order/pay";
    //获取地址接口
    public static final String V1_ADDRESS_INFO = "/address/info";
    //查询京东订单状态
    public static final String ORDER_INFO_GET ="/order/getOrderInfo";
    //确认预占库存订单
    public static final String CONFIRM_OCCUPY_STOCK ="/order/confirmOccupyStock";
    //查询京东库存接口
    public static final String GOODS_ORDER_STOCK_GET = "/goods/stock/getOrderStock";
    //取消订单
    public static final String ORDER_CANCEL = "/goods/cancelOrder";
    //查询运费接口
    public static final String GOODS_FREIGHT_GET = "/goods/getFreight";
    //增加余额
    public static final String  V1_OMS_VENDER_SETTLEMENT_BALANCE_ADD = "/v1/oms/vendersettlement/balance/add/{venderId}";
    //减少余额
    public static final String  V1_OMS_VENDER_SETTLEMENT_BALANCE_REDUCE = "/v1/oms/vendersettlement/balance/reduce/{venderId}";
    //获取厂商余额
    public static final String  V1_OMS_VENDER_SETTLEMENT_BALANCE_GET = "/v1/oms/vendersettlement/balance/get";
    //获取结算方式
    public static final String  V1_OMS_VENDER_SETTLEMENT_GET = "/v1/oms/vendersettlement/get";

    //售后原因配置
    public static final String V1_OMS_AFTERSALE_CONFIG = "/v1/oms/aftersale/config";
    //tcmall退款给用户接口
    public static final String ROUTER_TC_MALL_PAY_REFUND = "/api/tcmall/pay/refund";
    //结算账单
    public static final String  V1_OMS_VENDER_SETTLEMENT_ACCOUNT = "/v1/oms/vendersettlement/account";

    //获取京东消息
    public static final String GOODS_MESSAGE_GET = "/goods/getMessage";
    //删除京东消息
    public static final String GOODS_MESSAGE_DELETE ="/goods/deleteMessage";
    //获取包邮价格
    public static final String V1_OMS_FREEFREIGHT_GETBY_VENDERID ="/v1/oms/freeFreightManager/getByVenderId";
    //获取商家实际支付给京东包邮价格的总和
    public static final String V1_OMS_FREEFREIGHT_SUMBY_VENDERID ="/v1/oms/freeFreightManagesumFreightr/ByVenderId";
    //根据uid查询用户信息
    public static final String ROUTER_USER_INFO = "/v1/user/info";
    //删除购物车的商品
    public static final String ROUTE_CART_DELETE_BY_USERID = "/v1/ims/cart/delete";
    //搜索商品
    public static final String ROUTER_SEARCH_PRODUCT = "/v1/search/skus";
    //获取厂商用户
    public static final String ROUTER_GET_VENDER_INFO = "/v1/vender/info";
    //查询微信退款是否成功
    public static final String ROUTER_REFUND_WECHAT_QUERY = "/api/tcmall/refund/query/wechat";
    //查询支付宝退款是否成功
    public static final String ROUTER_REFUND_ALIPAY_QUERY = "/api/tcmall/refund/query/alipay";
}
