package com.cibnvideo.jdsync.dao;

import java.util.List;
import java.util.Map;

import com.cibnvideo.jdsync.bean.*;
import org.apache.ibatis.annotations.Mapper;

import org.apache.ibatis.annotations.Param;

@Mapper
public interface JdProductDetailDao {
	ProductResult get(Long sku);
	
	ProductInfo getinfo(Long sku);

	List<String> getBrandNamesByCat2(Map<String,Object> map);

	List<String> getBrandNamesBySkus(@Param("skus") List<Long> skus);
	
	List<ProductResult> list(Map<String,Object> map);
	
	List<ProductInfo> listinfo(Map<String,Object> map);

	List<ProductInfo> searchinfo(Map<String,Object> map);
	
	int count(Map<String,Object> map);

	int searchcount(Map<String,Object> map);

	List<ProductInfo> listInfo(@Param("skuArray") String[] skuArray);

	List<ProductCtime> getProductCtime(@Param("skus") List<Long> skus);

	List<ProductInfo> getProductInfoBySkus(@Param("skus") List<Long> skus);

	SalesManagerProductDetailVo getSalesManagerProductDetail(@Param("sku")Long sku);

    List<SalesManagerVo> listSalesManager(Map<String, Object> params);

	int countSalesManager(Map<String, Object> params);

	ProductEsBean getProductForEs(Long sku);

	List<ProductEsBean> listProductForEs(Map<String, Object> params);

	int countProductForEs(Map<String, Object> params);

	ProductVideo getProductVideo(@Param("sku") Long sku);

	int saveProductVideo(@Param("sku") Long sku, @Param("videoPath") String videoPath);

	int updateProductVideo(@Param("sku") Long sku, @Param("videoPath") String videoPath);

	List<ProductSynchronizationBean> productDetailList(Map<String,Object> params);

	String getSkus(Map<String, Object> params);
}
