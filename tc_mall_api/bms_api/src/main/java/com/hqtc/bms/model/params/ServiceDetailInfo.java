package com.hqtc.bms.model.params;

import java.io.Serializable;
import java.util.List;

/**
 * description:
 * Created by laiqingchuang on 18-7-13 .
 */
public class ServiceDetailInfo implements Serializable {

    private Integer afsServiceId;     //服务单号
    private Integer customerExpect;   //服务类型码  退货(10)、换货(20)、维修(30)
    private String afsApplyTime;      //服务单申请时间 格式为yyyy-MM-dd HH:mm:ss
    private Long orderId;             //订单号
    private Integer isHasInvoice;      //是不是有发票  0没有 1有
    private Integer isNeedDetectionReport;//是不是有检测报告 0没有 1有
    private Integer isHasPackage;      //是不是有包装 0没有 1有
    private String questionPic;        //上传图片访问地址
    private Integer afsServiceStep;    //服务单环节  申请阶段(10),审核不通过(20),客服审核(21),商家审核(22),京东收货(31),商家收货(32), 京东处理(33),商家处理(34), 用户确认(40),完成(50), 取消 (60);
    private String afsServiceStepName; //服务单环节名称
    private String approveNotes;       //审核意见
    private String questionDesc;       //问题描述
    private Integer approvedResult;    //审核结果 直赔积分(11), 直赔余额(12), 直赔优惠卷(13), 直赔京豆 (14), 直赔商品 (21), 上门换新 (22),自营取件(31),客户送货(32),客户发货 (33),闪电退款 (34),虚拟退款 (35),大家电检测 (80),大家电安装(81),大家电移机 (82),大家电维修(83),大家电其它 (84);
    private String approvedResultName; //审核结果名称
    private Integer processResult;     //处理结果 返修换新(23),退货(40), 换良(50),原返60),病单 (71),出检 (72),维修(73),强制关单 (80),线下换新(90)
    private String processResultName;  //处理结果名称
    private ServiceCustomerInfoDTO serviceCustomerInfoDTO;   //客户信息
    private ServiceAftersalesAddressInfoDTO serviceAftersalesAddressInfoDTO;//售后地址信息
    private ServiceExpressInfoDTO serviceExpressInfoDTO;     //客户发货信息
    private List<ServiceFinanceDetailInfoDTO> serviceFinanceDetailInfoDTOs;   //退款明细
    private List<ServiceTrackInfoDTO> serviceTrackInfoDTOs;  //服务单追踪信息
    private List<ServiceDetailInfoDTO> serviceDetailInfoDTOs;//服务单商品明细
    private List<Integer> allowOperations;                   //获取服务单允许的操作列表

    public Integer getAfsServiceId() {
        return afsServiceId;
    }

    public void setAfsServiceId(Integer afsServiceId) {
        this.afsServiceId = afsServiceId;
    }

    public Integer getCustomerExpect() {
        return customerExpect;
    }

    public void setCustomerExpect(Integer customerExpect) {
        this.customerExpect = customerExpect;
    }

    public String getAfsApplyTime() {
        return afsApplyTime;
    }

    public void setAfsApplyTime(String afsApplyTime) {
        this.afsApplyTime = afsApplyTime;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Integer getIsHasInvoice() {
        return isHasInvoice;
    }

    public void setIsHasInvoice(Integer isHasInvoice) {
        this.isHasInvoice = isHasInvoice;
    }

    public Integer getIsNeedDetectionReport() {
        return isNeedDetectionReport;
    }

    public void setIsNeedDetectionReport(Integer isNeedDetectionReport) {
        this.isNeedDetectionReport = isNeedDetectionReport;
    }

    public Integer getIsHasPackage() {
        return isHasPackage;
    }

    public void setIsHasPackage(Integer isHasPackage) {
        this.isHasPackage = isHasPackage;
    }

    public String getQuestionPic() {
        return questionPic;
    }

    public void setQuestionPic(String questionPic) {
        this.questionPic = questionPic;
    }

    public Integer getAfsServiceStep() {
        return afsServiceStep;
    }

    public void setAfsServiceStep(Integer afsServiceStep) {
        this.afsServiceStep = afsServiceStep;
    }

    public String getAfsServiceStepName() {
        return afsServiceStepName;
    }

    public void setAfsServiceStepName(String afsServiceStepName) {
        this.afsServiceStepName = afsServiceStepName;
    }

    public String getApproveNotes() {
        return approveNotes;
    }

    public void setApproveNotes(String approveNotes) {
        this.approveNotes = approveNotes;
    }

    public String getQuestionDesc() {
        return questionDesc;
    }

    public void setQuestionDesc(String questionDesc) {
        this.questionDesc = questionDesc;
    }

    public Integer getApprovedResult() {
        return approvedResult;
    }

    public void setApprovedResult(Integer approvedResult) {
        this.approvedResult = approvedResult;
    }

    public String getApprovedResultName() {
        return approvedResultName;
    }

    public void setApprovedResultName(String approvedResultName) {
        this.approvedResultName = approvedResultName;
    }

    public Integer getProcessResult() {
        return processResult;
    }

    public void setProcessResult(Integer processResult) {
        this.processResult = processResult;
    }

    public String getProcessResultName() {
        return processResultName;
    }

    public void setProcessResultName(String processResultName) {
        this.processResultName = processResultName;
    }

    public ServiceCustomerInfoDTO getServiceCustomerInfoDTO() {
        return serviceCustomerInfoDTO;
    }

    public void setServiceCustomerInfoDTO(ServiceCustomerInfoDTO serviceCustomerInfoDTO) {
        this.serviceCustomerInfoDTO = serviceCustomerInfoDTO;
    }

    public ServiceAftersalesAddressInfoDTO getServiceAftersalesAddressInfoDTO() {
        return serviceAftersalesAddressInfoDTO;
    }

    public void setServiceAftersalesAddressInfoDTO(ServiceAftersalesAddressInfoDTO serviceAftersalesAddressInfoDTO) {
        this.serviceAftersalesAddressInfoDTO = serviceAftersalesAddressInfoDTO;
    }

    public ServiceExpressInfoDTO getServiceExpressInfoDTO() {
        return serviceExpressInfoDTO;
    }

    public void setServiceExpressInfoDTO(ServiceExpressInfoDTO serviceExpressInfoDTO) {
        this.serviceExpressInfoDTO = serviceExpressInfoDTO;
    }

    public List<ServiceFinanceDetailInfoDTO> getServiceFinanceDetailInfoDTOs() {
        return serviceFinanceDetailInfoDTOs;
    }

    public void setServiceFinanceDetailInfoDTOs(List<ServiceFinanceDetailInfoDTO> serviceFinanceDetailInfoDTOs) {
        this.serviceFinanceDetailInfoDTOs = serviceFinanceDetailInfoDTOs;
    }

    public List<ServiceTrackInfoDTO> getServiceTrackInfoDTOs() {
        return serviceTrackInfoDTOs;
    }

    public void setServiceTrackInfoDTOs(List<ServiceTrackInfoDTO> serviceTrackInfoDTOs) {
        this.serviceTrackInfoDTOs = serviceTrackInfoDTOs;
    }

    public List<ServiceDetailInfoDTO> getServiceDetailInfoDTOs() {
        return serviceDetailInfoDTOs;
    }

    public void setServiceDetailInfoDTOs(List<ServiceDetailInfoDTO> serviceDetailInfoDTOs) {
        this.serviceDetailInfoDTOs = serviceDetailInfoDTOs;
    }

    public List<Integer> getAllowOperations() {
        return allowOperations;
    }

    public void setAllowOperations(List<Integer> allowOperations) {
        this.allowOperations = allowOperations;
    }
}
