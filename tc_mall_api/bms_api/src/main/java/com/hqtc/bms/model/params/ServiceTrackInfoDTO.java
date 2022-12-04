package com.hqtc.bms.model.params;

/**
 * description:服务单追踪信息
 * Created by laiqingchuang on 18-7-13 .
 */
public class ServiceTrackInfoDTO {

    private Integer afsServiceId; //服务单号
    private String title;         //追踪标题
    private String context;       //追踪内容
    private String createDate;    //提交时间
    private String createName;    //操作人昵称
    private String createPin;     //操作人账号

    public Integer getAfsServiceId() {
        return afsServiceId;
    }

    public void setAfsServiceId(Integer afsServiceId) {
        this.afsServiceId = afsServiceId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public String getCreatePin() {
        return createPin;
    }

    public void setCreatePin(String createPin) {
        this.createPin = createPin;
    }
}
