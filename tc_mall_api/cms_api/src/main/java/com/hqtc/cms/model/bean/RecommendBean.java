package com.hqtc.cms.model.bean;

import java.io.Serializable;

/**
 * Created by makuan on 18-6-27.
 */
public class RecommendBean implements Serializable {
    private String name = "";
    private String subName = "";
    private String image = "";
    private String action = "";
    private String layout = "";
    private Object actionParam;

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

    public String getLayout() {
        return layout;
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }

    public Object getActionParam() {
        return actionParam;
    }

    public void setActionParam(Object actionParam) {
        this.actionParam = actionParam;
    }
}
