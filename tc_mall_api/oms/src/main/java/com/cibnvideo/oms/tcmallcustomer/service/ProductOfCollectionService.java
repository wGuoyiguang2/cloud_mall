package com.cibnvideo.oms.tcmallcustomer.service;

import com.cibnvideo.oms.tcmallcustomer.bean.ProductOfCollection;

import java.util.List;
import java.util.Map;

public interface ProductOfCollectionService {
    List<ProductOfCollection> list(Map<String, Object> map);

    ProductOfCollection get(Integer id);

    int count(Map<String, Object> map);

    int save(ProductOfCollection productOfCollection);

    int batchSave(List<ProductOfCollection> productOfCollections);

    int update(ProductOfCollection productOfCollection);

    int remove(Integer id);

    int batchRemove(Map<String, Object> map);

    Integer getCollectionIdBySku(Integer venderId, Long sku);
}
