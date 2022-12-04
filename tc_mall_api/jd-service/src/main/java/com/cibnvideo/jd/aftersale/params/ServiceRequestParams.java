package com.cibnvideo.jd.aftersale.params;

import java.util.List;

/**
 * description:服务单
 * Created by laiqingchuang on 18-7-11 .
 */
public class ServiceRequestParams {
    private Long afsServiceId;                 // 服务单号
    private List<Integer> appendInfoSteps;     //获取信息模块 不设置数据表示只获取服务单主信息、商品明细以及客户信息；1、代表增加获取售后地址信息 2、代表增加获取客户发货信息 3、代表增加获取退款明细 4、 增加获取跟踪信息 5.获取允许的操作信息
    private List<Integer> serviceIdList;       //服务单号集合
    private String approveNotes;               //审核意见

    public Long getAfsServiceId() {
        return afsServiceId;
    }

    public void setAfsServiceId(Long afsServiceId) {
        this.afsServiceId = afsServiceId;
    }

    public List<Integer> getAppendInfoSteps() {
        return appendInfoSteps;
    }

    public void setAppendInfoSteps(List<Integer> appendInfoSteps) {
        this.appendInfoSteps = appendInfoSteps;
    }

    public List<Integer> getServiceIdList() {
        return serviceIdList;
    }

    public void setServiceIdList(List<Integer> serviceIdList) {
        this.serviceIdList = serviceIdList;
    }

    public String getApproveNotes() {
        return approveNotes;
    }

    public void setApproveNotes(String approveNotes) {
        this.approveNotes = approveNotes;
    }
}
