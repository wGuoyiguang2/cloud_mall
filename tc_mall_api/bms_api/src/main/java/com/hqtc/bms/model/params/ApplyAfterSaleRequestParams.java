package com.hqtc.bms.model.params;

import java.io.Serializable;

/**
 * description:申请售后服务（退货、换货、维修）
 * Created by laiqingchuang on 18-7-11 .
 */
public class ApplyAfterSaleRequestParams implements Serializable {

    private Long jdOrderId;                         //订单号
    private Integer customerExpect;                 //客户预期（退货(10)、换货(20)、维修(30)）
    private String questionDesc;                    //产品问题描述
    private Boolean isNeedDetectionReport;          //是否需要检测报告
    private String questionPic;                     //问题描述图片
    private Boolean isHasPackage;                   //是否有包装
    private String packageDesc;                     //包装描述
    private AfterSaleCustomerDto asCustomerDto;     //客户信息实体
    private AfterSalePickwareDto asPickwareDto;     //取件信息实体
    private AfterSaleReturnwareDto asReturnwareDto; //返件信息实体
    private AfterSaleDetailDto asDetailDto;         //申请单明细实体

    public Long getJdOrderId() {
        return jdOrderId;
    }

    public void setJdOrderId(Long jdOrderId) {
        this.jdOrderId = jdOrderId;
    }

    public Integer getCustomerExpect() {
        return customerExpect;
    }

    public void setCustomerExpect(Integer customerExpect) {
        this.customerExpect = customerExpect;
    }

    public String getQuestionDesc() {
        return questionDesc;
    }

    public void setQuestionDesc(String questionDesc) {
        this.questionDesc = questionDesc;
    }

    public Boolean getNeedDetectionReport() {
        return isNeedDetectionReport;
    }

    public void setNeedDetectionReport(Boolean needDetectionReport) {
        isNeedDetectionReport = needDetectionReport;
    }

    public String getQuestionPic() {
        return questionPic;
    }

    public void setQuestionPic(String questionPic) {
        this.questionPic = questionPic;
    }

    public Boolean getHasPackage() {
        return isHasPackage;
    }

    public void setHasPackage(Boolean hasPackage) {
        isHasPackage = hasPackage;
    }

    public String getPackageDesc() {
        return packageDesc;
    }

    public void setPackageDesc(String packageDesc) {
        this.packageDesc = packageDesc;
    }

    public AfterSaleCustomerDto getAsCustomerDto() {
        return asCustomerDto;
    }

    public void setAsCustomerDto(AfterSaleCustomerDto asCustomerDto) {
        this.asCustomerDto = asCustomerDto;
    }

    public AfterSalePickwareDto getAsPickwareDto() {
        return asPickwareDto;
    }

    public void setAsPickwareDto(AfterSalePickwareDto asPickwareDto) {
        this.asPickwareDto = asPickwareDto;
    }

    public AfterSaleReturnwareDto getAsReturnwareDto() {
        return asReturnwareDto;
    }

    public void setAsReturnwareDto(AfterSaleReturnwareDto asReturnwareDto) {
        this.asReturnwareDto = asReturnwareDto;
    }

    public AfterSaleDetailDto getAsDetailDto() {
        return asDetailDto;
    }

    public void setAsDetailDto(AfterSaleDetailDto asDetailDto) {
        this.asDetailDto = asDetailDto;
    }
}