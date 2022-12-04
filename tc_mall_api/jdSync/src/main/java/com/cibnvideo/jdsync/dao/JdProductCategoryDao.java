package com.cibnvideo.jdsync.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.cibnvideo.jdsync.bean.CategoryResultInfo;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface JdProductCategoryDao {
	CategoryResultInfo get(Integer catId);
	
	List<CategoryResultInfo> list(Map<String,Object> map);

	List<CategoryResultInfo> listByCatIds(@Param("catIds") Integer[] catIds);
	
	int count(Map<String,Object> map);

	int searchcount(Map<String,Object> params);

	List<CategoryResultInfo> searchinfo(Map<String,Object> params);
}
