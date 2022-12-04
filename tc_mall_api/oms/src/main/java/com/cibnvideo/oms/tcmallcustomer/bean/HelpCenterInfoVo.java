package com.cibnvideo.oms.tcmallcustomer.bean;

/**
 * @Description: 帮助中心返回结果
 * @Author: WangBin
 * @Date: 2018/7/11 16:22
 */
public class HelpCenterInfoVo {
    private Integer typeId;
    private String typeName;
    private String QA;
    private Integer QAId;
    private Integer typePosition;
    private Integer position;
    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getTypePosition() {
        return typePosition;
    }

    public void setTypePosition(Integer typePosition) {
        this.typePosition = typePosition;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getQA() {
        return QA;
    }

    public void setQA(String QA) {
        this.QA = QA;
    }

    public Integer getQAId() {
        return QAId;
    }

    public void setQAId(Integer QAId) {
        this.QAId = QAId;
    }
}
