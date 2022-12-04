package com.cibnvideo.jdsynctask.dao;

import com.cibnvideo.jdsynctask.model.StyleOfMobileResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface JdStyleOfMobileDao {
	StyleOfMobileResult get(Long sku);
	
	List<StyleOfMobileResult> list(Map<String, Object> map);

	int count(Map<String, Object> map);
	
	int save(StyleOfMobileResult style);
	
	int update(StyleOfMobileResult style);
	
	int remove(Long sku);
	
	int batchRemove(@Param("skus") Long[] skus);
}
