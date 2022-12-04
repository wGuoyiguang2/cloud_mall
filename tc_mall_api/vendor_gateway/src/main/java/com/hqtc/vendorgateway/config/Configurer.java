package com.hqtc.vendorgateway.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * Created by makuan on 19-1-11.
 */

@Component
@ConfigurationProperties(prefix = "server")
public class Configurer {

    private HashMap<String, String> appKey = new HashMap<>();

    public HashMap<String, String> getAppKey() {
        return appKey;
    }

    public void setAppKey(HashMap<String, String> appKey) {
        this.appKey = appKey;
    }
}
