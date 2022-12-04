package com.hqtc.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieOperate {
    private Logger logger = LoggerFactory.getLogger("CookieOperate");

    public static final int LOGIN_TYPE_PHONE = 1;
    public static final int LOGIN_TYPE_USERNAME = 2;
    public static final int LOGIN_TYPE_WECHAT = 3;

    private static final String COOKIE_HASH_SALT = "643876B6-2F38-4BE0-9FA4-9804C5D07327";
    public static final String COOKIE_NAME = "hqtc";
    private HttpServletResponse httpServletResponse;
    private int userId = -1;
    private int loginType = LOGIN_TYPE_PHONE;
    private int deviceType;
    private int timeStamp;
    private static final int COOKIE_EXPIRE = 3600 * 24 * 365;
    private static final String COOKIE_PATH = "/";
    private static final String AES_KEY = "I230GFRQERG2DGMS";//AES密码

    public int getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(int deviceType) {
        this.deviceType = deviceType;
    }

    public int getLoginType() {
        return loginType;
    }

    public void setLoginType(int loginType) {
        this.loginType = loginType;
    }

    public int getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(int timeStamp) {
        this.timeStamp = timeStamp;
    }

    private String cookieStr;

    public CookieOperate(){
    }

    public CookieOperate(HttpServletResponse httpServletResponse){
        this.httpServletResponse = httpServletResponse;
    }

    public static CookieOperate createCookieOperation(String cookie){
        String cookieValue = getCookieValue(cookie);
        if (cookieValue == null){
            return null;
        }
        CookieOperate cookieOperate = new CookieOperate();
        cookieOperate.setCookieStr(cookieValue);
        return cookieOperate;
    }

//    public static String getCookeValue(String cookie){
//        if (cookie == null || "".equals(cookie)){
//            return null;
//        }
//        String cookieTemp;
//        try {
//            cookieTemp = Tools.base64Decode(cookie);
//        }catch (Exception e){
//            return null;
//        }
//        String[] cookieValue = cookieTemp.split("\\|");
//        if (cookieValue.length != 2){
//            return null;
//        }
//        String value = cookieValue[0];
//        String signClient = cookieValue[1];
//        String signServer = Tools.md5(String.format("%s&%s", value, COOKIE_HASH_SALT));
//        if (!signClient.equals(signServer)){
//            return null;
//        }
//        return value;
//    }

    //add by wanghaoyang at 20180707
    public static String getCookieValue(String cookie){
        if (cookie == null || "".equals(cookie)){
            return null;
        }
        String cookieTemp;
        try {
            cookieTemp = AESTool.Decrypt(cookie, AES_KEY);
        }catch (Exception e){
            return null;
        }
        if(null == cookieTemp){
            return null;
        }
        String[] cookieValue = cookieTemp.split("\\|");
        if (cookieValue.length < 1){
            return null;
        }
        String value = cookieValue[0];
        return value;
    }

//    public void writeCookie(){
//        String value = String.format("userid:%d&loginType:%d&deviceType:%d", userId, loginType, deviceType);
//        String sign = Tools.md5(String.format("%s&%s", value, COOKIE_HASH_SALT));
//        String cookieValue = Tools.base64Encode(String.format("%s|%s", value, sign));
////        String cookieValue = Tools.base64Encode(String.format("%s|%s", "", sign));
//        cookieValue = cookieValue.replaceAll("\n", "").replaceAll("\r", "");
//        Cookie cookie = new Cookie(COOKIE_NAME, cookieValue);
//        cookie.setPath("/");
//        cookie.setMaxAge(COOKIE_EXPIRE);
//        this.httpServletResponse.addCookie(cookie);
//    }

    //change by wanghaoyang
    public void writeCookie(){
        String value = String.format("userid:%d&loginType:%d&deviceType:%d&timeStamp:%d", userId, loginType, deviceType, timeStamp);
        String cookieValue = "";
        try {
            cookieValue = AESTool.Encrypt(value, AES_KEY);
        }catch (Exception e){

        }
        cookieValue = cookieValue.replaceAll("\n", "").replaceAll("\r", "");
        Cookie cookie = new Cookie(COOKIE_NAME, cookieValue);
        cookie.setPath("/");
        cookie.setMaxAge(COOKIE_EXPIRE);
        this.httpServletResponse.addCookie(cookie);
    }


    public void deleteCookie(){
        Cookie cookie = new Cookie(COOKIE_NAME, "0");
        cookie.setPath("/");
        cookie.setMaxAge(0);
        this.httpServletResponse.addCookie(cookie);
    }

    public static void deleteCookie(HttpServletResponse response){
        Cookie cookie = new Cookie(COOKIE_NAME, "0");
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getCookieStr() {
        return cookieStr;
    }

    public void setCookieStr(String cookie) {
        String[] cookieValues = cookie.split("&");
        for (String kv:cookieValues){
            String[] kvs = kv.split(":");
            if ("userid".equals(kvs[0])){
                this.userId = Integer.parseInt(kvs[1]);
            }else if ("loginType".equals(kvs[0])){
                this.loginType = Integer.parseInt(kvs[1]);
            }else if ("deviceType".equals(kvs[0])){
                this.deviceType = Integer.parseInt(kvs[1]);
            }else if("timeStamp".equals(kvs[0])){
                this.timeStamp = Integer.parseInt(kvs[1]);
            }
            else{

            }
        }
    }
    public static void writeCookie(HttpServletResponse httpServletResponse,
                                   int userId,
                                   int loginType,
                                   int deviceType,
                                   int timeStamp) {
        CookieOperate cookieOperate = new CookieOperate(httpServletResponse);
        cookieOperate.setUserId(userId);
        cookieOperate.setLoginType(loginType);
        cookieOperate.setDeviceType(deviceType);
        cookieOperate.setTimeStamp(timeStamp+COOKIE_EXPIRE);
        cookieOperate.writeCookie();
    }

    /**
     * 添加userId的cookie
     * */
    public static void writeUserIdCookie(HttpServletResponse response, int userId){
        try {
            String word = AESTool.Encrypt(String.valueOf(userId), AES_KEY);
            Cookie cookie = new Cookie("userId", word);
            cookie.setPath(COOKIE_PATH);
            cookie.setMaxAge(COOKIE_EXPIRE);
            response.addCookie(cookie);
        } catch (Exception e) {
        }
    }

    /**
     * 删除userId的Cookie
     * */
    public static void delUserIdCookie(){
        throw new NoSuchMethodError("此方法未实现");
    }

    /**
     * 获取名为 hqtc 的cookie的值
     * */
    public static String getCookie(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if(null == cookies || cookies.length<1){
            return null;
        }
        for (Cookie c:cookies){
            if (CookieOperate.COOKIE_NAME.equals(c.getName())){
                return c.getValue();
            }
        }
        return null;
    }
}
