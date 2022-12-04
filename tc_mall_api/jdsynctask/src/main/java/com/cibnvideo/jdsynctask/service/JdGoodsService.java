package com.cibnvideo.jdsynctask.service;

import com.cibnvideo.jdsynctask.model.*;

import java.util.List;
import java.util.Map;

public interface JdGoodsService {

    List<PageNumInfo> getPageNum();

    List<String> getSkus(String pageNum);

    Map<Long, SellPriceResult> getSellPrice(List<Long> skuList);

    ProductResult getProductDetail(Long sku);

    StyleOfPcResult getStyleOfPc(Long sku);

    StyleOfMobileResult getStyleOfMobile(Long sku);

    PictureOfPcResponse getPictureOfPc(Long sku);

    PictureOfMobileResponse getPictureOfMobile(Long sku);

    CategoryResult getCategorys(CategoryPageRequestParams param);

    Map<String, Integer> getAddressProvinces();

    Map<String, Integer> getAddressCityss(Integer provinceId);

    Map<String, Integer> getAddressCountys(Integer cityId);

    Map<String, Integer> getAddressTowns(Integer countyId);

    boolean deleteMessage(String id);

    List<GetMessageResponseResult> getMessage(String types);
}
