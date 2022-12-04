package com.hqtc.bms.model.params;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * description:售后消息类
 * Created by laiqingchuang on 18-9-5 .
 */
public class MessageInfo implements Serializable {

    private String childTradeNo;   //子订单号
    private List<Integer> seqList; //序号集合
    private int size;              //服务单集合数量
    private Date startDate;        //开始时间

    public String getChildTradeNo() {
        return childTradeNo;
    }

    public void setChildTradeNo(String childTradeNo) {
        this.childTradeNo = childTradeNo;
    }

    public List<Integer> getSeqList() {
        return seqList;
    }

    public void setSeqList(List<Integer> seqList) {
        this.seqList = seqList;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
}
