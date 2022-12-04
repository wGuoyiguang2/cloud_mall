package com.hqtc.bms.service;

import com.hqtc.bms.model.params.SalesAmountVo;
import com.hqtc.bms.model.params.SalesManagerVo;
import com.hqtc.bms.model.rpc.ProductPriceBean;
import com.hqtc.bms.model.rpc.ProductVenderPriceBean;
import com.hqtc.bms.model.rpc.ProductsOriginInfoBean;
import io.swagger.models.auth.In;

import java.util.List;
import java.util.Map;

/**
 * Created by wanghaoyang on 18-7-4.
 */
public interface ProductService {

    /**
     * 判断商品id是否合法
     * */
    boolean checkProductIds(List<String> productIds);

    /**
     * 获取商品原始信息
     * @param productIds 商品id集合
     * @return 商品信息
     * */
    List<ProductsOriginInfoBean> getOriginProductsInfo(List<String> productIds);

    /**
     * 获取商品的商家定价
     * @param venderId 商户id
     * @param productIds 商品列表
     * @return 商品价格
     * */
    List<ProductVenderPriceBean> getProductVenderPrice(int venderId, List<String> productIds);


    /**
     * 获取商品的协议价和商户价
     * @param venderId 商户id
     * @param productIds 商品列表
     * @return 商品信息
     * */
    Map<Long, ProductPriceBean> getProductPrice(int venderId, List<String> productIds);

    /**
     * 获取销售总金额和盈利金额
     * @param venderId
     * @return
     */
    SalesAmountVo getSalesAmount(Long venderId);

    List<SalesManagerVo> salesManagerList(Map<String, Object> params);

    int countSalesManager(Map<String, Object> params);

    SalesManagerVo getSalesManagerVo(Long sku);

    /**
     * 判断商品上下架状态
     * add by wanghaoyang at 20181010
     * @param venderId 大客户id
     * @param productIds 商品编号
     * @return 商品编号:商品状态(0下架|1上架)
     * */
    Map<Long, Integer> getProductState(int venderId, List<String> productIds);
}
