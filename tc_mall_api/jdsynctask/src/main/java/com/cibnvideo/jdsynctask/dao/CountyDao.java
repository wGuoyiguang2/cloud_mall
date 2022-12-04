package com.cibnvideo.jdsynctask.dao;

import com.cibnvideo.jdsynctask.model.County;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface CountyDao {
	County get(Integer countyId);
	
	List<County> list(Map<String, Object> map);

	int count(Map<String, Object> map);
	
	int save(County county);
	
	int update(County county);
	
	int remove(Integer countyId);

	int removeByCityId(Integer cityId);

	int removeByProvinceId(Integer provinceId);
	
	int batchRemove(@Param("countyIds") Integer[] countyIds);
}
