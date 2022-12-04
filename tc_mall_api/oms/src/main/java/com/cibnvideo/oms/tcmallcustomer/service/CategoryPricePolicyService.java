package com.cibnvideo.oms.tcmallcustomer.service;

import com.cibnvideo.oms.tcmallcustomer.bean.PriceCategory;
import com.cibnvideo.oms.tcmallcustomer.bean.PricePolicyCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CategoryPricePolicyService {
    PriceCategory get(Integer id);

    List<PriceCategory> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(PriceCategory priceCategory);

    int remove(Integer id);

    int batchRemove(Map<String, Object> map);

    List<PricePolicyCategory> getByCatId(Integer venderid, Integer cat0, Integer cat1, Integer cat2);
}
