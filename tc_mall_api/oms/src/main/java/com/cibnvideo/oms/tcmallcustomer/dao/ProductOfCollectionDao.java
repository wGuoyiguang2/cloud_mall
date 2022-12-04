package com.cibnvideo.oms.tcmallcustomer.dao;

import com.cibnvideo.oms.tcmallcustomer.bean.ProductOfCollection;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface ProductOfCollectionDao {

    List<ProductOfCollection> list(Map<String, Object> map);

    ProductOfCollection get(Integer id);

    int count(Map<String, Object> map);

    int save(ProductOfCollection productOfCollection);

    int batchSave(List<ProductOfCollection> productOfCollections);

    int update(ProductOfCollection productOfCollection);

    int remove(Integer id);

    int batchRemove(Map<String, Object> map);

    Integer getCollectionIdBySku(@Param("venderId") Integer venderId,@Param("sku") Long sku);
}
