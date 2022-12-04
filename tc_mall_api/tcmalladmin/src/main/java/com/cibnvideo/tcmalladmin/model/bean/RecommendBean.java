package com.cibnvideo.tcmalladmin.model.bean;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by makuan on 18-6-27.
 */
public class RecommendBean implements Serializable {
    private Integer id;
    private Integer venderId;
    private String name = "";
    private String subName = "";
    private Integer ctype;
    private String image = "";
    private String action = "";
    private String actionParam = "";
    private Long actionParamDetail;
    private Integer actionParamCat0;
    private Integer actionParamCat1;
    private Integer actionParamCat2;
    private Integer actionParamCollection;
    private Integer position;
    private Integer status = 0;
    private String layout = "";
    private Integer layoutId;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date ctime;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date utime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVenderId() {
        return venderId;
    }

    public void setVenderId(Integer venderId) {
        this.venderId = venderId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public Integer getCtype() {
        return ctype;
    }

    public void setCtype(Integer ctype) {
        this.ctype = ctype;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getActionParam() {
        return actionParam;
    }

    public void setActionParam(String actionParam) {
        this.actionParam = actionParam;
    }

    public Long getActionParamDetail() {
        return actionParamDetail;
    }

    public void setActionParamDetail(Long actionParamDetail) {
        this.actionParamDetail = actionParamDetail;
    }

    public Integer getActionParamCat0() {
        return actionParamCat0;
    }

    public void setActionParamCat0(Integer actionParamCat0) {
        this.actionParamCat0 = actionParamCat0;
    }

    public Integer getActionParamCat1() {
        return actionParamCat1;
    }

    public void setActionParamCat1(Integer actionParamCat1) {
        this.actionParamCat1 = actionParamCat1;
    }

    public Integer getActionParamCat2() {
        return actionParamCat2;
    }

    public void setActionParamCat2(Integer actionParamCat2) {
        this.actionParamCat2 = actionParamCat2;
    }

    public Integer getActionParamCollection() {
        return actionParamCollection;
    }

    public void setActionParamCollection(Integer actionParamCollection) {
        this.actionParamCollection = actionParamCollection;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getLayout() {
        return layout;
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }

    public Integer getLayoutId() {
        return layoutId;
    }

    public void setLayoutId(Integer layoutId) {
        this.layoutId = layoutId;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    public Date getUtime() {
        return utime;
    }

    public void setUtime(Date utime) {
        this.utime = utime;
    }
}
