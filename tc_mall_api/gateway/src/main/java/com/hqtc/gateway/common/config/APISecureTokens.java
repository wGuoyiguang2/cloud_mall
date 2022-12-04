package com.hqtc.gateway.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.HashMap;
import java.util.Map;


@Configuration
public class APISecureTokens implements EnvironmentAware {

    @Value("${server.api.tokens}")
    private String tokens;

    public Map<String, String> getTokenMap() {
        return tokenMap;
    }

    public void setTokenMap(Map<String, String> tokenMap) {
        this.tokenMap = tokenMap;
    }

    private Map<String, String> tokenMap = new HashMap<String, String>();

    @Override
    public void setEnvironment(Environment environment) {
        String[] tks = tokens.split(",");
        for (String t:tks){
            String k = environment.getProperty("server.api."+t);
            tokenMap.put(t, k);
        }
    }
}
