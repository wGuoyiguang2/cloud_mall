package com.cibnvideo.jdsynctask.dao;

import com.cibnvideo.jdsynctask.model.Province;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface ProvinceDao {
	Province get(Integer provinceId);
	
	List<Province> list(Map<String, Object> map);

	int count(Map<String, Object> map);
	
	int save(Province province);
	
	int update(Province province);
	
	int remove(Integer provinceId);
	
	int batchRemove(@Param("provinceIds") Integer[] provinceIds);
}
