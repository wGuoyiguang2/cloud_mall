package com.cibnvideo.jdsync.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.cibnvideo.jdsync.bean.City;
import com.cibnvideo.jdsync.bean.County;
import com.cibnvideo.jdsync.bean.Province;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CountyDao {
	County get(Integer countyId);
	
	List<County> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
}
