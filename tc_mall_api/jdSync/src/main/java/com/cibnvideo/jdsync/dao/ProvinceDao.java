package com.cibnvideo.jdsync.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.cibnvideo.jdsync.bean.Province;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ProvinceDao {
	Province get(Integer provinceId);
	
	List<Province> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
}
