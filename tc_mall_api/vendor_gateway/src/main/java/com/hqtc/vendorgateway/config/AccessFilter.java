package com.hqtc.vendorgateway.config;

import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.utils.ServletOperate;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.HashMap;

@Component
public class AccessFilter extends ZuulFilter {

    private static Logger log = LoggerFactory.getLogger(AccessFilter.class);

    @Autowired
    private Configurer configurer;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest httpServletRequest = ctx.getRequest();
      	log.info("send {} request to {}", httpServletRequest.getMethod(), httpServletRequest.getRequestURL().toString());
        String appKey = httpServletRequest.getParameter("appKey");
        String vendor = httpServletRequest.getParameter("venderId");

        if(appKey==null){
            appKey = ((HttpServletRequestWrapper) httpServletRequest).getRequest().getParameter("appKey");
        }
        if(vendor==null){
            vendor = ((HttpServletRequestWrapper) httpServletRequest).getRequest().getParameter("venderId");
        }
        if(appKey==null || vendor==null){
            log.error("appKey or venderId is empty");
            ctx.setSendZuulResponse(false);
            try {
                ServletOperate.writeResult(ctx.getResponse(), ErrorCode.MISS_PARAM, "缺少参数");
            } catch (Exception e) {
                log.error(e.getMessage());
            }
            return null;
        }

        HashMap<String, String> cfg = configurer.getAppKey();
        if (cfg == null || cfg.getOrDefault("vendor_"+vendor, null) == null){
            log.error("unknown vendor ...");
            ctx.setSendZuulResponse(false);
            try {
                ServletOperate.writeResult(ctx.getResponse(), ErrorCode.PARAM_ERROR, "参数错误");
            } catch (Exception e) {
                log.error(e.getMessage());
            }
            return null;
        }
        if (!appKey.equals(cfg.getOrDefault("vendor_" + vendor, ""))){
            log.error("invalid appKey or vendor ...");
            ctx.setSendZuulResponse(false);
            try {
                ServletOperate.writeResult(ctx.getResponse(), ErrorCode.PARAM_ERROR, "参数错误");
            } catch (Exception e) {
                log.error(e.getMessage());
            }
            return null;
        }

        log.info("authentication ok");
        return null;
    }
}