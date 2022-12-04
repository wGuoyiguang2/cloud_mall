package com.cibnvideo.jd.aftersale.params;

/**
 * description:基类
 * Created by laiqingchuang on 18-7-10 .
 */
public class RequestParams<T> {
    private T param;

    public T getParam() {
        return param;
    }

    public void setParam(T param) {
        this.param = param;
    }
}