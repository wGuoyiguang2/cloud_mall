package com.hqtc.bms.config.interceptor;

import com.hqtc.bms.config.Router;
import com.hqtc.bms.config.VenderRouter;
import com.hqtc.common.config.CookieInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class ApiConfigure extends WebMvcConfigurerAdapter{

    @Value("${ipWhiteList.pmsServer}")
    private String pmsIpWhiteList = "";

    @Bean
    CookieInterceptor cookieInterceptor(){
        return new CookieInterceptor();
    }

    @Bean
    VenderValidInterceptor venderValidInterceptor(){
        return new VenderValidInterceptor();
    }

    @Bean
    PmsIPWhiteListInterceptor pmsIPWhiteListInterceptor(){
        return new PmsIPWhiteListInterceptor(pmsIpWhiteList);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(cookieInterceptor())
                .addPathPatterns(Router.ROUTER_ORDER)
                .addPathPatterns(Router.ROUTER_ORDER_DETAIL)
                .addPathPatterns(Router.ROUTER_CREATE_PAY_QR_CODE)
                .addPathPatterns(Router.ROUTER_PRODUCT_ORDER_DETAIL)
                .addPathPatterns(Router.ROUTER_ORDER_LIST)
                .addPathPatterns(Router.ROUTER_JD_ORDER_DETAIL)
                .addPathPatterns(Router.ROUTER_ORDER_CANCEL)
                .addPathPatterns(Router.ROUTER_SCAN)
                .addPathPatterns("/v1/order/track**")
                .addPathPatterns("/v1/afterSale/**")
                .excludePathPatterns("/v1/afterSale/reason")
                .addPathPatterns(Router.ROUTER_CARD_ALL)
                .addPathPatterns(Router.ROUTER_CARD_DETAIL)
                .addPathPatterns(Router.ROUTER_CARD_BIND)
                .addPathPatterns(Router.ROUTER_CREATE_PAY_MUTIPLE_AUTH)
                .addPathPatterns(Router.ROUTER_CARD_INFO)
                .addPathPatterns(Router.ROUTER_ORDER_DELETE);


        //二级平台合作商用户名和密码的校验
        registry.addInterceptor(venderValidInterceptor())
                .addPathPatterns(VenderRouter.ROUTER_VENDER_BALANCE)
                .addPathPatterns(VenderRouter.ROUTER_VENDER_ORDER)
                .addPathPatterns(VenderRouter.ROUTER_VENDER_NOTIFY)
                .addPathPatterns(VenderRouter.ROUTER_VENDER_TRACK)
                .addPathPatterns(VenderRouter.AFTER_SALE_SERVICETYPE)
                .addPathPatterns(VenderRouter.AFTER_SALE_LIST)
                .addPathPatterns(VenderRouter.AFTER_SALE_ISCAN)
                .addPathPatterns(VenderRouter.AFTER_SALE_APPLYAFTERSALE)
                .addPathPatterns(VenderRouter.AFTER_SALE_CANCELSERVICE)
                .addPathPatterns(VenderRouter.ROUTER_ORDER_CANCEL)
                .addPathPatterns(VenderRouter.ROUTER_ORDER_DETAIL);

        //Pms白名单校验
        registry.addInterceptor(pmsIPWhiteListInterceptor())
                .addPathPatterns(Router.ROUTER_NOTIFY_NEW);

        super.addInterceptors(registry);
    }
}
