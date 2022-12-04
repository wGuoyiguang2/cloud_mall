package com.cibnvideo.jdsynctask.service;

import java.util.List;

public interface JdPriceSyncService {
    void syncProductPrice() throws Exception;
    boolean updatePrice(List<Long> skuList);
}
