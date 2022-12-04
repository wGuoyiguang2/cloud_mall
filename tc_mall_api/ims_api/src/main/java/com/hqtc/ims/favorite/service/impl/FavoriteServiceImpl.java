package com.hqtc.ims.favorite.service.impl;

import com.hqtc.ims.favorite.model.bean.FavoriteBean;
import com.hqtc.ims.favorite.model.mapper.FavoriteMapper;
import com.hqtc.ims.favorite.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/6/25 18:57
 */
@Service
public class FavoriteServiceImpl implements FavoriteService{
    @Autowired
    private FavoriteMapper favoriteMapper;
    @Override
    public int addFavorite(Integer userId, Long sku) {
        return favoriteMapper.addFavorite(userId, sku);
    }

    @Override
    public int deleteFavorite(Integer userId, Long sku) {
        return favoriteMapper.deleteFavorite(userId, sku);
    }

    @Override
    public List<FavoriteBean> listByUserId(Integer userId,Integer pageNum,Integer pageSize) {
        Integer startIndex=pageSize*(pageNum-1);
        return favoriteMapper.listByUserId(userId,startIndex,pageSize);
    }

    @Override
    public FavoriteBean getByUserIdAndSku(Integer userId, Long sku) {
        return favoriteMapper.getByUserIdAndSku(userId,sku);
    }

    @Override
    public int countFavorite(Integer userId) {
        return favoriteMapper.countFavorite(userId);
    }

}
