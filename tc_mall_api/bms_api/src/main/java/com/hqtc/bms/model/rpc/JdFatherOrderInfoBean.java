package com.hqtc.bms.model.rpc;

import java.util.List;

/**
 * Created by wanghaoyang on 18-7-31.
 */
public class JdFatherOrderInfoBean {
    private JdChildOrderInfoBean pOrder;
    private int jdOrderState;
    private int orderState;
    private List<JdChildOrderInfoBean> cOrder;
    private int submitState;
    private int type;

    public JdChildOrderInfoBean getpOrder() {
        return pOrder;
    }

    public void setpOrder(JdChildOrderInfoBean pOrder) {
        this.pOrder = pOrder;
    }

    public int getJdOrderState() {
        return jdOrderState;
    }

    public void setJdOrderState(int jdOrderState) {
        this.jdOrderState = jdOrderState;
    }

    public int getOrderState() {
        return orderState;
    }

    public void setOrderState(int orderState) {
        this.orderState = orderState;
    }

    public List<JdChildOrderInfoBean> getcOrder() {
        return cOrder;
    }

    public void setcOrder(List<JdChildOrderInfoBean> cOrder) {
        this.cOrder = cOrder;
    }

    public int getSubmitState() {
        return submitState;
    }

    public void setSubmitState(int submitState) {
        this.submitState = submitState;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
