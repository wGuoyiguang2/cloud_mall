package com.cibnvideo.oms.tcmallcustomer.dao;

import com.cibnvideo.oms.tcmallcustomer.bean.PriceCategory;
import com.cibnvideo.oms.tcmallcustomer.bean.PricePolicy;
import com.cibnvideo.oms.tcmallcustomer.bean.PricePolicyCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface CategoryPricePolicyDao {

    PriceCategory get(Integer id);

    List<PriceCategory> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(PriceCategory priceCategory);

    int remove(Integer id);

    int batchRemove(Map<String, Object> map);

    List<PricePolicyCategory> getByCatId(@Param("venderid") Integer venderid, @Param("cat0") Integer cat0, @Param("cat1") Integer cat1, @Param("cat2") Integer cat2);
}
