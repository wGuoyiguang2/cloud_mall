package com.cibnvideo.oms.tcmallcustomer.service;

import com.cibnvideo.oms.bean.SellPriceResult;
import com.cibnvideo.oms.tcmallcustomer.bean.PricePolicy;
import com.cibnvideo.oms.tcmallcustomer.bean.VenderSettlement;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface PricePolicyService {
    PricePolicy get(Integer id);

    List<PricePolicy> list(Map<String, Object> map);

    List<PricePolicy> getByType(Integer venderId, Integer type);

    List<PricePolicy> getPricePolicyByCollectionId(Integer venderId, Integer collectionId);

    SellPriceResult getSellPriceBySku(BigDecimal venderPercent, Integer venderId, Long sku);

    int count(Map<String, Object> map);

    int save(PricePolicy pricePolicy);

    int update(PricePolicy pricePolicy);

    int remove(Integer id);

    BigDecimal getMaxPricePolicy(Integer venderId);
}
