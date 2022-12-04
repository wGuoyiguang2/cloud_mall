package com.cibnvideo.jd.aftersale.params.apply;

/**
 * description:客户信息实体
 * Created by laiqingchuang on 18-7-11 .
 */
public class AfterSaleCustomerDto {
    private String customerContactName;//联系人
    private String customerTel;        //联系电话
    private String customerMobilePhone;//手机号
    private String customerEmail;      //Email
    private String customerPostcode;   //邮编

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
