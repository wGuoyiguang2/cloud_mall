package com.hqtc.common.config;

import com.hqtc.common.utils.CookieOperate;
import com.hqtc.common.utils.ServletOperate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length <= 0){
            ServletOperate.writeResult(response, ErrorCode.NOT_LOGIN, "没有登录");
            return false;
        }
        String cookieValue = null;
        for (Cookie c:cookies){
            if (! CookieOperate.COOKIE_NAME.equals(c.getName())){
                continue;
            }
            cookieValue = c.getValue();
            break;
        }
        if (cookieValue == null){
            ServletOperate.writeResult(response, ErrorCode.NOT_LOGIN, "没有登录");
            return false;
        }
        CookieOperate cookieOperate = CookieOperate.createCookieOperation(cookieValue);
        if (cookieOperate == null){
            ServletOperate.writeResult(response, ErrorCode.NOT_LOGIN, "没有登录");
            return false;
        }
        int userId = cookieOperate.getUserId();
        if (userId == -1){
            ServletOperate.writeResult(response, ErrorCode.NOT_LOGIN, "没有登录");
            return false;
        }
        int timeStamp = cookieOperate.getTimeStamp();
        if(timeStamp < ((int)(System.currentTimeMillis()/1000))){
            ServletOperate.writeResult(response, ErrorCode.COOKIE_EXPIRE, "cookie过期,请重新登录");
            return false;
        }
        int loginType = cookieOperate.getLoginType();
        int deviceType = cookieOperate.getDeviceType();
        request.setAttribute("loginType", loginType);
        request.setAttribute("userId", userId);
        request.setAttribute("deviceType", deviceType);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
