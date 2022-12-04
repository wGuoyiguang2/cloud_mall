package com.cibnvideo.oms.tcmallcustomer.service.impl;

import com.cibnvideo.oms.tcmallcustomer.bean.PriceCollection;
import com.cibnvideo.oms.tcmallcustomer.bean.PricePolicy;
import com.cibnvideo.oms.tcmallcustomer.dao.CollectionPricePolicyDao;
import com.cibnvideo.oms.tcmallcustomer.service.CollectionPricePolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CollectionPricePolicyServiceImpl implements CollectionPricePolicyService {

    @Autowired
    CollectionPricePolicyDao collectionPricePolicyDao;
    @Override
    public PriceCollection get(Integer id) {
        return collectionPricePolicyDao.get(id);
    }

    @Override
    public List<PriceCollection> list(Map<String, Object> map) {
        return collectionPricePolicyDao.list(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return collectionPricePolicyDao.count(map);
    }

    @Override
    public int save(PriceCollection priceCollection) {
        return collectionPricePolicyDao.save(priceCollection);
    }

    @Override
    public int remove(Integer id) {
        return collectionPricePolicyDao.remove(id);
    }

    @Override
    public int batchRemove(Map<String, Object> map) {
        return collectionPricePolicyDao.batchRemove(map);
    }

    @Override
    public PricePolicy getByCollectionId(Integer venderId, Integer collectionId) {
        return collectionPricePolicyDao.getByCollectionId(venderId, collectionId);
    }
}
