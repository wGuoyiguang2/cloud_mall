package com.hqtc.cms.config;

/**
 * description:路径常用类
 * Created by laiqingchuang on 18-6-20.
 */
public class Router {
    //商品分类
    public static final String ROUTE_PRODUCT_CATE_LIST = "/v1/product/cate/list";
    public static final String ROUTE_PRODUCT_CATE1_LIST = "/v1/product/cate1/list";
    public static final String ROUTE_PRODUCT_LIST = "/v1/product/list";
    public static final String ROUTE_PRODUCT_BRANDLIST = "/v1/product/brandlist";
    public static final String ROUTE_PRODUCT_DETAILPC = "/v1/product/detailPc";
    public static final String ROUTE_PRODUCT_DETAILMOBILE = "/v1/product/detailMobile";
    public static final String ROUTE_PRODUCT_SIMILARGOODS ="/v1/product/similarGoods";
    public static final String ROUTE_PRODUCT_HOTSEARCH ="/v1/product/hotSearch";
    public static final String ROUTE_PRODUCT_WARRANTY ="/v1/product/warranty";
    public static final String ROUTE_PRODUCT_SYNCLIST ="/v1/product/syncList";

    public static final String V1_OMS_CATEGORY_PICTURE_BY_VENDERID = "/v1/oms/category/picture/{venderId}";
    public static final String V1_OMS_PRODUCT_HOTSEARCH="/v1/oms/hotSearch/listByVenderId";
    public static final String V1_JDSYNC_CATEGORY_LIST = "/v1/jdsync/category/list";
    public static final String V1_JDSYNC_PRODUCT_DETAILINFOLIST = "/v1/jdsync/product/detailinfolist";
    public static final String V1_JDSYNC_PRODUCT_DETAILINFO = "/v1/jdsync/product/detail";
    public static final String V1_JDSYNC_PRODUCT_BRANKNAME = "/v1/jdsync/product/brandnamebycat";
    public static final String V1_JDSYNC_PRODUCT_DETAILINFOBYSKUS = "/v1/jdsync/product/detailinfobyskus";
    public static final String GOODS_GETSIMILARGOODS = "goods/getSimilarGoods";
    public static final String GOODS_GETWARRANTYLIST = "goods/yanbaoList";
    public static final String V1_JDSYNC_PRODUCT_DETAILINFOSEARCH = "/v1/jdsync/product/detailinfosearch";

    public static final String V1_JDSYNC_PICTURE_PC = "/v1/jdsync/picture/pc";
    public static final String V1_JDSYNC_PICTURE_MOBILE = "/v1/jdsync/picture/mobile";
    public static final String V1_JDSYNC_STYLE_PC = "/v1/jdsync/style/pc";
    public static final String V1_JDSYNC_STYLE_MOBILE = "/v1/jdsync/style/mobile";
    public static final String V1_JDSYNC_PRODUCT_DETAIL_LIST = "/v1/jdsync/product/detail/list";

    //商品集
    public static final String ROUTE_PRODUCTOFCOLLECTION_LIST = "/v1/product/setlist";
    public static final String V1_OMS_PRODUCTCOLLECTION_LIST = "/v1/oms/productcollection/list";
    public static final String V1_OMS_PRODUCTCOLLECTION_GET="/v1/oms/productcollection/get";
    public static final String V1_OMS_PRODUCTOFCOLLECTION_LIST="/v1/oms/productofcollection/list";
    public static final String V1_OMS_PRODUCTCOLLECTION_BRANDNAMES="/v1/oms/productcollection/brandnames";
    public static final String V1_OMS_PRODUCT_REMOVESKUS="/v1/oms/productremove/skus/{venderId}";
    public static final String V1_OMS_PRODUCT_COLLECTION_BATCHPRICE="/v1/oms/product/batchprice/{venderId}";
    public static final String V1_OMS_PRODUCT_COLLECTION_PRICE="/v1/oms/product/price/{venderId}";

    //关于我们
    public static final String ROUTE_GET_OMS_CONTACT = "/v1/oms/customerinfo/get";
    public static final String ROUTE_GET_CONTACT = "/v1/center/contact";

    //帮助中心
    public static final String ROUTE_GET_OMS_ASSISTANCE = "/v1/oms/helpcenter/get";
    public static final String ROUTE_GET_ASSISTANCE = "/v1/center/assist/list";
    public static final String ROUTE_GET_OMS_ASSISTANCE_DETAIL = "/v1/oms/helpcenterinfo/get";
    public static final String ROUTE_GET_ASSISTANCE_DETAIL = "/v1/center/assist/detail";

    //首页推荐
    public static final String ROUTE_GET_OMS_HOME_RECOMMEND = "/v1/oms/home/recommend";
    public static final String ROUTE_GET_HOME_RECOMMEND = "/v1/home/recommend";

    //往期推荐
    public static final String ROUTE_POST_OMS_HISTORY_RECOMMEND = "/v1/oms/recommendhistory/listbyday";
    public static final String ROUTE_GET_HOME_HISTORY_RECOMMEND = "/v1/home/recommendhistory";

    //搜索
    public static final String ROUTE_SEARCH_PRODUCT = "/v1/search/product";
    public static final String ROUTE_SEARCH_PRODUCT_SEARCH = "/v1/search/fuzzysearch";
    public static final String ROUTE_SEARCH_PRODUCT_CATELIST ="/v1/search/catelist";
    public static final String ROUTE_SEARCH_PRODUCT_SKUS="/v1/search/skus";
    public static final String ROUTE_SEARCH_BRANDLIST="/v1/search/brandlist";

    //同步搜索记录
    public static final String ROUTE_SYNC_SEARCH_HISTORY = "/v1/searchHistory/create";
    //获取商品入库时间
    public static final String V1_JDSYNC_PRODUCT_CTIME_GET ="/v1/jdsync/product/getProductCtime";
    //获取下架商品列表
    public static final String V1_OMS_PRODUCT_REMOVE_LIST ="/v1/oms/productremove/list";
    public static final String ROUTER_GET_SEARCH_QRCODE = "/v1/search/qrcode";
    //秒杀
    public static final String V1_FLASHSALE ="/v1/product/flashSale";
    public static final String V1_BMS_FLASHSALE_ORDER_CREATE = "/v1/bms/flashSale/pay/order";
    public static final String V1_FLASHSALE_ORDER_STATE_GET ="/v1/product/flashSale/orderState";
    public static final String V1_FLASHSALE_START ="/v1/cms/flashSale/start";
    public static final String V1_FLASHSALE_END ="/v1/cms/flashSale/end";
    //查询订单详情
    public static final String ROUTER_ORDER_DETAIL = "/v1/bms/order/detail";

}
