package com.hqtc.ims.favorite.service;

import com.hqtc.ims.favorite.model.bean.FavoriteBean;

import java.util.List;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/6/25 18:57
 */
public interface FavoriteService {
    int addFavorite(Integer userId, Long sku);

    int deleteFavorite(Integer userId, Long sku);

    List<FavoriteBean> listByUserId(Integer userId,Integer pageNum,Integer pageSize);

    FavoriteBean getByUserIdAndSku(Integer userId, Long sku);

    int countFavorite(Integer userId);
}
