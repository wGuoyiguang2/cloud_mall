package com.hqtc.bms.model.params;

import java.math.BigDecimal;

/**
 * description:退款明细
 * Created by laiqingchuang on 18-7-13 .
 */
public class ServiceFinanceDetailInfoDTO {

    private Integer refundWay;     //退款方式
    private String refundWayName;  //退款方式名称
    private Integer status;        //状态
    private String statusName;     //状态名称
    private BigDecimal refundPrice;//退款金额
    private String wareName;       //商品名称
    private Integer wareId;        //商品编号

    public Integer getRefundWay() {
        return refundWay;
    }

    public void setRefundWay(Integer refundWay) {
        this.refundWay = refundWay;
    }

    public String getRefundWayName() {
        return refundWayName;
    }

    public void setRefundWayName(String refundWayName) {
        this.refundWayName = refundWayName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public BigDecimal getRefundPrice() {
        return refundPrice;
    }

    public void setRefundPrice(BigDecimal refundPrice) {
        this.refundPrice = refundPrice;
    }

    public String getWareName() {
        return wareName;
    }

    public void setWareName(String wareName) {
        this.wareName = wareName;
    }

    public Integer getWareId() {
        return wareId;
    }

    public void setWareId(Integer wareId) {
        this.wareId = wareId;
    }
}
