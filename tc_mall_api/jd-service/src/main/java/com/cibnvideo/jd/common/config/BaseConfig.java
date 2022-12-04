package com.cibnvideo.jd.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/6/22 14:36
 */
@Component
public class BaseConfig {
    @Value("${spring.profiles.active}")
    private String profiles;
    @Value("${jd.username}")
    private String username;
    @Value("${jd.password}")
    private String password;
    @Value("${jd.app_key}")
    private String appKey;
    @Value("${jd.app_secret}")
    private String appSecret;
    @Value("${jd.url.token}")
    private String tokenUrl;
    @Value("${jd.url.api}")
    private String apiUrl;
    @Value("${redis.token_key}")
    private String jdTokenKey;
    @Value("${mail.url}")
    private String mailUrl;
    @Value("${mail.user}")
    private String mailUser;
    @Value("${mail.key}")
    private String mailKey;

    public String getMailKey() {
        return mailKey;
    }

    public String getMailUrl() {
        return mailUrl;
    }

    public String getMailUser() {
        return mailUser;
    }

    public String getProfiles() {
        return profiles;
    }

    public String getJdTokenKey() {
        return jdTokenKey;
    }

    public String getTokenUrl() {
        return tokenUrl;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getAppKey() {
        return appKey;
    }

    public String getAppSecret() {
        return appSecret;
    }
}
