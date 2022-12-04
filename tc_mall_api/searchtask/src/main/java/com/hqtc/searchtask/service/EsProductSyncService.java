package com.hqtc.searchtask.service;


import com.hqtc.searchtask.model.bean.ProductDo;

public interface EsProductSyncService {
    void productSync(Integer venderId, ProductDo productDo);

    void productSyncByVenderId(Integer venderId);

    void productRemove(Integer venderId, Long sku);

    void venderProductRemove(Integer venderId);

    void productSyncBySku(Integer venderId, Long sku);

    void productSyncByCollectionId(Integer venderId, Integer collectionId);

    void productSyncByCatId(Integer venderId, Integer catId, Integer catType);
}
