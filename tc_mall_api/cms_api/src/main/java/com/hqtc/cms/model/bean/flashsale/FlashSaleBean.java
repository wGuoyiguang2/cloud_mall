package com.hqtc.cms.model.bean.flashsale;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/8/22 15:00
 */
public class FlashSaleBean {
    private String venderId;
    private String activityId;
    private Long startTime;
    private Long endTime;
    private List<FlashSaleProductBean> flashSaleProductBeanList;

    public String getVenderId() {
        return venderId;
    }

    public void setVenderId(String venderId) {
        this.venderId = venderId;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public List<FlashSaleProductBean> getFlashSaleProductBeanList() {
        return flashSaleProductBeanList;
    }

    public void setFlashSaleProductBeanList(List<FlashSaleProductBean> flashSaleProductBeanList) {
        this.flashSaleProductBeanList = flashSaleProductBeanList;
    }
}
