package com.cibnvideo.jdsynctask.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface ThirdPartService {

    Map<Long, BigDecimal> listJDSitePrice(List<Long> skuList);
}
