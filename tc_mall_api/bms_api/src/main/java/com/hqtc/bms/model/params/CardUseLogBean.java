package com.hqtc.bms.model.params;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by wanghaoyang on 18-9-20.
 */
public class CardUseLogBean implements Serializable {
    private int operateType;
    private String orderSn;
    private String refundNo;
    private BigDecimal useFee;
    private String ctime;

    public int getOperateType() {
        return operateType;
    }

    public void setOperateType(int operateType) {
        this.operateType = operateType;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
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

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }
}
