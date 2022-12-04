package com.hqtc.ims.interceptor;

import com.hqtc.common.config.CookieInterceptor;
import com.hqtc.common.config.UserIdInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class ApiConfigure extends WebMvcConfigurerAdapter{

    @Bean
    CookieInterceptor cookieInterceptor(){
        return new CookieInterceptor();
    }

    @Bean
    UserIdInterceptor userIdInterceptor(){
        return new UserIdInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(cookieInterceptor())
                .addPathPatterns("/v1/address/**")
                .addPathPatterns("/v1/favorite/list**")
                .addPathPatterns("/v1/favorite/add**")
                .addPathPatterns("/v1/favorite/delete**")
                .addPathPatterns("/v1/invoice/**")
                .addPathPatterns("/v1/cart/**")
                .excludePathPatterns("/v1/address/provincelist**")
                .excludePathPatterns("/v1/address/citylist**")
                .excludePathPatterns("/v1/address/countylist**")
                .excludePathPatterns("/v1/address/townlist**");
        registry.addInterceptor(userIdInterceptor())
                .addPathPatterns("/v1/favorite/check**")
                .addPathPatterns("/v1/search/history/**");
        super.addInterceptors(registry);
    }
}
