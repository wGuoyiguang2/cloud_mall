package com.cibnvideo.thirdpart.service;

import com.cibnvideo.thirdpart.model.bean.JDSitePriceBean;
import com.cibnvideo.thirdpart.model.bean.SyncJDSitePriceResponse;

import java.util.List;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2019/1/21 17:23
 */
public interface JDSitePriceService {
    void syncProductStatus(List<JDSitePriceBean> jdSitePriceBeanList);

    List<JDSitePriceBean> saveJDSitePrice(List<SyncJDSitePriceResponse> jdSitePriceList);

    int save(List<JDSitePriceBean> jdSitePriceBeanList);

    List<JDSitePriceBean> listJDSitePrice(List<Long> skuList);
}
