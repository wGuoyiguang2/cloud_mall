package com.cibnvideo.oms.tcmallcustomer.dao;

import com.cibnvideo.oms.tcmallcustomer.bean.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Mapper
public interface CategoryPictureDao {

    List<CategoryPicture> list(Map<String,Object> map);

    CategoryPicture get(Integer id);

    CategoryPicture getByCatId(@Param("venderId") Integer venderId,@Param("catId") Integer catId);

    List<CategoryPicture> getByCatIds(@Param("venderId") Integer venderId,@Param("catIds") List<Integer> catIds);

    int count(Map<String,Object> map);

    int save(CategoryPicture categoryPicture);

    int update(CategoryPicture categoryPicture);

    int remove(Integer id);

}
