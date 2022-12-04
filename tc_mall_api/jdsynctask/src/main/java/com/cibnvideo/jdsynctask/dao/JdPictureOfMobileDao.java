package com.cibnvideo.jdsynctask.dao;

import com.cibnvideo.jdsynctask.model.PictureOfMobileResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface JdPictureOfMobileDao {
	PictureOfMobileResponse get(Long sku);
	
	List<PictureOfMobileResponse> list(Map<String, Object> map);

	int count(Map<String, Object> map);
	
	int save(PictureOfMobileResponse picture);
	
	int update(PictureOfMobileResponse picture);
	
	int remove(Long sku);
	
	int batchRemove(@Param("skus") Long[] skus);
}
