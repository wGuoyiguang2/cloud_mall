package com.cibnvideo.jd.common.params;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/10/12 11:27
 */
public
class ErrorResponseInner{
    private String code;
    private String msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}