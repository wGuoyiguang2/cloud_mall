package com.hqtc.ums.config;

/**
 * Created by wanghaoyang on 18-6-28.
 */
public class RPCRouter {
    //手机用户注册
    public static final String RPC_ROUTER_START_USER_REGISTER = "/api/start/userRegister";
    //发送验证码
    public static final String RPC_ROUTER_START_SEND_CODE_MESSAGE = "/api/start/sendCodeMessage";
    //手机登录
    public static final String RPC_ROUTER_START_USER_LOGIN = "/api/start/userLogin";
    //重置密码
    public static final String RPC_ROUTER_START_RESET_PASSWORD = "/api/start/resetPassword";
    //校验验证码
    public static final String RPC_ROUTER_START_VERIFY_CODE = "/api/start/verifyCode";

    //生成登录二维码
    public static final String RPC_ROUTER_START_CREATE_QR_CODE = "/api/start/createqrcode";
    //二维码轮循
    public static final String RPC_ROUTER_START_EQ_CHECK_SCAN = "/api/start/eqCheckScan";
    //自动登录接口
    public static final String RPC_ROUTER_AUTO_LOGIN = "/api/start/autoLogin";


    //tc_ums相关接口
    public static final String SMS_MESSAGE_SEND = "/v1/ums/sms/send";
    public static final String SMS_MESSAGE_VERIFY = "/v1/ums/sms/verify";
    public static final String USER_LOGIN_MOBILE = "/v1/ums/mobile/login";
    public static final String USER_LGOIN_PASSWORD = "/v1/ums/password/login";
    public static final String USER_REGISTER_MOBILE = "/v1/ums/password/register";
    public static final String USER_RESET_PASSWORD = "/v1/ums/password/reset";
    public static final String USER_LOGIN_APPLET = "/v1/ums/applet/login";
    public static final String WECHAT_QRCODE_CREATE = "/v1/ums/wechat/qrcode";
    public static final String WECHAT_QRCODE_SCAN = "/v1/ums/wechat/qrcode/scan";

}
