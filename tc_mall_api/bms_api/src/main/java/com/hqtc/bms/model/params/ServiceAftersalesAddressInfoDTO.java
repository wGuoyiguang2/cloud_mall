package com.hqtc.bms.model.params;

import java.io.Serializable;

/**
 * description://售后地址信息
 * Created by laiqingchuang on 18-7-13 .
 */
public class ServiceAftersalesAddressInfoDTO implements Serializable {
    private String address; //售后地址
    private String tel;     //售后电话
    private String linkMan; //售后联系人
    private String postCode;//售后邮编

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getLinkMan() {
        return linkMan;
    }

    public void setLinkMan(String linkMan) {
        this.linkMan = linkMan;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }
}
