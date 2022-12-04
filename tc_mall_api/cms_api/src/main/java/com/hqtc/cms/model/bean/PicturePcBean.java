package com.hqtc.cms.model.bean;

import java.io.Serializable;

/**
 * description:获取指定商品pc端详情图片接口
 * Created by laiqingchuang on 18-7-16 .
 */
public class PicturePcBean extends GoodDetailinfoBean{

    private String propCode;  //规格参数
    private String service;   //服务
    private String wReadMe;   //readme
    private String shouhou;   //售后
    private String wdis;      //商品详情
    private String wareQD;    //商品清单

    public String getPropCode() {
        return propCode;
    }

    public void setPropCode(String propCode) {
        this.propCode = propCode;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getwReadMe() {
        return wReadMe;
    }

    public void setwReadMe(String wReadMe) {
        this.wReadMe = wReadMe;
    }

    public String getShouhou() {
        return shouhou;
    }

    public void setShouhou(String shouhou) {
        this.shouhou = shouhou;
    }

    public String getWdis() {
        return wdis;
    }

    public void setWdis(String wdis) {
        this.wdis = wdis;
    }

    public String getWareQD() {
        return wareQD;
    }

    public void setWareQD(String wareQD) {
        this.wareQD = wareQD;
    }
}
