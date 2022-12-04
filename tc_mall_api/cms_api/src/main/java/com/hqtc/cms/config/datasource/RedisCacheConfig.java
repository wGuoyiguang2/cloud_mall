package com.hqtc.cms.config.datasource;

import com.hqtc.common.config.RedisCacheBaseConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;

import java.lang.reflect.Method;

/**
 * Created by makuan on 17-9-27.
 */
@Configuration
@EnableCaching
public class RedisCacheConfig extends RedisCacheBaseConfig {

    @Value("${redis.expire}")
    private Integer expire;

    @Bean(name = "CmsCacheManager")
    public CacheManager CmsCacheManager(RedisTemplate<?, ?> redisTemplate) {
        RedisCacheManager redisCacheManager = new RedisCacheManager(redisTemplate);
        redisCacheManager.setDefaultExpiration(expire);
        return (CacheManager) redisCacheManager;
    }

    @Bean(name = "CmsKeyGenerator")
    public KeyGenerator CmsKeyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object o, Method method, Object... objects) {
                return "cms_";
            }
        };
    }
}
