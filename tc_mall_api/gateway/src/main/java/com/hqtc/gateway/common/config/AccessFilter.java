package com.hqtc.gateway.common.config;

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
import java.util.List;

@Component
public class AccessFilter extends ZuulFilter {

    @Autowired
    private APISecureTokens apiSecureTokens;

    @Autowired RouterFilter routerFilter;

    private static Logger log = LoggerFactory.getLogger(AccessFilter.class);

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
        List routers = routerFilter.getRouterList();
        if (routers.contains(httpServletRequest.getRequestURI())){
            return null;
        }
        String timeStamp = httpServletRequest.getParameter("timestamp");
        String rand = httpServletRequest.getParameter("rand");
        String token = httpServletRequest.getParameter("token");
        String clientOpaque = httpServletRequest.getParameter("opaque");

        if(timeStamp==null){
            timeStamp = ((HttpServletRequestWrapper) httpServletRequest).getRequest().getParameter("timestamp");
        }
        if(rand==null){
            rand = ((HttpServletRequestWrapper) httpServletRequest).getRequest().getParameter("rand");
        }
        if(token==null){
            token = ((HttpServletRequestWrapper) httpServletRequest).getRequest().getParameter("token");
        }
        if(clientOpaque == null){
            clientOpaque = ((HttpServletRequestWrapper) httpServletRequest).getRequest().getParameter("opaque");
        }
        if(timeStamp==null || rand==null || token==null || clientOpaque == null){
            log.warn("access token is empty");
            ctx.setSendZuulResponse(false);
            try {
                ServletOperate.writeResult(ctx.getResponse(), ErrorCode.MISS_PARAM, "缺少参数");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        String secureToken = apiSecureTokens.getTokenMap().get(token);
        String serverOpaque = ServletOperate.calcOpaque(httpServletRequest, secureToken);
        if("".equals(serverOpaque) || !serverOpaque.equals(clientOpaque)){
            ctx.setSendZuulResponse(false);
            try {
                ServletOperate.writeResult(ctx.getResponse(), ErrorCode.OPAQUE_ERROR, "opaque计算错误");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        log.info("access token ok");
        return null;
    }
}