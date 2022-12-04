package com.cibnvideo.oms.tcmallcustomer.dao;

import com.cibnvideo.oms.tcmallcustomer.bean.PriceCollection;
import com.cibnvideo.oms.tcmallcustomer.bean.PricePolicy;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface CollectionPricePolicyDao {

    PriceCollection get(Integer id);

    List<PriceCollection> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(PriceCollection priceCollection);

    int remove(Integer id);

    int batchRemove(Map<String, Object> map);

    PricePolicy getByCollectionId(@Param("venderId") Integer venderId, @Param("collectionId") Integer collectionId);
}
