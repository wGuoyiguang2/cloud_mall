package com.hqtc.ims.cart.model.mapper;

import com.hqtc.ims.cart.model.bean.CartBean;
import com.hqtc.ims.cart.model.bean.CartProductBean;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * description:  购物车Mapper
 * Created by sunjianqiang  18-9-19
 */
@Repository
public interface CartMapper {

    /**
     * 添加或修改购物车商品信息
     * @param userId
     * @param sku
     * @param count
     * @return
     */
    @Insert("insert into t_shopping_cart (user_id, sku, count, ctime) values (#{userId}, #{sku}, #{count}, NOW()) " +
            "on duplicate key update count = count + values(count)")
    int createOrUpdate(@Param("userId") Integer userId,
                       @Param("sku") Long sku,
                       @Param("count") Integer count);

    /**
     * 查询购物车中商品存在的数量和sku总件数
     */
    @Select("select ifnull(count(distinct a.sku),0) as total, b.sku, b.count " +
            "from t_shopping_cart a left join t_shopping_cart b on b.user_id=a.user_id and b.sku=#{sku} " +
            "where a.user_id=#{userId}")
    CartBean selectCount(@Param("userId") Integer userId,
                         @Param("sku") Long sku);

    /**
     * 更新购物车数量（+号 -号）
     */
    @Update("update t_shopping_cart set count= if(count + #{count} < 1, 1, count + #{count}) where user_id=#{userId} and sku=#{sku}")
    int updateCart(@Param("userId") Integer userId,
                   @Param("sku") Long sku,
                   @Param("count") Integer count);


    /**
     * 删除购物车中商品
     */
    @Delete("delete from t_shopping_cart where user_id=#{userId} and sku in (${sku})")
    int deleteCartAll(@Param("userId") Integer userId,
                      @Param("sku") String sku);


    /**
     * 查询用户购物车中所有skuid 和 count
     */
    @Select("select sku,count from t_shopping_cart where user_id=#{userId} order by ctime desc")
    List<CartProductBean> selectAllSku(@Param("userId") Integer userId);

    /**
     * 查询购物车sku数量
     */
    @Select("select count(sku) from t_shopping_cart where user_id=#{userId}")
    Integer selectTotal(@Param("userId") Integer userId);

}