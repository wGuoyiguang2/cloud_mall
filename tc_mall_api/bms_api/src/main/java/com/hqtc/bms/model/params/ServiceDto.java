package com.hqtc.bms.model.params;

import java.io.Serializable;
import java.util.List;

/**
 * description:服务单信息
 * Created by laiqingchuang on 18-7-13 .
 */
public class ServiceDto implements Serializable {

    List<ServiceInfoDto> serviceInfoList;
    private Integer totalNum;        //总记录数
    private Integer pageSize;        //每页记录数
    private Integer pageNum;         //总页数
    private Integer pageIndex;       //当前页数

    public List<ServiceInfoDto> getServiceInfoList() {
        return serviceInfoList;
    }

    public void setServiceInfoList(List<ServiceInfoDto> serviceInfoList) {
        this.serviceInfoList = serviceInfoList;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }
}