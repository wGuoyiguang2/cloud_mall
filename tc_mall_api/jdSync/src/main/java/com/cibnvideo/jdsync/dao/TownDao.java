package com.cibnvideo.jdsync.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.cibnvideo.jdsync.bean.Town;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TownDao {
	Town get(Integer countyId);
	
	List<Town> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
}
