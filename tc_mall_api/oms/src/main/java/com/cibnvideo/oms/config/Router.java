package com.cibnvideo.oms.config;

public class Router {
    //从京东原始库获取商品价格
    public static final String V1_JDSYNC_PRODUCT_BATCH_PRICE = "/v1/jdsync/product/batchprice";
    public static final String V1_JDSYNC_PRODUCT_PRICE = "/v1/jdsync/product/price";
    public static final String V1_JDSYNC_PRODUCT_BRANDNAME_BY_SKUS = "/v1/jdsync/product/brandnamebyskus";
    public static final String V1_JDSYNC_PRODUCT_DETAILINFO = "/v1/jdsync/product/detailinfo";
    public static final String V1_JDSYNC_PRODUCT_DETAILINFO_BY_SKUS = "/v1/jdsync/product/detailinfobyskus";

    ////////////////////settleMent///////////////
    //添加结算方式
    public static final String V1_OMS_VENDER_SETTLEMENT_ADD = "/v1/oms/vendersettlement/add";
    //获取结算方式
    public static final String  V1_OMS_VENDER_SETTLEMENT_GET = "/v1/oms/vendersettlement/get";
    //更新结算方式
    public static final String  V1_OMS_VENDER_SETTLEMENT_UPDATE = "/v1/oms/vendersettlement/update";
    //删除结算方式
    public static final String  V1_OMS_VENDER_SETTLEMENT_REMOVE = "/v1/oms/vendersettlement/remove";
    //获取厂商余额
    public static final String  V1_OMS_VENDER_SETTLEMENT_BALANCE_GET = "/v1/oms/vendersettlement/balance/get";
    //增加余额
    public static final String  V1_OMS_VENDER_SETTLEMENT_BALANCE_ADD = "/v1/oms/vendersettlement/balance/add/{venderId}";
    //减少余额
    public static final String  V1_OMS_VENDER_SETTLEMENT_BALANCE_REDUCE = "/v1/oms/vendersettlement/balance/reduce/{venderId}";
    //结算账单
    public static final String  V1_OMS_VENDER_SETTLEMENT_ACCOUNT = "/v1/oms/vendersettlement/account";


    ////////////////////payMent//////////////////////
    //批量保存付款方式
    public static final String  V1_OMS_VENDER_PAYMENT_BATCHSAVE = "/v1/oms/venderpayment/batchSave";
    //根据venderId获取付款方式
    public static final String  V1_OMS_VENDER_PAYMENT_GET = "/v1/oms/venderpayment/get";
    //更新付款方式
    public static final String  V1_OMS_VENDER_PAYMENT_UPDATE = "/v1/oms/venderpayment/update";
    //删除付款方式
    public static final String V1_OMS_VENDER_PAYMENT_REMOVE = "/v1/oms/venderpayment/remove";
    //获取付款方式数量
    public static final String V1_OMS_VENDER_PAYMENT_COUNT = "/v1/oms/venderpayment/count";


    /////////////////////客户信息//////////////////////

    public static final String V1_OMS_CUSTOMERINFO_GET = "/v1/oms/customerinfo/get";

    public static final String V1_OMS_CUSTOMERINFO_UPDATE = "/v1/oms/customerinfo/update";

    public static final String V1_OMS_CUSTOMERINFO_ADD = "/v1/oms/customerinfo/add";

    public static final String V1_OMS_CUSTOMERINFO_DELETE = "/v1/oms/customerinfo/delete";

    /////////////////////帮助中心//////////////////////

    public static final String V1_OMS_HELPCENTER_GET = "/v1/oms/helpcenter/get";

    public static final String V1_OMS_HELPCENTER_UPDATE = "/v1/oms/helpcenter/update";

    public static final String V1_OMS_HELPCENTER_ADD = "/v1/oms/helpcenter/add";

    public static final String V1_OMS_HELPCENTER_DELETE = "/v1/oms/helpcenter/delete";

    public static final String V1_OMS_HELPCENTER_CUSTOMER_DELETE = "/v1/oms/helpcenter/customer/delete";

    /////////////////////帮助中心菜单详细描述//////////////////////

    public static final String V1_OMS_HELPCENTERINFO_DELETE = "/v1/oms/helpcenterinfo/delete";
    public static final String V1_OMS_HELPCENTERINFO_GET = "/v1/oms/helpcenterinfo/get";
    public static final String V1_OMS_HELPCENTERINFO_UPDATE = "/v1/oms/helpcenterinfo/update";
    public static final String V1_OMS_HELPCENTERINFO_ADD = "/v1/oms/helpcenterinfo/add";


    //获取商品运营价格
    public static final String  V1_OMS_PRODUCT_BATCH_PRICE = "/v1/oms/product/batchprice/{venderId}";
    public static final String  V1_OMS_PRODUCT_PRICE = "/v1/oms/product/price/{venderId}";

    //获取首页推荐
    public static final String ROUTE_GET_HOME_RECOMMEND = "/v1/oms/home/recommend";

    //模板信息
    public static final String V1_OMS_TEMPLATE_LIST = "/v1/oms/template/list";
    public static final String V1_OMS_TEMPLATE_GET = "/v1/oms/template/get";
    public static final String V1_OMS_TEMPLATE_COUNT = "/v1/oms/template/count";
    public static final String V1_OMS_TEMPLATE_ADD = "/v1/oms/template/add";
    public static final String V1_OMS_TEMPLATE_REMOVE = "/v1/oms/template/remove";
    public static final String V1_OMS_TEMPLATE_UPDATE = "/v1/oms/template/update";

    //布局信息
    public static final String V1_OMS_LAYOUT_LIST = "/v1/oms/layout/list";
    public static final String V1_OMS_LAYOUT_GET = "/v1/oms/layout/get";
    public static final String V1_OMS_LAYOUT_COUNT = "/v1/oms/layout/count";
    public static final String V1_OMS_LAYOUT_ADD = "/v1/oms/layout/add";
    public static final String V1_OMS_LAYOUT_REMOVE = "/v1/oms/layout/remove";
    public static final String V1_OMS_LAYOUT_UPDATE = "/v1/oms/layout/update";
    public static final String V1_OMS_LAYOUT_COPY_BY_LAYOUTID = "/v1/oms/layout/copybylayoutid";

    //推荐位信息
    public static final String V1_OMS_RECOMMEND_LIST = "/v1/oms/recommend/list";
    public static final String V1_OMS_RECOMMEND_GET = "/v1/oms/recommend/get";
    public static final String V1_OMS_RECOMMEND_GET_BY_POSITION = "/v1/oms/recommend/getbyposition";
    public static final String V1_OMS_RECOMMEND_COUNT = "/v1/oms/recommend/count";
    public static final String V1_OMS_RECOMMEND_ADD = "/v1/oms/recommend/add";
    public static final String V1_OMS_RECOMMEND_REMOVE = "/v1/oms/recommend/remove";
    public static final String V1_OMS_RECOMMEND_UPDATE = "/v1/oms/recommend/update";

    //往期推荐位信息
    public static final String V1_OMS_RECOMMEND_HISTORY_LIST = "/v1/oms/recommendhistory/list";
    public static final String V1_OMS_RECOMMEND_HISTORY_LIST_BY_DAY = "/v1/oms/recommendhistory/listbyday";
    public static final String V1_OMS_RECOMMEND_HISTORY_GET = "/v1/oms/recommendhistory/get";
    public static final String V1_OMS_RECOMMEND_HISTORY_GET_BY_POSITION = "/v1/oms/recommendhistory/getbyposition";
    public static final String V1_OMS_RECOMMEND_HISTORY_COUNT_TODAY = "/v1/oms/recommendhistory/counttoday";
    public static final String V1_OMS_RECOMMEND_HISTORY_ADD = "/v1/oms/recommendhistory/add";
    public static final String V1_OMS_RECOMMEND_HISTORY_REMOVE = "/v1/oms/recommendhistory/remove";
    public static final String V1_OMS_RECOMMEND_HISTORY_UPDATE = "/v1/oms/recommendhistory/update";
    public static final String V1_OMS_RECOMMEND_HISTORY_UPDATE_STATUS = "/v1/oms/recommendhistory/updatestatus";
    public static final String V1_OMS_RECOMMEND_HISTORY_PERIOD = "/v1/oms/recommendhistory/period";
    public static final String V1_OMS_RECOMMEND_HISTORY_PERIOD_UPDATE = "/v1/oms/recommendhistory/period/update";

    //商品集信息
    public static final String V1_OMS_PRODUCTCOLLECTION_LIST = "/v1/oms/productcollection/list";
    public static final String V1_OMS_PRODUCTCOLLECTION_GET = "/v1/oms/productcollection/get";
    public static final String V1_OMS_PRODUCTCOLLECTION_ADD = "/v1/oms/productcollection/add";
    public static final String V1_OMS_PRODUCTCOLLECTION_REMOVE = "/v1/oms/productcollection/remove";
    public static final String V1_OMS_PRODUCTCOLLECTION_UPDATE = "/v1/oms/productcollection/update";
    public static final String V1_OMS_PRODUCTCOLLECTION_BRANDNAMES_BY_ID = "/v1/oms/productcollection/brandnames";

    //商品集内商品
    public static final String V1_OMS_PRODUCT_OF_COLLECTION_LIST = "/v1/oms/productofcollection/list";
    public static final String V1_OMS_PRODUCT_OF_COLLECTION_GET = "/v1/oms/productofcollection/get";
    public static final String V1_OMS_PRODUCT_OF_COLLECTION_COUNT = "/v1/oms/productofcollection/count";
    public static final String V1_OMS_PRODUCT_OF_COLLECTION_ADD = "/v1/oms/productofcollection/add";
    public static final String V1_OMS_PRODUCT_OF_COLLECTION_BATCH_ADD = "/v1/oms/productofcollection/batchadd";
    public static final String V1_OMS_PRODUCT_OF_COLLECTION_REMOVE = "/v1/oms/productofcollection/remove";
    public static final String V1_OMS_PRODUCT_OF_COLLECTION_BATCH_REMOVE = "/v1/oms/productofcollection/batchremove";
    public static final String V1_OMS_PRODUCT_OF_COLLECTION_ID_BY_SKU = "/v1/oms/productofcollection/collectionid";
    //帮助中心、联系我们

    public static final String V1_OMS_CONTACTUS_GET_BY_VENDERID= "/v1/oms/contactUs/getByVenderId";
    public static final String V1_OMS_HELPCENTER_LIST = "/v1/oms/helpCenter/list";
    public static final String V1_OMS_CONTACTUS_GET_BY_ID ="/v1/oms/contactus/getById";
    public static final String V1_OMS_CONTACTUS_UPDATE = "/v1/oms/contactus/update";
    public static final String V1_OMS_HELPCENTERINFO_GET_QA_BY_ID = "/v1/oms/helpCenter/getQAById";
    public static final String V1_OMS_HELPCENTER_UPDATE_QA_BY_ID = "/v1/oms/helpCenter/updateQAById";
    public static final String V1_OMS_HELPCENTER_UPDATE_TYPE_BY_ID ="/v1/oms/helpCenter/updateTypeById";
    public static final String V1_OMS_HELPCENTER_DELETE_QA_BY_ID ="/v1/oms/helpcenterinfo/deleteQAById";
    public static final String V1_OMS_HELPCENTER_ADD_QA ="/v1/oms/helpCenter/addQA";
    public static final String V1_OMS_HELPCENTER_ADD_TYPE ="/v1/oms/helpCenter/addType";
    public static final String V1_OMS_HELPCENTER_LIST_ALL_TYPE = "/v1/oms/helpCenter/listAllType";

    //定价策略
    public static final String V1_OMS_PRICE_POLICY_LIST = "/v1/oms/pricepolicy/list";
    public static final String V1_OMS_PRICE_POLICY_GET = "/v1/oms/pricepolicy/get";
    public static final String V1_OMS_PRICE_POLICY_COUNT = "/v1/oms/pricepolicy/count";
    public static final String V1_OMS_PRICE_POLICY_ADD = "/v1/oms/pricepolicy/add";
    public static final String V1_OMS_PRICE_POLICY_REMOVE = "/v1/oms/pricepolicy/remove";
    public static final String V1_OMS_PRICE_POLICY_BATCHREMOVE = "/v1/oms/pricepolicy/batchremove";
    public static final String V1_OMS_PRICE_POLICY_UPDATE = "/v1/oms/pricepolicy/update";
    public static final String V1_OMS_PRICE_POLICY_LIST_BY_COLLECTIONID = "/v1/oms/pricepolicy/listbycollectionid/{venderId}";
    public static final String V1_OMS_PRICE_POLICY_MAX = "/v1/oms/pricepolicy/max";

    //分类定价策略
    public static final String V1_OMS_PRICE_POLICY_CATEGORY_LIST = "/v1/oms/pricepolicy/category/list";
    public static final String V1_OMS_PRICE_POLICY_CATEGORY_GET = "/v1/oms/pricepolicy/category/get";
    public static final String V1_OMS_PRICE_POLICY_CATEGORY_COUNT = "/v1/oms/pricepolicy/category/count";
    public static final String V1_OMS_PRICE_POLICY_CATEGORY_ADD = "/v1/oms/pricepolicy/category/add";
    public static final String V1_OMS_PRICE_POLICY_CATEGORY_REMOVE = "/v1/oms/pricepolicy/category/remove";
    public static final String V1_OMS_PRICE_POLICY_CATEGORY_BATCHREMOVE = "/v1/oms/pricepolicy/category/batchremove";

    //商品集定价策略
    public static final String V1_OMS_PRICE_POLICY_COLLECTION_LIST = "/v1/oms/pricepolicy/collection/list";
    public static final String V1_OMS_PRICE_POLICY_COLLECTION_GET = "/v1/oms/pricepolicy/collection/get";
    public static final String V1_OMS_PRICE_POLICY_COLLECTION_COUNT = "/v1/oms/pricepolicy/collection/count";
    public static final String V1_OMS_PRICE_POLICY_COLLECTION_ADD = "/v1/oms/pricepolicy/collection/add";
    public static final String V1_OMS_PRICE_POLICY_COLLECTION_REMOVE = "/v1/oms/pricepolicy/collection/remove";
    public static final String V1_OMS_PRICE_POLICY_COLLECTION_BATCHREMOVE = "/v1/oms/pricepolicy/collection/batchremove";

    //毛利率区间定价策略
    public static final String V1_OMS_PRICE_POLICY_RATE_LIST = "/v1/oms/pricepolicy/rate/list";
    public static final String V1_OMS_PRICE_POLICY_RATE_GET = "/v1/oms/pricepolicy/rate/get";
    public static final String V1_OMS_PRICE_POLICY_RATE_COUNT = "/v1/oms/pricepolicy/rate/count";
    public static final String V1_OMS_PRICE_POLICY_RATE_ADD = "/v1/oms/pricepolicy/rate/add";
    public static final String V1_OMS_PRICE_POLICY_RATE_REMOVE = "/v1/oms/pricepolicy/rate/remove";
    public static final String V1_OMS_PRICE_POLICY_RATE_BATCHREMOVE = "/v1/oms/pricepolicy/rate/batchremove";

    //商品单品定价策略
    public static final String V1_OMS_PRICE_POLICY_PRODUCT_LIST = "/v1/oms/pricepolicy/product/list";
    public static final String V1_OMS_PRICE_POLICY_PRODUCT_GET = "/v1/oms/pricepolicy/product/get";
    public static final String V1_OMS_PRICE_POLICY_PRODUCT_COUNT = "/v1/oms/pricepolicy/product/count";
    public static final String V1_OMS_PRICE_POLICY_PRODUCT_ADD = "/v1/oms/pricepolicy/product/add";
    public static final String V1_OMS_PRICE_POLICY_PRODUCT_UPDATE = "/v1/oms/pricepolicy/product/update";
    public static final String V1_OMS_PRICE_POLICY_PRODUCT_REMOVE = "/v1/oms/pricepolicy/product/remove";
    public static final String V1_OMS_PRICE_POLICY_PRODUCT_BATCHREMOVE = "/v1/oms/pricepolicy/product/batchremove";

    //厂商删除商品
    public static final String V1_OMS_PRODUCT_REMOVE_LIST = "/v1/oms/productremove/list";
    public static final String V1_OMS_PRODUCT_REMOVE_GET = "/v1/oms/productremove/get";
    public static final String V1_OMS_PRODUCT_REMOVE_SKUS_BY_VENDERID = "/v1/oms/productremove/skus/{venderId}";
    public static final String V1_OMS_PRODUCT_REMOVE_COUNT = "/v1/oms/productremove/count";
    public static final String V1_OMS_PRODUCT_REMOVE_ADD = "/v1/oms/productremove/add";
    public static final String V1_OMS_PRODUCT_REMOVE_BATCH_ADD = "/v1/oms/productremove/batch/add";
    public static final String V1_OMS_PRODUCT_REMOVE_REMOVE = "/v1/oms/productremove/remove";
    public static final String V1_OMS_PRODUCT_REMOVE_REMOVE_BY_SKU = "/v1/oms/productremove/removebysku/{venderId}";
    public static final String V1_OMS_PRODUCT_REMOVE_BATCH_REMOVE_BY_SKUS = "/v1/oms/productremove/batch/removebyskus";
    public static final String V1_OMS_PRODUCT_REMOVE_BATCHREMOVE = "/v1/oms/productremove/batchremove";

    //获取售后申请原因
    public static final String V1_OMS_AFTERSALE_CONFIG = "/v1/oms/aftersale/config";

    //获取客户订单管理列表
    public static final String V1_OMS_VENDER_ORDER_MANAGER_LIST ="/v1/oms/listVenderOrderManager";
    public static final String V1_OMS_VENDER_SETTLEMENT_GET_BY_PARAMS ="/v1/oms/listVenderSettlementByParams";
    public static final String V1_OMS_VENDER_INVOICE_GET_BY_PARAMS ="/v1/oms/listVenderInvoiceManager";
    //账期结算
    public static final String OMS_ACCOUNT_INFO_GET = "/v1/oms/account/getAccountInfo";
    public static final String OMS_ACCOUNT_MANAGER_LIST ="/v1/oms/account/accountManagerList";
    public static final String OMS_ACCOUNT_GET_ACCOUNT_LIST ="/v1/bms/account/getAccountListByOrders";
    public static final String V1_OMS_CONTACTUS_ADD = "/v1/oms/contactus/add";
    public static final String OMS_ACCOUNT_SETTLE = "/v1/oms/account/settleAccount";
    public static final String OMS_ACCOUNT_GET_VENDER_ACCOUNT_LIST ="/v1/oms/account/getVenderAccountList";
    //帮助中心
    public static final String V1_OMS_HELPCENTER_TYPE_GET ="/v1/oms/helpcenter/getTypeById";
    public static final String V1_OMS_HELPCENTER_DELETE_TYPE ="/v1/oms/helpcenter/deleteTypeById";

    //分类图片
    public static final String V1_OMS_CATEGORY_PICTURE_LIST = "/v1/oms/category/picture/list";
    public static final String V1_OMS_CATEGORY_PICTURE_GET = "/v1/oms/category/picture/get";
    public static final String V1_OMS_CATEGORY_PICTURE_COUNT = "/v1/oms/category/picture/count";
    public static final String V1_OMS_CATEGORY_PICTURE_ADD = "/v1/oms/category/picture/add";
    public static final String V1_OMS_CATEGORY_PICTURE_UPDATE = "/v1/oms/category/picture/update";
    public static final String V1_OMS_CATEGORY_PICTURE_REMOVE = "/v1/oms/category/picture/remove";
    public static final String V1_OMS_CATEGORY_PICTURE_BY_VENDERID = "/v1/oms/category/picture/{venderId}";

    //配置管理
    public static final String V1_OMS_CONFIG_MANAGER_LIST ="/v1/oms/config/configManagerList";
    public static final String V1_OMS_CONFIG_MANAGER_ADD = "/v1/oms/config/addConfig";
    public static final String V1_OMS_CONFIG_MANAGER_GETBYID = "/v1/oms/config/getById";
    public static final String V1_OMS_CONFIG_MANAGER_UPDATE ="/v1/oms/config/update";
    public static final String V1_OMS_CONFIG_MANAGER_DELETE ="/v1/oms/config/deleteById";
    public static final String V1_OMS_CONFIG_TYPE_GET_ALL ="/v1/oms/configType/getAll";
    public static final String V1_OMS_CONFIG_TYPE_MANAGER_LIST ="/v1/oms/configType/configTypeManagerList";
    public static final String V1_OMS_CONFIG_TYPE_MANAGER_ADD = "/v1/oms/config/addConfigType";
    public static final String V1_OMS_CONFIG_TYPE_MANAGER_GETBYID = "/v1/oms/configType/getById";
    public static final String V1_OMS_CONFIG_TYPE_MANAGER_UPDATE = "/v1/oms/configType/update";
    public static final String V1_OMS_CONFIG_TYPE_MANAGER_DELETE = "/v1/oms/configType/deleteById";
    //热门搜索管理
    public static final String V1_OMS_HOTSEARCH_MANAGER_LIST ="/v1/oms/hotSearchManager/list";
    public static final String V1_OMS_HOTSEARCH_ADD ="/v1/oms/hotSearch/add";
    public static final String V1_OMS_HOTSEARCH_GET ="/v1/oms/hotSearch/getById";
    public static final String V1_OMS_HOTSEARCH_UPDATE ="/v1/oms/hotSearch/update";
    public static final String V1_OMS_HOTSEARCH_DELETE ="/v1/oms/hotSearch/deleteById";
    public static final String V1_OMS_HOTSEARCH_LIST_BY_VENDERID = "/v1/oms/hotSearch/listByVenderId";
    public static final String V1_OMS_LIST_VENDERID = "/v1/oms/listvenderid";
    //包邮管理
    public static final String V1_OMS_FREEFREIGHT_MANAGER_LIST ="/v1/oms/freeFreightManager/list";
    public static final String V1_OMS_FREEFREIGHT_MANAGER_ADD ="/v1/oms/freeFreightManager/add";
    public static final String V1_OMS_FREEFREIGHT_MANAGER_GETBYID ="/v1/oms/freeFreightManager/getById";
    public static final String V1_OMS_FREEFREIGHT_MANAGER_UPDATE ="/v1/oms/freeFreightManager/update";
    public static final String V1_OMS_FREEFREIGHT_GETBY_VENDERID ="/v1/oms/freeFreightManager/getByVenderId";
    public static final String V1_OMS_FREEFREIGHT_SUGGEST_PRICE ="/v1/oms/freeFreightManager/getSuggestPrice";
    //获取商家实际支付给京东包邮价格的总和
    public static final String V1_OMS_FREEFREIGHT_SUMBY_VENDERID ="/v1/oms/freeFreightManagesumFreightr/ByVenderId";
}
