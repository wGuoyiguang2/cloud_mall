package com.cibnvideo.jdsynctask.service;

public interface JdProductSyncService {
    void syncProductDetailStart()throws Exception;
    int productRemove(Long sku);
    int updateState(Long sku, Integer state);
    int updateProductDetail(String sku);
    int updatePictureOfPc(Long sku);
    int updatePictureOfMobile(Long sku);
}
