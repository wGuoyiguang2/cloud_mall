package com.hqtc.cms.interceptor;

import com.hqtc.common.config.CookieInterceptor;
import com.hqtc.common.config.UserIdInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class ApiConfigure extends WebMvcConfigurerAdapter{

    @Bean
    UserIdInterceptor userIdInterceptor(){
        return new UserIdInterceptor();
    }

    @Bean
    CookieInterceptor cookieInterceptor(){
        return new CookieInterceptor();
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(cookieInterceptor())
                .addPathPatterns("/v1/product/flashSale");
        registry.addInterceptor(userIdInterceptor())
                .addPathPatterns("/v1/search/product**");
        super.addInterceptors(registry);
    }
}
