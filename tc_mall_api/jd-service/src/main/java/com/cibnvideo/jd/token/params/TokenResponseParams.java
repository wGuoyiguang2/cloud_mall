package com.cibnvideo.jd.token.params;

/**
 * @Description: token接口返回参数
 * @Author: WangBin
 * @Date: 2018/6/22 14:55
 */
public class TokenResponseParams {
    private String access_token;
    private String code;
    private Long expires_in;
    private String refresh_token;
    private Long time;
    private String token_type;
    private String uid;
    private String user_nick;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public Long getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(Long expires_in) {
        this.expires_in = expires_in;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUser_nick() {
        return user_nick;
    }

    public void setUser_nick(String user_nick) {
        this.user_nick = user_nick;
    }
}
