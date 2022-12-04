package com.cibnvideo.oms.tcmallcustomer.service.impl;

import com.cibnvideo.oms.tcmallcustomer.bean.PriceProduct;
import com.cibnvideo.oms.tcmallcustomer.dao.ProductPricePolicyDao;
import com.cibnvideo.oms.tcmallcustomer.service.ProductPricePolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ProductPricePolicyServiceImpl implements ProductPricePolicyService {

    @Autowired
    ProductPricePolicyDao productPricePolicyDao;
    @Override
    public PriceProduct get(Integer id) {
        return productPricePolicyDao.get(id);
    }

    @Override
    public List<PriceProduct> list(Map<String, Object> map) {
        return productPricePolicyDao.list(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return productPricePolicyDao.count(map);
    }

    @Override
    public int save(PriceProduct priceProduct) {
        return productPricePolicyDao.save(priceProduct);
    }

    @Override
    public int update(PriceProduct priceProduct) {
        return productPricePolicyDao.update(priceProduct);
    }

    @Override
    public int remove(Integer id) {
        return productPricePolicyDao.remove(id);
    }

    @Override
    public int batchRemove(Map<String, Object> map) {
        return productPricePolicyDao.batchRemove(map);
    }

    @Override
    public PriceProduct getBySku(Integer venderId, Long sku) {
        return productPricePolicyDao.getBySku(venderId, sku);
    }
}
