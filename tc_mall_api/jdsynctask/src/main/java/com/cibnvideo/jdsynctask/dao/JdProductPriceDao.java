package com.cibnvideo.jdsynctask.dao;

import com.cibnvideo.jdsynctask.model.SellPriceResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface JdProductPriceDao {
	SellPriceResult get(Long skuId);
	
	List<SellPriceResult> list(Map<String, Object> map);

	int count(Map<String, Object> map);
	
	int save(SellPriceResult product);
	
	int update(SellPriceResult product);
	
	int remove(Long sku);
	
	int batchRemove(@Param("skus") Long[] skus);
}
