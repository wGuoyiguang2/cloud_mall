package com.cibnvideo.config;

public class Router {
    //健康检查
    public static final String API_HEALTHMONITOR = "/api/healthMonitor.php";
    ///////////////////////jdsync address//////////////////////
    public static final String V1_JDSYNC_ADDRESS_PROVINCELIST = "/v1/jdsync/address/provincelist";
    public static final String V1_JDSYNC_ADDRESS_PROVINCE = "/v1/jdsync/address/province";
    public static final String V1_JDSYNC_ADDRESS_CITYLIST = "/v1/jdsync/address/citylist";
    public static final String V1_JDSYNC_ADDRESS_CITY = "/v1/jdsync/address/city";
    public static final String V1_JDSYNC_ADDRESS_COUNTYLIST = "/v1/jdsync/address/countylist";
    public static final String V1_JDSYNC_ADDRESS_COUNTY = "/v1/jdsync/address/county";
    public static final String V1_JDSYNC_ADDRESS_TOWNLIST = "/v1/jdsync/address/townlist";
    public static final String V1_JDSYNC_ADDRESS_TOWN = "/v1/jdsync/address/town";

    //////////////////////jdsync picture///////////////////////
    public static final String V1_JDSYNC_PICTURE_MOBILE = "/v1/jdsync/picture/mobile";
    public static final String V1_JDSYNC_PICTURE_PC = "/v1/jdsync/picture/pc";

    //////////////////////jdsync category/////////////////////
    public static final String V1_JDSYNC_CATEGORY_LIST = "/v1/jdsync/category/list";
    public static final String V1_JDSYNC_CATEGORY_GET = "/v1/jdsync/category/get";
    public static final String V1_JDSYNC_CATEGORY_LIST_BY_CATIDS = "/v1/jdsync/category/listbycatids";

    /////////////////////jdsync product detail////////////////
    public static final String V1_JDSYNC_PRODUCT_DETAILINFOLIST = "/v1/jdsync/product/detailinfolist";
    public static final String V1_JDSYNC_PRODUCT_DETAILINFOSEARCH = "/v1/jdsync/product/detailinfosearch";
    public static final String V1_JDSYNC_PRODUCT_DETAILINFO = "/v1/jdsync/product/detailinfo";
    public static final String V1_JDSYNC_PRODUCT_DETAILINFOBYSKUS = "/v1/jdsync/product/detailinfobysku";
    public static final String V1_JDSYNC_PRODUCT_DETAILLIST = "/v1/jdsync/product/detaillist";
    public static final String V1_JDSYNC_PRODUCT_DETAIL = "/v1/jdsync/product/detail";
    public static final String V1_JDSYNC_PRODUCT_PRICE = "/v1/jdsync/product/price";
    public static final String V1_JDSYNC_PRODUCT_BRANDNAME_BY_CAT = "/v1/jdsync/product/brandnamebycat";
    public static final String V1_JDSYNC_PRODUCT_VIDEO_ADD = "/v1/jdsync/product/video/add";

    /////////////////////jdsync style////////////////////////
    public static final String V1_JDSYNC_STYLE_MOBILE = "/v1/jdsync/style/mobile";
    public static final String V1_JDSYNC_STYLE_PC = "/v1/jdsync/style/pc";

    /////////////////////task////////////////////////
    public static final String V1_TASK_JOB = "/v1/task/job";
    public static final String V1_TASK_LIST = "/v1/task/list";
    public static final String V1_TASK_COUNT = "/v1/task/count";
    public static final String V1_TASK_GET = "/v1/task/get";
    public static final String V1_TASK_SAVE = "/v1/task/save";
    public static final String V1_TASK_UPDATE = "/v1/task/update";
    public static final String V1_TASK_REMOVE = "/v1/task/remove";
    public static final String V1_TASK_BATCH_REMOVE = "/v1/task/batchremove";
    public static final String V1_TASK_CHANGE_STATUS = "/v1/task/changestatus";

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
    public static final String V1_OMS_RECOMMEND_HISTORY_GET = "/v1/oms/recommendhistory/get";
    public static final String V1_OMS_RECOMMEND_HISTORY_COUNT_TODAY = "/v1/oms/recommendhistory/counttoday";
    public static final String V1_OMS_RECOMMEND_HISTORY_ADD = "/v1/oms/recommendhistory/add";
    public static final String V1_OMS_RECOMMEND_HISTORY_REMOVE = "/v1/oms/recommendhistory/remove";
    public static final String V1_OMS_RECOMMEND_HISTORY_UPDATE = "/v1/oms/recommendhistory/update";
    public static final String V1_OMS_RECOMMEND_HISTORY_UPDATE_STATUS = "/v1/oms/recommendhistory/updatestatus";
    public static final String V1_OMS_RECOMMEND_HISTORY_PERIOD = "/v1/oms/recommendhistory/period";
    public static final String V1_OMS_RECOMMEND_HISTORY_PERIOD_UPDATE = "/v1/oms/recommendhistory/period/update";

    //file upload
    public static final String V1_FILEUPLOAD_UPLOAD = "/v1/fileupload/upload";

    //商品集信息
    public static final String V1_OMS_PRODUCTCOLLECTION_LIST = "/v1/oms/productcollection/list";
    public static final String V1_OMS_PRODUCTCOLLECTION_GET = "/v1/oms/productcollection/get";
    public static final String V1_OMS_PRODUCTCOLLECTION_ADD = "/v1/oms/productcollection/add";
    public static final String V1_OMS_PRODUCTCOLLECTION_REMOVE = "/v1/oms/productcollection/remove";
    public static final String V1_OMS_PRODUCTCOLLECTION_UPDATE = "/v1/oms/productcollection/update";
    public static final String V1_OMS_ORDER_LIST = "/v1/oms/order/list";
    public static final String ADMIN_HELP_CENTER_HTML ="/v1/tcmalladmin/helpCenterHtml";
    public static final String ADMIN_HELP_CENTER_LIST ="/v1/tcmalladmin/helpCenter/list";
    public static final String V1_OMS_HELPCENTER_LIST= "/v1/oms/helpCenter/list";
    public static final String ADMIN_HELP_CENTER_EDIT ="/v1/tcmalladmin/helpCenter/edit";
    public static final String ADMIN_HELP_CENTER_ADD ="/v1/tcmalladmin/helpCenter/add";
    public static final String ADMIN_HELP_CENTER_DELETE ="/v1/tcmalladmin/helpCenter/delete";
    public static final String ADMIN_HELP_CENTER_EDIT_TYPE_NAME = "/v1/tcmalladmin/helpCenter/editTypeName";
    public static final String ADMIN_HELP_CENTER_ADD_TYPE = "/v1/tcmalladmin/helpCenter/addType";
    public static final String V1_OMS_HELPCENTER_GET_QA_BY_ID ="/v1/oms/helpCenter/getQAById";
    public static final String V1_OMS_HELPCENTER_UPDATE_QA_BY_ID ="/v1/oms/helpCenter/updateQAById";
    public static final String V1_OMS_HELPCENTER_UPDATE_TYPE_BY_ID ="/v1/oms/helpCenter/updateTypeById";
    public static final String V1_OMS_HELPCENTER_DELETE_QA_BY_ID = "/v1/oms/helpcenterinfo/deleteQAById";
    public static final String V1_OMS_HELPCENTER_ADD_QA = "/v1/oms/helpCenter/addQA";
    public static final String V1_OMS_HELPCENTER_ADD_TYPE ="/v1/oms/helpCenter/addType";
    //联系我们
    public static final String V1_OMS_CONTACTUS_GET_BY_VENDERID ="/v1/oms/contactUs/getByVenderId";
    public static final String V1_OMS_CONTACTUS_GET_BY_ID ="/v1/oms/contactus/getById";
    public static final String V1_OMS_CONTACTUS_UPDATE = "/v1/oms/contactus/update";
    public static final String V1_OMS_CONTACTUS_ADD = "/v1/oms/contactus/add";
    //商品集内商品
    public static final String V1_OMS_PRODUCT_OF_COLLECTION_LIST = "/v1/oms/productofcollection/list";
    public static final String V1_OMS_PRODUCT_OF_COLLECTION_GET = "/v1/oms/productofcollection/get";
    public static final String V1_OMS_PRODUCT_OF_COLLECTION_COUNT = "/v1/oms/productofcollection/count";
    public static final String V1_OMS_PRODUCT_OF_COLLECTION_ADD = "/v1/oms/productofcollection/add";
    public static final String V1_OMS_PRODUCT_OF_COLLECTION_BATCH_ADD = "/v1/oms/productofcollection/batchadd";
    public static final String V1_OMS_PRODUCT_OF_COLLECTION_REMOVE = "/v1/oms/productofcollection/remove";
    public static final String V1_OMS_PRODUCT_OF_COLLECTION_BATCH_REMOVE = "/v1/oms/productofcollection/batchremove";
    public static final String V1_OMS_HELPCENTER_LIST_TYPE = "/v1/oms/helpCenter/listAllType";

    //定价策略
    public static final String V1_OMS_PRICE_POLICY_LIST = "/v1/oms/pricepolicy/list";
    public static final String V1_OMS_PRICE_POLICY_GET = "/v1/oms/pricepolicy/get";
    public static final String V1_OMS_PRICE_POLICY_COUNT = "/v1/oms/pricepolicy/count";
    public static final String V1_OMS_PRICE_POLICY_ADD = "/v1/oms/pricepolicy/add";
    public static final String V1_OMS_PRICE_POLICY_REMOVE = "/v1/oms/pricepolicy/remove";
    public static final String V1_OMS_PRICE_POLICY_BATCHREMOVE = "/v1/oms/pricepolicy/batchremove";
    public static final String V1_OMS_PRICE_POLICY_UPDATE = "/v1/oms/pricepolicy/update";
    public static final String V1_OMS_PRICE_POLICY_LIST_BY_COLLECTIONID = "/v1/oms/pricepolicy/listbycollectionid/{venderId}";

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
    public static final String V1_OMS_PRODUCT_REMOVE_SKUS_BY_VENDERID = "/v1/oms/productremove/skus/{venderId}";
    public static final String V1_OMS_PRODUCT_REMOVE_GET = "/v1/oms/productremove/get";
    public static final String V1_OMS_PRODUCT_REMOVE_COUNT = "/v1/oms/productremove/count";
    public static final String V1_OMS_PRODUCT_REMOVE_ADD = "/v1/oms/productremove/add";
    public static final String V1_OMS_PRODUCT_REMOVE_REMOVE = "/v1/oms/productremove/remove";
    public static final String V1_OMS_PRODUCT_REMOVE_BATCHREMOVE = "/v1/oms/productremove/batchremove";
    //订单管理
    public static final String BMS_ORDER_MANAGER_LIST = "/v1/bms/order/orderManagerList";
    public static final String V1_OMS_VENDER_ORDER_MANAGER_LIST ="/v1/oms/listVenderOrderManager";
    public static final String V1_OMS_VENDER_SETTLEMENT_GET_BY_PARAMS ="/v1/oms/listVenderSettlementByParams";
    public static final String BMS_ORDER_SUM_BY_VENDER ="/v1/bms/countOrderByVenderId";
    //发票管理
    public static final String BMS_INVOICE_MANAGER_LIST ="/v1/bms/invoice/invoiceManagerList";
    public static final String V1_OMS_VENDER_INVOICE_GET_BY_PARAMS ="/v1/oms/listVenderInvoiceManager";
    public static final String BMS_INVOICE_INFO_MANAGER_GET ="/v1/bms/getInvoiceInfo";
    public static final String BMS_INVOICE_MANAGER_REMOVE ="/v1/bms/invoice/remove";

    //账期结算
    public static final String BMS_PRODUCT_GET_ORDERNO = "/v1/bms/product/getProductOrderNo";
    public static final String OMS_ACCOUNT_GET_ACCOUNT_LIST ="/v1/bms/account/getAccountListByOrders";
    public static final String OMS_ACCOUNT_MANAGER_LIST ="/v1/oms/account/accountManagerList";
    public static final String OMS_ACCOUNT_INFO_GET = "/v1/oms/account/getAccountInfo";
    public static final String OMS_ACCOUNT_SETTLE = "/v1/oms/account/settleAccount";
    public static final String OMS_ACCOUNT_GET_VENDER_ACCOUNT_LIST ="/v1/oms/account/getVenderAccountList";
    //帮助中心
    public static final String V1_OMS_HELPCENTER_TYPE_GET ="/v1/oms/helpcenter/getTypeById";
    public static final String V1_OMS_HELPCENTER_DELETE_TYPE ="/v1/oms/helpcenter/deleteTypeById";
    //获取商品的销量
    public static final String V1_BMS_PRODUCT_VOLUME = "/v1/bms/product/volume";

    //分类图片
    public static final String V1_OMS_CATEGORY_PICTURE_LIST = "/v1/oms/category/picture/list";
    public static final String V1_OMS_CATEGORY_PICTURE_GET = "/v1/oms/category/picture/get";
    public static final String V1_OMS_CATEGORY_PICTURE_COUNT = "/v1/oms/category/picture/count";
    public static final String V1_OMS_CATEGORY_PICTURE_ADD = "/v1/oms/category/picture/add";
    public static final String V1_OMS_CATEGORY_PICTURE_UPDATE = "/v1/oms/category/picture/update";
    public static final String V1_OMS_CATEGORY_PICTURE_REMOVE = "/v1/oms/category/picture/remove";
    public static final String V1_OMS_CATEGORY_PICTURE_BATCHREMOVE = "/v1/oms/category/picture/batchremove";
    //配置管理
    public static final String V1_OMS_CONFIG_MANAGER_LIST ="/v1/oms/config/configManagerList";
    public static final String V1_OMS_CONFIG_MANAGER_ADD = "/v1/oms/config/addConfig";
    public static final String V1_OMS_CONFIG_MANAGER_GETBYID = "/v1/oms/config/getById";
    public static final String V1_OMS_CONFIG_MANAGER_UPDATE ="/v1/oms/config/update";
    public static final String V1_OMS_CONFIG_MANAGER_DELETE ="/v1/oms/config/deleteById";
    public static final String V1_OMS_CONFIG_TYPE_GET_ALL ="/v1/oms/configType/getAll";
    public static final String V1_OMS_CONFIG_TYPE_MANAGER_LIST = "/v1/oms/configType/configTypeManagerList";
    public static final String V1_OMS_CONFIG_TYPE_MANAGER_ADD = "/v1/oms/config/addConfigType";
    public static final String V1_OMS_CONFIG_TYPE_MANAGER_GETBYID = "/v1/oms/configType/getById";
    public static final String V1_OMS_CONFIG_TYPE_MANAGER_UPDATE = "/v1/oms/configType/update";
    public static final String V1_OMS_CONFIG_TYPE_MANAGER_DELETE = "/v1/oms/configType/deleteById";
    //销量管理
    public static final String V1_BMS_PRODUCT_SALES_AMOUNT ="/v1/bms/sales/getSalesAmount";
    public static final String V1_BMS_PRODUCT_SALES_LIST ="/v1/bms/sales/salesManagerList";
    public static final String V1_JDSYNC_SALES_MANAGER__PRODUCT_DETAIL ="/v1/jdsync/sales/salesManagerProductDetail";
    public static final String V1_BMS_PRODUCT_SALES_GET ="/v1/bms/sales/getProductInfo";
    public static final String V1_JDSYNC_SALES_MANAGER_LIST ="/v1/jdsync/sales/getSalesManagerList";
    public static final String V1_JDSYNC_PRODUCT_DETAILINFO_BY_SKUS = "/v1/jdsync/product/detailinfobyskus";
    //热门搜索管理
    public static final String V1_OMS_HOTSEARCH_MANAGER_LIST ="/v1/oms/hotSearchManager/list";
    public static final String V1_OMS_HOTSEARCH_ADD ="/v1/oms/hotSearch/add";
    public static final String V1_OMS_HOTSEARCH_GET ="/v1/oms/hotSearch/getById";
    public static final String V1_OMS_HOTSEARCH_UPDATE ="/v1/oms/hotSearch/update";
    public static final String V1_OMS_HOTSEARCH_DELETE ="/v1/oms/hotSearch/deleteById";
    //包邮管理
    public static final String V1_OMS_FREEFREIGHT_MANAGER_LIST ="/v1/oms/freeFreightManager/list";
    public static final String V1_OMS_FREEFREIGHT_MANAGER_ADD ="/v1/oms/freeFreightManager/add";
    public static final String V1_OMS_FREEFREIGHT_MANAGER_GETBYID ="/v1/oms/freeFreightManager/getById";
    public static final String V1_OMS_FREEFREIGHT_MANAGER_UPDATE ="/v1/oms/freeFreightManager/update";
    public static final String V1_OMS_FREEFREIGHT_SUGGEST_PRICE ="/v1/oms/freeFreightManager/getSuggestPrice";
    //购物卡
    public static final String ADMIN_CARD_HTML ="/v1/tcmalladmin/cardHtml";
    public static final String ADMIN_CARD_LIST ="/v1/tcmalladmin/cardList";
    public static final String ADMIN_CARD_BATCH_HTML ="/v1/tcmalladmin/cardBatchHtml";
    public static final String ADMIN_CARD_BATCH_LIST ="/v1/tcmalladmin/cardBatchList";
    public static final String V1_BMS_CARD_BATCH_MANAGER_LIST ="/v1/bms/card/listManagerCardBatch";
    public static final String V1_BMS_CARD_MANAGER_LIST ="/v1/bms/card/listManagerCard";
    public static final String V1_ADMIN_CARD_ADD_HTML="/v1/tcmalladmin/addCardHtml";
    public static final String V1_ADMIN_CARD_BATCH_RECORD_HTML="/v1/tcmalladmin/cardAdminRecordHtml";
    public static final String V1_BMS_CARD_BATCH_RECORD_LIST ="/v1/bms/cardBatchRecord/list";
    public static final String V1_ADMIN_CARD_BATCH_ADD_CARD="/v1/tcmalladmin/cardbatch/addCard";
    public static final String V1_BMS_CARD_BATCH_ADMIN_RECORD_LIST ="/v1/bms/cardAdminRecord/batchList";
    public static final String V1_ADMIN_CARD_BATCH_START ="/v1/tcmalladmin/cardbatch/start";
    public static final String V1_ADMIN_CARD_BATCH_PAUSE ="/v1/tcmalladmin/cardbatch/pause";
    public static final String V1_ADMIN_CARD_BATCH_EXPORT ="/v1/tcmalladmin/cardbatch/export";
    public static final String V1_ADMIN_CARD_BATCH_ABANDON ="/v1/tcmalladmin/cardbatch/abandon";
    public static final String V1_BMS_CARD_BATCH_OPERATE ="/v1/bms/cardbatch/operate";
    public static final String V1_ADMIN_CARD_BATCH_OPERATE_HTML="/v1/tcmalladmin/cardbatch/operateHtml";
    public static final String V1_BMS_CARD_BATCH_GETBY_ID ="/v1/bms/cardbatch/getById";
    public static final String V1_ADMIN_CARD_BIND ="/v1/tcmalladmin/card/bind";
    public static final String V1_ADMIN_CARD_EXPORT ="/v1/tcmalladmin/card/export";
    public static final String V1_ADMIN_CARD_ABANDON ="/v1/tcmalladmin/card/abandon";
    public static final String V1_ADMIN_CARD_START ="/v1/tcmalladmin/card/start";
    public static final String V1_BMS_CARD_OPERATE ="/v1/bms/card/operate";
    public static final String V1_ADMIN_CARD_BATCH_ADMIN_RECORD_DETAIL_HTML ="/v1/tcmalladmin/cardBatchAdminRecord/detailHtml";
    public static final String V1_BMS_CARD_BATCH_ADMIN_RECORD_DETAIL ="/v1/bms/cardBatchAdminRecord";
    public static final String V1_BMS_CARD_GET_BALANCE_BY_CARDNOS ="/v1/bms/card/getBalanceByCardNos";
    public static final String V1_ADMIN_CARD_BIND_HTML ="/v1/tcmalladmin/cardBindHtml";
    public static final String V1_ADMIN_CARD_START_HTML ="/v1/tcmalladmin/cardStartHtml";
    public static final String V1_ADMIN_CARD_ABANDON_HTML ="/v1/tcmalladmin/cardAbandonHtml";
    public static final String V1_BMS_CARD_BATCH_DETAIL ="/v1/bms/cardBatchDetail";
    public static final String V1_ADMIN_CARD_EXPORT_HTML ="/v1/tcmalladmin/cardExprotHtml";
    public static final String V1_BMS_CARD_LIST ="/v1/bms/card/list";
    public static final String V1_BMS_CARD_ADMIN_OPERATE_ADD ="/v1/bms/cardAdminRecord/add";
    public static final String V1_BMS_CARD_BY_CARDNOS ="/v1/bms/card/listCardsByCardNos";
    public static final String V1_ADMIN_CARD_BATCH_CARD_LIST ="/v1/tcmalladmin/cardBatch/cardList";
    public static final String V1_ADMIN_CARD_DETAIL="/v1/tcmalladmin/card/detail";
    public static final String V1_ADMIN_CARD_USER_RECORD_HTML ="/v1/tcmalladmin/cardUserRecordHtml";
    public static final String V1_ADMIN_CARD_USER_RECORD_LIST ="/v1/tcmalladmin/cardUserRecordList";
    public static final String V1_BMS_CARD_USER_RECORD_LIST ="/v1/bms/cardUserRecord/list";
    public static final String V1_BMS_CARD_DETAIL ="/v1/bms/cardDetail";
    public static final String V1_ADMIN_CARD_DETAIL_LIST ="/v1/tcmalladmin/cardDetailList";
    public static final String V1_BMS_CARD_DETAIL_LIST ="/v1/bms/cardDetailList";
    public static final String V1_BMS_CARD_BATCH_ADD_CARD ="/v1/bms/cardBatch/addCard";
    public static final String V1_BMS_AFTER_SALES_MANAGER_LIST ="/v1/bms/afterSale/managerList";
    public static final String V1_BMS_ORDER_REFUND_LIST ="/v1/bms/orderRefund/list";
    public static final String ROUTER_REFUND_RETRY_USER = "/v1/bms/order/refund/retry/user";
    public static final String ROUTER_REFUND_RETRY_VENDER = "/v1/bms/order/refund/retry/vender";

    /**
     * 定时任务服务
     */
    public static final String V1_JOB_GET = "/v1/job/get";
    public static final String V1_JOB_LIST = "/v1/job/list";
    public static final String V1_JOB_COUNT = "/v1/job/count";
    public static final String V1_JOB_ADD = "/v1/job/add";
    public static final String V1_JOB_REMOVE = "/v1/job/remove";
    public static final String V1_JOB_BATCH_REMOVE = "/v1/job/batchremove";
    public static final String V1_JOB_UPDATE = "/v1/job/update";
    public static final String V1_JOB_CHANGE_STATUS = "/v1/job/changeJobStatus";
    public static final String V1_JOB_RUN = "/v1/job/run";
}
