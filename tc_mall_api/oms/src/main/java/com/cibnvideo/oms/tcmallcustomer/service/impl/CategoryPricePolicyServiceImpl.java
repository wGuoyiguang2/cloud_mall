package com.cibnvideo.oms.tcmallcustomer.service.impl;

import com.cibnvideo.oms.tcmallcustomer.bean.PriceCategory;
import com.cibnvideo.oms.tcmallcustomer.bean.PricePolicyCategory;
import com.cibnvideo.oms.tcmallcustomer.dao.CategoryPricePolicyDao;
import com.cibnvideo.oms.tcmallcustomer.service.CategoryPricePolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
@Service
public class CategoryPricePolicyServiceImpl implements CategoryPricePolicyService {

    @Autowired
    CategoryPricePolicyDao categoryPricePolicyDao;
    @Override
    public PriceCategory get(Integer id) {
        return categoryPricePolicyDao.get(id);
    }

    @Override
    public List<PriceCategory> list(Map<String, Object> map) {
        return categoryPricePolicyDao.list(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return categoryPricePolicyDao.count(map);
    }

    @Override
    public int save(PriceCategory priceCategory) {
        return categoryPricePolicyDao.save(priceCategory);
    }

    @Override
    public int remove(Integer id) {
        return categoryPricePolicyDao.remove(id);
    }

    @Override
    public int batchRemove(Map<String, Object> map) {
        return categoryPricePolicyDao.batchRemove(map);
    }

    @Override
    public List<PricePolicyCategory> getByCatId(Integer venderid, Integer cat0, Integer cat1, Integer cat2) {
        return categoryPricePolicyDao.getByCatId(venderid, cat0, cat1, cat2);
    }
}
