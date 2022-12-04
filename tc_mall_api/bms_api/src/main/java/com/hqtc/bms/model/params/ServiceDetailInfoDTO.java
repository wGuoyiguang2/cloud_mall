package com.hqtc.bms.model.params;

import java.io.Serializable;

/**
 * description:服务单商品明细
 * Created by laiqingchuang on 18-7-13 .
 */
public class ServiceDetailInfoDTO implements Serializable {

    private Integer wareId;         //商品编号
    private String wareName;        //商品名称
    private String wareBrand;       //商品品牌
    private Integer afsDetailType;  //明细类型   主商品(10),赠品(20),附件(30)
    private String wareDescribe;    //附件描述

    public Integer getWareId() {
        return wareId;
    }

    public void setWareId(Integer wareId) {
        this.wareId = wareId;
    }

    public String getWareName() {
        return wareName;
    }

    public void setWareName(String wareName) {
        this.wareName = wareName;
    }

    public String getWareBrand() {
        return wareBrand;
    }

    public void setWareBrand(String wareBrand) {
        this.wareBrand = wareBrand;
    }

    public Integer getAfsDetailType() {
        return afsDetailType;
    }

    public void setAfsDetailType(Integer afsDetailType) {
        this.afsDetailType = afsDetailType;
    }

    public String getWareDescribe() {
        return wareDescribe;
    }

    public void setWareDescribe(String wareDescribe) {
        this.wareDescribe = wareDescribe;
    }
}