package com.cibnvideo.jdsynctask.dao;

import com.cibnvideo.jdsynctask.model.StyleOfPcResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface JdStyleOfPcDao {
	StyleOfPcResult get(Long sku);
	
	List<StyleOfPcResult> list(Map<String, Object> map);

	int count(Map<String, Object> map);
	
	int save(StyleOfPcResult style);
	
	int update(StyleOfPcResult style);
	
	int remove(Long sku);
	
	int batchRemove(@Param("skus") Long[] skus);
}
