package com.hqtc.cms.model.bean;

import java.io.Serializable;

/**
 * description:获取分类列表bean
 * Created by laiqingchuang on 18-6-22 .
 */
public class GoodClassificationBean implements Serializable {

    private Integer catId;   //分类ID
    private Integer parentId;//父分类ID
    private String name;     //分类名称
    private Integer catClass;//分类类型 0:一级分类 1:二级分类 2:三级分类
    private Integer state;	 //是否可用 0:不可用 1:可用
    private String icon="";
    private String picture="";
    private String background="";

    public Integer getCatId() {
        return catId;
    }

    public void setCatId(Integer catId) {
        this.catId = catId;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCatClass() {
        return catClass;
    }

    public void setCatClass(Integer catClass) {
        this.catClass = catClass;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }
}