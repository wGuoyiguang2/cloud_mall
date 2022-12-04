package com.hqtc.cms.model.bean.flashsale;

import java.io.Serializable;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/8/28 11:48
 */
public class PayOrderBean implements Serializable{
    private String orderNo;
    private Integer count;
    private String cookie;
    private Long time;

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }
}
