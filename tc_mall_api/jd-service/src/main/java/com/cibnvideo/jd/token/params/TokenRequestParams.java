package com.cibnvideo.jd.token.params;

/**
 * @Description: token接口入参
 * @Author: WangBin
 * @Date: 2018/6/22 14:54
 */
public class TokenRequestParams {
    private String grant_type;//	String	是	调用时写死为字符串”password”
    private String app_key;//	String	是	分配给应用的AppKey。
    private String app_secret;//	String	是	Oauth2颁发的动态令牌，必填。
    private String State="0";//	String	是	可填任何值
    private String username;//	String	否	京东分配用户名，中文用户名需要urlencode
    private String password;//String	是	京东分配的密码，需32位的MD5加密，（小写）

    public String getGrant_type() {
        return grant_type;
    }

    public void setGrant_type(String grant_type) {
        this.grant_type = grant_type;
    }

    public String getApp_key() {
        return app_key;
    }

    public void setApp_key(String app_key) {
        this.app_key = app_key;
    }

    public String getApp_secret() {
        return app_secret;
    }

    public void setApp_secret(String app_secret) {
        this.app_secret = app_secret;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
