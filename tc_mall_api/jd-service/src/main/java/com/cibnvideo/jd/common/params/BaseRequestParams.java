package com.cibnvideo.jd.common.params;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/6/22 21:08
 */
public class BaseRequestParams {
    private String v;
    private String method;
    private String access_token;
    private String app_key;
    private String param_json;
    private String format;
    private String timestamp;

    public String getV() {
        return v;
    }

    public void setV(String v) {
        this.v = v;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getApp_key() {
        return app_key;
    }

    public void setApp_key(String app_key) {
        this.app_key = app_key;
    }

    public String getParam_json() {
        return param_json;
    }

    public void setParam_json(String param_json) {
        this.param_json = param_json;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
