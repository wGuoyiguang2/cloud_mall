package com.cibnvideo.jdsynctask.dao;

import com.cibnvideo.jdsynctask.model.PictureOfPcResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface JdPictureOfPcDao {
	PictureOfPcResponse get(Long sku);
	
	List<PictureOfPcResponse> list(Map<String, Object> map);

	int count(Map<String, Object> map);
	
	int save(PictureOfPcResponse picture);
	
	int update(PictureOfPcResponse picture);
	
	int remove(Long sku);
	
	int batchRemove(@Param("skus") Long[] skus);
}
