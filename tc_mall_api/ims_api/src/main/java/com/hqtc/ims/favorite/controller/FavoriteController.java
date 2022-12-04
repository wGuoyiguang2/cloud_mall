package com.hqtc.ims.favorite.controller;

import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.ResultData;
import com.hqtc.common.utils.Tools;
import com.hqtc.ims.common.constant.PathConstants;
import com.hqtc.ims.favorite.model.bean.FavoriteBean;
import com.hqtc.ims.favorite.model.bean.ProductInfo;
import com.hqtc.ims.favorite.model.bean.SellPriceResult;
import com.hqtc.ims.favorite.service.FavoriteFeignClient;
import com.hqtc.ims.favorite.service.FavoriteService;
import com.hqtc.ims.favorite.service.PriceService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.hqtc.common.utils.Tools.getThreadResultData;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/6/25 19:37
 */
@RestController
public class FavoriteController {
    private Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    private FavoriteService favoriteService;
    @Autowired
    private FavoriteFeignClient feignClient;
    @Autowired
    private PriceService priceService;
    /**
     * 通过用户id分页查询收藏商品列表
     * @param userId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping(path= PathConstants.USER_FAVORITE_LIST_GOODS)
    public ResultData listFavoriteGoods(@RequestAttribute("userId") Integer userId,
                                        @RequestParam(value = "venderId") Long venderId,
                                        @RequestParam(value = "pageNum", defaultValue="1") Integer pageNum,
                                        @RequestParam(value = "pageSize", defaultValue="10") Integer pageSize){
        ResultData resultData = Tools.getThreadResultData();
        pageNum=(pageNum<=0?1:pageNum);
        pageSize=(pageSize<=0?10:pageSize);
        List<FavoriteBean> favoriteBeanList=favoriteService.listByUserId(userId,pageNum,pageSize);
        int total=favoriteService.countFavorite(userId);
        List<Long> skuList=new ArrayList<>();
        if(favoriteBeanList!=null) {
            for (FavoriteBean favoriteBean : favoriteBeanList) {
                skuList.add(favoriteBean.getSourceId());
            }
        }
        if(skuList==null||skuList.size()==0){
            return resultData;
        }
        ResultData<List<ProductInfo>> result=feignClient.listUserFavorites(skuList);
        List<ProductInfo> productInfoList=result.getData();
        ResultData<List<SellPriceResult>> sellPriceResult=priceService.getBatchPrices(venderId,skuList);
        List<SellPriceResult> sellPriceResultList=sellPriceResult.getData();
        if(productInfoList!=null){
            for (ProductInfo p:productInfoList) {
                if(sellPriceResultList!=null){
                    for (SellPriceResult s:sellPriceResultList) {
                        if(s.getSkuId().longValue()==p.getSku().longValue()){
                            p.setJdPrice(s.getJdPrice());
                            p.setPrice(s.getPrice());
                        }
                    }
                }
            }
        }
        Map<String,Object> map=new HashMap<>();
        if(result!=null&&result.getError()==0) {
            map.put("total",total);
            map.put("dataList",result.getData());
            resultData.setData(map);
        }else{
            resultData.setError(ErrorCode.SERVER_EXCEPTION);
            resultData.setMsg("获取收藏列表失败！");
        }
        return resultData;
    }

    /**
     * 收藏操作
     * @param userId
     * @param sku
     * @return
     */
    @PostMapping(path=PathConstants.USER_FAVORITE_ADD)
    public ResultData addFavorite(@RequestAttribute("userId") Integer userId,
                                  @RequestParam(value="sku",required = true) Long sku){
        ResultData resultData = getThreadResultData();
        int row = 0;
        try {
            row = favoriteService.addFavorite(userId, sku);
        } catch (DuplicateKeyException e){
            return resultData;
        } catch (Exception e){
            log.error("收藏接口异常："+e.getMessage());
            resultData.setMsg("收藏失败");
            resultData.setError(ErrorCode.WRITE_DATA_ERROR);
        }
        if (row == 0){
            resultData.setMsg("收藏失败");
            resultData.setError(ErrorCode.WRITE_DATA_ERROR);
        }
        return resultData;
    }

    /**
     * 取消收藏操作
     * @param userId
     * @param sku
     * @return
     */
    @PostMapping(path=PathConstants.USER_FAVORITE_DELETE)
    public ResultData deleteFavorite(@RequestAttribute("userId") Integer userId,
                                     @RequestParam(value="sku",required = true) Long sku){
        ResultData resultData = getThreadResultData();
        int row = 0;
        try {
            row = favoriteService.deleteFavorite(userId, sku);
        } catch (Exception e){
            log.error("取消收藏接口异常："+e.getMessage());
            resultData.setMsg("取消收藏失败");
            resultData.setError(ErrorCode.WRITE_DATA_ERROR);
        }
        if (row == 0){
            resultData.setMsg("取消收藏失败");
            resultData.setError(ErrorCode.WRITE_DATA_ERROR);
        }
        return resultData;
    }
    @GetMapping(path = PathConstants.USER_FAVORITE_IS_EXIST)
    public ResultData isExist(@RequestAttribute("userId") Integer userId,
                              @RequestParam(value="sku",required = true) Long sku){
        ResultData resultData = getThreadResultData();
        if(userId==0){
            resultData.setData(0);
            return resultData;
        }
        FavoriteBean favoriteBean=favoriteService.getByUserIdAndSku(userId,sku);
        if(favoriteBean==null){
            resultData.setData(0);
        }else{
            resultData.setData(1);
        }
        return resultData;
    }
}
