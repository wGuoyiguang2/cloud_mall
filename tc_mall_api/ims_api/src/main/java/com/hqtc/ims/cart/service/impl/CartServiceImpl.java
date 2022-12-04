package com.hqtc.ims.cart.service.impl;

import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.ResultData;
import com.hqtc.common.utils.Tools;
import com.hqtc.ims.cart.model.bean.CartBean;
import com.hqtc.ims.cart.model.bean.CartProductBean;
import com.hqtc.ims.cart.model.mapper.CartMapper;
import com.hqtc.ims.cart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;;

import java.util.List;


/**
 * description:  购物车实现impl
 * Created by sunjianqiang  18-9-21
 */
@Service
public class CartServiceImpl implements CartService {
    @Value("${service.config.cartMaxCount}")
    private int cartMaxCount;

    @Autowired
    CartMapper cartMapper;

    @Autowired
    RedisTemplate redisTemplate;

    /**
     * 购物车添加商品
     * @param userId
     * @param sku
     * @param count
     * @return
     */
    @Override
    public ResultData addCart(Integer userId, Long sku, Integer count) {
        ResultData result = Tools.getThreadResultData();
        CartBean cartBean = cartMapper.selectCount(userId, sku);
        if (sku.equals(cartBean.getSku()) || cartBean.getTotal() < cartMaxCount){
            int row = cartMapper.createOrUpdate(userId, sku, count);
            if (row <= 0){
                result.setError(ErrorCode.WRITE_DATA_ERROR);
                result.setMsg("读写数据库错误");
            }
        } else {
            result.setError(ErrorCode.CART_NUM_LIMIT);
            result.setMsg("购物车商品数量已达上限");
        }
        //清除缓存
        redisTemplate.delete("cart:userId:" + userId);
        return result;
    }

    /**
     *  删除购物车中商品（支持批量）
     *  @param userId
     *  @param sku
     */

    @Override
    public ResultData deleteCartAll(Integer userId, String sku) {
        ResultData result = Tools.getThreadResultData();
        try {
            int row = cartMapper.deleteCartAll(userId, sku);
            if (row >= 0) {
                result.setMsg("删除购物车数据成功");
                //清除缓存
                redisTemplate.delete("cart:userId:" + userId);
                return result;
            }
        } catch (Exception e) {
            result.setError(ErrorCode.WRITE_DATA_ERROR);
            result.setMsg("删除购物车数据失败");
        }
        return result;
    }
    /**
     * 修改购物车中商品数量
     * @param userId
     * @param sku
     * @param count
     */
    @Override
    public ResultData updateCart(Integer userId,Long sku,Integer count) {
        ResultData result = Tools.getThreadResultData();
        int row=cartMapper.updateCart(userId,sku,count);
        if (row > 0) {
            //清除缓存
            redisTemplate.delete("cart:userId:" + userId);
            return result;
        } else {
            result.setError(ErrorCode.WRITE_DATA_ERROR);
            result.setMsg("修改购物车数据失败");
            return result;
        }
    }

    /**
     * 查询购物车中商品列表
     * @param  userId
     * @return
     */
    @Override
    public List<CartProductBean> selectList(Integer userId) {
        List<CartProductBean>  skuList = cartMapper.selectAllSku(userId);
        return skuList;
    }

    /**
     * 查询购物车中商品数量
     * @param  userId
     */
    @Override
    public Integer selectTotal(Integer userId) {
        Integer   skuCount = cartMapper.selectTotal(userId);
        return  skuCount;
    }
}
