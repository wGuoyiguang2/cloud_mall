package com.cibnvideo.oms.recommend.model.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by makuan on 18-6-28.
 */
public class LayoutRecommendBean extends LayoutBean {

    private String layoutSize = "";

    private List<RecommendBean> dataList;

    public String getLayoutSize() {
        return layoutSize;
    }

    public void setLayoutSize(String layoutSize) {
        this.layoutSize = layoutSize;
    }

    public List<RecommendBean> getDataList() {
        return dataList;
    }

    public void setDataList(List<RecommendBean> dataList) {
        this.dataList = dataList;
    }
}
