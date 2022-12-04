package com.cibnvideo.oms.tcmallcustomer.dao;

import com.cibnvideo.oms.tcmallcustomer.bean.ProductCollection;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ProductCollectionDao {

    List<ProductCollection> list(Map<String,Object> map);

    ProductCollection get(Integer id);

    int count(Map<String,Object> map);

    int save(ProductCollection productCollection);

    int update(ProductCollection productCollection);

    int remove(Integer id);
}
