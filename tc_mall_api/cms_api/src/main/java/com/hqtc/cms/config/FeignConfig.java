package com.hqtc.cms.config;

import com.google.gson.Gson;
import com.hqtc.cms.model.bean.flashsale.OrderParams;
import com.hqtc.cms.model.bean.flashsale.PayOrderBean;
import feign.Feign;
import feign.Feign.Builder;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/8/17 13:16
 */
@Configuration
public class FeignConfig {
    @Bean(name = "feignHystrixBuilder")
    public Builder feignBuilder(){
        return Feign.builder().requestInterceptor(new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate requestTemplate) {
                Gson gson=new Gson();
                String url=requestTemplate.url();
                String cookie="";
                if(Router.V1_BMS_FLASHSALE_ORDER_CREATE.equals(url)){
                    cookie=gson.fromJson(new String(requestTemplate.body()), OrderParams.class).getCookie();
                }else if(Router.ROUTER_ORDER_DETAIL.equals(url)){
                    Map<String, Collection<String>> queries = requestTemplate.queries();
                    List<String> list= (List<String>)(queries.get("cookie"));
                    cookie=list.get(0);
                }else{
                    return;
                }
                requestTemplate.header("Content-type", "application/json")
                        .header("Cookie","hqtc="+cookie);
            }
        });
    }
}
