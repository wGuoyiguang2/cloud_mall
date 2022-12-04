package com.hqtc.ims.common.constant;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/6/25 19:39
 */
public class PathConstants {
    public static final String USER_FAVORITE_LIST_GOODS="/v1/favorite/list";
    public static final String FEIGN_USER_FAVORITE_LIST="/v1/jdsync/product/detailinfobyskus";
    public static final String USER_FAVORITE_ADD = "/v1/favorite/add";
    public static final String USER_FAVORITE_DELETE = "/v1/favorite/delete";
    public static final String USER_FAVORITE_IS_EXIST ="/v1/favorite/check";
    //地址管理
    public static final String V1_ADDADDRESS = "/v1/address/add";
    public static final String V1_ADDRESSDETAIL = "/v1/address/detail";
    public static final String V1_UPDATEADDRESS = "/v1/address/update";
    public static final String V1_DELETEADDRESS = "/v1/address/delete";
    public static final String V1_ADDRESSLIST = "/v1/address/list";
    public static final String V1_SETDEFAULT = "/v1/address/setDefault";
    //bms下单调用
    public static final String V1_ADDRESSDETAIL_NOLOGIN = "/address/detail";
    //省市区乡
    public static final String V1_ADDRESS_PROVINCELIST = "/v1/address/provincelist";
    public static final String V1_ADDRESS_PROVINCE = "/v1/address/province";
    public static final String V1_ADDRESS_CITYLIST = "/v1/address/citylist";
    public static final String V1_ADDRESS_CITY = "/v1/address/city";
    public static final String V1_ADDRESS_COUNTYLIST = "/v1/address/countylist";
    public static final String V1_ADDRESS_COUNTY = "/v1/address/county";
    public static final String V1_ADDRESS_TOWNLIST = "/v1/address/townlist";
    public static final String V1_ADDRESS_TOWN = "/v1/address/town";
    public static final String V1_ADDRESS_INFO = "/address/info";
    //省市区乡(jd)
    public static final String V1_JD_ADDRESS_PROVINCELIST = "/v1/jdsync/address/provincelist";
    public static final String V1_JD_ADDRESS_PROVINCE = "/v1/jdsync/address/province";
    public static final String V1_JD_ADDRESS_CITYLIST = "/v1/jdsync/address/citylist";
    public static final String V1_JD_ADDRESS_CITY = "/v1/jdsync/address/city";
    public static final String V1_JD_ADDRESS_COUNTYLIST = "/v1/jdsync/address/countylist";
    public static final String V1_JD_ADDRESS_COUNTY = "/v1/jdsync/address/county";
    public static final String V1_JD_ADDRESS_TOWNLIST = "/v1/jdsync/address/townlist";
    public static final String V1_JD_ADDRESS_TOWN = "/v1/jdsync/address/town";
    public static final String V1_JD_ADDRESS_INFO="/v1/jdsync/address/info";

    public static final String SEARCH_HISTORY_CREATE = "/v1/searchHistory/create";
    public static final String SEARCH_HISTORY_LIST = "/v1/search/history/list";
    //获取地址二维码信息
    public static final String ROUTE_GET_ADDRESS_QRCODE = "/v1/address/qrcode";
    //获取发票二维码信息
    public static final String ROUTE_GET_INVOICE_QRCODE = "/v1/invoice/qrcode";
    public static final String ROUTE_MODIFY_INVOICE ="/v1/invoice/modify";
    public static final String ROUTE_GET_INVOICE_INFO = "/v1/invoice/detail";
    public static final String ROUTE_GET_INVOICE_NOLOGININFO = "/invoice/detail";
    //购物车商品添加
    public static final String ROUTE_CART_ADD = "/v1/cart/add";
    //购物车商品删除
    public static final String ROUTE_CART_DELETE = "/v1/cart/delete";
    //获取购物车商品列表
    public static final String ROUTE_CART_LIST = "/v1/cart/list";
    //购物车商品数量修改
    public static final String ROUTE_CART_NUM_MODIFY = "/v1/cart/modify";
    //删除购物车商品(购买成功删除)
    public static final String ROUTE_CART_DELETE_BY_USERID = "/v1/ims/cart/delete";
    //搜索
    public static final String ROUTE_SEARCH_PRODUCT_SKUS="/v1/search/skus";
    public static final String SEARCH_HISTORY_DELETE ="/v1/search/history/delete";
}
