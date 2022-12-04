package com.cibnvideo.jd.common.constants;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/6/22 15:01
 */
public class PathConstants {
    private PathConstants() {
    }

    public static final String GOODS_PRICE_GET = "/goods/price/get";
    public static final String GOODS_STOCK_GET = "/goods/stock/get";
    public static final String GOODS_ADDRESS_ONE_LEVEL_GET = "/goods/address/getOneLevel";
    public static final String GOODS_ADDRESS_TWO_LEVEL_GET = "/goods/address/getTwoLevel";
    public static final String GOODS_ADDRESS_THREE_LEVEL_GET = "/goods/address/getThreeLevel";
    public static final String GOODS_ADDRESS_FOUR_LEVEL_GET = "/goods/address/getFourLevel";
    public static final String GOODS_ORDER_STOCK_GET = "/goods/stock/getOrderStock";
    public static final String GOODS_TAX_PRICE_GET = "/goods/price/getTaxPrices";
    public static final String ORDER_INFO_GET = "/order/getOrderInfo";
    //商品搜索接口
    public static final String V1_PRODUCT_SEARCH = "/v1/product/search";

    public static final String GOODS_PRODUCT_POOL_GET_PAGENUM = "/goods/getProductPoolPageNum";
    public static final String GOODS_SKUS_BY_PAGENUM = "/goods/getSkusByPageNum";
    public static final String GOODS_PRODUCT_DETAIL_GET = "/goods/getProductDetail";
    public static final String GOODS_PC_STYLE_GET = "/goods/getPCStyle";
    public static final String GOODS_MOBILE_STYLE_GET = "/goods/getMobileStyle";
    public static final String GOODS_PC_PRODUCT_DETAIL_IMG = "/goods/getPCProductDetailIMG";
    public static final String GOODS_H5_PRODUCT_DETAIL_IMG = "/goods/getH5ProductDetailIMG";
    public static final String GOODS_SKUS_BY_PAGE = "/goods/getSkusByPage";
    public static final String GOODS_STATE_GET = "/goods/getProductState";
    public static final String TOKEN_GET = "/token/getToken";
    public static final String GOODS_IMG_BY_SKU = "/goods/getImgBySku";
    public static final String GOODS_COMMENTS_GET = "/goods/getProductComments";
    public static final String GOODS_AREA_LIMIT_GET = "/goods/getAreaLimit";
    public static final String GOODS_IS_COD_GET = "/goods/getIsCod";
    public static final String GOODS_GIFT_GET = "/goods/getGiftBySku";
    public static final String GOODS_FREIGHT_GET = "/goods/getFreight";
    public static final String GOODS_SELL_CHECK_GET = "/goods/getSellCheck";
    public static final String GOODS_CALENDAR_GET = "/goods/getCalendar";
    public static final String GOODS_CATEGORY_GET = "/goods/getCategory";
    public static final String GOODS_CATEGORY_PAGE_GET = "/goods/getCategoryPage";
    public static final String GOODS_MESSAGE_GET = "/goods/getMessage";
    public static final String GOODS_MESSAGE_DELETE = "/goods/deleteMessage";
    public static final String ORDER_SUBMIT = "/order/submitOrder";
    public static final String CONFIRM_OCCUPY_STOCK = "/order/confirmOccupyStock";
    public static final String ORDER_PAY = "/order/pay";
    public static final String ORDER_CANCEL = "/goods/cancelOrder";
    //售后
    public static final String ISCAN = "/v1/jDafterSale/isCan";
    public static final String GETJDMETHOD = "/v1/jDafterSale/getJdMethod";
    public static final String GETJDCUSTOMERTYPE = "/v1/jDafterSale/getJdCustomerType";
    public static final String GETSERVICELISTPAGE = "/v1/jDafterSale/getServiceListPage";
    public static final String GETSERVICEDETAIL = "/v1/jDafterSale/getServiceDetail";
    public static final String CANCELSERVICE = "/v1/jDafterSale/cancelService";
    public static final String ADDSENDSKU = "/v1/jDafterSale/addSendSku";
    public static final String APPLYAFTERSALE = "/v1/jDafterSale/applyAfterSale";
    //余额
    public static final String BALANCE_GET = "/balance/getBalance";
    public static final String ORDER_ID_GET = "/order/getOrderIdByThirdId";
    public static final String ORDER_TRACK_GET = "/order/getOrderTrack";
    public static final String GOODS_SIMILAR_GET = "/goods/getSimilarGoods";
    public static final String GOODS_YANBAO_GET = "/goods/yanbaoList";
    public static final String BALANCE_GET_JINCAI = "/balance/getJincaiInfo";
    public static final String BALANCE_GET_INFO = "/balance/getBalanceInfo";
}
