package com.cibnvideo.oms.tcmallcustomer.service.impl;

import com.cibnvideo.oms.tcmallcustomer.bean.ProductCollection;
import com.cibnvideo.oms.tcmallcustomer.dao.ProductCollectionDao;
import com.cibnvideo.oms.tcmallcustomer.service.ProductCollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ProductCollectionServiceImpl implements ProductCollectionService {

    @Autowired
    ProductCollectionDao productCollectionDao;
    @Override
    public List<ProductCollection> list(Map<String, Object> map) {
        return productCollectionDao.list(map);
    }

    @Override
    public ProductCollection get(Integer id) {
        return productCollectionDao.get(id);
    }

    @Override
    public int count(Map<String, Object> map) {
        return productCollectionDao.count(map);
    }

    @Override
    public int save(ProductCollection productCollection) {
        return productCollectionDao.save(productCollection);
    }

    @Override
    public int update(ProductCollection productCollection) {
        return productCollectionDao.update(productCollection);
    }

    @Override
    public int remove(Integer id) {
        return productCollectionDao.remove(id);
    }
}
