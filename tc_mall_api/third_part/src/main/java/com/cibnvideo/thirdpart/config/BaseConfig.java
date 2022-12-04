package com.cibnvideo.thirdpart.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2019/1/21 18:05
 */
@Component
public class BaseConfig {
    @Value("${service.config.vendorIds}")
    private String vendorIds;
    @Value("${service.config.perCount}")
    private int perCount;
    public String getVendorIds() {
        return vendorIds;
    }

    public int getPerCount() {
        return perCount;
    }
}
