package com.hqtc.ums.controller;

import com.hqtc.common.config.ErrorCode;
import com.hqtc.common.response.ResultData;
import com.hqtc.common.utils.CookieOperate;
import com.hqtc.ums.model.response.LoginResponseBean;
import com.hqtc.ums.utils.AESTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by wanghaoyang on 18-7-6.
 */
public class CommonController {
    private Logger logger = LoggerFactory.getLogger("CommonController");

    private static final String AES_KEY = "I230GFRQERG2DGMS";//AES密码
    private static final int COOKIEEXPIRES = 2592000;//cookie有效时间(30天)
    private static final String COOKIEPATH = "/";//cookie有效路径

    /**
     * 将userId添加至Cookie
     * @param response
     * @param userId 用户id
     */
    public void addCookie(HttpServletResponse response, int userId) {
        try {
            String word = AESTool.Encrypt(String.valueOf(userId), AES_KEY);
            Cookie cookie = new Cookie("userId", word);
            cookie.setMaxAge(COOKIEEXPIRES);
            cookie.setPath(COOKIEPATH);
            response.addCookie(cookie);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public void resolveResult(ResultData resultData, HttpServletResponse response){
        if(0 != resultData.getError()){
            return;
        }
        Map data = (Map)resultData.getData();
        if(null == data || data.isEmpty()){
            return;
        }
        int userId = Integer.parseInt(data.getOrDefault("userId", 0).toString());
        if(userId > 0){
//            addCookie(response, userId);
            CookieOperate.writeCookie(response, userId, 1,1, ((int)(System.currentTimeMillis()/1000)));
        }
    }

    public void resolveResult2(ResultData resultData, HttpServletResponse response){
        if(0 != resultData.getError()){
            return;
        }
        LoginResponseBean data = (LoginResponseBean)resultData.getData();
        if(null == data){
            return;
        }
        int userId = data.getUserId();
        if(userId > 0){
            CookieOperate.writeCookie(response, userId, 1,1, ((int)(System.currentTimeMillis()/1000)));
        }
    }

    /***
     * 用户登出
     * @param request HTTPRequest
     * @param response HTTPResponse
     * @return 标准格式
     */
    public void userLogout(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if(null == cookies){
            return;
        }
        CookieOperate.deleteCookie(response);
    }

    public ResultData umsErrorCodeFormat(int code){
        ResultData result = new ResultData();
        result.setError(code);
        switch (code) {
            case 0:
                result.setError(ErrorCode.SUCCESS);
                result.setMsg("成功");
                break;
            case 1:
                result.setError(ErrorCode.FALI);
                result.setMsg("失败");
                break;
            case 2:
                result.setError(ErrorCode.PARAM_ERROR);
                result.setMsg("参数值错误（手机号或选择类型）！");
                break;
            case -1:
                result.setError(ErrorCode.PHONE_EXIST);
                result.setMsg("手机号已注册！请登录..");
                break;
            case -2:
                result.setError(ErrorCode.USER_NOT_EXIST);
                result.setMsg("该账户不存在！请注册..");
                break;
            case -3:
                result.setError(ErrorCode.PARAM_ERROR);
                result.setMsg("请求参数错误！");
                break;
            case -4:
                result.setError(ErrorCode.CODE_ERROR);
                result.setMsg("验证码错误！");
                break;
            case -5:
                result.setError(ErrorCode.FALI);
                result.setMsg("渠道错误！");
                break;
            case -6:
                result.setError(ErrorCode.PHONE_ERROR);
                result.setMsg("手机号错误！");
                break;
            case -7:
                result.setError(ErrorCode.SERVER_EXCEPTION);
                result.setMsg("系统错误！");
                break;
            case 100:
                result.setError(ErrorCode.PARAM_ERROR);
                result.setMsg("微信请求参数错误！");
                break;
            case 101:
                result.setError(ErrorCode.USER_OR_PW_ERROR);
                result.setMsg("登录失败，用户名或密码错误");
                break;
            case 102:
                result.setError(ErrorCode.WX_NOT_SCAN);
                result.setMsg("微信未扫描");
                break;
            case 103:
                result.setError(ErrorCode.LOGIN_LIMIT);
                result.setMsg("登录超过限制");
                break;
            case 104:
                result.setError(ErrorCode.USER_NOT_EXIST);
                result.setMsg("用户不存在或已被禁用");
                break;
            default:
                result.setError(ErrorCode.FALI);
                result.setMsg("未知");
                break;
        }
        return result;
    }
}
