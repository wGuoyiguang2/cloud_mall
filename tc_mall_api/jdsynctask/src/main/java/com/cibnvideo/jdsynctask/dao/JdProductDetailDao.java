package com.cibnvideo.jdsynctask.dao;

import com.cibnvideo.jdsynctask.model.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface JdProductDetailDao {
	ProductResult get(Long sku);

	List<ProductResult> list(Map<String, Object> map);

	List<Long> listSkus(@Param("offset") Integer offset, @Param("limit") Integer limit);

	int count(Map<String, Object> map);

	int save(ProductResult product);

	int update(ProductResult product);

	int remove(Long sku);

	int batchRemove(@Param("skus") Long[] skus);

	int updateState(@Param("sku") Long sku, @Param("state") Integer state);

}
