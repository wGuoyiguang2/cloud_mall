package com.hqtc.cms.model.bean;

import java.io.Serializable;

/**
 * Created by makuan on 18-6-27.
 */
public class LayoutBean implements Serializable{
    private String title = "";
    private Integer position;
    private String layoutSize = "";
    private Boolean showTitle;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public String getLayoutSize() {
        return layoutSize;
    }

    public void setLayoutSize(String layoutSize) {
        this.layoutSize = layoutSize;
    }

    public Boolean getShowTitle() {
        return showTitle;
    }

    public void setShowTitle(Boolean showTitle) {
        this.showTitle = showTitle;
    }

}
