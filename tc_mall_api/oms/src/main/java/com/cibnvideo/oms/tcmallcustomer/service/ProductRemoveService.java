package com.cibnvideo.oms.tcmallcustomer.service;

import com.cibnvideo.oms.tcmallcustomer.bean.ProductRemove;

import java.util.List;
import java.util.Map;

public interface ProductRemoveService {
    ProductRemove get(Integer id);

    List<ProductRemove> list(Map<String, Object> map);

    List<Long> skusByVenderId(Integer venderId);

    int count(Map<String, Object> map);

    int save(ProductRemove productRemove);

    int batchSave(List<ProductRemove> productRemoves);

    int update(ProductRemove productRemove);

    int remove(Integer id);

    int removeBySku(Integer venderId, Long sku);

    int batchRemove(Map<String, Object> map);

    int batchRemoveBySku(List<ProductRemove> productRemoves);

}
