package com.hqtc.bms.model.params;

import java.io.Serializable;

/**
 * description:客户信息
 * Created by laiqingchuang on 18-7-13 .
 */
public class ServiceCustomerInfoDTO implements Serializable {
    private String customerPin;         //客户京东账号
    private String customerName;        //用户昵称
    private String customerContactName; //服务单联系人
    private String customerTel;         //联系电话
    private String customerMobilePhone; // 手机号
    private String customerEmail;       //邮箱
    private String customerPostcode;    //邮编

    public String getCustomerPin() {
        return customerPin;
    }

    public void setCustomerPin(String customerPin) {
        this.customerPin = customerPin;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerContactName() {
        return customerContactName;
    }

    public void setCustomerContactName(String customerContactName) {
        this.customerContactName = customerContactName;
    }

    public String getCustomerTel() {
        return customerTel;
    }

    public void setCustomerTel(String customerTel) {
        this.customerTel = customerTel;
    }

    public String getCustomerMobilePhone() {
        return customerMobilePhone;
    }

    public void setCustomerMobilePhone(String customerMobilePhone) {
        this.customerMobilePhone = customerMobilePhone;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerPostcode() {
        return customerPostcode;
    }

    public void setCustomerPostcode(String customerPostcode) {
        this.customerPostcode = customerPostcode;
    }
}