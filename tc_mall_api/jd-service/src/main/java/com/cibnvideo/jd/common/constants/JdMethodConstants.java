package com.cibnvideo.jd.common.constants;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/6/22 21:56
 */
public class JdMethodConstants {
    private JdMethodConstants() {
    }

    //版本信息
    private static final String VERSION = "1.0";
    //批量价格查询
    private static final String PRICE_METHOD = "biz.price.sellPrice.get";
    private static final String PRICE_TAX_METHOD = "jd.kpl.open.getsellprice.query";
    //批量库存查询
    private static final String STOCK_METHOD = "biz.stock.forList.batget";
    private static final String STOCK_ORDER_METHOD = "biz.stock.fororder.batget";
    //查询一级地址
    private static final String ADDRESS_LEVEL1_METHOD = "biz.address.allProvinces.query";
    //查询二级地址
    private static final String ADDRESS_LEVEL2_METHOD = "biz.address.citysByProvinceId.query";
    //查询三级地址
    private static final String ADDRESS_LEVEL3_METHOD = "biz.address.countysByCityId.query";
    //查询四级地址
    private static final String ADDRESS_LEVEL4_METHOD = "biz.address.townsByCountyId.query";
    //京东商品搜索接口
    private static final String SEARCH = "jd.biz.search.search";
    //京东查询商品池编号接口
    private static final String PRODUCT_POOL_PAGE_NUM = "biz.product.PageNum.query";
    //通过商品池编号查询sku
    private static final String SKUS_BY_PAGE_NUM = "biz.product.sku.query";
    //查询商品详情接口
    private static final String PRODUCT_DETAIL = "biz.product.detail.query";
    //获取PC端样式
    private static final String PC_STYLE = "jd.kpl.open.item.getwarestyleandjsbywareid";
    //获取移动端样式
    private static final String MOBILE_STYLE = "jd.kpl.open.item.getmobilewarestyleandjsbywareid";
    //获取PC端商品详情图片
    private static final String PC_PRODUCT_DETAIL_IMG = "jd.kepler.item.querybigfieldconvertsku";
    //获取H5商品详情图片
    private static final String H5_PRODUCT_DETAIL_IMG = "jingdong.new.ware.mobilebigfield.get";
    //分页获取skus
    private static final String SKUS_BY_PAGE = "jd.biz.product.getSkuByPage";
    //查询商品上下架状态
    private static final String PRODUCT_STATE = "biz.product.state.query";
    //查询商品图片
    private static final String PRODUCT_IMG = "biz.product.skuImage.query";
    //查询商品好评度
    private static final String PRODUCT_COMMENTS = "biz.product.commentSummarys.query";
    //查询商品区域购买限制接口
    private static final String CHECK_AREA_LIMIT = "biz.product.checkAreaLimit.query";
    //查询商品区域是否支持货到付款
    private static final String IS_COD = "biz.product.isCod.query";
    //查询赠品信息接口
    private static final String SKU_GIFT = "biz.product.skuGift.query";
    //运费查询接口
    private static final String FREIGHT = "biz.order.freight.get";
    //商品可售验证接口
    private static final String SELL_CHECK = "biz.product.sku.check";
    //获取京东预约日历
    private static final String CALENDAR = "biz.order.promise.calendar.get";
    //查询商品分类列表信息
    private static final String CATEGORY = "jd.biz.product.getcategory";
    //查询商品分类分页信息
    private static final String CATEGORY_PAGE = "jd.biz.product.getcategorys";
    //消息推送
    private static final String MESSAGE_GET = "biz.message.get";
    //删除消息推送
    private static final String MESSAGE_DELETE = "biz.message.del";
    //订单发起接口
    private static final String SUBMIT_ORDER = "biz.order.unite.submit";
    private static final String CONFIRM_OCCUPY_STOCK = "biz.order.occupyStock.confirm";
    private static final String ORDER_PAY = "biz.order.doPay";
    private static final String CANCEL_ORDER = "biz.order.cancelorder";
    //查询京东订单信息
    private static final String SELECT_ORDER = "jd.kpl.open.selectjdorder.query";
    //查询余额
    private static final String BALANCE = "biz.price.balance.get";
    //订单反查（通过第三方订单号查询京东订单号）
    private static final String GET_JD_ORDER_ID = "biz.order.jdOrderIDByThridOrderID.query";
    //查询配送信息
    private static final String ORDER_TRACK = "biz.order.orderTrack.query";
    //查询类似商品
    private static final String SIMILAR_GOODS = "jd.biz.product.getSimilarSku";
    //获取金采明细
    private static final String JINCAI_INFO = "biz.price.jincaiCredit.query";
    //查询余额明细
    private static final String BALANCE_INFO = "biz.price.balancedetail.get";
    //查询延保商品
    private static final String YANBAO_GOODS = "biz.product.yanbao.sku.query";
    //售后
    private static final String AVAILABLE_NUMBER_COMP = "biz.afterSale.availableNumberComp.query";
    private static final String WARE_RETURN_COMP = "biz.afterSale.wareReturnJdComp.query";
    private static final String CUSTOMER_EXPECT_COMP = "biz.afterSale.customerExpectComp.query";
    private static final String SERVICE_LIST_PAGE = "biz.afterSale.serviceListPage.query";
    private static final String SERVICE_DETAIL_INFO = "biz.afterSale.serviceDetailInfo.query";
    private static final String AUDIT_CANCEL = "biz.afterSale.auditCancel.query";
    private static final String SEND_SKU = "biz.afterSale.sendSku.update";
    private static final String AFS_APPLY = "biz.afterSale.afsApply.create";

    public static String getAfsApply() {
        return AFS_APPLY;
    }

    public static String getSendSku() {
        return SEND_SKU;
    }

    public static String getAuditCancel() {
        return AUDIT_CANCEL;
    }

    public static String getServiceDetailInfo() {
        return SERVICE_DETAIL_INFO;
    }

    public static String getServiceListPage() {
        return SERVICE_LIST_PAGE;
    }

    public static String getCustomerExpectComp() {
        return CUSTOMER_EXPECT_COMP;
    }

    public static String getWareReturnComp() {
        return WARE_RETURN_COMP;
    }

    public static String getAvailableNumberComp() {
        return AVAILABLE_NUMBER_COMP;
    }

    public static String getVERSION() {
        return VERSION;
    }

    public static String getPriceMethod() {
        return PRICE_METHOD;
    }

    public static String getPriceTaxMethod() {
        return PRICE_TAX_METHOD;
    }

    public static String getStockMethod() {
        return STOCK_METHOD;
    }

    public static String getStockOrderMethod() {
        return STOCK_ORDER_METHOD;
    }

    public static String getAddressLevel1Method() {
        return ADDRESS_LEVEL1_METHOD;
    }

    public static String getAddressLevel2Method() {
        return ADDRESS_LEVEL2_METHOD;
    }

    public static String getAddressLevel3Method() {
        return ADDRESS_LEVEL3_METHOD;
    }

    public static String getAddressLevel4Method() {
        return ADDRESS_LEVEL4_METHOD;
    }

    public static String getSEARCH() {
        return SEARCH;
    }

    public static String getProductPoolPageNum() {
        return PRODUCT_POOL_PAGE_NUM;
    }

    public static String getSkusByPageNum() {
        return SKUS_BY_PAGE_NUM;
    }

    public static String getProductDetail() {
        return PRODUCT_DETAIL;
    }

    public static String getPcStyle() {
        return PC_STYLE;
    }

    public static String getMobileStyle() {
        return MOBILE_STYLE;
    }

    public static String getPcProductDetailImg() {
        return PC_PRODUCT_DETAIL_IMG;
    }

    public static String getH5ProductDetailImg() {
        return H5_PRODUCT_DETAIL_IMG;
    }

    public static String getSkusByPage() {
        return SKUS_BY_PAGE;
    }

    public static String getProductState() {
        return PRODUCT_STATE;
    }

    public static String getProductImg() {
        return PRODUCT_IMG;
    }

    public static String getProductComments() {
        return PRODUCT_COMMENTS;
    }

    public static String getCheckAreaLimit() {
        return CHECK_AREA_LIMIT;
    }

    public static String getIsCod() {
        return IS_COD;
    }

    public static String getSkuGift() {
        return SKU_GIFT;
    }

    public static String getFREIGHT() {
        return FREIGHT;
    }

    public static String getSellCheck() {
        return SELL_CHECK;
    }

    public static String getCALENDAR() {
        return CALENDAR;
    }

    public static String getCATEGORY() {
        return CATEGORY;
    }

    public static String getCategoryPage() {
        return CATEGORY_PAGE;
    }

    public static String getMessageGet() {
        return MESSAGE_GET;
    }

    public static String getMessageDelete() {
        return MESSAGE_DELETE;
    }

    public static String getSubmitOrder() {
        return SUBMIT_ORDER;
    }

    public static String getConfirmOccupyStock() {
        return CONFIRM_OCCUPY_STOCK;
    }

    public static String getOrderPay() {
        return ORDER_PAY;
    }

    public static String getCancelOrder() {
        return CANCEL_ORDER;
    }

    public static String getSelectOrder() {
        return SELECT_ORDER;
    }

    public static String getBALANCE() {
        return BALANCE;
    }

    public static String getGetJdOrderId() {
        return GET_JD_ORDER_ID;
    }

    public static String getOrderTrack() {
        return ORDER_TRACK;
    }

    public static String getSimilarGoods() {
        return SIMILAR_GOODS;
    }

    public static String getJincaiInfo() {
        return JINCAI_INFO;
    }

    public static String getBalanceInfo() {
        return BALANCE_INFO;
    }

    public static String getYanbaoGoods() {
        return YANBAO_GOODS;
    }
}
