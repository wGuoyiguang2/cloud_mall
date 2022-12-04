package com.cibnvideo.oms.tcmallcustomer.dao;

import com.cibnvideo.oms.tcmallcustomer.bean.PricePolicy;
import com.cibnvideo.oms.tcmallcustomer.bean.VenderPayment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Mapper
public interface PricePolicyDao {

    PricePolicy get(Integer id);

    List<PricePolicy> list(Map<String, Object> map);

    List<PricePolicy> getByType(@Param("venderId") Integer venderId, @Param("type") Integer type);

    List<PricePolicy> getPricePolicyByCollectionId(@Param("venderId") Integer venderId, @Param("collectionId") Integer collectionId);

    int count(Map<String, Object> map);

    int save(PricePolicy pricePolicy);

    int update(PricePolicy pricePolicy);

    int remove(Integer id);

    BigDecimal getMaxPricePolicy(Integer venderId);
}
