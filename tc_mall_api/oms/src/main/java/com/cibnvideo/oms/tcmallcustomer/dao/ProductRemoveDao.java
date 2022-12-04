package com.cibnvideo.oms.tcmallcustomer.dao;

import com.cibnvideo.oms.tcmallcustomer.bean.ProductRemove;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface ProductRemoveDao {

    ProductRemove get(Integer id);

    List<ProductRemove> list(Map<String, Object> map);

    List<Long> skusByVenderId(Integer venderId);

    int count(Map<String, Object> map);

    int save(ProductRemove productRemove);

    int batchSave(List<ProductRemove> productRemoves);

    int update(ProductRemove productRemove);

    int remove(Integer id);

    int removeBySku(@Param("venderId") Integer venderId, @Param("sku") Long sku);

    int batchRemove(Map<String, Object> map);

    int batchRemoveBySku(List<ProductRemove> productRemoves);
}
