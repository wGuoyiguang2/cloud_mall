package com.hqtc.ims.cart.service;

import com.hqtc.common.response.ResultData;
import com.hqtc.ims.cart.model.bean.CartProductBean;

import java.util.List;

/**
 * description:  购物车service
 * Created by sunjianqiang  18-9-18
 */
public interface CartService {

    /**
     * 购物车中添加商品
     * @param userId
     * @param sku
     * @param count
     */
    ResultData addCart(Integer userId, Long sku, Integer count);

    /**
     * 修改购物车数量
     * @param userId
     * @param sku
     */
    ResultData updateCart(Integer userId, Long sku, Integer count);

    /**
     *  删除购物车中商品（支持批量）
     *  @param userId
     *  @param sku
     */
    ResultData deleteCartAll(Integer userId, String sku);

    /**
     * 查询购物车中商品列表
     * @param  userId
     * @return
     */
    List<CartProductBean> selectList(Integer userId);

    /**
     *  查询购物车中商品数量
     * @param  userId
     */
    Integer  selectTotal(Integer userId);
}