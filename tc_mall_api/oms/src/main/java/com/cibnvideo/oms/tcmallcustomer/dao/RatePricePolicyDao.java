package com.cibnvideo.oms.tcmallcustomer.dao;

import com.cibnvideo.oms.tcmallcustomer.bean.PricePolicy;
import com.cibnvideo.oms.tcmallcustomer.bean.PriceRate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Mapper
public interface RatePricePolicyDao {

    PriceRate get(Integer id);

    List<PriceRate> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(PriceRate priceRate);

    int remove(Integer id);

    int batchRemove(Map<String, Object> map);

    PricePolicy getPricePolicyByRate(@Param("venderId") Integer venderId, @Param("rate") BigDecimal rate);
}
