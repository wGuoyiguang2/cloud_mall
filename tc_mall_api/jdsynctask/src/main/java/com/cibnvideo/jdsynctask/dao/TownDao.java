package com.cibnvideo.jdsynctask.dao;

import com.cibnvideo.jdsynctask.model.Town;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface TownDao {
	Town get(Integer countyId);
	
	List<Town> list(Map<String, Object> map);

	int count(Map<String, Object> map);
	
	int save(Town town);
	
	int update(Town town);
	
	int remove(Integer townId);

	int removeByCountyId(Integer countyId);

	int removeByCityId(Integer cityId);

	int removeByProvinceId(Integer provinceId);
	
	int batchRemove(@Param("townIds") Integer[] townIds);
}
