package com.cibnvideo.jdsynctask.dao;

import com.cibnvideo.jdsynctask.model.City;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface CityDao {
	City get(Integer cityId);
	
	List<City> list(Map<String, Object> map);

	int count(Map<String, Object> map);
	
	int save(City city);
	
	int update(City city);
	
	int remove(Integer cityId);

	int removeByProvinceId(Integer provinceId);
	
	int batchRemove(@Param("cityIds") Integer[] cityIds);
}
