package com.cibnvideo.oms.tcmallcustomer.service.impl;

import com.cibnvideo.oms.tcmallcustomer.bean.ProductRemove;
import com.cibnvideo.oms.tcmallcustomer.dao.ProductRemoveDao;
import com.cibnvideo.oms.tcmallcustomer.service.ProductRemoveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ProductRemoveServiceImpl implements ProductRemoveService {

    @Autowired
    ProductRemoveDao productRemoveDao;
    @Override
    public ProductRemove get(Integer id) {
        return productRemoveDao.get(id);
    }

    @Override
    public List<ProductRemove> list(Map<String, Object> map) {
        return productRemoveDao.list(map);
    }

    @Override
    public List<Long> skusByVenderId(Integer venderId) {
        return productRemoveDao.skusByVenderId(venderId);
    }

    @Override
    public int count(Map<String, Object> map) {
        return productRemoveDao.count(map);
    }

    @Override
    public int save(ProductRemove productRemove) {
        return productRemoveDao.save(productRemove);
    }

    @Override
    public int batchSave(List<ProductRemove> productRemoves) {
        if(productRemoves == null || productRemoves.isEmpty()) {
            return 0;
        }
        return productRemoveDao.batchSave(productRemoves);
    }

    @Override
    public int update(ProductRemove productRemove) {
        return productRemoveDao.update(productRemove);
    }

    @Override
    public int remove(Integer id) {
        return productRemoveDao.remove(id);
    }

    @Override
    public int removeBySku(Integer venderId, Long sku) {
        return productRemoveDao.removeBySku(venderId, sku);
    }

    @Override
    public int batchRemove(Map<String, Object> map) {
        return productRemoveDao.batchRemove(map);
    }

    @Override
    public int batchRemoveBySku(List<ProductRemove> productRemoves) {
        if(productRemoves == null || productRemoves.isEmpty()) {
            return 0;
        }
        return productRemoveDao.batchRemoveBySku(productRemoves);
    }
}
