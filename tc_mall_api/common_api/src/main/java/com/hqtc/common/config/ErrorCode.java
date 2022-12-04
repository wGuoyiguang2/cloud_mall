package com.hqtc.common.config;

public class ErrorCode {
    //请求ok
    public static final int SUCCESS = 0;

    //参数错误
    public static final int PARAM_ERROR = 10001;

    //服务端发生异常
    public static final int SERVER_EXCEPTION = 10002;

    //缺少参数
    public static final int MISS_PARAM = 10003;

    //OPAQUE计算错误
    public static final int OPAQUE_ERROR = 10004;

    //写入数据失败
    public static final int WRITE_DATA_ERROR = 10005;

    //没有登录
    public static final int NOT_LOGIN = 10006;

    //没有返回数据
    public static final int NO_RESPONSE = 10007;

    //订单不存在
    public static final int NO_ORDER = 10008;

    //订单已支付
    public static final int ORDER_PAYED = 10009;

    //订单未支付
    public static final int UN_PAYED = 10010;

    //没有查找到商品
    public static final int NO_PRODUCT = 10011;

    //订单已完成
    public static final int ORDER_COMPLATE = 10012;

    //向上级平台下单失败
    public static final int UPPER_ORDER_FAIL = 10013;

    //没有查找到收货地址
    public static final int NO_ADDRESS = 10014;

    //订单不包含此商品
    public static final int NO_SUCH_PRODUCT = 10015;

    //向京东下单失败
    public static final int PAY_JD_FAIL = 10016;

    //向京东确认下单
    public static final int STOCK_JD_ORDER = 10017;

    //请求失败
    public static final int FALI = 10018;
    //验证码错误
    public static final int CODE_ERROR = 10019;
    //手机号错误
    public static final int PHONE_ERROR = 10020;
    //手机号已存在
    public static final int PHONE_EXIST = 10021;
    //用户不存在
    public static final int USER_NOT_EXIST = 10022;
    //用户名或密码错误
    public static final int USER_OR_PW_ERROR = 10023;
    //微信未扫描
    public static final int WX_NOT_SCAN = 10024;
    //登录超过限制
    public static final int LOGIN_LIMIT = 10025;

    //cookie已经过期
    public static final int COOKIE_EXPIRE = 10026;
    //商户预存余额不足
    public static final int VENDER_BANANCE_NOT_ENOUGH = 10027;
    //商品库存不足
    public static final int STOCK_NOT_ENOUGH = 10028;
    //退款失败
    public static final int REFUND_FAIL = 10029;

    //请求第三方服务失败
    public static final int REQ_THIRD_PART_SERVICE_FAIL = 10030;

    //退款金额错误
    public static final int REFUND_AMOUNT_ERROR = 10031;

    //购物车商品数量已达上限
    public static final int CART_NUM_LIMIT = 10032;

    //购物卡密码错误
    public static final int CARD_PASSWORD_ERROR = 10033;
    //购物卡已绑定
    public static final int CARD_BINDEBD = 10034;
    //购物卡绑定失败
    public static final int CARD_BIND_FAIL = 10035;
    //购物卡不可用
    public static final int CARD_CANT_USE = 10036;
    //购物卡卡号错误
    public static final int CARD_CARDNO_ERROR = 10037;
    //发起支付失败(失败原因看data)
    public static final int CARD_START_PAY_ERROR = 10038;
    //购物卡余额不足
    public static final int CARD_BALANCE_NOT_ENOUGH = 10039;
    //支付金额错误
    public static final int PAY_MONEY_ERROR = 10040;
    //订单已存在
    public static final int ORDER_EXIST = 10041;
    //购物卡支付成功
    public static final int CARD_MUTIPLE_PAY_SUCCESS = 10042;
    //商品下架
    public static final int GOODS_OUTLINE = 10043;
    //请求过于频繁
    public static final int REQUEST_FREQUENTLY = 10044;
    //微信服务器异常
    public static final int WECHAT_SERVER_ERROR = 10045;
    //购物卡已过期
    public static final int CARD_EXPIRE = 10046;

    //不存在的合作模式
    public static final int COOPERATION_MODE_ERROR = 10047;
    //重复的数据
    public static final int DUPLICATE_DATA = 10048;
    //无访问权限
    public static final int PERMISSION_DENIED = 10049;

}
