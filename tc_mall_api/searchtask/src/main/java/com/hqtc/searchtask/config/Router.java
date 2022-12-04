package com.hqtc.searchtask.config;

public class Router {

    /////////////////////task////////////////////////
    public static final String V1_SEARCH_TASK_LIST = "/v1/search/task/list";
    public static final String V1_SEARCH_TASK_COUNT = "/v1/search/task/count";
    public static final String V1_SEARCH_TASK_GET = "/v1/search/task/get";
    public static final String V1_SEARCH_TASK_SAVE = "/v1/search/task/save";
    public static final String V1_SEARCH_TASK_UPDATE = "/v1/search/task/update";
    public static final String V1_SEARCH_TASK_REMOVE = "/v1/search/task/remove";
    public static final String V1_SEARCH_TASK_BATCH_REMOVE = "/v1/search/task/batchremove";
    public static final String V1_SEARCH_TASK_CHANGE_STATUS = "/v1/search/task/changestatus";

    /////////////////////product detail////////////////
    public static final String V1_JDSYNC_PRODUCT_ES_GET ="/v1/jdsync/product/es/get";
    public static final String V1_JDSYNC_PRODUCT_ES_LIST ="/v1/jdsync/product/es/list";

    //获取商品运营价格
    public static final String  V1_OMS_PRODUCT_BATCH_PRICE = "/v1/oms/product/batchprice/{venderId}";
    public static final String  V1_OMS_PRODUCT_PRICE = "/v1/oms/product/price/{venderId}";

    public static final String V1_OMS_LIST_VENDERID = "/v1/oms/listvenderid";

    public static final String V1_OMS_PRODUCT_OF_COLLECTION_LIST = "/v1/oms/productofcollection/list";

    public static final String V1_OMS_PRODUCT_REMOVE_COUNT = "/v1/oms/productremove/count";

    public static final String V1_OMS_PRODUCT_OF_COLLECTION_ID_BY_SKU = "/v1/oms/productofcollection/collectionid";

    //获取商品的销量
    public static final String ROUTER_PRODUCT_SALED_COUNT = "/v1/bms/product/volume";
}
