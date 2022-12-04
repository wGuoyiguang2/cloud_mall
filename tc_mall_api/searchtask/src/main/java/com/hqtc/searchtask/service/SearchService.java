package com.hqtc.searchtask.service;


import com.hqtc.searchtask.model.bean.ProductDo;

public interface SearchService {

    boolean insertOrUpdateProduct(Integer venderId, ProductDo productDo);

    boolean productRemove(Integer venderId, Long sku);

    boolean venderRemove(Integer venderId);

    ProductDo getProduct(Integer venderId, Long sku);

}
