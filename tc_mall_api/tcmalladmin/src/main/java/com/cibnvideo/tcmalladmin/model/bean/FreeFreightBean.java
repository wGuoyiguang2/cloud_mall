package com.cibnvideo.tcmalladmin.model.bean;

import java.math.BigDecimal;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/9/7 16:20
 */
public class FreeFreightBean {
    private Integer id;
    private Integer venderid;
    private BigDecimal price;
    private String ctime;
    private String utime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVenderid() {
        return venderid;
    }

    public void setVenderid(Integer venderid) {
        this.venderid = venderid;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public String getUtime() {
        return utime;
    }

    public void setUtime(String utime) {
        this.utime = utime;
    }
}
