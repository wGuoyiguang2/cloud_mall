package com.cibnvideo.jdsynctask.service.impl;

import com.cibnvideo.jdsynctask.model.JDSitePriceBean;
import com.cibnvideo.jdsynctask.service.ThirdPartService;
import com.cibnvideo.jdsynctask.thirdpartapi.ThirdPartApi;
import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.ResultData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ThirdPartServiceImpl implements ThirdPartService {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    ThirdPartApi thirdPartApi;

    @Override
    public Map<Long, BigDecimal> listJDSitePrice(List<Long> skuList) {
        ResultData<List<JDSitePriceBean>> resultData = thirdPartApi.listJDSitePrice(skuList);
        if (resultData.getError() == ErrorCode.SUCCESS) {
            List<JDSitePriceBean> priceBeanList = resultData.getData();
            if (priceBeanList != null) {
                return priceBeanList.stream().collect(Collectors.toMap(JDSitePriceBean::getSku, priceBean -> priceBean.getPrice()));
            }
        } else {
            logger.error(resultData.getMsg());
        }
        return null;
    }
}
