package com.hqtc.bms.config;

/**
 * Created by makuan on 18-6-20.
 */
public class Router {

    //下单(to client)
    public static final String ROUTER_ORDER = "/v1/bms/pay/order";
    //秒杀下单
    public static final String V1_BMS_FLASHSALE_ORDER_CREATE = "/v1/bms/flashSale/pay/order";
    //生成二维码(内部调用pms生成二维码)
    public static final String ROUTER_CREATE_PAY_QR_CODE = "/v1/bms/pay/multiple/qrcode";
    //混合支付(购物卡,扫码),应用于大屏端扫码
    public static final String ROUTER_CREATE_PAY_MUTIPLE_QRCODE = "/v1/bms/pay/qrcode";
    //混合支付(购物卡,跳转),应用于APP,小程序等
    public static final String ROUTER_CREATE_PAY_MUTIPLE_AUTH = "/v1/bms/pay/multiple/auth";
    //支付成功后pms的回调接口
    public static final String ROUTER_NOTIFY_NEW = "/v1/bms/pay/notify";
    //轮询订单是否支付成功
    public static final String ROUTER_SCAN = "/v1/bms/pay/scan";


    //电子发票
    public static final String V1_INVOICE_ADD="/v1/invoice/add";
    public static final String V1_INVOICE_GETRESULT="/v1/invoice/getResult";
    public static final String V1_INVOICE_GETFPQQLSH="/v1/invoice/getFpqqlsh";
    public static final String V1_INVOICE_SAVE="/v1/invoice/save";

    //--------------------
    //查询订单详情
    public static final String ROUTER_ORDER_DETAIL = "/v1/bms/order/detail";
    //根据京东订单号,和商品编号查询订单详情
    public static final String ROUTER_PRODUCT_ORDER_DETAIL = "/v1/bms/order/product";
    //获取用户订单列表
    public static final String ROUTER_ORDER_LIST = "/v1/bms/order/list";
    //查询京东订单信息
    public static final String ROUTER_JD_ORDER_DETAIL = "/v1/bms/order/jddetail";
    //查询库存
    public static final String ROUTER_JD_SEARCH_STOCK = "/v1/bms/stock/detail";
    //取消订单
    public static final String ROUTER_ORDER_CANCEL = "/v1/bms/order/cancel";
    //查询运费(内含大客户包邮逻辑)
    public static final String ROUTER_ORDER_FREIGHT = "/v1/bms/order/freight";
    //查询运费
    public static final String ROUTER_ORDER_FREIGHT_V2 = "/v2/bms/order/freight";
    //向终端用户退款
    public static final String ROUTER_USER_REFUND = "/v1/refund/user";
    //向商户退款
    public static final String ROUTER_VENDER_REFUND = "/v1/refund/vender";
    //退款成功后pms的回调接口
    public static final String ROUTER_REFUND_NOTIFY = "/v1/bms/refund/notify";
    //退款接口
    public static final String ROUTER_REFUND = "/v1/bms/order/refund";
    //删除订单
    public static final String ROUTER_ORDER_DELETE = "/v1/bms/order/delete";
    //查询退款状态
    public static final String ROUTER_REFUND_STATE = "/v1/bms/order/refund/state";
    //退款重试
    public static final String ROUTER_REFUND_RETRY_USER = "/v1/bms/order/refund/retry/user";
    public static final String ROUTER_REFUND_RETRY_VENDER = "/v1/bms/order/refund/retry/vender";

    //售后(JD)
    public static final String JDAFTERSALE_ISCAN="/v1/jDafterSale/isCan";
    public static final String JDAFTERSALE_GETJDMETHOD="/v1/jDafterSale/getJdMethod";
    public static final String JDAFTERSALE_GETJDCUSTOMERTYPE="/v1/jDafterSale/getJdCustomerType";
    public static final String JDAFTERSALE_GETSERVICELISTPAGE="/v1/jDafterSale/getServiceListPage";
    public static final String JDAFTERSALE_GETSERVICEDETAIL="/v1/jDafterSale/getServiceDetail";
    public static final String JDAFTERSALE_CANCELSERVICE="/v1/jDafterSale/cancelService";
    public static final String JDAFTERSALE_ADDSENDSKU="/v1/jDafterSale/addSendSku";
    public static final String JDAFTERSALE_APPLYAFTERSALE="/v1/jDafterSale/applyAfterSale";

    //售后(bms)
    public static final String AFTERSALE_ISCAN="/v1/afterSale/isCan";
    public static final String AFTERSALE_VIEW="/v1/afterSale/view";
    public static final String AFTERSALE_GETSERVICELISTPAGE="/v1/afterSale/getServiceListPage";
    public static final String AFTERSALE_GETSERVICEDETAIL="/v1/afterSale/getServiceDetail";
    public static final String AFTERSALE_CANCELSERVICE="/v1/afterSale/cancelService";
    public static final String AFTERSALE_ADDSENDSKU="/v1/afterSale/addSendSku";
    public static final String AFTERSALE_APPLYAFTERSALE="/v1/afterSale/applyAfterSale";
    public static final String AFTER_SALE_LISTBYORDERSN = "/v1/afterSale/listByOrderSn";
    public static final String AFTER_SALE_LISTBYUSERID = "/v1/afterSale/listByUserId";

    //申请售后原因
    public static final String AFTER_SALE_REASON = "/v1/afterSale/reason";

    //物流
    public static final String ORDERTRACK_INFO="/v1/order/track";
    public static final String JDORDERTRACK_INFO="/order/getOrderTrack";

    //余额信息
    public static final String V1_PRICE_BALANCE = "/v1/bms/price/balcance";

    // 订单管理列表
    public static final String BMS_ORDER_MANAGER_LIST ="/v1/bms/order/orderManagerList";
    public static final String BMS_ORDER_MANAGER_VENDER_LIST = "/v1/bms/order/venderOrderManagerList";
    public static final String BMS_ORDER_SUM_BY_VENDER ="/v1/bms/countOrderByVenderId";

    //获取售后二维码
    public static final String ROUTE_GET_AFTER_SALE_QRCODE = "/v1/afterSale/qrcode";
    //发票管理
    public static final String BMS_INVOICE_MANAGER_LIST ="/v1/bms/invoice/invoiceManagerList";
    public static final String BMS_INVOICE_INFO_MANAGER_GET ="/v1/bms/getInvoiceInfo";
    public static final String BMS_INVOICE_MANAGER_REMOVE ="/v1/bms/invoice/remove";
    //账期结算
    public static final String BMS_PRODUCT_GET_ORDERNO = "/v1/bms/product/getProductOrderNo";

    //获取商品的销量
    public static final String ROUTER_PRODUCT_SALED_COUNT = "/v1/bms/product/volume";
    //销量管理，获取总销售金额和盈利金额
    public static final String V1_BMS_PRODUCT_SALES_AMOUNT ="/v1/bms/sales/getSalesAmount";
    //销量管理，获取销量管理列表
    public static final String V1_BMS_PRODUCT_SALES_LIST ="/v1/bms/sales/salesManagerList";
    public static final String V1_BMS_PRODUCT_SALES_GET ="/v1/bms/sales/getProductInfo";

    //京东消息接口
    public static final String ROUTER_JD_MESSAGE = "/v1/bms/jd/message";

    //查询地址接口
    public static final String ROUTER_ADDRESS_INFO = "/address/info";
    //发票详情
    public static final String ROUTER_INVOICE_DETAIL = "/invoice/detail";

    //购物卡相关
    //获取用户的购物卡
    public static final String ROUTER_CARD_ALL = "/v1/bms/card";
    //获取某张购物卡的详情,绑定充值卡
    public static final String ROUTER_CARD_DETAIL = "/v1/bms/card/record";
    //绑定充值卡
    public static final String ROUTER_CARD_BIND = "/v1/bms/card/bind";
    //通过卡密查询购物卡的余额
    public static final String ROUTER_CARD_INFO = "/v1/bms/card/info";


    //购物卡
    public static final String V1_BMS_CARD_BATCH_MANAGER_LIST ="/v1/bms/card/listManagerCardBatch";
    public static final String V1_BMS_CARD_MANAGER_LIST ="/v1/bms/card/listManagerCard";
    public static final String V1_BMS_CARD_BATCH_ADD_CARD="/v1/bms/cardBatch/addCard";
    public static final String V1_BMS_CARD_BATCH_ADMIN_RECORD_LIST ="/v1/bms/cardAdminRecord/batchList";
    public static final String V1_BMS_CARD_BATCH_OPERATE ="/v1/bms/cardbatch/operate";
    public static final String V1_BMS_CARD_BATCH_GETBY_ID ="/v1/bms/cardbatch/getById";
    public static final String V1_BMS_CARD_OPERATE ="/v1/bms/card/operate";
    public static final String V1_BMS_CARD_BATCH_ADMIN_RECORD_DETAIL ="/v1/bms/cardBatchAdminRecord";
    public static final String V1_BMS_CARD_GET_BALANCE_BY_CARDNOS ="/v1/bms/card/getBalanceByCardNos";
    public static final String V1_BMS_CARD_BATCH_DETAIL ="/v1/bms/cardBatchDetail";
    public static final String V1_BMS_CARD_LIST ="/v1/bms/card/list";
    public static final String V1_BMS_CARD_ADMIN_OPERATE_ADD ="/v1/bms/cardAdminRecord/add";
    public static final String V1_BMS_CARD_BY_CARDNOS ="/v1/bms/card/listCardsByCardNos";
    public static final String V1_BMS_CARD_USER_RECORD_LIST ="/v1/bms/cardUserRecord/list";
    public static final String V1_BMS_CARD_DETAIL ="/v1/bms/cardDetail";
    public static final String V1_BMS_CARD_DETAIL_LIST ="/v1/bms/cardDetailList";
    public static final String V1_BMS_AFTER_SALES_MANAGER_LIST ="/v1/bms/afterSale/managerList";
    public static final String V1_BMS_ORDER_REFUND_LIST ="/v1/bms/orderRefund/list";
}
