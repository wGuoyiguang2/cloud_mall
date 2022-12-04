package com.hqtc.common.config;

import com.hqtc.common.utils.HttpRequestUtil;
import com.hqtc.common.utils.ServletOperate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ip白名单拦截器
 * 拦截规则：ipWhiteList设置为‘0.0.0.0’则全部都可访问,多个ip用逗号隔开
 * Created by wanghaoyang on 19-1-22.
 */
public class IPWhiteListInterceptor implements HandlerInterceptor {

    private Logger logger = LoggerFactory.getLogger(IPWhiteListInterceptor.class);

    private static final String DEFAULTIP = "0.0.0.0";

    private String ipWhiteList;

    public IPWhiteListInterceptor(String ipWhiteList){
        this.ipWhiteList = ipWhiteList;
    }


    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse response, Object var3) throws Exception{
        String ip = HttpRequestUtil.getIpAddress(httpServletRequest);
        String url = httpServletRequest.getRequestURI();
        if(DEFAULTIP.equals(ipWhiteList)){
            return true;
        }
        if(null == ipWhiteList || !ipWhiteList.contains(ip)){
            try {
                ServletOperate.writeResult(response, ErrorCode.PERMISSION_DENIED, "IP无权访问");
            } catch (Exception e) {
                logger.warn(e.getMessage());
            }
            logger.warn("调用内部接口被拦截:{}:{}", url, ip);
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest var1, HttpServletResponse var2, Object var3, ModelAndView var4) throws Exception{
    }

    @Override
    public void afterCompletion(HttpServletRequest var1, HttpServletResponse var2, Object var3, Exception var4) throws Exception{
    }
}
