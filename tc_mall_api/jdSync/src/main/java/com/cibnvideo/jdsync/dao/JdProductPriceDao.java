package com.cibnvideo.jdsync.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.cibnvideo.jdsync.bean.SellPriceResult;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface JdProductPriceDao {
	SellPriceResult get(Long skuId);

	List<SellPriceResult> getPriceBatch(@Param("skus") List<Long> skus);
	
	List<SellPriceResult> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
}
