package com.cibnvideo.oms.tcmallcustomer.service.impl;

import com.cibnvideo.oms.tcmallcustomer.bean.ProductOfCollection;
import com.cibnvideo.oms.tcmallcustomer.dao.ProductOfCollectionDao;
import com.cibnvideo.oms.tcmallcustomer.service.ProductOfCollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ProductOfCollectionServiceImpl implements ProductOfCollectionService {

    @Autowired
    ProductOfCollectionDao productOfCollectionDao;
    @Override
    public List<ProductOfCollection> list(Map<String, Object> map) {
        return productOfCollectionDao.list(map);
    }

    @Override
    public ProductOfCollection get(Integer id) {
        return productOfCollectionDao.get(id);
    }

    @Override
    public int count(Map<String, Object> map) {
        return productOfCollectionDao.count(map);
    }

    @Override
    public int save(ProductOfCollection productOfCollection) {
        return productOfCollectionDao.save(productOfCollection);
    }

    @Override
    public int batchSave(List<ProductOfCollection> productOfCollections) {
        return productOfCollectionDao.batchSave(productOfCollections);
    }

    @Override
    public int update(ProductOfCollection productOfCollection) {
        return productOfCollectionDao.update(productOfCollection);
    }

    @Override
    public int remove(Integer id) {
        return productOfCollectionDao.remove(id);
    }

    @Override
    public int batchRemove(Map<String, Object> map) {
        return productOfCollectionDao.batchRemove(map);
    }

    @Override
    public Integer getCollectionIdBySku(Integer venderId, Long sku) {
        return productOfCollectionDao.getCollectionIdBySku(venderId, sku);
    }
}
