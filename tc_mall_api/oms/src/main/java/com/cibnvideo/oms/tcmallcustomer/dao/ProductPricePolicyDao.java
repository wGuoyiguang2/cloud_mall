package com.cibnvideo.oms.tcmallcustomer.dao;

import com.cibnvideo.oms.tcmallcustomer.bean.PriceCollection;
import com.cibnvideo.oms.tcmallcustomer.bean.PriceProduct;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface ProductPricePolicyDao {

    PriceProduct get(Integer id);

    List<PriceProduct> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(PriceProduct priceProduct);

    int update(PriceProduct priceProduct);

    int remove(Integer id);

    int batchRemove(Map<String, Object> map);

    PriceProduct getBySku(@Param("venderId") Integer venderId, @Param("sku") Long sku);
}
