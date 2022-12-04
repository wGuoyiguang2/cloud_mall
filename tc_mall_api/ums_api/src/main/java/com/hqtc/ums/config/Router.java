package com.hqtc.ums.config;

/**
 * Created by wanghaoyang on 18-6-28.
 */
public class Router {

    //发送短信验证码
    public static final String ROUTER_SEND_CODE = "/v1/message/send";
    //校验短信验证码
    public static final String ROUTER_VERIFY_CODE = "/v1/message/verify";
    //手机注册(添加cookie)
    public static final String ROUTER_PHONE_REGISTER = "/v1/phone/register";
    //手机登录(添加cookie)
    public static final String ROUTER_PHONE_LOGIN = "/v1/phone/login";
    //重置密码
    public static final String ROUTER_PWD_RESET = "/v1/phone/password";

    //生成微信二维码
    public static final String ROUTER_QRCODE_GET = "/v1/qrcode/create";
    //轮循微信二维码(添加cookie)
    public static final String ROUTER_QRCODE_CHECK = "/v1/qrcode/check";
    //登出操作
    public static final String ROUTER_LOGOUT = "/v1/user/logout";

    //根据uid查询用户信息
    public static final String ROUTER_USER_INFO = "/v1/user/info";

    //小程序登录
    public static final String ROUTER_WECHAT_APPLET_LOGIN = "/v1/user/applet/login";
    //小程序获取用户的openid
    public static final String ROUTER_WECHAT_APPLET_OPENID = "/v1/user/applet/openid";

    public static final String WECHAT_SUBSCRIPTION_YOUXIANG = "/v1/user/wechat/subscription/youxiang";

    public static final String ROUTER_GET_VENDER_INFO = "/v1/vender/info";
}
