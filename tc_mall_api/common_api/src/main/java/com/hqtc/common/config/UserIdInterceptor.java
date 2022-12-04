package com.hqtc.common.config;

import com.hqtc.common.utils.CookieOperate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserIdInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length <= 0){
            request.setAttribute("userId", 0);
            return true;
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
            request.setAttribute("userId", 0);
            return true;
        }
        CookieOperate cookieOperate = CookieOperate.createCookieOperation(cookieValue);
        if (cookieOperate == null){
            request.setAttribute("userId", 0);
            return true;
        }
        int userId = cookieOperate.getUserId();
        if (userId == -1){
            request.setAttribute("userId", 0);
            return true;
        }
        request.setAttribute("userId", userId);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
