package com.cibnvideo.oms.tcmallcustomer.service;

import com.cibnvideo.oms.tcmallcustomer.bean.PriceCollection;
import com.cibnvideo.oms.tcmallcustomer.bean.PricePolicy;

import java.util.List;
import java.util.Map;

public interface CollectionPricePolicyService {
    PriceCollection get(Integer id);

    List<PriceCollection> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(PriceCollection priceCollection);

    int remove(Integer id);

    int batchRemove(Map<String, Object> map);

    PricePolicy getByCollectionId(Integer venderId, Integer collectionId);
}
