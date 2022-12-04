package com.cibnvideo.jdsync.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.cibnvideo.jdsync.bean.PictureOfPcResponse;
import com.cibnvideo.jdsync.bean.StyleOfPcResult;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface JdPictureOfPcDao {
	PictureOfPcResponse get(Long sku);
	
	List<PictureOfPcResponse> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
}
