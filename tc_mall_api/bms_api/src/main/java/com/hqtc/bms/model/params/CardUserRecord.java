package com.hqtc.bms.model.params;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/10/19 17:35
 */
public class CardUserRecord {
    private Integer id;
    private String cardNo;
    private Integer operateType;
    private Integer userId;
    private Integer orderSn;
    private String refundNo;
    private BigDecimal useFee;
    private Date ctime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public Integer getOperateType() {
        return operateType;
    }

    public void setOperateType(Integer operateType) {
        this.operateType = operateType;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(Integer orderSn) {
        this.orderSn = orderSn;
    }

    public String getRefundNo() {
        return refundNo;
    }

    public void setRefundNo(String refundNo) {
        this.refundNo = refundNo;
    }

    public BigDecimal getUseFee() {
        return useFee;
    }

    public void setUseFee(BigDecimal useFee) {
        this.useFee = useFee;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }
}
