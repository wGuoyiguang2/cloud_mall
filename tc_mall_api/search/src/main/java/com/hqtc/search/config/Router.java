package com.hqtc.search.config;

/**
 * Created by makuan on 18-8-8.
 */
public class Router {
    //搜索
    public static final String ROUTE_SEARCH_FUZZYSEARCH = "/v1/search/fuzzysearch";
    //获取分类id列表接口
    public static final String ROUTE_SEARCH_CATE_LIST = "/v1/search/catelist";
    //根据sku搜索商品
    public static final String ROUTE_SEARCH_PRODUCT_BY_SKU = "/v1/search/skus";
    //根据分类id获取品牌
    public static final String ROUTE_SEARCH_BRANDNAME_BY_CATE = "/v1/search/brandlist";
}
