package com.hqtc.bms.model.response;

/**
 * Created by wanghaoyang on 19-2-15.
 */
public class PmsResultData<T> {
    private int status = 200;
    private String message = "";
    private T data = null;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
