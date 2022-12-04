package com.hqtc.cms.model.bean;

import java.util.List;

/**
 * Created by makuan on 18-6-27.
 */
public class LayoutRecommendBean extends LayoutBean{
    private List<RecommendBean> dataList;

    public List<RecommendBean> getDataList() {
        return dataList;
    }

    public void setDataList(List<RecommendBean> dataList) {
        this.dataList = dataList;
    }
}
