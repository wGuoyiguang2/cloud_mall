package com.cibnvideo.oms.tcmallcustomer.service;

import com.cibnvideo.oms.tcmallcustomer.bean.ProductCollection;

import java.util.List;
import java.util.Map;

public interface ProductCollectionService {
    List<ProductCollection> list(Map<String,Object> map);

    ProductCollection get(Integer id);

    int count(Map<String,Object> map);

    int save(ProductCollection productCollection);

    int update(ProductCollection productCollection);

    int remove(Integer id);
}
