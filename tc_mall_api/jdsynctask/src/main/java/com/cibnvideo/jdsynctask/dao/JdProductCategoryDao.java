package com.cibnvideo.jdsynctask.dao;

import com.cibnvideo.jdsynctask.model.CategoryResultInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface JdProductCategoryDao {
	CategoryResultInfo get(Integer catId);
	
	List<CategoryResultInfo> list(Map<String, Object> map);

	int count(Map<String, Object> map);

	int save(CategoryResultInfo category);

	int update(CategoryResultInfo category);

	int remove(Integer catId);

	int batchRemove(@Param("catIds") Integer[] catIds);
}
