package com.cibnvideo.jdsynctask.config;

public class Router {


    /////////////////////task////////////////////////
    public static final String V1_JDSYNC_TASK_LIST = "/v1/jdsync/task/list";
    public static final String V1_JDSYNC_TASK_COUNT = "/v1/jdsync/task/count";
    public static final String V1_JDSYNC_TASK_GET = "/v1/jdsync/task/get";
    public static final String V1_JDSYNC_TASK_SAVE = "/v1/jdsync/task/save";
    public static final String V1_JDSYNC_TASK_UPDATE = "/v1/jdsync/task/update";
    public static final String V1_JDSYNC_TASK_REMOVE = "/v1/jdsync/task/remove";
    public static final String V1_JDSYNC_TASK_BATCH_REMOVE = "/v1/jdsync/task/batchremove";
    public static final String V1_JDSYNC_TASK_CHANGE_STATUS = "/v1/jdsync/task/changestatus";

    ////////////////////jd service//////////////////////////
    public static final String GOODS_PRICE_GET = "/goods/price/get";
    public static final String GOODS_STOCK_GET = "/goods/stock/get";
    public static final String GOODS_ADDRESS_ONE_LEVEL_GET = "/goods/address/getOneLevel";
    public static final String GOODS_ADDRESS_TWO_LEVEL_GET = "/goods/address/getTwoLevel";
    public static final String GOODS_ADDRESS_THREE_LEVEL_GET = "/goods/address/getThreeLevel";
    public static final String GOODS_ADDRESS_FOUR_LEVEL_GET = "/goods/address/getFourLevel";
    public static final String GOODS_ORDER_STOCK_GET = "/goods/stock/getOrderStock";
    public static final String GOODS_TAX_PRICE_GET = "/goods/price/getTaxPrices";
    public static final String ORDER_INFO_GET ="/order/getOrderInfo";
    public static final String V1_PRODUCT_SEARCH ="/v1/product/search";
    public static final String GOODS_PRODUCT_POOL_GET_PAGENUM ="/goods/getProductPoolPageNum" ;
    public static final String GOODS_SKUS_BY_PAGENUM ="/goods/getSkusByPageNum";
    public static final String GOODS_PRODUCT_DETAIL_GET ="/goods/getProductDetail";
    public static final String GOODS_PC_STYLE_GET ="/goods/getPCStyle";
    public static final String GOODS_MOBILE_STYLE_GET ="/goods/getMobileStyle";
    public static final String GOODS_PC_PRODUCT_DETAIL_IMG = "/goods/getPCProductDetailIMG";
    public static final String GOODS_H5_PRODUCT_DETAIL_IMG = "/goods/getH5ProductDetailIMG";
    public static final String GOODS_SKUS_BY_PAGE ="/goods/getSkusByPage";
    public static final String GOODS_STATE_GET ="/goods/getProductState" ;
    public static final String TOKEN_GET ="/token/getToken";
    public static final String GOODS_IMG_BY_SKU ="/goods/getImgBySku";
    public static final String GOODS_COMMENTS_GET ="/goods/getProductComments";
    public static final String GOODS_AREA_LIMIT_GET = "/goods/getAreaLimit";
    public static final String GOODS_IS_COD_GET = "/goods/getIsCod";
    public static final String GOODS_GIFT_GET = "/goods/getGiftBySku";
    public static final String GOODS_FREIGHT_GET = "/goods/getFreight";
    public static final String GOODS_SELL_CHECK_GET = "/goods/getSellCheck";
    public static final String GOODS_CALENDAR_GET ="/goods/getCalendar";
    public static final String GOODS_CATEGORY_GET = "/goods/getCategory";
    public static final String GOODS_CATEGORY_PAGE_GET ="/goods/getCategoryPage";
    public static final String GOODS_MESSAGE_GET = "/goods/getMessage";
    public static final String GOODS_MESSAGE_DELETE ="/goods/deleteMessage";

    //第三方服务接口
    public static final String V1_3RD_PART_JD_SITE_PRICE_LIST ="/v1/3rdPart/jdSitePrice/list";
}
