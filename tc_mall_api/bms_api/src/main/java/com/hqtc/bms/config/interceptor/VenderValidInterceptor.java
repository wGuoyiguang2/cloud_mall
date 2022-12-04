package com.hqtc.bms.config.interceptor;

import com.hqtc.bms.model.response.TLoginAccountBean;
import com.hqtc.bms.service.rpc.UmsService;
import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.ResultData;
import com.hqtc.common.utils.ServletOperate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by wanghaoyang on 19-1-8.
 */
public class VenderValidInterceptor implements HandlerInterceptor {

    @Autowired
    private UmsService rpcUmsService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String userName = request.getParameter("userName");
        String passWord = request.getParameter("passWord");
        if(null == userName || userName.isEmpty() || null == passWord || passWord.isEmpty()){
            ServletOperate.writeResult(response, ErrorCode.USER_OR_PW_ERROR, "用户名密码错误");
            return false;
        }
        ResultData<TLoginAccountBean> resultData = rpcUmsService.getVenderUserInfo(userName, passWord);
        if(ErrorCode.SUCCESS != resultData.getError()){
            if(ErrorCode.SERVER_EXCEPTION == resultData.getError()) {
                ServletOperate.writeResult(response, resultData.getError(), "服务异常，请重试");
            }else {
                ServletOperate.writeResult(response, resultData.getError(), resultData.getMsg());
            }
            return false;
        }
        if(null == resultData.getData()){
            ServletOperate.writeResult(response, resultData.getError(), "服务异常，请重试");
            return false;
        }
        request.setAttribute("userId", resultData.getData().getUserid());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {}

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {}
}
