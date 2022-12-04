package com.hqtc.cms.model.bean;

import java.io.Serializable;
import java.util.List;

/**
 * description:
 * Created by laiqingchuang on 18-8-14 .
 */
public class RecommendHistoryResonseBean implements Serializable {
    private String time;
    private List<RecommendHistoryBean> list;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<RecommendHistoryBean> getList() {
        return list;
    }

    public void setList(List<RecommendHistoryBean> list) {
        this.list = list;
    }
}
