package com.cibnvideo.jdsync.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.cibnvideo.jdsync.bean.PictureOfMobileResponse;
import com.cibnvideo.jdsync.bean.StyleOfPcResult;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface JdPictureOfMobileDao {
	PictureOfMobileResponse get(Long sku);
	
	List<PictureOfMobileResponse> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
}
