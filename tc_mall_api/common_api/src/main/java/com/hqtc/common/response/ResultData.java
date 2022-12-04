package com.hqtc.common.response;


import com.hqtc.common.config.ErrorCode;

import java.io.Serializable;

public class ResultData<T> implements Serializable {
    private int error = ErrorCode.SUCCESS;
    private String msg = "ok";
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
