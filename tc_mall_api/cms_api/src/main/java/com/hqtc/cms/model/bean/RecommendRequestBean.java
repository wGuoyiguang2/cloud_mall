package com.hqtc.cms.model.bean;

import java.io.Serializable;

/**
 * description:往期推荐
 * Created by laiqingchuang on 18-8-6 .
 */
public class RecommendRequestBean implements Serializable {
    private Integer venderId;
    private String startTime;
    private Integer limit;

    public Integer getVenderId() {
        return venderId;
    }

    public void setVenderId(Integer venderId) {
        this.venderId = venderId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}
