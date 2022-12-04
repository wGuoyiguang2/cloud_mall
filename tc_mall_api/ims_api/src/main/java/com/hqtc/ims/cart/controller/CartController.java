package com.hqtc.ims.cart.controller;

import com.google.gson.Gson;
import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.ResultData;
import com.hqtc.common.utils.Tools;
import com.hqtc.ims.cart.model.bean.CartProductBean;
import com.hqtc.ims.cart.model.bean.CartProductListBean;
import com.hqtc.ims.cart.service.CartSearchService;
import com.hqtc.ims.cart.service.CartService;
import com.hqtc.ims.common.constant.PathConstants;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * description: 购物车管理 （需要登陆）
 * Created by sunjianqiang  18-9-18
 */
@RestController
public class CartController {

    @Value("${service.config.cartMaxCount}")
    private int cartMaxCount;

    @Autowired
    CartService cartService;

    @Autowired
    CartSearchService  cartSearchService;

    @Autowired
    RedisTemplate redisTemplate;

    @Value("${redis.cartExpire}")
    private Integer expire;
    /**
     * 购物车中添加商品
     * @param userId  用户id
     * @param sku     商品id
     * @param count   商品添加数量 !=0
     * @return
     */
    @RequestMapping(value = PathConstants.ROUTE_CART_ADD,method = RequestMethod.POST)
    public ResultData addCart(@RequestAttribute("userId") Integer userId,
                              @RequestParam Long sku,
                              @RequestParam Integer count){
        count = (count <= 0 ? 1 : count);
        ResultData result= cartService.addCart(userId,sku,count);
        return  result;
    }

    /**
     *  删除购物车中商品（支持批量）
     *  @param userId     用户id
     *  @param sku        商品id 多个sku用逗号分隔
     */
    @RequestMapping(value = PathConstants.ROUTE_CART_DELETE,method = RequestMethod.POST)
    public ResultData deleteCartAll(@RequestAttribute("userId") Integer userId,
                                    @RequestParam String sku){
        if (sku == "" || sku == null){
            ResultData result = Tools.getThreadResultData();
            result.setMsg("商品sku不能为空");
            result.setError(ErrorCode.PARAM_ERROR);
            return  result;
        }
        ResultData result= cartService.deleteCartAll(userId,sku);
        return result;
    }

    /**
     * 更改购物车中商品数量 (+号 -号)
     * @param userId   用户Id
     * @param sku     商品Id
     * @param count    数量 添加 1  删除 -1
     * @return
     */
    @RequestMapping(value = PathConstants.ROUTE_CART_NUM_MODIFY,method = RequestMethod.POST)
    public ResultData updateCart(@RequestAttribute("userId") Integer userId,
                                 @RequestParam Long sku,
                                 @RequestParam Integer count){
        //参数校验
        if(sku == 0 ||  count == 0){
            ResultData result = Tools.getThreadResultData();
            result.setError(ErrorCode.MISS_PARAM);
            result.setMsg("缺少参数");
            return result;
        }
        ResultData result= cartService.updateCart(userId,sku,count);
        return result;
    }

    /**
     * 查询购物车中商品列表
     * @param userId     用户id
     * @param venderId   客户id
     * @param pageNum    页码
     * @param pageSize   每页显示的条数
     * @return
     */
    @RequestMapping(value = PathConstants.ROUTE_CART_LIST,method = RequestMethod.GET)
    public ResultData  getCartList(@RequestAttribute("userId") Integer userId,
                                   @RequestParam Integer venderId,
                                   @RequestParam(value = "pageNum",required = false) Integer pageNum,
                                   @RequestParam(value = "pageSize",required = false) Integer pageSize){
        //下标
        Integer index = (pageNum == null || pageNum <= 0) ? 0: (pageNum-1)*pageSize;
        //每页显示数量
        pageSize = (pageSize == null || pageSize <= 0) ? cartMaxCount: pageSize;

        ResultData result = Tools.getThreadResultData();
        CartProductListBean data =new CartProductListBean();
        //获取redis 中的数据
        List<CartProductBean> cartInfos = redisTemplate.opsForList().range("cart:userId:" + userId, index, index + pageSize - 1);
        if (cartInfos.size() == 0 && redisTemplate.opsForList().size("cart:userId:" + userId) == 0){
            cartInfos = cartService.selectList(userId);
            if (cartInfos.size()>0) {
                redisTemplate.opsForList().rightPushAll("cart:userId:" + userId, cartInfos);
                redisTemplate.expire("cart:userId:" + userId,expire, TimeUnit.SECONDS);
                cartInfos = redisTemplate.opsForList().range("cart:userId:" + userId, index, index + pageSize - 1);
            }
        }
        if(cartInfos != null && cartInfos.size() != 0) {
            String skus="";
            for (int i=0;i<cartInfos.size();i++){
                skus += cartInfos.get(i).getSku()+",";
            }
            skus = skus.substring(0, skus.length() - 1);
            data = cartSearchService.idsQuery(venderId, skus,cartMaxCount).getData();
            if (data != null && data.getDataList() != null && data.getDataList().size() != 0) {
                for (CartProductBean bean : data.getDataList()) {
                    for (CartProductBean cartProductBean : cartInfos) {
                        if (bean.getSku().equals(cartProductBean.getSku())) {
                            cartProductBean.setName(bean.getName());
                            cartProductBean.setImagePath(bean.getImagePath());
                            cartProductBean.setPrice(bean.getPrice());
                            cartProductBean.setState(bean.getState());
                        }
                    }
                }
            }
        }
        data.setTotal(redisTemplate.opsForList().size("cart:userId:" + userId));
        data.setDataList(cartInfos);
        result.setData(data);
        return  result;
    }

    /**
     * 删除购物车商品(供bms支付成功使用)
     * @param userId
     * @param sku
     * @return
     */
    @RequestMapping(value = PathConstants.ROUTE_CART_DELETE_BY_USERID, method = RequestMethod.POST)
    public ResultData deleteCartProduct(@RequestParam("userId") Integer userId,
                                        @RequestParam("sku") String sku){
        if (StringUtils.isBlank(sku)){
            ResultData result = Tools.getThreadResultData();
            result.setMsg("商品sku不能为空");
            result.setError(ErrorCode.PARAM_ERROR);
            return  result;
        }
        ResultData result= cartService.deleteCartAll(userId,sku);
        return result;
    }
}
