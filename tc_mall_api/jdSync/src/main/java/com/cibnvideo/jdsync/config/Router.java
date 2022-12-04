package com.cibnvideo.jdsync.config;

public class Router {

    ///////////////////////address//////////////////////
    public static final String V1_JDSYNC_ADDRESS_PROVINCELIST = "/v1/jdsync/address/provincelist";
    public static final String V1_JDSYNC_ADDRESS_PROVINCE = "/v1/jdsync/address/province";
    public static final String V1_JDSYNC_ADDRESS_CITYLIST = "/v1/jdsync/address/citylist";
    public static final String V1_JDSYNC_ADDRESS_CITY = "/v1/jdsync/address/city";
    public static final String V1_JDSYNC_ADDRESS_COUNTYLIST = "/v1/jdsync/address/countylist";
    public static final String V1_JDSYNC_ADDRESS_COUNTY = "/v1/jdsync/address/county";
    public static final String V1_JDSYNC_ADDRESS_TOWNLIST = "/v1/jdsync/address/townlist";
    public static final String V1_JDSYNC_ADDRESS_TOWN = "/v1/jdsync/address/town";
    public static final String V1_JDSYNC_ADDRESS_INFO = "/v1/jdsync/address/info";

    //////////////////////picture///////////////////////
    public static final String V1_JDSYNC_PICTURE_MOBILE = "/v1/jdsync/picture/mobile";
    public static final String V1_JDSYNC_PICTURE_PC = "/v1/jdsync/picture/pc";

    //////////////////////category/////////////////////
    public static final String V1_JDSYNC_CATEGORY_LIST = "/v1/jdsync/category/list";
    public static final String V1_JDSYNC_CATEGORY_SEARCH = "/v1/jdsync/category/search";
    public static final String V1_JDSYNC_CATEGORY_GET = "/v1/jdsync/category/get";
    public static final String V1_JDSYNC_CATEGORY_LIST_BY_CATIDS = "/v1/jdsync/category/listbycatids";
    /////////////////////product detail////////////////
    public static final String V1_JDSYNC_PRODUCT_DETAILINFOLIST = "/v1/jdsync/product/detailinfolist";
    public static final String V1_JDSYNC_PRODUCT_DETAILINFO_BY_SKUS = "/v1/jdsync/product/detailinfobyskus";
    public static final String V1_JDSYNC_PRODUCT_DETAILINFOSEARCH = "/v1/jdsync/product/detailinfosearch";
    public static final String V1_JDSYNC_PRODUCT_DETAILINFO = "/v1/jdsync/product/detailinfo";
    public static final String V1_JDSYNC_PRODUCT_DETAILINFOBYSKUS = "/v1/jdsync/product/detailinfobyskus";
    public static final String V1_JDSYNC_PRODUCT_DETAILLIST = "/v1/jdsync/product/detaillist";
    public static final String V1_JDSYNC_PRODUCT_DETAIL = "/v1/jdsync/product/detail";
    public static final String V1_JDSYNC_PRODUCT_BATCH_PRICE = "/v1/jdsync/product/batchprice";
    public static final String V1_JDSYNC_PRODUCT_PRICE = "/v1/jdsync/product/price";
    public static final String V1_JDSYNC_PRODUCT_BRANDNAME_BY_CAT = "/v1/jdsync/product/brandnamebycat";
    public static final String V1_JDSYNC_PRODUCT_BRANDNAME_BY_SKUS = "/v1/jdsync/product/brandnamebyskus";
    public static final String GOODS_PRODUCT_CTIME_GET ="/v1/jdsync/product/getProductCtime";
    public static final String V1_JDSYNC_SALES_MANAGER__PRODUCT_DETAIL ="/v1/jdsync/sales/salesManagerProductDetail";
    public static final String V1_JDSYNC_SALES_MANAGER_LIST ="/v1/jdsync/sales/getSalesManagerList";
    public static final String V1_JDSYNC_PRODUCT_ES_GET ="/v1/jdsync/product/es/get";
    public static final String V1_JDSYNC_PRODUCT_ES_LIST ="/v1/jdsync/product/es/list";
    public static final String V1_JDSYNC_PRODUCT_VIDEO_ADD = "/v1/jdsync/product/video/add";
    public static final String V1_JDSYNC_PRODUCT_DETAIL_LIST = "/v1/jdsync/product/detail/list";
    public static final String V1_JDSYNC_PRODUCT_COUNT ="/v1/jdsync/product/detail/count";
    public static final String V1_JDSYNC_PRODUCT_SKULIST ="/v1/jdsync/product/detail/getSkus";

    /////////////////////style////////////////////////
    public static final String V1_JDSYNC_STYLE_MOBILE = "/v1/jdsync/style/mobile";
    public static final String V1_JDSYNC_STYLE_PC = "/v1/jdsync/style/pc";

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
}
