package com.hqtc.bms.model.params;

import java.io.Serializable;

/**
 * description:物流描述
 * Created by laiqingchuang on 18-7-13 .
 */
public class TrackDto implements Serializable {

    private String msgTime;   //日期
    private String content;   //内容信息
    private String operator;  //操作人

    public String getMsgTime() {
        return msgTime;
    }

    public void setMsgTime(String msgTime) {
        this.msgTime = msgTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}
