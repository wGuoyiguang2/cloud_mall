package com.hqtc.bms.config;

/**
 * Created by wanghaoyang on 19-1-25.
 */
public class VenderRouter {

    public static final String ROUTER_VENDER_BALANCE = "/v1/bms/vender/balance";
    public static final String ROUTER_VENDER_ORDER  = "/v1/bms/vender/order";
    public static final String ROUTER_VENDER_NOTIFY  = "/v1/bms/vender/order/notify";
    public static final String ROUTER_VENDER_TRACK  = "/v1/bms/vender/order/track";
    public static final String ROUTER_ORDER_CANCEL = "/v1/bms/vender/order/cancel";
    public static final String ROUTER_ORDER_DETAIL = "/v1/bms/vender/order/detail";

    public static final String AFTER_SALE_SERVICETYPE = "/v1/vender/afterSale/service";
    public static final String AFTER_SALE_LIST = "/v1/vender/afterSale/list";
    public static final String AFTER_SALE_ISCAN = "/v1/vender/afterSale/isCan";
    public static final String AFTER_SALE_APPLYAFTERSALE="/v1/vender/afterSale/applyAfterSale";
    public static final String AFTER_SALE_CANCELSERVICE="/v1/vender/afterSale/cancelService";
}
