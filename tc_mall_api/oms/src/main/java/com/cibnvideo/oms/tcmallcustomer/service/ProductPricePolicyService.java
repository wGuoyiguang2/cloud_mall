package com.cibnvideo.oms.tcmallcustomer.service;

import com.cibnvideo.oms.tcmallcustomer.bean.PriceProduct;

import java.util.List;
import java.util.Map;

public interface ProductPricePolicyService {
    PriceProduct get(Integer id);

    List<PriceProduct> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(PriceProduct priceProduct);

    int update(PriceProduct priceProduct);

    int remove(Integer id);

    int batchRemove(Map<String, Object> map);

    PriceProduct getBySku(Integer venderId, Long sku);
}
