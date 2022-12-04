package com.cibnvideo.oms.tcmallcustomer.service;

import com.cibnvideo.oms.tcmallcustomer.bean.PricePolicy;
import com.cibnvideo.oms.tcmallcustomer.bean.PriceRate;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface RatePricePolicyService {
    PriceRate get(Integer id);

    List<PriceRate> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(PriceRate priceRate);

    int remove(Integer id);

    int batchRemove(Map<String, Object> map);

    PricePolicy getPricePolicyByRate(Integer venderId, BigDecimal rate);
}
