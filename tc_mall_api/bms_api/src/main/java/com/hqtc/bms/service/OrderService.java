package com.hqtc.bms.service;

import com.hqtc.bms.model.database.TOrderAddressBean;
import com.hqtc.bms.model.database.TOrderBean;
import com.hqtc.bms.model.database.TOrderProductBean;
import com.hqtc.bms.model.params.GetFreightRequestParams;
import com.hqtc.bms.model.params.OrderManagerVo;
import com.hqtc.bms.model.params.PayNotifyUrlParams;
import com.hqtc.bms.model.params.VenderOrderNotifyParams;
import com.hqtc.bms.model.response.OrderListResponse;
import com.hqtc.bms.model.response.OrderRefundVo;
import com.hqtc.bms.model.rpc.*;
import com.hqtc.common.response.ResultData;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by wanghaoyang on 18-7-3.
 */
public interface OrderService {

    /**
     * 生成订单号
     * add by wanghaoyang at 20180705
     * */
    String createOrderSn();

    /**
     * 生成订单
     * add by wanghaoyang at 20180705
     * @param tOrderBean 订单详情
     * */
    int createOrder(TOrderBean tOrderBean);

    /**
     * 插入订单商品信息
     * add by wanghaoyang at 20180705
     * @param tOrderProductBeans 订单商品信息
     * */
    int addOrderProduct(List<TOrderProductBean> tOrderProductBeans);

    /**
     * 插入订单商品信息（批量）,用来替代addOrderProduct方法
     * add by wanghaoyang at 20180906
     * @param tOrderProductBeans 订单商品信息
     * */
    int addOrderProduct2(List<TOrderProductBean> tOrderProductBeans);

    /**
     * 根据订单号查询订单详情
     * add by wanghaoyang at 20180705
     * @param orderSn 平台订单号
     * @return 订单商品
     * */
    List<TOrderProductBean> getOrderProduct(List<String> orderSn);

    /**
     * 根据订单号查询订单详情
     * add by wanghaoyang at 20180705
     * @param orderSn 平台订单号
     * @return 订单商品
     * */
    List<TOrderProductBean> getOrderProduct(String orderSn);

    /**
     * 根据订单号获取订单总信息
     * add by wanghaoyang at 20180715
     * @param orderSn 平台订单号
     * @return 订单信息
     * */
    TOrderBean getOrderInfo(String orderSn);

    /**
     * 根据订单号获取订单总信息
     * add by wanghaoyang at 20180715
     * @param tradeNo 京东订单号
     * @return 订单信息
     * */
    TOrderBean getOrderInfoByTradeNo(String tradeNo);

    /**
     * 根据订单号获取订单总信息
     * add by wanghaoyang at 20180715
     * @param orderSn 平台订单号
     * @return 订单信息
     * */
    List<TOrderBean> getOrderInfo(List<String> orderSn);

    /**
     * 支付成功后更改订单状态
     * add by wanghaoyang at 20180707
     * @param tOrderBean 订单信息
     * */
    int updateOrderStatus(TOrderBean tOrderBean);

    /**
     * 向京东下单
     * add by wanghaoyang at 20180717
     * */
    ResultData<OrderRepVo2> orderToJdService(SubmitOrderRequestParams submitOrderRequestParams);

    /**
     * 向京东下单成功后更新订单状态
     * add by wanghaoyang at 20180709
     * @param tOrderBean 订单信息
     * */
    int updateJdOrderStatus(TOrderBean tOrderBean);

    /**
     * 根据唯一id获取收货信息
     * add by wanghaoyang at 20180712
     * @param id 终端上传的唯一Id
     * @return  收件人信息
     * */
    AddressBean getReceiverInfo(int id);

    /**
     * 根据订单号和商品id查询这笔订单的信息
     * add by wanghaoyang at 20180712
     * @param orderSn 订单号
     * @param sku 商品Id
     * @return
     * */
    List<TOrderProductBean> getOrderProduct(String orderSn, long sku);

    /**
     * 插入订单的收货地址
     * add by wanghaoyang at 20180712
     * */
    int addOrderAddress(TOrderAddressBean tOrderAddressBean);

    /**
     * 根据订单id查找收货信息
     * add by wanghaoyang at 20180712
     * @param orderId 订单id
     * @return  收货信息
     * */
    TOrderAddressBean findOrderAddress(int orderId);

    /**
     * 获取某个订单中商品详情
     * add by wanghaoyang at 20180712
     * @param orderSn 平台订单号
     * @return 商品详情
     * */
    List<Map<String, Object>> getProductCount(String orderSn);

    /**
     * 通过地址id获取地址详情
     * add by wanghaoyang at 20180712
     * @param orderAddressBean  订单地址信息
     * @return 可读的具体地址信息
     * */
    String getOrderAddress(TOrderAddressBean orderAddressBean);

    /**
     * 通过京东订单号查询京东订单状态
     * add by wanghaoyang at 20180713
     * @param tradeNo 京东订单号
     * */
    Object getJdOrderDetail(String tradeNo);

    /**
     * 向京东下单
     * @param jdTradeNo 京东订单号
     * @return 京东订单信息
     * */
    OrderPayResponseParams orderToJd(String jdTradeNo);

    /**
     * 查询京东订单状态
     * add by wanghaoyang at 20180713
     * @param jdTradeNo 京东订单号
     * */
    String getJdOrderInfo(String jdTradeNo);

    /**
     * 确认预占库存
     * add by wanghaoyang at 20180713
     * */
    RPCConfirmOccupyStockResponseParams jdOrderStock(String jdTradeNo);

    /**
     * 查询用户订单列表
     * add by wanghaoyang at 20180714
     * @param userId 用户ID
     * @param start 页数
     * @param size 每页多少个
     * @param orderType 订单类型(0:全部1:待付款2:待收货3:已完成)
     * */
    List<OrderListResponse> getUserOrder(int userId, int start, int size, int orderType);

    /**
     * 更改订单运费
     * add by wanghaoyang at 20170714
     * @param freight 运费
     * @param orderSn 平台订单号
     * */
    int updateFreight(BigDecimal freight, String orderSn);

    /**
     * 查询订单管理列表
     * @param params
     * @return
     */
    List<OrderManagerVo> orderManagerList(Map<String, Object> params);

    /**
     * 计算管理后台订单列表数量
     * @param params
     * @return
     */
    int countManagerOrderList(Map<String, Object> params);
    /**
     * 修改商品税率 TODO wanghaoyang
     * add by wanghaoyang at 20170717
     *
     * */
    int updateProductTaxRate(List<Map<Integer, Integer>> maps);

    /**
     * 获取商品的库存信息
     * @param products 商品ID和商品数量
     * @param address 四级地址id, 如 12_23_34_34
     * */
    StockOrderResponseParams getProductStock(Map<Long, Integer> products, String address);

    /**
     * 取消订单
     * add by wanghaoyang at 20180718
     * @param orderSn 平台订单号
     * @return true取消成功|false取消失败
     * */
    boolean cancelOrder(String orderSn);

    /**
     * 获取商品图片
     * add by wanghaoyang at 20180719
     * @param productIds 商品列表
     * */
    Map<Long, String> getProductImage(List<String> productIds);

    /**
     * 获取商品运费
     * add by wanghaoyang at 20180720
     * @param params 商品信息
     * */
    FreightDetailBean getProductFreight(FreightRequestParams params);

    /**
     * 查询商家余额
     * add by wanghaoyang at 20180720
     * @param venderId 商户id
     * */
    BigDecimal getVenderBalance(int venderId);

    /**
     * 查询商家支付方式
     * add by wanghaoyang at 20180720
     * @param venderId 商户id
     * */
    int getVenderPayType(int venderId);

    /**
     * 向商家扣款
     * add by wanghaoyang at 20180720
     * @param type 操作 1:扣钱|2:加钱
     * @param totalFee 操作的金额
     * @param venderId 商户id
     * */
    boolean handelVendBalance(int type, BigDecimal totalFee, int venderId);

    /**
     * 向商家扣款
     * add by wanghaoyang at 20180731
     * @param orderSn 平台订单号
     * @return true扣款成功|false扣款失败
     * */
    boolean handelVendBalance(String orderSn);

    /**
     * 向商家扣款
     * add by wanghaoyang at 20180830
     * @param venderSettlementAccount 扣款详情
     * @return true扣款成功|false扣款失败
     * */
    boolean handelVendBalance(VenderSettlementAccount venderSettlementAccount);

    /**
     * 向商家退款
     * 退款原因(1:用户拒签,京东退款到天成后2:用户售后退货,京东退款到天成3:后台操作退款(暂不支持))
     * add by wanghaoyang at 20180801
     * @param orderSn 平台订单号
     * @param refundPrice 本次退款金额
     * @return true退款成功|false退款失败
     * */
    boolean refundToVender(String orderSn, BigDecimal refundPrice);

    /**
     * 向商家退款
     * add by wanghaoyang at 20181015
     * @param orderSn 平台订单号
     * @param refundPrice 向商户的退款金额
     * @param payPrice 向用户的微信支付宝退的钱
     * @param cardPrice 向用户的卡里退的钱
     * */
    boolean refundToVender(String orderSn, BigDecimal refundPrice, BigDecimal payPrice, BigDecimal cardPrice);

    /**
     * 向商家扣款
     * add by wanghaoyang at 20180720
     * @param totalFee 金额
     * @param venderId 商户id
     * */
    boolean reduceVenderBalance(BigDecimal totalFee, int venderId);

    /**
     * 查询商品是否有库存
     * add by wanghaoyang at 20180721
     * @param address 收货地址 1_12_123_0
     * @param productCount 商品数量集
     * @return
     * */
    int checkProductStock(String address, Map<Long, Integer> productCount);

    /**
     * 查询商品是否有库存
     * add by wanghaoyang at 20180911
     * @param address 收货地址 1_12_123_0
     * @param productCount 商品数量集
     * @return 无货的商品
     * */
    List<Long> checkProductStock2(String address, Map<Long, Integer> productCount);

    int countOrderByVenderId(Long venderId);

    List<String> getProductOrderNo(Map<String, Object> params);

    /**
     * 获取订单商品列表详情
     * add by wanghaoyang at 20180727
     * */
    List<Map<String, Object>> getProductsDetail(String orderSn);

    int countProductOrderNos(Map<String, Object> params);

    /**
     * 格式化京东订单信息
     * add by wanghaoyang at 20180730
     * @param jdOrderInfo 京东订单信息
     * */
    Object formatJdOrderInfo(String jdOrderInfo);

    /**
     * 将京东订单(父订单或子订单)写入t_order_product
     * add by wanghaoyang at 20180731
     * @param jdOrderInfo 订单信息
     * @param orderSn 平台订单号
     * @return 成功或失败
     * */
    int writeJdOrderInfo(String jdOrderInfo, String orderSn);

    /**
     * 更新商品子订单信息
     * add by wanghaoyang at 20180731
     * @param orderSn 平台订单号
     * @param childTradeNo 京东子订单号
     * @param products 商品id集合
     * @return 成功或失败
     * */
    int updateChildTradeNo(String orderSn, String childTradeNo, List<String> products);

    /**
     * 获取商品的销量
     * add by wanghaoyang at 20180801
     * @param skus 商品id
     * @param venderId 大客户id
     * */
    List<Map<String, Long>> getProductSalesVolume(String skus, int venderId);

    /**
     * 获取商品销量(不包含退货拒签的)
     * add by wanghaoyang at 20180903
     * @param skus 商品id
     * @param venderId 大客户id
     */
    List<Map<String, Long>> getProductSalesVolumeWrapper(String skus, int venderId);

    /**
     * 获取商品销量(不包含退货拒签的)
     * add by wanghaoyang at 20180903
     * @param skus 商品id
     * @param venderId 大客户id
     */
    Map<String, Long> getProductSalesVolume2Wrapper(String skus, int venderId);

    /**
     * 获取商品的销量
     * add bu wanghaoyang at 20180801
     * @param skus 商品id
     * @param venderId 大客户id
     * */
    Map<String, Long> getProductSalesVolume2(String skus, int venderId);

    /**
     * 判断一个字符串是否是逗号分割数字的格式,如"1231,123123,123"
     * add by wangahoyang at 20180801
     * @param str 要判断的字符串
     * @return true是|false否
     * */
    boolean isNumericByComma(String str);

    /**
     * 根据京东的订单号刷新京东的拆单信息
     * add by wanghaoyang at 20180815
     * @param jdOrderSn 京东订单号
     * */
    ResultData splitOrder(String jdOrderSn);

    /**
     * 查询商品的京东运费
     * add by wanghaoyang at 20180912
     * @param params 地址商品信息
     * @return 运费详情
     * */
    ResultData<FreightDetailBean> getJdFreight(GetFreightRequestParams params);

    /**
     * 查询大客户向用户收取的运费
     * 查询运费,此接口仅供终端查询运费展示使用，中心平台是否扣大客户的运费则不可通过此接口判断
     * 20180910更新业务逻辑:查询商品是否满足大客户的包邮策略,如果满足,则用户不付运费。如果不满足，则查询京东运费
     * 如果未达到大客户所设置的包邮价格,但达到京东的包邮价格,收取运费6块
     * 如果未达到大客户所设置的包邮价格，也未达到京东的包邮价格，则按京东的邮费计算
     * 如果达到大客户设置的包邮价格，但未达到京东的包邮价格，则用户不付运费，大客户付运费
     *
     * add by wanghaoyang at 20180912
     * @param venderId 大客户id
     * @param params 地址商品信息
     * */
    ResultData<FreightDetailBean> getVenderFreight(int venderId, GetFreightRequestParams params);

    /**
     * 查询某笔订单应扣大客户的运费
     * add by wanghaoyang at 20180912
     * */
    FreightDetailBean getJdFreightByOrderSn(String orderSn);

    /**
     * 查询一定时间内未支付的订单
     * add by wanghaoyang at 20180913
     * @param limitSize 每次取多少个
     * @param expireMinute 距下单时间的时长
     * @return 平台订单号的集合
     * */
    List<String> getUnPayedOrderSnLimit(int limitSize, int expireMinute);

    /**
     * 由于支付金额错误自动向用户退款
     * add by wanghaoyang at 20180928
     * */
    boolean refundToUser(TOrderBean orderBean, BigDecimal refundPrice, int payType, String outTradeNo);

    /**
     * 支付成功更新订单状态
     * add by wanghaoyang at 20180928
     * @param payPrice 扫码支付金额
     * @param cardPrice 购物卡支付金额
     * @param payType 支付类型
     * @param orderSn 平台订单号
     * @param payOrderSn 第三方支付单号
     * */
    int paySuccessUpdateOrderState(BigDecimal payPrice, BigDecimal cardPrice, int payType, String orderSn, String payOrderSn);

    /**
     * 支付成功向大客户扣款
     * add by wanghaoyang at 20180928
     * @param venderId 大客户id
     * @param orderSn 平台订单号
     * @param jdOrderSn 京东订单号
     * @param payType 支付类型
     * @param agreePrice 批发价
     * @param price 零售价
     * @param payPrice 事假扫码支付价
     * @return true扣款成功|false扣款失败
     * */
    boolean paySuccessVenderCharged(int venderId, String orderSn, String jdOrderSn, int payType, BigDecimal agreePrice, BigDecimal price, BigDecimal payPrice, BigDecimal cardPrice);

    /**
     * 支付成功后的回调通知
     * add by wanghaoyang at 20180929
     * @param payNotifyUrlParams 通知的信息
     * */
    ResultData paySuccessNotify(PayNotifyUrlParams payNotifyUrlParams);

    /**
     * 大客户支付回调
     * add by wanghaoyang at 20190109
     * */
    ResultData venderOrderNotify(VenderOrderNotifyParams venderOrderNotifyParams);

    /**
     * 清空某笔订单相关的购物车
     * add by wanghaoyang at 20180929
     * @param userId 用户id
     * @param orderSn 平台订单号
     * */
    boolean clearShopCart(int userId ,String orderSn);

    /**
     * 删除用户订单
     * add by wanghaoyang at 20181015
     * @param orderSn 用户订单(多个用逗号隔开)
     * @param userId 用户id
     * @return  删除成功失败
     * */
    int deleteOrder(String orderSn, int userId);

    /**
     * 获取京东状态码对应的提示信息
     * add by wanghaoyang at 20181025
     * @param resultCode 京东返回状态吗
     * */
    String getJdErrorCodeMessage(String resultCode);

    /**
     * 查询商品的库存信息(包含库存和上下架的逻辑)
     * add by wanghaoyang at 20181025
     * @param venderId 大客户id
     * @param address 地址信息
     * @param products 商品数量
     * */
    ResultData getProductState(String address,int venderId, Map<Long, Integer> products);

    /**
     * 判断商品是否可售(上下架和库存)
     * add by wanghaoyang at 20181025
     * @param venderId 大客户id
     * @param address 地址信息
     * @param products 商品数量
     * @return  null服务器异常|empty可售|不可售的商品
     * */
    List<Long> checkStockAndOnline(String address,int venderId, Map<Long, Integer> products);

    /**
     * 获取某个用户的某笔订单
     * add by wanghaoyang at 20190128
     * @param orderSn 平台订单号
     * @param userId 用户id
     * */
    TOrderBean getUserOrderDetail(String orderSn, int userId);

    int countOrderRefund(Map<String, Object> params);

    List<OrderRefundVo> listOrderRefund(Map<String, Object> params);
}
